package projects.koko.tinder_backend.profiles.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import projects.koko.tinder_backend.profiles.Profile;
import projects.koko.tinder_backend.profiles.dto.ProfileRequest;
import projects.koko.tinder_backend.profiles.dto.ProfileResponse;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponse toProfileResponse(Profile profile);

    @Mapping(target="id", ignore=true)
    Profile toProfileFromRequest(ProfileRequest request);

    @Mapping(target="id", ignore=true)
    void updateProfileFromRequest(ProfileRequest request, @MappingTarget Profile profile);
}
