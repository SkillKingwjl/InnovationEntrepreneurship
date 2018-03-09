package com.wjl.model;

import java.util.List;

public class MessagePoJo {
    private int pageNum;
    private int NowPage;
    private int count;
    private List<MessageInfo> messageInfo;

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

    public List<MessageInfo> getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(List<MessageInfo> messageInfo) {
        this.messageInfo = messageInfo;
    }
}
