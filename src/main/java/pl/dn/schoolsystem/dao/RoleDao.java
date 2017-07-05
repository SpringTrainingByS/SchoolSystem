package pl.dn.schoolsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.dn.schoolsystem.model.security.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, Integer> {
	Role findByName(String name);
	Role findByRoleId(int id);
	List<Role> findAll();
}
