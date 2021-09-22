package application.repository;

import application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
 /*
    Optional<User> findByUsernameOrEmail(String username, String email);


    @Query("SELECT u FROM users u WHERE u.name = :new_name AND u.email = :new_email")
    List<User> findUserByNameAndEmail(@Param("new_name") String new_name, @Param("new_email") String new_email);
*/
}