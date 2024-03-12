package uni.diploma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.diploma.exceptions.AlreadyExistsException;
import uni.diploma.exceptions.UserNotFoundException;
import uni.diploma.model.Role;
import uni.diploma.model.User;
import uni.diploma.model.dto.UserDto;
import uni.diploma.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceUpdateValidation {
    private  final UserRepository userRepository;
    private final PasswordEncoder encoder;
    User userFromDb;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUserById(Long userId){

        return userRepository.findById(userId).orElseThrow();
    }
    public User getUser(String username){

        return userRepository.findByUsername(username).orElseThrow();
    }

    public User updateUser(Long userId, UserDto userDto){
        userFromDb = getUserById(userId);
        if(checkEmail(userDto)) userFromDb.setEmail(userDto.getEmail());
        if(checkUsername(userDto)) userFromDb.setUsername(userDto.getUsername());
        if (checkPassword(userDto)) userFromDb.setPassword(encoder.encode(userDto.getPassword()));
        if (!userDto.getFirstName().equals(userFromDb.getFirstName())) userFromDb.setFirstName(userDto.getFirstName());
        if (!userDto.getLastName().equals(userFromDb.getLastName())) userFromDb.setLastName(userDto.getLastName());
        if (!userDto.getDateOfBirth().equals(userFromDb.getDateOfBirth())) userFromDb.setDateOfBirth(userDto.getDateOfBirth());

        return userRepository.save(userFromDb);
    }

    public User createUser(User user) {
        boolean ifUsernameExists = getAllUsers().stream().anyMatch(userFromList -> userFromList.getUsername().equals(user.getUsername()));
        if(ifUsernameExists) throw new AlreadyExistsException("Such user already exists");

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER.getRoleName());
        return userRepository.save(user);
    }
    public User createAdmin(User user) {
        boolean ifUsernameExists = getAllUsers().stream().anyMatch(userFromList -> userFromList.getUsername().equals(user.getUsername()));
        if(ifUsernameExists) throw new AlreadyExistsException("Such admin already exists");

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN.getRoleName());
        return userRepository.save(user);
    }

    @Override
    public boolean checkEmail(UserDto userDto) {
        return userDto.getEmail() != null &&
                !userDto.getEmail().equals(userFromDb.getEmail()) &&
                userRepository.existsByEmail(userFromDb.getEmail());
    }

    @Override
    public boolean checkUsername(UserDto userDto) {
        return userDto.getUsername() != null &&
                !userDto.getUsername().equals(userFromDb.getUsername()) &&
                userRepository.existsByUsername(userFromDb.getUsername());
    }

    @Override
    public boolean checkPassword(UserDto userDto) {
        return !userDto.getPassword().equals(userFromDb.getPassword());
    }

    public void deleteUser(final Long id)  {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }
}
