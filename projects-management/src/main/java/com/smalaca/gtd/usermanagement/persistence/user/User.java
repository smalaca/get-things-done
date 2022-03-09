package com.smalaca.gtd.usermanagement.persistence.user;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static com.smalaca.gtd.usermanagement.persistence.user.Role.USER;

@SuppressFBWarnings("URF_UNREAD_FIELD")
@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String userName;
    private String password;
    private boolean active;
    private Role role;

    public static User user(String userName, String password) {
        User user = new User();
        user.userName = userName;
        user.password = password;
        user.active = true;
        user.role = USER;
        return user;
    }

    String id() {
        return id.toString();
    }
}
