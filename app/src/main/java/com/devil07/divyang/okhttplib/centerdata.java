package com.devil07.divyang.okhttplib;

import io.realm.RealmList;
import io.realm.RealmObject;


public class centerdata extends RealmObject {

    private int id;
    //  private String adress1;
    // private String adress2;
    // private byte[] img;
    private RealmList<ammenities> mAmmenities;
    // ammenities ammen[];




    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

/// can add any set get method

    public void setAmmenss(RealmList<ammenities> mAmmenities) {
        // RealmQuery<data> query[]=new RealmQuery[array.length];
        this.mAmmenities=mAmmenities;

    }
    public RealmList<ammenities>getAmmenss() {

        return mAmmenities;
    }
}
