package projects.koko.tinder_backend.profiles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ethnicity")
    private String ethnicity;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "bio")
    private String bio;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "MBP_Type")
    private String myersBriggsPersonalityType;
}
