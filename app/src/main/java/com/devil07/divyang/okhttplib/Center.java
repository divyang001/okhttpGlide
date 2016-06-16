package com.devil07.divyang.okhttplib;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Center extends AppCompatActivity {
    TextView tv55;
    Boolean isInternetPresent = false;
    Detection cd;
    private JSONObject jsn;
    private JSONObject jsnobj;
    String imgurls;
    String posi;
    int positions;
    Realm realm;
    //String[] amenities={"Music" , "Lockers" , "Parking" , "Air conditioning" , "Wifi" , "Changing room" , "Cafe"
      //      , "Shower" , "Drinking water" , "Television" ,"Soft floor"};
    //int [] amenID={1,2,3,4,5,6,7,8,9,10,11};
   // ammenities Am[]=new ammenities[11];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.center_layout);
        super.onCreate(savedInstanceState);

        Bundle basket=  getIntent().getExtras();
        posi= basket.getString("key");
        positions=Integer.parseInt(posi);
        initialiseRealm();
        initialiseVariables();
        checkInternet();


    }

    private void checkInternet() {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
         /*   realm = Realm.getDefaultInstance();
            clearAmmenities();
            realm.beginTransaction();
            for(int ctr=0;ctr<11;ctr++){
                Am[ctr] = realm.createObject(ammenities.class);
                Am[ctr].setAmmen(amenities[ctr]);
                Am[ctr].setId(amenID[ctr]);
            }


            realm.commitTransaction();
            realm.close();*/
            new GetCenter().execute();
        }
        else {
            String da="devil\n";
            realm = Realm.getDefaultInstance();
            RealmResults<centerdata> Mydata = realm.where(centerdata.class).equalTo("id",positions).findAll();
            RealmList<ammenities> amm[]=new RealmList[Mydata.size()];
            for (int i=0;i<amm.length;i++)
            { Toast.makeText(Center.this,"in for",Toast.LENGTH_SHORT).show();
                amm[i]=Mydata.get(i).getAmmenss();
                for (int j=0;j<amm[i].size();j++)
                {
                    Toast.makeText(Center.this,"in dfor",Toast.LENGTH_SHORT).show();
                    da+=amm[i].get(j).getAmmen();
                }
            }
            if(Mydata.size()==0)
            {
                tv55.setText("load kro beta");
            }
            else
            {
                Toast.makeText(Center.this,"in elsefor",Toast.LENGTH_SHORT).show();
                tv55.setText(da);

            }

        }

    }

    private void clearAmmenities() {
      //  RealmResults<ammenities> Mydata = realm.where(ammenities.class).findAll();
        RealmResults<centerdata> Mydat = realm.where(centerdata.class).equalTo("id",positions).findAll();
        Toast.makeText(Center.this,"ammmm"+Mydat.size(),Toast.LENGTH_SHORT).show();
        realm.beginTransaction();
      //  Mydata.clear();
        Mydat.clear();
        realm.commitTransaction();
    }

    private void initialiseVariables() {
        cd = new Detection(getApplicationContext());
        tv55 = (TextView) findViewById(R.id.tv55);
    }

    private void initialiseRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfig);
    }

    private class GetCenter extends AsyncTask<Void, Integer, String[]> {
       String namesam[] ;
        RealmList<ammenities> mainn =new RealmList<ammenities>();


        @Override
        protected String[] doInBackground(Void... params) {
            realm = Realm.getDefaultInstance();
            RealmResults<centerdata> Mydata = realm.where(centerdata.class).equalTo("id",positions).findAll();
            realm.beginTransaction();
            Mydata.clear();
            realm.commitTransaction();
            JSONParser obj = new JSONParser();
            obj.setMAIN_URL(posi);

            JSONObject jsonObject = obj.getDataFromWeb();
            jsn = jsonObject;
            try {
                if (jsn != null) {
                        // int id = Integer.parseInt(jsonObject.op  tString("id").toString());
                        String name = jsn.getString("name");
                        String about = jsn.getString("about");
                        int ID=jsn.getInt("id");
                        JSONArray jb = jsn.getJSONArray("amenities");

                    //    int idam[]=new int[jb.length()];
                        namesam=new String[jb.length()];
                        int counter=jb.length();
                        for (int j = 0; j < jb.length(); j++) {
                            JSONObject jsonobj = jb.getJSONObject(j);
                            RealmResults<ammenities> datt=realm.where(ammenities.class).equalTo("ammen",jsonobj.getString("name")).findAll();
                            if(datt.size()!=0)
                            {
                                String[] ar= new String[3];
                                ar[0]="hello";
                                ar[1]="vello";
                                ar[2]="bello";

                                return ar;


                            }
                            else
                            {
                                return null;
                            }
                          //  mainn.add(datt.get(0));
                          //  namesam[j] = jsonobj.getString("name");
                        }

                        JSONArray jc = jsn.getJSONArray("categories");

                        for (int k = 0; k < jc.length(); k++) {
                            JSONObject jsonobj = jc.getJSONObject(k);
                            String id = jsonobj.getString("id");
                            String names = jsonobj.getString("name");
                            //  data+="id="+id+"\nname="+names+"\n";
                        }

                        String address_1 = jsn.getString("address_1");

                        String address_2 = jsn.getString("address_2");

                        String city = jsn.getString("city");
                        String lat = jsn.getString("latitude");
                        String lng = jsn.getString("longitude");
                        //  data += "id=" + lat + "\nname=" + lng + "\nfeatured=" + city + "\n";

                        JSONArray jd = jsn.getJSONArray("photos");
                        for (int l = 0; l < jd.length(); l++) {
                            JSONObject jsonobj = jd.getJSONObject(l);
                            String id = jsonobj.getString("id");
                            String names = jsonobj.getString("image");
                            imgurls = names;
                            String fea = jsonobj.getString("is_featured");
                            // data+="id="+id+"\nname="+names+"\nfeatured="+fea+"\n";
                            //  data+=id+names+fea;
                        }
                        realm.beginTransaction();
                         centerdata y=realm.createObject(centerdata.class);
                        y.setId(ID);
                        y.setAmmenss(mainn);
                     /*  RealmQuery<data> query[]=new RealmQuery[counter];
                        RealmResults<data> result;
                        for(int tempo=0;tempo<counter;tempo++){
                            query[tempo]=realm.where(data.class).equalTo(Am.getId(),idam[tempo]);
                        }*/
                        realm.commitTransaction();

                        //  dataList.add(x);

                       return namesam;


                } else {
                    // Toast.makeText(MainActivity.this, "Hellooooooo in Center", Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            realm.close();
            return null;

        }
        @Override

        protected void onPostExecute(String result[]) {
            super.onPostExecute(result);
            String da=" ";
            if(result!=null) {
                for (int i = 0; i < result.length; i++) {
                    da += result[i];
                }
                tv55.setText(da);
            }
            else
            {
                tv55.setText("parsing nhi hui theek beta");
            }

        }
    }
}



