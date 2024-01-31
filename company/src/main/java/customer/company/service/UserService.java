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
import java.util.*;

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

    public UserResponse getUserById(Long userID) {
        User user = userRepository.findById(userID).orElse(null);

        if (user != null) {
            UserResponse response = new UserResponse();
            response.setUserId(user.getUserId());
            response.setFirst_name(user.getFirst_name());
            response.setLast_name(user.getLast_name());
            response.setStreet(user.getStreet());
            response.setAddress(user.getAddress());
            response.setCity(user.getCity());
            response.setState(user.getState());
            response.setEmail(user.getEmail());
            response.setPhone(user.getPhone());

            return response;
        } else {
            return null;
        }
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

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(user.getUserId());
            userResponse.setFirst_name(user.getFirst_name());  // Assuming these getter methods exist
            userResponse.setLast_name(user.getLast_name());
            userResponse.setStreet(user.getStreet());
            userResponse.setAddress(user.getAddress());
            userResponse.setCity(user.getCity());
            userResponse.setState(user.getState());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhone(user.getPhone());

            userResponses.add(userResponse);
        }
        return userResponses;
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

