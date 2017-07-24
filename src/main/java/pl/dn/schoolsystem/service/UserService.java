package pl.dn.schoolsystem.service;

import java.util.List;
import java.util.Set;

import pl.dn.schoolsystem.jsonMapper.UserMapper;
import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.model.security.UserRole;

public interface UserService {
	User findByUsername(String username);

    User findByEmail(String email);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
    
    void addUser(UserMapper userMapper) throws Exception;
    
    void save (User user);
    
    User saveUser (User user); 
    
    List<User> findUserList();
}
