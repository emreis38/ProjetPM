package com.pm.projetpkmn;

public class blog_post {
    private String author ;
    private String title ;
    private String date;
    private String imgUrl;
    private String slug;
    private String smallContent;
    private String content;


    public blog_post(){
        this.imgUrl = "https://cdn.hytale.com/variants/blog_cover_";
    }
    public String getSmallContent() {
        return smallContent;
    }

    public void setSmallContent(String smallContent) {
        this.smallContent = smallContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String url) {
        this.imgUrl += url;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
