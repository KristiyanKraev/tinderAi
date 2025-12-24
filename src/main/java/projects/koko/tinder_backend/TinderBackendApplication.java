package projects.koko.tinder_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import projects.koko.tinder_backend.conversations.ChatMessage;
import projects.koko.tinder_backend.conversations.Conversation;
import projects.koko.tinder_backend.conversations.ConversationRepository;
import projects.koko.tinder_backend.profiles.Gender;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.ProfileRepository;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication

public class TinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		Profile profile = new Profile(
				"1",
				"Koko",
				"Kraev",
				22,
				"Bulgarian",
				Gender.MALE,
				"Gym dude",
				"foo.jpg",
				"HTN"

		);Profile profile2 = new Profile(
				"2",
				"Foo",
				"Bar",
				22,
				"Bulgarian",
				Gender.MALE,
				"Gym dude",
				"foo.jpg",
				"HTN"

		);

		profileRepository.save(profile);
		profileRepository.save(profile2);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
				"1",
				profile.id(),
				List.of(new ChatMessage("Hello",profile.id(), LocalDateTime.now()))
		);
		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
