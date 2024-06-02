package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.User;

public interface UserRepository extends CrudRepository<User, String>{
	User findByUsername(@Param("name") String name);
}
