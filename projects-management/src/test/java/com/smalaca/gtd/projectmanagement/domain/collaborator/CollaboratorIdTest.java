package com.smalaca.gtd.projectmanagement.domain.collaborator;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class CollaboratorIdTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(CollaboratorId.class).verify();
    }

}