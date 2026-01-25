package projects.koko.tinder_backend.profiles.dto;

import lombok.Getter;
import lombok.Setter;
import projects.koko.tinder_backend.profiles.Gender;


@Getter
@Setter
public class ProfileResponse {
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String ethnicity;
    private Gender gender;
    private String bio;
    private String imageUrl;
    private String myersBriggsPersonalityType;
}
