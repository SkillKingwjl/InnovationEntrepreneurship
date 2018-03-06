package com.wjl.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author wjl
 * @date 2018/2/8
 */

public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(flag, user.flag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, username, password, flag);
    }
}
