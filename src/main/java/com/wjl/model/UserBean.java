package com.wjl.model;

import java.util.List;

public class UserBean {
    private User user;
    private UserDetail userDetail;
    private List<ProjectDetail> project;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<ProjectDetail> getProject() {
        return project;
    }

    public void setProject(List<ProjectDetail> project) {
        this.project = project;
    }
}
