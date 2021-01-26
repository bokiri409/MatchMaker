package com.web.blog.model.room;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private int roomNo;
    private int backgroundNo;
    private int bgmNo;

    private String roomName;
    private String roomPassword;
}
