package com.lang.big.biglang.bean;

import com.lang.big.biglang.utils.MyOkHttp_util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/30.
 */
public class Commodity {
    /**
     *
     * commId 商品的id
     * User 发布商品的用户对象
     * commArea 商品所在地
     * describe 商品的描述
     * commName 商品名称
     * CommSort 商品的分类
     * imgPaths 全部图片的路径
     * commFranking 商品的邮费
     * commPRICE 商品的价格
     * commCOST 商品的原价
     * commDate 商品发布的时间
     * ifLike 我是否喜欢这件商品
     * likeTheComm 喜欢这件商品的人数
     * CommReview 这件商品的回复的人数
     */
    private int commId;
    private User user;
    private String commArea;
    private String describe;
    private String commName;
    private String CommSort;
    private String[] imgPaths;
    private int commFranking;
    private int commPRICE;
    private int commCOST;
    private long commDate;
    private boolean ifLike;
    private int likeTheComm;
    private int CommReview;
    /**
     * @return the commId
     */
    public int getCommId() {
        return commId;
    }
    /**
     * @param commId the commId to set
     */
    public void setCommId(int commId) {
        this.commId = commId;
    }
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }
    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * @return the commArea
     */
    public String getCommArea() {
        return commArea;
    }
    /**
     * @param commArea the commArea to set
     */
    public void setCommArea(String commArea) {
        this.commArea = commArea;
    }
    /**
     * @return the describe
     */
    public String getDescribe() {
        return describe;
    }
    /**
     * @param describe the describe to set
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    /**
     * @return the commName
     */
    public String getCommName() {
        return commName;
    }
    /**
     * @param commName the commName to set
     */
    public void setCommName(String commName) {
        this.commName = commName;
    }
    /**
     * @return the commSort
     */
    public String getCommSort() {
        return CommSort;
    }
    /**
     * @param commSort the commSort to set
     */
    public void setCommSort(String commSort) {
        CommSort = commSort;
    }
    /**
     * @return the imgPaths
     */
    public String[] getImgPaths() {
        return imgPaths;
    }
    /**
     * @param imgPaths the imgPaths to set
     */
    public void setImgPaths(String[] imgPaths) {
        this.imgPaths = imgPaths;
    }
    /**
     * @return the commFranking
     */
    public int getCommFranking() {
        return commFranking;
    }
    /**
     * @param commFranking the commFranking to set
     */
    public void setCommFranking(int commFranking) {
        this.commFranking = commFranking;
    }
    /**
     * @return the commPRICE
     */
    public int getCommPRICE() {
        return commPRICE;
    }
    /**
     * @param commPRICE the commPRICE to set
     */
    public void setCommPRICE(int commPRICE) {
        this.commPRICE = commPRICE;
    }
    /**
     * @return the commCOST
     */
    public int getCommCOST() {
        return commCOST;
    }
    /**
     * @param commCOST the commCOST to set
     */
    public void setCommCOST(int commCOST) {
        this.commCOST = commCOST;
    }
    /**
     * @return the commDate
     */
    public long getCommDate() {
        return commDate;
    }
    /**
     * @param commDate the commDate to set
     */
    public void setCommDate(long commDate) {
        this.commDate = commDate;
    }
    /**
     * @return the ifLike
     */
    public boolean getIsIfLike() {
        return ifLike;
    }
    /**
     * @param ifLike the ifLike to set
     */
    public void setIfLike(boolean ifLike) {
        this.ifLike = ifLike;
    }
    /**
     *
     * @return the likeTheComm
     */
    public int getLikeTheComm() {
        return likeTheComm;
    }
    /**
     * @param likeTheComm the likeTheComm to set
     */
    public void setLikeTheComm(int likeTheComm) {
        this.likeTheComm = likeTheComm;
    }
    /**
     * @return the commReview
     */
    public int getCommReview() {
        return CommReview;
    }
    /**
     * @param commReview the commReview to set
     */
    public void setCommReview(int commReview) {
        CommReview = commReview;
    }

    public ArrayList<String> getAbsolutePath(){
        ArrayList<String> arr = new ArrayList<>();
        for(String path:imgPaths){
            arr.add(MyOkHttp_util.ServicePath+"Myimg/"+commId+"/"+path);
        }
        return arr;
    }
}
