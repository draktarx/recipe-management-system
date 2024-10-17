package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.AuthUser;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AuthUser, Long> {

    Optional<AuthUser> findByEmail(String email);

    boolean existsByEmail(String email);

}
