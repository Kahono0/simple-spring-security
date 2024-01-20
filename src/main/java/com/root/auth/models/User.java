package com.root.auth.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Column(unique = true)
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, max = 50)
    private String password;

    // many to one with Roles, set default to ROLE_USER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;


    public User() {
    }

    public User(String username, String password, Roles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = new Roles(UserRoles.ROLE_USER);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
