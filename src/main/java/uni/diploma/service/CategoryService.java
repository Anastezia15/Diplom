package uni.diploma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uni.diploma.exceptions.AlreadyExistsException;
import uni.diploma.exceptions.CategoryNotFoundException;
import uni.diploma.exceptions.CategoryNotValidException;
import uni.diploma.model.Category;
import uni.diploma.model.Event;
import uni.diploma.model.dto.CategoryDto;
import uni.diploma.repository.CategoryRepository;
import uni.diploma.repository.EventRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public Category getWithEvents(Long id, int page, int size, boolean upcoming) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventsPage = upcoming ? eventRepository.findUpcomingEventsByCategory(category, pageable) :
                eventRepository.findByCategory(category, pageable);

        category.setEvents(new HashSet<>(eventsPage.getContent()));

        return category;
    }


    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }

    public Category saveCategory(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new CategoryNotValidException("Invalid category data: " + ex.getMessage());
        }
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new AlreadyExistsException("This category is already registered");
        } else {
            return saveCategory(category);
        }
    }

    public Category updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category categoryFromDb = getCategoryById(categoryId);

        if (categoryDto.getName() != null &&
                checkNameUpdate(categoryFromDb.getName(), categoryDto.getName())) {
            categoryFromDb.setName(categoryDto.getName());
        }

        return saveCategory(categoryFromDb);
    }

    public boolean checkNameUpdate(String nameFromDb, String updatedName) {
        if (!updatedName.equals(nameFromDb) && categoryRepository.existsByName(updatedName)) {
            throw new AlreadyExistsException("This name is already registered");
        }
        return true;
    }

    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new CategoryNotFoundException(id);
        }
    }
}