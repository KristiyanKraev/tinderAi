package projects.koko.tinder_backend.profiles.dto;

import lombok.Data;
import projects.koko.tinder_backend.profiles.Gender;

import java.time.LocalDateTime;

@Data
public class ProfileResponse {
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Gender gender;
    private String ethnicity;
    private String bio;
    private String myersBriggsPersonalityType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
