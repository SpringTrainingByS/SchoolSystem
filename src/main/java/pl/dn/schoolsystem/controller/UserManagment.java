package pl.dn.schoolsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.dn.schoolsystem.jsonMapper.UserMapper;
import pl.dn.schoolsystem.model.User;

import pl.dn.schoolsystem.service.ResponseEntityService;
import pl.dn.schoolsystem.service.UserService;


@RestController
@RequestMapping("/admin")
public class UserManagment {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserManagment.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	ResponseEntityService responseEntityService;
	
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public ResponseEntity saveUser(@RequestBody UserMapper userMapper) {
		LOG.debug("URL: /user/add ");
		
		try {
			userService.addUser(userMapper);
		}
		catch (Exception e) {
			LOG.debug(e.getMessage());
			
			return responseEntityService.prepareErrorResponse(e.getMessage());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public @ResponseBody List<User> getAllUsers() {
		LOG.debug("URL: /user/all ");
		return userService.findUserList();
	}

}
