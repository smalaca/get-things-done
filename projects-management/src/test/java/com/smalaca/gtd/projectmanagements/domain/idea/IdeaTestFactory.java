package com.smalaca.gtd.projectmanagements.domain.idea;

public class IdeaTestFactory {
    public Idea create(String title, String description) {
        return new Idea(title, description);
    }
}