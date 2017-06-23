package pl.dn.schoolsystem.dao;

import org.springframework.data.repository.CrudRepository;

import pl.dn.schoolsystem.model.security.Role;

public interface RoleDao extends CrudRepository<Role, Integer> {
	Role findByName(String name);
}
