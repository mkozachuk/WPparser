package com.mkozachuk.wpparser.DAO;

public class Post {
    private int postNo;
    private String title;
    private String description;
    private String metaTags;
    private String text;
    private String imgLink;
    private String allImgs;
    private String fromWhereUrl;

    public Post(int postNo, String title, String description, String metaTags, String text, String imgLink, String allImgs, String fromWhereUrl) {
        this.postNo = postNo;
        this.title = title;
        this.description = description;
        this.metaTags = metaTags;
        this.text = text;
        this.imgLink = imgLink;
        this.allImgs = allImgs;
        this.fromWhereUrl = fromWhereUrl;
    }

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetaTags() {
        return metaTags;
    }

    public void setMetaTags(String metaTags) {
        this.metaTags = metaTags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getAllImgs() {
        return allImgs;
    }

    public void setAllImgs(String allImgs) {
        this.allImgs = allImgs;
    }

    public String getFromWhereUrl() {
        return fromWhereUrl;
    }

    public void setFromWhereUrl(String fromWhereUrl) {
        this.fromWhereUrl = fromWhereUrl;
    }
}
