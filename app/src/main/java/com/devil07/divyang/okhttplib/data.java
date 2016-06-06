package com.devil07.divyang.okhttplib;

public class data {
    private String adress1, adress2, img;

    public data() {

    }

    public data(String adress1, String adress2, String img) {

        this.adress1 = adress1;
        this.adress2 = adress2;
        this.img = img;

    }

    public String getAdress1() {
        return adress1;
    }

    public void setAdress1(String adress1) {
        this.adress1 = adress1;
    }

    public String getAdress2() {
        return adress2;
    }

    public void setAdress2(String adress2) {
        this.adress2 = adress2;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}