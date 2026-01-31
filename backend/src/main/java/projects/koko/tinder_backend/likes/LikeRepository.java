package projects.koko.tinder_backend.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,String> {
    @Query("SELECT l FROM Like l WHERE l.liker.id = :likerId AND l.liked.id = :likedId")
    Optional<Like> findByLikerAndLiked(@Param("likerId") String likerId, @Param("likedId") String likedId);


    boolean existsByLikerIdAndLikedId(String likerId, String likedId);

    @Query("SELECT COUNT(l) > 0 FROM Like l WHERE l.liker.id = :profile2Id AND l.liked.id = :profile1Id")
    boolean isMatch(@Param("profile1Id") String profile1Id, @Param("profile2Id") String profile2Id);
}
