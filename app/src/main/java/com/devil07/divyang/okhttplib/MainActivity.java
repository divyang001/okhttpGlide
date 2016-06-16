package com.devil07.divyang.okhttplib;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;


import io.realm.Realm;
import io.realm.RealmConfiguration;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Boolean isInternetPresent = false;
    Detection cd;
    private JSONObject jsn;
    private JSONObject jsnobj;
    String imgurls;
    TextView output;
    private List<data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private dataadaptor mAdapter;
    private TextView tv1;
    Realm realm;
    String[] amenities={"Music" , "Lockers" , "Parking" , "Air conditioning" , "Wifi" , "Changing room" , "Cafe"
            , "Shower" , "Drinking water" , "Television" ,"Soft floor"};
    int [] amenID={1,2,3,4,5,6,7,8,9,10,11};
    ammenities Am[]=new ammenities[11];
    Boolean checkRealm;
    //  String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseRealm();
        realm = Realm.getDefaultInstance();
        createAM();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cd = new Detection(getApplicationContext());
        output = (TextView) findViewById(R.id.tv1);
        mAdapter = new dataadaptor(this, dataList);

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            // Internet Connection is Present
            RealmResults<data> Mydata = realm.where(data.class).findAll();
            realm.beginTransaction();
            Mydata.clear();
            realm.commitTransaction();
            realm.close();
            new GetDataTask().execute();

            //  mAdapter.notifyDataSetChanged();

        }
        else
        {
            RealmResults<data> Mydata = realm.where(data.class).findAll();
            for (int i =0;i<Mydata.size();i++)
            {
                String add1=Mydata.get(i).getAdress1();
                String add2=Mydata.get(i).getAdress2();
                byte [] img =Mydata.get(i).getImg();
                data x = new data(add1,add2,img);
                dataList.add(x);
            }
            realm.close();

        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());// get Internet status
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());                                     // check for Internet status
        recyclerView.setAdapter(mAdapter);



    }

    private void createAM() {


        realm.beginTransaction();
        for(int ctr=0;ctr<11;ctr++){
            Am[ctr] = realm.createObject(ammenities.class);
            Am[ctr].setAmmen(amenities[ctr]);
            Am[ctr].setId(amenID[ctr]);
        }


        realm.commitTransaction();

    }

    public class GetDataTask extends AsyncTask<Void, Integer, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            //   dialog = new ProgressDialog(MainActivity.this);
            //  dialog.setTitle("Hey Wait Please...");
            // dialog.setMessage("I am getting your JSON");
            //   dialog.show();
        }

        //  @Nullable
        @Override

        protected String doInBackground(Void... params) {
            realm = Realm.getDefaultInstance();
            JSONParser obj = new JSONParser();
            obj.setMAIN_URL("");

            JSONObject jsonObject = obj.getDataFromWeb();
            jsn = jsonObject;
            try {
                // Toast.makeText(MainActivity.this, "Hellooooooo in Center", Toast.LENGTH_LONG).show();
                /**
                 * Check Whether Its NULL???
                 *
                 */
                //  if (jsn != null)
                // {
                //  return jsn.toString();
                //}

                if (jsn != null) {

                    JSONArray ja = jsn.getJSONArray("results");
                    for (int i = 0; i < ja.length(); i++) {


                        JSONObject jsonObj = ja.getJSONObject(i);
                        // int id = Integer.parseInt(jsonObject.op  tString("id").toString());
                        String name = jsonObj.getString("name");
                        String about = jsonObj.getString("about");


                        JSONArray jb = jsonObj.getJSONArray("amenities");
                        int idam[]=new int[jb.length()];
                        String namesam[]=new String[jb.length()];
                        int counter=jb.length();
                        for (int j = 0; j < jb.length(); j++) {
                            JSONObject jsonobj = jb.getJSONObject(j);
                            idam[j] = jsonobj.getInt("id");
                            namesam[j] = jsonobj.getString("name");

                            //  data+="id="+id+"\nname="+names+"\n";
                        }

                        JSONArray jc = jsonObj.getJSONArray("categories");

                        for (int k = 0; k < jc.length(); k++) {
                            JSONObject jsonobj = jc.getJSONObject(k);
                            String id = jsonobj.getString("id");
                            String names = jsonobj.getString("name");
                            //  data+="id="+id+"\nname="+names+"\n";
                        }

                        String address_1 = jsonObj.getString("address_1");

                        String address_2 = jsonObj.getString("address_2");

                        String city = jsonObj.getString("city");
                        String lat = jsonObj.getString("latitude");
                        String lng = jsonObj.getString("longitude");
                        //  data += "id=" + lat + "\nname=" + lng + "\nfeatured=" + city + "\n";

                        JSONArray jd = jsonObj.getJSONArray("photos");

                        for (int l = 0; l < jd.length(); l++) {
                            JSONObject jsonobj = jd.getJSONObject(l);
                            String id = jsonobj.getString("id");
                            String names = jsonobj.getString("image");
                            imgurls = names;
                            String fea = jsonobj.getString("is_featured");
                            // data+="id="+id+"\nname="+names+"\nfeatured="+fea+"\n";
                            //  data+=id+names+fea;
                        }
                        Bitmap theBitmap = Glide.
                                with(MainActivity.this).
                                load(imgurls).
                                asBitmap().
                                into(100, 100). // Width and height
                                get();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        theBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        realm.beginTransaction();
                        data x = new data(address_1, address_2, byteArray);
                        data y=realm.createObject(data.class);
                        y.setAdress1(address_1);
                        y.setAdress2(address_2);
                        y.setImg(byteArray);



                     /*  RealmQuery<data> query[]=new RealmQuery[counter];
                        RealmResults<data> result;
                        for(int tempo=0;tempo<counter;tempo++){
                            query[tempo]=realm.where(data.class).equalTo(Am.getId(),idam[tempo]);
                        }*/

                        realm.commitTransaction();
                        dataList.add(x);
                        //  dataList.add(x);
                    }
                    //   return data;


                } else {
                    // Toast.makeText(MainActivity.this, "Hellooooooo in Center", Toast.LENGTH_LONG).show();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            realm.close();

            return "orange";

        }

        @Override

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //  dialog.dismiss();
            mAdapter.notifyDataSetChanged();

        }
    }
    private void initialiseRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}