package uni.diploma.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EventCreateDto {

    @NotNull
    private Long creatorId;

    @Size(min = 3, message = "Title must be at least 3 characters")
    @Size(max = 50, message = "Title must be no more than 50 characters")
    @NotNull(message = "Come up with title")
    private String title;

    @Size(max = 300, message = "Description must be no more than 300 characters")
    private String description;

    private String imageUrl;

    @Size(min = 3, message = "Location must be at least 3 characters")
    private String location;

    @Future(message = "Date can't be in the past =)")
    @NotNull(message = "Set event date")
    private Date date;

    @NotNull(message = "Provide time for event")
    private Time time;

    @NotNull(message = "Choose a category")
    private String category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventCreateDto that = (EventCreateDto) o;
        return Objects.equals(creatorId, that.creatorId) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(imageUrl, that.imageUrl) && Objects.equals(location, that.location) && Objects.equals(date, that.date) && Objects.equals(time, that.time) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creatorId, title, description, imageUrl, location, date, time, category);
    }
}
