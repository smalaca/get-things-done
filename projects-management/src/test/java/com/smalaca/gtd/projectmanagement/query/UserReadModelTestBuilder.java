package com.smalaca.gtd.projectmanagement.query;

import com.smalaca.gtd.projectmanagement.query.user.UserReadModel;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@NoArgsConstructor(access = PACKAGE)
public class UserReadModelTestBuilder {
    private UUID id;
    private String userName;

    public UserReadModelTestBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public UserReadModelTestBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserReadModel build() {
        UserReadModel user = mock(UserReadModel.class);
        given(user.getId()).willReturn(id);
        given(user.getUserName()).willReturn(userName);
        return user;
    }
}
