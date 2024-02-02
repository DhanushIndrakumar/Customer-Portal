package customer.company.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import customer.company.dto.AuthRequest;
import customer.company.dto.UserDTO;
import customer.company.entities.User;
import customer.company.exceptions.ApplicationException;
import customer.company.repositories.UserRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  /**
   * Method under test: {@link UserService#login(AuthRequest)}
   */
  @Test
  void testLogin() {
    // Arrange
    when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any()))
            .thenThrow(new ApplicationException(-1, "An error occurred", HttpStatus.CONTINUE));

    User user = new User();
    user.setAddress("24th Main");
    user.setCity("Oxford");
    user.setEmail("john@example.com");
    user.setFirst_name("John");
    user.setLast_name("Loyal");
    user.setPassword("securePassword");
    user.setPhone("6625550144");
    user.setState("California");
    user.setStreet("Church Street");
    user.setUserId(1L);
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
    AuthRequest request = AuthRequest.builder().email("john@example.com").password("securePassword").build();

    // Act and Assert
    assertThrows(ApplicationException.class, () -> userService.login(request));
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
  }

  /**
   * Method under test: {@link UserService#login(AuthRequest)}
   */
  @Test
  void testLogin2() {

    Optional<User> emptyResult = Optional.empty();
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
    AuthRequest request = AuthRequest.builder().email("test@example.org").password("test").build();


    assertThrows(ApplicationException.class, () -> userService.login(request));
    verify(userRepository).findByEmail(Mockito.<String>any());
  }

  /**
   * Method under test: {@link UserService#getUserById(Long)}
   */
  @Test
  void getUserById() {
    // Arrange
    User user = new User();
    user.setAddress("24th Main");
    user.setCity("Oxford");
    user.setEmail("john@example.com");
    user.setFirst_name("John");
    user.setLast_name("Loyal");
    user.setPassword("securePassword");
    user.setPhone("6625550144");
    user.setState("California");
    user.setStreet("Church Street");
    user.setUserId(1L);
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

    // Act
    UserDTO actualUserById = userService.getUserById(1L);

    // Assert
    verify(userRepository).findById(Mockito.<Long>any());
    assertEquals("24th Main", actualUserById.getAddress());
    assertEquals("6625550144", actualUserById.getPhone());
    assertEquals("Loyal", actualUserById.getLast_name());
    assertEquals("John", actualUserById.getFirst_name());
    assertEquals("California", actualUserById.getState());
    assertEquals("Oxford", actualUserById.getCity());
    assertEquals("Church Street", actualUserById.getStreet());
    assertEquals("john@example.com", actualUserById.getEmail());
    assertEquals(1L, actualUserById.getUserId().longValue());
  }

  /**
   * Method under test: {@link UserService#getUserById(Long)}
   */
  @Test
  void getUserById2() {

    Optional<User> emptyResult = Optional.empty();
    when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);


    UserDTO actualUserById = userService.getUserById(1L);


    verify(userRepository).findById(Mockito.<Long>any());
    assertNull(actualUserById);
  }

  /**
   * Method under test: {@link UserService#getUserById(Long)}
   */
  @Test
  void getUserById3() {

    when(userRepository.findById(Mockito.<Long>any()))
            .thenThrow(new ApplicationException(-1, "An error occurred", HttpStatus.CONTINUE));


    assertThrows(ApplicationException.class, () -> userService.getUserById(1L));
    verify(userRepository).findById(Mockito.<Long>any());
  }

  /**
   * Method under test: {@link UserService#createUser(User)}
   */
  @Test
  void testCreateUser() {
    // Arrange
    when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

    User user = new User();
    user.setAddress("24th Main");
    user.setCity("Oxford");
    user.setEmail("john@example.com");
    user.setFirst_name("John");
    user.setLast_name("Loyal");
    user.setPassword("securePassword");
    user.setPhone("6625550144");
    user.setState("California");
    user.setStreet("Church Street");
    user.setUserId(1L);
    when(userRepository.save(Mockito.<User>any())).thenReturn(user);

    User user2 = new User();
    user2.setAddress("Little Hearts Colony");
    user2.setCity("Seatle");
    user2.setEmail("Nily@example.com");
    user2.setFirst_name("Nily");
    user2.setLast_name("Jose");
    user2.setPassword("securePassword");
    user2.setPhone("6625550144");
    user2.setState("North America");
    user2.setStreet("Merry Street");
    user2.setUserId(1L);

    // Act
    UserDTO actualCreateUserResult = userService.createUser(user);

    // Assert
    verify(userRepository).save(Mockito.<User>any());
    verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    assertEquals("24th Main", actualCreateUserResult.getAddress());
    assertEquals("6625550144", actualCreateUserResult.getPhone());
    assertEquals("Loyal", actualCreateUserResult.getLast_name());
    assertEquals("John", actualCreateUserResult.getFirst_name());
    assertEquals("California", actualCreateUserResult.getState());
    assertEquals("Oxford", actualCreateUserResult.getCity());
    assertEquals("Church Street", actualCreateUserResult.getStreet());
    assertEquals("john@example.com", actualCreateUserResult.getEmail());
    assertEquals("securePassword", user2.getPassword());
    assertEquals(1L, actualCreateUserResult.getUserId().longValue());
  }

  /**
   * Method under test: {@link UserService#createUser(User)}
   */
  @Test
  void testCreateUser2() {

    when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
    when(userRepository.save(Mockito.<User>any()))
            .thenThrow(new ApplicationException(-1, "An error occurred", HttpStatus.CONTINUE));

    User user = new User();
    user.setAddress("24 Church colony");
    user.setCity("New Jersey");
    user.setEmail("Michaelajay@example.com");
    user.setFirst_name("Michael");
    user.setLast_name("Ajay");
    user.setPassword("securePassword");
    user.setPhone("6625550144");
    user.setState("California");
    user.setStreet("John Street");
    user.setUserId(1L);


    assertThrows(ApplicationException.class, () -> userService.createUser(user));
    verify(userRepository).save(Mockito.<User>any());
    verify(passwordEncoder).encode(Mockito.<CharSequence>any());
  }

  /**
   * Method under test: {@link UserService#deleteUser(Long)}
   */
  @Test
  void deleteUser() {
    // Arrange
    doNothing().when(userRepository).deleteById(Mockito.<Long>any());

    // Act
    userService.deleteUser(1L);

    // Assert that nothing has changed
    verify(userRepository).deleteById(Mockito.<Long>any());
    assertTrue(userService.getAllUsers().isEmpty());
  }

  /**
   * Method under test: {@link UserService#deleteUser(Long)}
   */
  @Test
  void deleteUser2() {

    doThrow(new ApplicationException(-1, "An error occurred", HttpStatus.CONTINUE)).when(userRepository)
            .deleteById(Mockito.<Long>any());


    assertThrows(ApplicationException.class, () -> userService.deleteUser(1L));
    verify(userRepository).deleteById(Mockito.<Long>any());
  }


}

