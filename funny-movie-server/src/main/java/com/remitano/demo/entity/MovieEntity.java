package com.remitano.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "movie", schema = "public")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;
    private String desc;
    private String url;

    private String img;

    @ManyToOne
    @JoinColumn(name="user_email", nullable=false)
    private UserEntity user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
