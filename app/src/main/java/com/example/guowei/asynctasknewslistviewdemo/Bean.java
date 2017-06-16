package com.example.guowei.asynctasknewslistviewdemo;

/**
 * Created by Guowei on 2017/6/15.
 */

public class Bean {
    private String mPhotoUrl;
    private String title;
    private String content;

    public Bean() {
    }

    public Bean(String mPhotoUrl, String title, String content) {
        this.mPhotoUrl = mPhotoUrl;
        this.title = title;
        this.content = content;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "mPhotoUrl='" + mPhotoUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
