package projects.koko.tinder_backend.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projects.koko.tinder_backend.profiles.dto.ProfileRequest;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;
import projects.koko.tinder_backend.profiles.mapper.ProfileMapper;
import projects.koko.tinder_backend.utils.ProfileNotFoundException;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ProfileService(ProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    public ProfileResponse getProfileById(String id){
        return profileRepository.findById(id)
                .map(profileMapper::toProfileResponse)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id : " + id));
    }
    public ProfileResponse getRandomProfile(){
        return profileRepository.getRandomProfile()
                .map(profileMapper::toProfileResponse)
                .orElseThrow(() -> new ProfileNotFoundException("No profiles available "));
    }

    public ProfileResponse createProfile(ProfileRequest request){
        Profile newProfile = profileMapper.toProfileFromRequest(request);
        Profile savedProfile = profileRepository.save(newProfile);
        return profileMapper.toProfileResponse(savedProfile);
    }

    public ProfileResponse updateUserProfile(String id, ProfileRequest request){
        return profileRepository.findById(id)
                .map(profile -> {
                    profileMapper.updateProfileFromRequest(request,profile);
                    Profile updatedProfile = profileRepository.save(profile);
                    return profileMapper.toProfileResponse(updatedProfile);
                })
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with id : " + id));
    }

    public void deleteProfileById(String id){
        if (!profileRepository.existsById(id)) {
            throw new ProfileNotFoundException("Profile not found with id : " + id);
        }
        profileRepository.deleteById(id);
    }
}
