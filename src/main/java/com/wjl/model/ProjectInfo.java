package com.wjl.model;

import java.util.List;

public class ProjectInfo {
    private ProjectDetail projectDetail;
    private List<UserBean> userList;

    public ProjectDetail getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
    }

    public List<UserBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserBean> userList) {
        this.userList = userList;
    }
}
