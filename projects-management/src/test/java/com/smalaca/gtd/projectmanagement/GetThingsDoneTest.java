package com.smalaca.gtd.projectmanagement;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetThingsDoneTest {
    @Test
    void shouldRecognizeIfThingsAreDone() {
        boolean actual = new GetThingsDone().isEverythingDone();

        assertThat(actual).isTrue();
    }
}