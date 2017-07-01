package pl.dn.schoolsystem.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.dn.schoolsystem.model.security.UserRole;

@Repository
public interface UserRoleDao extends CrudRepository<UserRole, Long> {
	UserRole save(UserRole userRole);
}
