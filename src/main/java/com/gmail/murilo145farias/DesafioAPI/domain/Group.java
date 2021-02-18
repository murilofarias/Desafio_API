package com.gmail.murilo145farias.DesafioAPI.domain;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name="TheGroup")
@Table(name = "groups",
        indexes = { @Index(
                columnList = "name",
                name = "Name_index")
        }
)

public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="name", nullable = false, length = 60)
    private String name;

    @Column(nullable = false)
    private Date createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<User> users;

    public Group() {
        super();
    }

    public Group(UUID id, String name, Date createdAt)
    {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if(this.users == null) {
            this.users = new ArrayList<User>();
        }
        user.setCreatedAt(new Date());
        user.setGroup(this);
        this.users.add(user);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return id != null ? id.equals(group.id) : group.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "Id=" + id +
                "Name=" + name +
                "CreatedAt=" + createdAt +
                "}";
    }

}

