package uni.diploma.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uni.diploma.model.Category;
import uni.diploma.model.Event;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CategoryWithEventsDto {
    private Long id;
    private String name;
    private Set<Event> events;

    public CategoryWithEventsDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.events = category.getEvents();
    }
}
