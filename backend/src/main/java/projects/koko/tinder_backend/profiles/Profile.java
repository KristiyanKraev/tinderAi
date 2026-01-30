package projects.koko.tinder_backend.profiles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import projects.koko.tinder_backend.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile extends BaseProfile{

    @OneToOne
    @JoinColumn(name="user_id")
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
    private Gender gender;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "mbp_type")
    private String myersBriggsPersonalityType;

    @Column(name = "is_ai")
    private boolean isAi;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    public Profile(){
        this.isAi = true;
    }

    public Profile(User user){
        this.user = user;
        this.isAi = false;
    }
}
