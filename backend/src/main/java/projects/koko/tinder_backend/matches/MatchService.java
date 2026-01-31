package projects.koko.tinder_backend.matches;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import projects.koko.tinder_backend.likes.Like;
import projects.koko.tinder_backend.likes.LikeRepository;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.ProfileRepository;

import java.util.List;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final LikeRepository likeRepository;
    private final ProfileRepository profileRepository;


    public MatchService(MatchRepository matchRepository, LikeRepository likeRepository, ProfileRepository profileRepository) {
        this.matchRepository = matchRepository;
        this.likeRepository = likeRepository;
        this.profileRepository = profileRepository;
    }

    /**
     * Handles the like action between two profiles and creates a match if it's mutual
     * @param likerId The ID of the profile that is liking
     * @param likedId The ID of the profile being liked
     * @return The created Match if it's a mutual like, null otherwise
     */
    @Transactional
    public Match createMatch(String likerId, String likedId){

        // Prevent self-liking
        if (likerId.equals(likedId)) {
            throw new IllegalArgumentException("Profile can't like itself");
        }
        // Check if the like already exists
        if(likeRepository.existsByLikerIdAndLikedId(likerId, likedId)){
            throw new RuntimeException("Like exists between liker: " + likerId + " and liked: " + likedId);
        }
        // Check if a match already exists
        if(matchRepository.existsMatchBetween(likerId, likedId)){
            throw new RuntimeException("Match exists between profiles");
        }
        // Get the profiles
        Profile liker = profileRepository.findById(likerId).
                orElseThrow(() -> new RuntimeException("Liker not found"));
        Profile liked = profileRepository.findById(likedId).
                orElseThrow(() -> new RuntimeException("Liked not found"));

        // Create and save the like
        Like like = new Like();
        like.setLiker(liker);
        like.setLiked(liked);
        //if its an ai profile create a like immediately
        if(liked.getIsAi()){
            Like aiLike = new Like();
            aiLike.setLiker(liker);
            aiLike.setLiker(liked);
            aiLike.setMatch(true);
            likeRepository.save(like);
            likeRepository.save(aiLike);

            //create and return the match
            Match match = new Match(liker, liked);
            matchRepository.save(match);

        }
        else{
            //for regular profiles check for mutual like
            boolean isMutualLike = likeRepository.isMatch(likerId, likedId);
            like.setMatch(isMutualLike);
            likeRepository.save(like);

            //if mutual like, create  a match
            if(isMutualLike){
                Match match = new Match(liker, liked);
                return matchRepository.save(match);
            }
        }
        return null;
    }

    /**
     * Get all matches for a given profile
     * @param profileId The ID of the profile
     * @return List of matches for the profile
     */
    public List<Match> getAllMatchesForProfile(String profileId){
        return matchRepository.findAllMatchesForProfile(profileId);
    }

    /**
     * Check if two profiles have matched
     * @param profile1Id First profile ID
     * @param profile2Id Second profile ID
     * @return true if the profiles have matched, false otherwise
     */
    public boolean haveMatched(String profile1Id, String profile2Id) {
        return matchRepository.existsMatchBetween(profile1Id, profile2Id);
    }

    public void deleteMatchById(String matchId) {
        matchRepository.deleteById(matchId);
    }
}
