package pl.dn.schoolsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.dn.schoolsystem.dao.RoleDao;
import pl.dn.schoolsystem.dao.UserRoleDao;
import pl.dn.schoolsystem.jsonMapper.UserMapper;
import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.model.security.Role;
import pl.dn.schoolsystem.model.security.UserRole;
import pl.dn.schoolsystem.service.UserService;
import pl.dn.schoolsystem.service.impl.UserServiceMapper;

@RestController
@RequestMapping("/admin")
public class UserManagment {
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserServiceMapper userServiceMapper;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@RequestMapping(value = "/role/all", method = RequestMethod.GET)
	public @ResponseBody List<Role> getRoles() {
		System.out.println("Pobieranie wszystkich możliwych ról.");
		List<Role> roles = roleDao.findAll();
		
		Role roleToRemove = roles.stream().filter(role -> role.getName().equals("ROLE_ADMIN")).findFirst().get();
		roles.remove(roleToRemove);
		
		return roles;
	}
	
	// consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public void saveUser(@RequestBody UserMapper userMapper) {
		
		System.out.println(userMapper.toString());
		
		User user = userServiceMapper.persistence(userMapper);
		user = userService.saveUser(user);
		
		int id = userMapper.getUserRoles().get(0).getRoleId();
		System.out.println("Pobranie id dla roli zakończone sukcesem");
		Role role = roleDao.findByRoleId(id);
		
		System.out.println("Pobranie roli zakończone sukcesem");
		
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		
		System.out.println("Przygotowanie userRole zakończone sukcesem");
		
		userRoleDao.save(userRole);	
		
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers() {
		return userService.findUserList();
	}

}
