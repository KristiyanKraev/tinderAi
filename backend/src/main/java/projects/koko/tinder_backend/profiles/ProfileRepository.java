package projects.koko.tinder_backend.profiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
    @Query("SELECT p FROM Profile p ORDER BY RANDOM() LIMIT 1")
    Optional<Profile> getRandomProfile();
    
    @Query("SELECT p FROM Profile p WHERE p.user.id = :userId")
    Optional<Profile> findByUserId(String userId);
}
