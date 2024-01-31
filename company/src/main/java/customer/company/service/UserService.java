package customer.company.service;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.dto.UserResponse;
import customer.company.entities.User;
import customer.company.exceptions.ApplicationException;
import customer.company.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.secret}")
    private  String SECRET;



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
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User getUserById(Long userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public UserResponse createUser(User user) {
        // Encode the password
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        // Save the user
        User savedUser = userRepository.save(user);

        // Create and return the RegisterResponse
        UserResponse response = new UserResponse();
        response.setUserId(savedUser.getUserId());
        response.setFirst_name(savedUser.getFirst_name());
        response.setLast_name(savedUser.getLast_name());
        response.setStreet(savedUser.getStreet());
        response.setAddress(savedUser.getAddress());
        response.setCity(savedUser.getCity());
        response.setState(savedUser.getState());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());

        return response;
    }
    public void deleteUser(Long userID) {
        userRepository.deleteById(userID);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long userID, User user) {
        if (userRepository.existsById(userID)) {
            user.setUserId(userID);
            return userRepository.save(user);
        }
        return null; // Handle not found scenario
    }

    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(()->new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN));
    }
}

