package com.example.myjob.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UserBean {
    @Id(autoincrement = false)
    long pid;
    private String title;
    private double price;
    private String images;
    @Generated(hash = 711318210)
    public UserBean(long pid, String title, double price, String images) {
        this.pid = pid;
        this.title = title;
        this.price = price;
        this.images = images;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public long getPid() {
        return this.pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }

}
