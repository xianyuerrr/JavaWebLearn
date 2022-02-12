package com.xianyue.statement;

/**
 * @auther xianyue
 * @date 2022/1/28 - 星期五 - 15:50
 **/
public class User {
    private String user;
    private String password;

    public User() {}

    public User(String user, String password) {
        super();
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }
}
