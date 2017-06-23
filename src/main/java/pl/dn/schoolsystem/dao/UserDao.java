package pl.dn.schoolsystem.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import pl.dn.schoolsystem.model.User;

@Component
public interface UserDao extends CrudRepository<User, Long>{
	User findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    User save(User user);
}

