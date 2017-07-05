package pl.dn.schoolsystem.service.impl;

import org.springframework.stereotype.Service;

import pl.dn.schoolsystem.jsonMapper.UserMapper;
import pl.dn.schoolsystem.model.User;

@Service
public class UserServiceMapper {
	
	public User persistence(UserMapper userMapper) {
		User user = new User();
		
		user.setEmail(userMapper.getEmail());
		user.setEnabled(true);
		user.setFirstName(userMapper.getFirstName());
		user.setLastName(userMapper.getLastName());
		user.setPassword(userMapper.getPassword());
		user.setPhone(userMapper.getPhone());
		user.setUsername(userMapper.getUsername());
		
		return user;
	}

}
