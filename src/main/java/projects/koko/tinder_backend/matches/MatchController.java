package projects.koko.tinder_backend.matches;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projects.koko.tinder_backend.conversations.Conversation;
import projects.koko.tinder_backend.conversations.ConversationRepository;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.ProfileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final ProfileRepository profileRepository;
    private final ConversationRepository conversationRepository;
    private final MatchRepository matchRepository;

    public MatchController(final ProfileRepository profileRepository, final ConversationRepository conversationRepository, MatchRepository matchRepository) {
        this.profileRepository = profileRepository;
        this.conversationRepository = conversationRepository;
        this.matchRepository = matchRepository;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Match createMatch(@RequestBody CreateMatchRequest request) {
        Profile profile = profileRepository.findById(request.profileId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to find Profile with Id: " + request.profileId));

        //TODO: Make sure there are no existing conversations with this profile already
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                profile.id(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);

        Match match = new Match(
                UUID.randomUUID().toString(),
                profile, conversation.id()
        );
        matchRepository.save(match);
        return match;

    }

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    public record CreateMatchRequest(String profileId) {

    }
}
