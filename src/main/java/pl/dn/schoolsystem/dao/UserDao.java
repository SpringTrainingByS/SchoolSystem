package pl.dn.schoolsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.dn.schoolsystem.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Long>{
	User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    User save(User user);
}

