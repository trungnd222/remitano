package com.remitano.demo.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", schema = "public")
@TypeDef(name = "list-array",typeClass = ListArrayType.class)
public class UserEntity {

    @Id
    private String email;

    private String password;

    @Type(type = "list-array")
    @Column(name = "sessions", columnDefinition = "array")
    private List<String> sessions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MovieEntity> movies;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getSessions() {
        return sessions;
    }

    public void setSessions(List<String> sessions) {
        this.sessions = sessions;
    }

    public List<MovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }
}
