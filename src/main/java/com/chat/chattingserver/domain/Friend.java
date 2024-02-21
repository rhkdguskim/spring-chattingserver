package com.chat.chattingserver.domain;

import com.chat.chattingserver.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="friend", uniqueConstraints = { @UniqueConstraint(columnNames = {"friend_id", "user_id"})})
@Getter
@Setter
public class Friend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="friend_id")
    private long friend_id;

    @Column(name="friend_name")
    @NotNull
    private String friend_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
