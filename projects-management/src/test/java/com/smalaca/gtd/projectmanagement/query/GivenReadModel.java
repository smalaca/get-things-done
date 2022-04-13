package com.smalaca.gtd.projectmanagement.query;

public class GivenReadModel {
    public UserReadModelTestBuilder user() {
        return new UserReadModelTestBuilder();
    }

    public IdeaReadModelTestBuilder idea() {
        return new IdeaReadModelTestBuilder();
    }
}
