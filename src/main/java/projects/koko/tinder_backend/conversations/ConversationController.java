package projects.koko.tinder_backend.conversations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.ProfileRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Conversation createConversation(@RequestBody CreateConversationRequest request) {
        profileRepository.findById(request.profileId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + request.profileId()));
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        return conversation;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId) {
        return conversationRepository.findById(conversationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to find a conversation with ID " + conversationId));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId,
                                                 @RequestBody ChatMessage chatMessage) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Unable to find conversation with id " + conversationId));
        String matchProfileId = conversation.profileId();
        Profile profile = profileRepository.findById(matchProfileId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find profile with id " + matchProfileId));
        Profile user = profileRepository.findById(chatMessage.authorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find profile with id " + chatMessage.authorId()));

        //TODO: Need to validate that an author of the message happens to be only the profile associated
        //      with the message user

        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
//        conversation.messages().add(llmMessage);
        conversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        return conversation;
    }

}
