package com.wjl.model;

public class MessageBean {
    private Message message;
    private ProjectDetail projectDetail;
    private User user;
    private UserDetail userDetail;
    private User own;
    private UserDetail ownDetail;
    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public ProjectDetail getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
    }

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

    public User getOwn() {
        return own;
    }

    public void setOwn(User own) {
        this.own = own;
    }

    public UserDetail getOwnDetail() {
        return ownDetail;
    }

    public void setOwnDetail(UserDetail ownDetail) {
        this.ownDetail = ownDetail;
    }
}
