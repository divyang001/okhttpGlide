package com.example.hunter.okhttp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private dataadaptor mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new dataadaptor(dataList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);



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

            String name1 = null;
            String[] name2 = new String[100];
            String[] padress1=new String[100];
            String[] padress2=new String[100];
            String[] pimage=new String[100];

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();
            data x= new data();          //yeh hai meri data vali class

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {


                    /**
                     * Check Length...
                     */
                    if (jsonObject.length() > 0) {
                        int count = jsonObject.getInt("count");

                        for(int ctr=0;ctr<count;ctr++) {
                            /**
                             * Getting Array named "contacts" From MAIN Json Object
                             *
                             */

                            JSONArray array = jsonObject.getJSONArray("results");
                            {
                                /**
                                 * Check Length of Array...
                                 */
                                int lenArray = array.length();
                                if (lenArray > 0) {
                                    for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                        /**
                                         * Getting Inner Object from contacts array...
                                         * and
                                         * From that We will get Name of that Contact
                                         *
                                         */
                                        JSONObject innerObject = array.getJSONObject(jIndex);


                                        name1 = innerObject.getString("name");


                                        int id = innerObject.getInt("id");
                                        JSONArray array2 = innerObject.getJSONArray("amenities");

                                        for (int j2Index = 0; j2Index < array2.length(); j2Index++) {

                                            JSONObject inner2Object = array2.getJSONObject(j2Index);
                                            name2[j2Index] = inner2Object.getString("name");
                                           // x.setAdress1(name1);



                                        }
                                        JSONArray array3= innerObject.getJSONArray("categories");
                                        for(int j3Index=0;j3Index<array3.length();j3Index++){
                                           //get the types from here
                                        }

                                        padress1[jIndex]=innerObject.getString("address1");
                                        padress2[jIndex]=innerObject.getString("address2");
                                        JSONArray array4=innerObject.getJSONArray("photos");
                                        for(int j4Index=0;j4Index<array4.length();j4Index++){
                                            JSONObject array4inner=array4.getJSONObject(j4Index);

                                            pimage[jIndex]=array4inner.getString("image");
                                            x.setImg(pimage[jIndex]);
                                            x.setAdress1(padress1[jIndex]);
                                            x.setAdress2(padress2[jIndex]);
                                            dataList.add(x);

                                        }

                                    }


                                }
                            }
                        }
                    }

                }     else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }


            return null;
        }
    }
}
