package pl.dn.schoolsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dn.schoolsystem.dao.RoleDao;
import pl.dn.schoolsystem.model.security.Role;

@Service
public class RoleService {
	
	@Autowired
	RoleDao roleDao;
	
	public List<Role> getAllRoles() {
		
		List<Role> roles = roleDao.findAll();
		roles = removeAdminRole(roles);
		
		return roles;
	}
	
	private List<Role> removeAdminRole(List<Role> roles) {
		
		Role roleToRemove = roles.stream().filter(role -> role.getName().equals("ROLE_ADMIN")).findFirst().get();
		roles.remove(roleToRemove);
		
		return roles;
	}
	
	

}
