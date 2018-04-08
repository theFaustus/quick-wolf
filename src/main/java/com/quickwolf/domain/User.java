package com.quickwolf.domain;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "wolf")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends AbstractEntity {

    private String email;
    private String password;
    private String role;
    private int enabled;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
