package com.smalaca.gtd.projectmanagement.domain.idea;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CreateIdeaCommandTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(CreateIdeaCommand.class).verify();
    }
}