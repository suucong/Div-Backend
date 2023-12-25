package com.damoacon.app.preference.entity;

import com.damoacon.app.event.entity.Event;
import com.damoacon.app.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="comment")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Event event;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(nullable = false, name = "content")
    private String content;

    @Builder
    public Comment (Event event, User user, String content) {
        this.event = event;
        this.user = user;
        this.content = content;
    }

    public void update (String content) {
        this.content = content;
    }
}
