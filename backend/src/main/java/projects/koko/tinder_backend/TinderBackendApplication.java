package projects.koko.tinder_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import projects.koko.tinder_backend.conversations.ConversationRepository;
import projects.koko.tinder_backend.matches.MatchRepository;
import projects.koko.tinder_backend.profiles.ProfileCreationService;
import projects.koko.tinder_backend.profiles.ProfileRepository;

@SpringBootApplication

public class TinderBackendApplication implements CommandLineRunner {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ProfileCreationService profileCreationService;


    public static void main(String[] args) {
        SpringApplication.run(TinderBackendApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
//        profileCreationService.createProfiles(0);
//        clearAllData();
        profileCreationService.saveProfilesToDB();

    }

    private void clearAllData() {
        conversationRepository.deleteAll();
        matchRepository.deleteAll();
        profileRepository.deleteAll();
    }
}
