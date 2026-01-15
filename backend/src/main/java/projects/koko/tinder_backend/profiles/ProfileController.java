package projects.koko.tinder_backend.profiles;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    public ProfileController(ProfileRepository profileRepository, ProfileService profileService) {
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/random")
    public Profile getRandomProfile() {
        return profileRepository.getRandomProfile();
    }

    @GetMapping("/user")
    public Profile getUserProfile(){
        return profileService.getUserProfile();
    }

    // TODO add get profile by id because when you click again on the profile after opening a chat with them their profile will
    //be opened also with picture bio and names and ages and stuff
}
