package com.berna.core.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Document(indexName = "users", type = "users", shards = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String teamName;
    @JsonIgnore
    private Long salary;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Bookmark> bookmarks = new HashSet<>();

    public User() { // JPA only
    }

    public User(String name, Long id, String teamName, Long salary) {
        this.name = name;
        this.id = id;
        this.teamName = teamName;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Set<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(Set<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(teamName, user.teamName) &&
                Objects.equals(salary, user.salary) &&
                Objects.equals(bookmarks, user.bookmarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teamName, salary, bookmarks);
    }
}
