package projects.koko.tinder_backend.matches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository  extends JpaRepository<Match, String> {

    @Query("SELECT m FROM Match m WHERE m.profileOne.id =:profileOneId AND m.profileTwo.id =: profileTwoId")
    Optional<Match> findMatchBetweenProfiles(@Param("profileOneId") String profileOneId,
                                             @Param("profileTwoId") String profileTwoId);

    @Query("SELECT m FROM Match m WHERE m.profileOne.id =:profileId OR m.profileTwo.id =:profileId")
    List<Match> findAllMatchesForProfile(@Param("profileId") String profileId);

//    boolean existsByProfileOneAndProfileTwo(String profileOneId, String profileTwoId);
//
//    default  boolean existsMatchBetween(String profileOneId, String profileTwoId){
//        return existsByProfileOneAndProfileTwo(profileOneId, profileTwoId) ||
//                existsByProfileOneAndProfileTwo(profileTwoId, profileOneId);
//    }

}
