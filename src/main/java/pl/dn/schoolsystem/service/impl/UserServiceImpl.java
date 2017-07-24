package pl.dn.schoolsystem.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.dn.schoolsystem.dao.RoleDao;
import pl.dn.schoolsystem.dao.UserDao;
import pl.dn.schoolsystem.dao.UserRoleDao;
import pl.dn.schoolsystem.jsonMapper.UserMapper;
import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.model.security.Role;
import pl.dn.schoolsystem.model.security.UserRole;
import pl.dn.schoolsystem.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	UserServiceMapper userServiceMapper;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void save(User user) {
		userDao.save(user);
	}
	
	public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
    
    public void addUser(UserMapper userMapper) throws Exception {
    	User user = userServiceMapper.persistence(userMapper);
    	
    	if (checkUserExists(user.getUsername(), user.getEmail())) {
    		LOG.debug("UserName already exists");
    		throw new Exception("Username already exists.");
    	}
    	
    	String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    	
		user = saveUser(user);
		
		int id = userMapper.getUserRoles().get(0).getRoleId();

		Role role = roleDao.findByRoleId(id);
		
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		
		userRoleDao.save(userRole);	
    }
    
    
    
    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }
        

        return false;
    }
    
    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    
    public List<User> findUserList() {
        return userDao.findAll();
    }
    
    public User saveUser(User user) {
        return userDao.save(user);
    }

	
}
