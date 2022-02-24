package au.com.asx.demo.repository;

import au.com.asx.demo.entity.UserDetail;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDetail, Integer> {

}
