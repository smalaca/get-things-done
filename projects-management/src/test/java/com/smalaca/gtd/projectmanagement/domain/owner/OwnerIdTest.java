package com.smalaca.gtd.projectmanagement.domain.owner;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class OwnerIdTest {
    @Test
    void shouldFulfillEqualsContract() {
        EqualsVerifier.forClass(OwnerId.class).verify();
    }
}