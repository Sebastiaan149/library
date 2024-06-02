package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Location;

public interface LocationRepository extends CrudRepository<Location, String>{

	//TODO
	boolean locationAlreadyExists(@Param("code1") String code1, @Param("code2") String code2, @Param("name") String name);

}
