package customer.company.service;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.entities.User;
import customer.company.exceptions.ApplicationException;
import customer.company.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;



    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN));
        boolean matches = encoder.matches(request.getPassword(), user.getPassword());

        if(matches){
            var jwtToken = generateToken(user);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        throw new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ) {
        return buildToken(extraClaims, userDetails, 1000*60*1000);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode("77397A244326462948404D635166546A576E5A7234753778214125442A472D4B");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User getUserById(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public User createUser(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(()->new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN));
    }
}

