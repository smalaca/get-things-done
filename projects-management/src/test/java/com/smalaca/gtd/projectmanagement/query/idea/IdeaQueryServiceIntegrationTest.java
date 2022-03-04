package com.smalaca.gtd.projectmanagement.query.idea;

import com.smalaca.gtd.projectmanagement.domain.idea.IdeaId;
import com.smalaca.gtd.projectmanagement.domain.idea.IdeaTestFactory;
import com.smalaca.gtd.projectmanagement.infrastructure.repository.jpa.idea.IdeaTestRepository;
import com.smalaca.gtd.tests.annotation.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.smalaca.gtd.projectmanagement.query.idea.IdeaReadModelAssertion.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@IntegrationTest
@Import(IdeaTestRepository.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class IdeaQueryServiceIntegrationTest {
    @Autowired private IdeaTestRepository ideaTestRepository;
    @Autowired private IdeaQueryRepository queryRepository;

    private final IdeaTestFactory factory = new IdeaTestFactory();

    private IdeaQueryService service;

    private final List<IdeaId> ids = new ArrayList<>();

    @BeforeEach
    void initService() {
        service = new IdeaQueryService(queryRepository);
    }

    @AfterEach
    void removeIdeas() {
        ids.forEach(ideaTestRepository::deleteById);
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
        IdeaId id = ideaTestRepository.save(factory.create(title, description));
        ids.add(id);
        return id.value();
    }
}