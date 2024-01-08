package com.damoacon.domain.user.entity;

import com.damoacon.domain.model.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="User")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "user_id")
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "profile")
    private String profile;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = true, name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User (String username, String profile, String email) {
        this.username = username;
        this.profile = profile;
        this.email = email;
        this.role = Role.USER;
    }
}
