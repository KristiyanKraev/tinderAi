package projects.koko.tinder_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import projects.koko.tinder_backend.profiles.ProfileCreationService;

@SpringBootApplication

public class TinderBackendApplication implements CommandLineRunner {

	private ProfileCreationService profileCreationService;

	public static void main(String[] args) {
		SpringApplication.run(TinderBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		profileCreationService.saveProfilesToDB();

	}
}
