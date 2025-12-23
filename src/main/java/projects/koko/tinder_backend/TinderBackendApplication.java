package projects.koko.tinder_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import projects.koko.tinder_backend.profiles.Gender;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.ProfileRepository;

@SpringBootApplication

public class TinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
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

		);

		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);
	}
}
