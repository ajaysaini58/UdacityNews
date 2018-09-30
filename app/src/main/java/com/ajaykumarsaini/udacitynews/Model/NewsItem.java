package com.ajaykumarsaini.udacitynews.Model;

public class NewsItem {

    String newsHeadline;
    String newsCategory;
    String newsAuthor;
    String publishDate;
    String newsUrl;

    public NewsItem(String newsHeadline, String newsCategory, String newsAuthor, String publishDate, String newsUrl) {
        this.newsHeadline = newsHeadline;
        this.newsCategory = newsCategory;
        this.newsAuthor = newsAuthor;
        this.publishDate = publishDate;
        this.newsUrl = newsUrl;
    }

    public String getNewsHeadline() {
        return newsHeadline;
    }

    public void setNewsHeadline(String newsHeadline) {
        this.newsHeadline = newsHeadline;
    }

    public String getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }


    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

}
