package com.smalaca.gtd.projectmanagements.query.idea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagements.query.idea.IdeaReadModelAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Tag("IntegrationTest")
class IdeaQueryServiceTest {
    @Autowired private IdeaQueryRepository repository;
    private IdeaQueryService service;

    private final List<UUID> ids = new ArrayList<>();

    @BeforeEach
    void initService() {
        service = new IdeaQueryService(repository);
    }

    @AfterEach
    void removeIdeas() {
        ids.forEach(repository::deleteById);
    }

    @Test
    void shouldFindNothingWhenNoIdeas() {
        List<IdeaReadModel> actual = service.findAll();

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFindAllIdeas() {
        givenIdeas();

        List<IdeaReadModel> actual = service.findAll();

        assertThat(actual)
                .hasSize(4)
                .anySatisfy(idea -> assertThat(idea)
                        .hasTitle("IdeaOne")
                        .hasDescription("With description"))
                .anySatisfy(idea -> assertThat(idea)
                        .hasTitle("IdeaTwo")
                        .hasNoDescription())
                .anySatisfy(idea -> assertThat(idea)
                        .hasNoTitle()
                        .hasDescription("Description is everything"))
                .anySatisfy(idea -> assertThat(idea)
                        .hasTitle("Idea Four")
                        .hasDescription("The greatest ideas makes us better!"));
    }

    @Test
    void shouldFindNothingWhenIdeaWithGivenIdDoesNotExist() {
        givenIdeas();

        Optional<IdeaReadModel> actual = service.findById(UUID.randomUUID());

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldFindSpecificIdea() {
        givenIdeas();
        UUID id = givenIdea("Idea Five", "It would be good to do something good");

        IdeaReadModel actual = service.findById(id).get();

        assertThat(actual)
                .hasTitle("Idea Five")
                .hasDescription("It would be good to do something good");
    }

    private void givenIdeas() {
        givenIdea("IdeaOne", "With description");
        givenIdea("IdeaTwo", null);
        givenIdea(null, "Description is everything");
        givenIdea("Idea Four", "The greatest ideas makes us better!");
    }

    private UUID givenIdea(String title, String description) {
        UUID id = repository.save(new IdeaReadModel(title, description)).getId();
        ids.add(id);
        return id;
    }
}