package projects.koko.tinder_backend.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.koko.tinder_backend.utils.ProfileNotFoundException;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile getUserProfile(){
        return profileRepository.findById("user").orElseThrow(() -> new ProfileNotFoundException("User profile not found"));
    }

    // TODO
    public Profile updateUserProfile(Profile profile){

    }

}
