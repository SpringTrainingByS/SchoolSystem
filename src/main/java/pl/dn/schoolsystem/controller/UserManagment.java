package pl.dn.schoolsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.dn.schoolsystem.dao.RoleDao;
import pl.dn.schoolsystem.model.User;
import pl.dn.schoolsystem.model.security.Role;
import pl.dn.schoolsystem.service.UserService;

@RestController
@RequestMapping("/admin")
public class UserManagment {
	
	@Autowired
	RoleDao roleDao;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/role/all", method = RequestMethod.GET)
	public @ResponseBody List<Role> getRoles() {
		System.out.println("Pobieranie wszystkich możliwych ról.");
		List<Role> roles = roleDao.findAll();
		
		return roles;
	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public void saveUser(@RequestBody User user) {
		System.out.println("username" + user.getFirstName());
		System.out.println("lastname" + user.getLastName());
		userService.save(user);
	}

}
