package projects.koko.tinder_backend.profiles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import projects.koko.tinder_backend.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // null for ai profiles

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ethnicity")
    private String ethnicity;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "mbp_type")
    private String myersBriggsPersonalityType;

    @Column(name = "is_ai")
    private Boolean isAi;

    @Column(name = "is_active")
    private Boolean isActive;

//    @Column(name = "created_at")
//    private LocalDateTime createdAt = LocalDateTime.now();

    public Profile(){
        this.isAi = true;
    }

    public Profile(User user){
        this.user = user;
        this.isAi = false;
    }

    public Profile(String id, User user, String firstName, String lastName, Integer age, String ethnicity, Gender gender, String bio, String imageUrl, String myersBriggsPersonalityType) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.ethnicity = ethnicity;
        this.gender = gender;
        this.bio = bio;
        this.imageUrl = imageUrl;
        this.myersBriggsPersonalityType = myersBriggsPersonalityType;
    }
}
