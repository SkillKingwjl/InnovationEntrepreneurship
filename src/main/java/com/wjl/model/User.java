package com.wjl.model;

import java.util.Objects;

/**
 * @author wjl
 * @date 2018/2/8
 */
public class User {
    private Integer id;
    private String userName;
    private String userPass;
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
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
                Objects.equals(userName, user.userName) &&
                Objects.equals(userPass, user.userPass) &&
                Objects.equals(flag, user.flag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, userPass, flag);
    }
}
