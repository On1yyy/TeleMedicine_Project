package com.example.carryingmedicine.News;
public class Item {

    String title;
    String link;
    String desc;
    String imgUrl;
    String date;

    public Item() { //혹시 몰라서 매개변수가 없는 것도 생성
    }

    public Item(String title, String link, String desc, String imgUrl, String date) {
        this.title = title;
        this.link = link;
        this.desc = desc;
        this.imgUrl = imgUrl;
        this.date = date;
    }

    //Getter & Setter Method..
    //멤버 변수가 private이면 다른 곳에서 접근하기 위해 Getter & Setter Method 필요하다.
    // 자동완성 Alt+Insert 에 Getter & Setter 있다.

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}