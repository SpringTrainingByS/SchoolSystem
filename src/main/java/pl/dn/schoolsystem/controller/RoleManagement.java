package pl.dn.schoolsystem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.dn.schoolsystem.model.security.Role;
import pl.dn.schoolsystem.service.RoleService;

@RestController
@RequestMapping("/admin")
public class RoleManagement {
	
	private static final Logger LOG = LoggerFactory.getLogger(RoleManagement.class);
	
	@Autowired 
	RoleService roleService;
	
	@RequestMapping(value = "/role/all", method = RequestMethod.GET)
	public @ResponseBody List<Role> getRoles() {
		
		LOG.debug("URL: /role/all (GET)");
		return roleService.getAllRoles();
		
	}

}
