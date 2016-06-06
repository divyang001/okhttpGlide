package com.devil07.divyang.okhttplib;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
  //  String data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cd = new Detection(getApplicationContext());
        output = (TextView) findViewById(R.id.tv1);
        mAdapter = new dataadaptor(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());// get Internet status
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());                                     // check for Internet status
        recyclerView.setAdapter(mAdapter);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            // Internet Connection is Present

            new GetDataTask().execute();
          //  mAdapter.notifyDataSetChanged();

        } else {
            // Internet connection is not present
        }


    }

    public class GetDataTask extends AsyncTask<Void, Integer, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("I am getting your JSON");
            dialog.show();
        }

        //  @Nullable
        @Override

        protected String doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb();
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
                        for (int j = 0; j < jb.length(); j++) {
                            JSONObject jsonobj = jb.getJSONObject(j);
                            String id = jsonobj.getString("id");
                            String names = jsonobj.getString("name");

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

                        data x = new data(address_1,address_2,imgurls);
                        dataList.add(x);
                        //data += "Blog Number "+(i+1)+" \n Blog Name= "+title  +" \n URL= "+ url +" \n\n\n\n ";
                    }
                    //  return  data;
                //    return data;


                } else {
                    // Toast.makeText(MainActivity.this, "Hellooooooo in Center", Toast.LENGTH_LONG).show();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "orange";
        }

        @Override

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.contentEquals("orange")) {
                // output.setText("blue");
            }
            if (result.contentEquals("green")) {
                // output.setText("green");
            } else {
                //   output.setText(result);

            }
            dialog.dismiss();
            mAdapter.notifyDataSetChanged();

        }
    }
}
