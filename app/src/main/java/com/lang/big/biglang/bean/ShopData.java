package com.lang.big.biglang.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ShopData {

    private String shopName;
    private String hand;
    private List<String> imageUrls;
    private int price;
    private String introduce;
    private String address;
    private int xiangYao;
    private int comments;//评论条数
    private boolean ifXiangYao;
    private Date release;

    public ShopData(String shopName, List<String> imageUrls, int price, String address, String introduce, int xiangYao, int comments, Date release) {
        this.shopName = shopName;
        this.imageUrls = imageUrls;
        this.price = price;
        this.address = address;
        this.introduce = introduce;
        this.xiangYao = xiangYao;
        this.comments = comments;
        this.release = release;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        price = price;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getXiangYao() {
        return xiangYao;
    }

    public void setXiangYao(int xiangYao) {
        this.xiangYao = xiangYao;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }
}
