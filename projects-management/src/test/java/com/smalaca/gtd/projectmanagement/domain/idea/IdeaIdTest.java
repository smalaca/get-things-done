package com.smalaca.gtd.projectmanagement.domain.idea;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class IdeaIdTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(IdeaId.class).verify();
    }
}