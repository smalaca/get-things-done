package com.smalaca.gtd.projectmanagement.infrastructure.api.web.rest.idea;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class IdeaDtoTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(IdeaDto.class).verify();
    }
}