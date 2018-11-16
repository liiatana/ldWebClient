package ru.lanit.ld.wc.model;

import java.util.Objects;

public class UserInfo {

    private int id;
    private String Login;
    private String UserName;
    private String Password;
    private String Auth;

    public String getAuth() {
        return Auth;
    }

    public UserInfo withAuth(String auth) {
        this.Auth = auth;
        return this;
    }

    public int getId() {
        return id;
    }

    public UserInfo withId(int id) {
        this.id = id;
        return this;
    }
    public UserInfo withLogin(String Login) {
        this.Login = Login;
        return this;
    }

    public String getLogin() {
        return Login;
    }

    public String getUserName() {
        return UserName;
    }

    public UserInfo withUserName(String userName) {
        this.UserName = userName;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public UserInfo withPassword(String password) {
        this.Password = password;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id &&
                Objects.equals(UserName, userInfo.UserName) &&
                Objects.equals(Password, userInfo.Password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, UserName, Password);
    }
}
