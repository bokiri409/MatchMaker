package com.web.blog.model.room;

import com.web.blog.model.background.Background;
import com.web.blog.model.bgm.Bgm;
import com.web.blog.model.user.User;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomNo;

    @OneToOne
    @JoinColumn(name = "backgroundNo")
    private Background background;
    @OneToOne
    @JoinColumn(name = "bgmNo")
    private Bgm bgm;

    @ManyToMany
    private Set<User> attenders;

    private String roomName;
    private String roomURL;
}
