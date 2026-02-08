package projects.koko.tinder_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import projects.koko.tinder_backend.profiles.Gender;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String ethnicity;
    private Gender gender;
    private String bio;
    private String imageUrl;
    private String myersBriggsPersonalityType;
    private boolean isAi;
    private boolean isActive;
//    private LocalDateTime createdAt;
}
