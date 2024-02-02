package customer.company.service;

import customer.company.dto.AuthRequest;
import customer.company.dto.AuthResponse;
import customer.company.dto.UserDTO;
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

    public UserDTO getUserById(Long userID) {
        User user = userRepository.findById(userID).orElse(null);

        if (user != null) {
            UserDTO response = new UserDTO();
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

    public UserDTO createUser(User user) {
        // Encode the password
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        // Save the user
        User savedUser = userRepository.save(user);//add new user to the repository

        // Create and return the UserResponse
        UserDTO response = new UserDTO();
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

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userResponses = new ArrayList<>();

        for (User user : users) {
            UserDTO userResponse = new UserDTO();
            userResponse.setUserId(user.getUserId());
            userResponse.setFirst_name(user.getFirst_name());
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
public UserDTO updateUser(Long userID, UserDTO userDTO) {
    if (userRepository.existsById(userID)) {
        // Fetching the existing user entity from the database
        User existingUser = userRepository.findById(userID).orElse(null);

        // Updating the attributes of the existing user entity with the values from the DTO
        if (existingUser != null) {
            existingUser.setFirst_name(userDTO.getFirst_name());
            existingUser.setLast_name(userDTO.getLast_name());
            existingUser.setStreet(userDTO.getStreet());
            existingUser.setAddress(userDTO.getAddress());
            existingUser.setCity(userDTO.getCity());
            existingUser.setState(userDTO.getState());
            existingUser.setEmail(userDTO.getEmail());
            existingUser.setPhone(userDTO.getPhone());

            // Saving the updated user entity
            User updatedUser = userRepository.save(existingUser);

            // Converting  the updated user entity to UserDTO
            UserDTO updatedUserDTO = new UserDTO();
            updatedUserDTO.setUserId(updatedUser.getUserId());
            updatedUserDTO.setFirst_name(updatedUser.getFirst_name());
            updatedUserDTO.setLast_name(updatedUser.getLast_name());
            updatedUserDTO.setStreet(updatedUser.getStreet());
            updatedUserDTO.setAddress(updatedUser.getAddress());
            updatedUserDTO.setCity(updatedUser.getCity());
            updatedUserDTO.setState(updatedUser.getState());
            updatedUserDTO.setEmail(updatedUser.getEmail());
            updatedUserDTO.setPhone(updatedUser.getPhone());

            return updatedUserDTO;
        }
    }
    return null;
}


    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(()->new ApplicationException(HttpStatus.FORBIDDEN.value(), "User Not Found",HttpStatus.FORBIDDEN));
    }
}

