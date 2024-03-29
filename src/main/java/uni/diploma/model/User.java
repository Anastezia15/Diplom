package uni.diploma.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "username", nullable = false, length = 50)
    @NotNull(message = "Provide a username")
    @Size(min = 3, message = "Username must be at least 3 characters")
    @Size(max = 50, message = "Username must be no more than 50 characters")
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    @NotNull(message = "Provide a password")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Size(max = 255, message = "Password must be no more than 255 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).+$", message = "Password must have at least 1 number and 1 letter")
    private String password;

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    @NotNull(message = "Provide an email")
    @Email(message = "Provide a valid email")
    private String email;

    @Basic
    @Column(name = "first_name", length = 50)
    @Size(min = 3, message = "First name must be at least 3 characters")
    @Size(max = 50, message = "First name must be no more than 50 characters")
    private String firstName;

    @Basic
    @Column(name = "last_name", length = 50)
    @Size(min = 3, message = "Last name must be at least 3 characters")
    @Size(max = 50, message = "Last name must be no more than 50 characters")
    private String lastName;

    @Basic
    @Column(name = "date_of_birth")
    @Past(message = "Birth date must be in the past")
    private Date dateOfBirth;

    @Basic
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private String role;

    @ManyToMany
    @JoinTable(
            name = "user_event_subscription",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    @JsonIgnore
    private Set<Event> eventSubscriptionList;


    public User(Long id, @NotNull String username, @NotNull String password, @NotNull String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // For testing purposes only
    public User(@NotNull String username, @NotNull String password, @NotNull String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}


