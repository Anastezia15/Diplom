package uni.diploma.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
@RequiredArgsConstructor
@Table(name="event")
public class Event {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "creator_id")
    private Long creatorId;

    @Basic
    @Column(name = "title", nullable = false)
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Size(max = 50, message = "Title must be no more than 50 characters")
    private String title;

    @Basic
    @Column(name = "description")
    @Size(max = 300, message = "Description must be no more than 300 characters")
    private String description;

    @Basic
    @Column(name = "image_url")
    private String imageUrl;

    @Basic
    @Column(name = "location", nullable = false)
    @Size(min = 3, message = "Location must be at least 3 characters")
    private String location;

    @Basic
    @Column(name = "date", nullable = false)
    @Future(message = "Date can't be in the past =) ")
    private Date date;

    @Basic
    @Column(name = "time", nullable = false)
    @NotNull(message = "Provide time for event")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @NotNull
    private Category category;


    @ManyToMany
    @JoinTable(
            name = "event_user_subscription",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIgnore
    private Set<User> userSubscriptionList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return getId() != null && Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
