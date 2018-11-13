package ru.lanit.ld.wc.model;

import java.util.Objects;

public class User {

    private int id;
    private String UserName;
    private String Password;

    public int getId() {
        return id;
    }

    public User withId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return UserName;
    }

    public User withUserName(String userName) {
        this.UserName = userName;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public User withPassword(String password) {
        this.Password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(UserName, user.UserName) &&
                Objects.equals(Password, user.Password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, UserName, Password);
    }
}
