package projects.koko.tinder_backend.profiles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
@AllArgsConstructor
public class Profile {
    @Id
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
