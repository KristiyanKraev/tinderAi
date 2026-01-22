package projects.koko.tinder_backend.profiles;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    @Aggregation(pipeline = {"{ $sample:  { size: 1 } }"})
    Optional<Profile> getRandomProfile();

}
