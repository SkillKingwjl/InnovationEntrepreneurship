package com.wjl.model;

import java.util.List;

public class ProjectMessage {
    private int pageNum;
    private int NowPage;
    private int count;
    private List<ProjectDetail> projectDetail;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNowPage() {
        return NowPage;
    }

    public void setNowPage(int nowPage) {
        NowPage = nowPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProjectDetail> getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(List<ProjectDetail> projectDetail) {
        this.projectDetail = projectDetail;
    }
}
