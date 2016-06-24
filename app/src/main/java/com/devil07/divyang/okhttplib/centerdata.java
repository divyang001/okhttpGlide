package com.devil07.divyang.okhttplib;

import io.realm.RealmList;
import io.realm.RealmObject;


public class centerdata extends RealmObject {

    private int id;
    //  private String adress1;
    // private String adress2;
    // private byte[] img;
    private RealmList<ammenities> ammenss;
    // ammenities ammen[];




    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

/// can add any set get method

    public void setAmmenss(RealmList<ammenities>ammenss) {
        // RealmQuery<data> query[]=new RealmQuery[array.length];
        this.ammenss=ammenss;

    }
    public RealmList<ammenities>getAmmenss() {

        return ammenss;
    }
}
