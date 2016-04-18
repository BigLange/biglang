package com.lang.big.biglang.bean;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ShopClass {
    private String className;
    private String biaoti;
    private String classImgUri;
    private String[] type;

    public ShopClass(String className, String[] type, String classImgUri, String biaoti) {
        this.className = className;
        this.type = type;
        this.classImgUri = classImgUri;
        this.biaoti = biaoti;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getClassImgUri() {
        return classImgUri;
    }

    public void setClassImgUri(String classImgUri) {
        this.classImgUri = classImgUri;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }
}
