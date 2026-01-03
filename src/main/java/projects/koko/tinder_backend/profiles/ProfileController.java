package projects.koko.tinder_backend.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/random")
    public Profile getRandomProfile() {
        return profileRepository.getRandomProfile();
    }

    // TODO add get profile by id because when you click again on the profile after opening a chat with them their profile will
    //be opened also with picture bio and names and ages and stuff
}
