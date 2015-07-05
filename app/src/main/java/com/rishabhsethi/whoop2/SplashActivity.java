package com.rishabhsethi.whoop2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
//import com.koushikdutta.ion.Response;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

//import com.easyandroidanimations.library.BlinkAnimation;
//import com.easyandroidanimations.library.FadeInAnimation;
//import com.easyandroidanimations.library.FadeOutAnimation;


public class SplashActivity extends AppCompatActivity {
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String androidId = "nullal";
    Contacts contact;
    List<Contacts> CONTACTS;
    private GCMClientManager pushClientManager;
    String PROJECT_NUMBER = "482423124462";
    //Contacts contact = new Contacts();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
            @Override
            public void onSuccess(String registrationId, boolean isNewRegistration) {
                Toast.makeText(SplashActivity.this, registrationId,
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(String ex) {
                super.onFailure(ex);
                // If there is an error registering, don't just keep trying to register.
                // Require the user to click a button again, or perform
                // exponential back-off when retrying.
            }
        });

        String regId = SharedPreferencesUtil.getRegistrationId(getApplicationContext());
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        final String imei_no = telephonyManager.getDeviceId();

        volleySingleton = VolleySingleton.getInstance();
        requestQueue =  volleySingleton.getRequestQueue();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;

        String w = Integer.toString(width);
        String h = Integer.toString(height);

        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        String deviceVersion = android.os.Build.VERSION.RELEASE;


        //L.T(getApplicationContext(),"Width: "+w+" Heigth: "+h);

        if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 9) {
            androidId = android.os.Build.SERIAL;
        }
        else{
            androidId = "not_found";
        }
        //L.T(getApplicationContext(), "Android ID: "+androidId);

        Select select = new Select();
        CONTACTS = select.all().from(Contacts.class).execute();


        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        /*for(int k=0; k<CONTACTS.size();k++) {
            Contacts c = Contacts.load(Contacts.class, k);
            c.delete();
        }*/
        //select.all().from(Contacts.class);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                final String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        final String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                        Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        /*if(isContactPresent(phoneNo, CONTACTS)==false ){
                            contact = new Contacts();
                            contact.name = name;
                            contact.phoneNumber = phoneNo;
                            contact.onGamezop = false;
                            contact.save();*/
                        Realm realm = Realm.getInstance(this);
                        RealmResults<User> r = realm.where(User.class)
                                .contains("phoneNumber", phoneNo)
                                .findAll();
                        L.T(getApplicationContext(),r.toString());
                        if(r.size()==0){
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    L.T(getApplicationContext(), "adddddddddd");
                                    User user = realm.createObject(User.class);
                                    user.setName(name);
                                    user.setPhoneNumber(phoneNo);
                                }
                            });

                            StringRequest myReq = new StringRequest(Request.Method.POST,
                                    "http://107.178.214.92/webservices/insert-friend.php?tag=insert_mobile&mobile_no="+phoneNo+"&imei_no="+imei_no,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String s) {
                                            L.t(getApplicationContext(), "Contact Added");
                                        }
                                    },
                                    new com.android.volley.Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                        }
                                    });
                            if(myReq!=null) {
                                requestQueue.add(myReq);
                            }
                            else{
                                //L.t(getApplicationContext(), "request queue null");
                            }

                        }

                    }
                    pCur.close();

                }

            }
        }

        //getAll();
        /*JsonObject json = new JsonObject();
        json.addProperty("imei", imei_no);
        json.addProperty("reg_id", "i_dont_know");
        json.addProperty("androidId",androidId);
        json.addProperty("ScreenWidth", w);
        json.addProperty("ScreenHeight", h);

        Ion.with(getApplicationContext())
                .load("POST", "http://107.178.214.92/webservices/processes.php?tag=register")
                .setLogging("ion-geny", Log.DEBUG)
                .setHeader("Content-Type","application/json")
                .setHeader("Cache-Control","no-cache")
                .setJsonObjectBody(json)
                .asString();*/
                /*.setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e != null){
                            Log.e("Error Result", e.toString());
                        }
                            Log.e("Result", result);
                    }
                });*/
        /*Ion.with(getApplicationContext())
                .load("https://koush.clockworkmod.com/test/echo")
                .setBodyParameter("imei", imei_no)
                .setBodyParameter("reg_id", "i_dont_know")
                .setBodyParameter("androidId", androidId)
                .setBodyParameter("ScreenWidth", w)
                .setBodyParameter("ScreenHeight", h)
                .asString();*/
                /*.setCallback(new FutureCallback<com.koushikdutta.ion.Response<String>>() {
                                 @Override
                                 public void onCompleted(Exception e, com.koushikdutta.ion.Response<String> result) {
                                     // print the response code, ie, 200
                                    // L.t(getApplicationContext(), "")
                                     // print the String that was downloaded
                                  //   System.out.println(result.getResult());
                                 }
                             });*/
        /*AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://107.178.214.92/webservices/processes.php?tag=register", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                RequestParams params = new RequestParams();
                params.put("imei", imei_no);
                params.put("reg_id", "dont_know");
                params.put("androidId",androidId);
                params.put("ScreenWidth",width);
                params.put("ScreenHeight",height);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });*/
        //requestQueue = Volley.newRequestQueue(this);
        /*StringRequest postReq = new StringRequest(Request.Method.POST, "http://107.178.214.92/webservices/processes.php?tag=register"+"&imei="+imei_no+"&reg_id="+"random_i_dont_know"+"&androidId="+androidId+"&ScreenWidth="+width+"&ScreenHeight="+height,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        L.t(getApplicationContext(), "Posted user's phone details"); // We set the response data in the TextView
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        L.T(getApplicationContext(),"Couldn't post data");
                    }
                }) ;
        if (postReq!=null) {
            requestQueue.add(postReq);
        }
        else{
            L.T(getApplicationContext(), "postreq is null");
        }*/

        //getActionBar().hide();
        /*Thread logoTimer = new Thread() {
            public void run(){
                try{
                    int logoTimer = 0;
                    while(logoTimer<100){
                        sleep(100);
                        new FadeInAnimation(findViewById(R.id.gamezoplogosplash)).animate();
                        logoTimer+=100;
                    }
                    while(logoTimer < 5000){
                        sleep(100);
                        logoTimer = logoTimer +100;
                        new BlinkAnimation(findViewById(R.id.gamezoplogosplash)).animate();
                    };
                    new FadeOutAnimation(findViewById(R.id.gamezoplogosplash)).animate();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

                catch (InterruptedException e) {

                    e.printStackTrace();
                }

                finally{
                    finish();
                }
            }
        };*/

        //logoTimer.start();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new FadeInAnimation(findViewById(R.id.gamezoplogosplash)).animate();
            }
        }, 200);

        for (int j=0;j<10;j++) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new BlinkAnimation(findViewById(R.id.gamezoplogosplash)).animate();
                }
            }, 200);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new FadeOutAnimation(findViewById(R.id.gamezoplogosplash)).animate();
            }
        }, 200);*/
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, 10000);*/


        sendPhoneValues(imei_no, regId, androidId, w, h, deviceName, deviceMan, deviceVersion);

                /*YoYo.with(Techniques.Landing).duration(500).playOn(findViewById(R.id.gamezoplogosplash));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.TakingOff).duration(500).playOn(findViewById(R.id.gamezoplogosplash));

            }
        }, 3000);*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }, 1500);

    }

    /*private boolean isContactPresent(String phoneNumber, List<Contacts> contacts){
        for(int i = 0; i<contacts.size();i++){
            if(contacts.get(i).phoneNumber==phoneNumber){
                return true;
            }
        }
        return false;
    }*/


    /*public void getAll(){
        Select select = new Select();
        List<Contacts> contactList = select.all().from(Contacts.class).execute();

        // Iterate through the ArrayList to get all our data. We ll simply add
        // all the data to our StringBuilder to display it inside a Toast.

        StringBuilder builder = new StringBuilder();
        for (Contacts contact : contactList) {
            builder.append("Name: ")
                    .append(contact.name)
                    .append("Phone Number: ")
                    .append(contact.phoneNumber)
                    .append("\n");
        }

        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
    }*/

    private void sendPhoneValues(String imei, String regID, String androidId, String w, String h, String deviceName, String deviceMan, String deviceVersion){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "http://107.178.214.92/webservices/processes.php?tag=register&imei="+imei+"&reg_id="+regID+"&androidId="+androidId+"&ScreenWidth="+w+"&ScreenHeight="+h+"&myDeviceModel="+deviceName+"&myDeviceCompany="+deviceMan+"&myDeviceVersion="+deviceVersion,

                (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //L.t(getActivity(), response.toString());
                        //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG);
                        parseResponseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //L.t(getApplicationContext(), "No response Error");
            }
        });

        requestQueue.add(request);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    public void parseResponseJson(JSONObject response){
        try {
            int successint = response.getInt("success");
            if(successint == 1){
                //L.t(getApplicationContext(), "SUCCESS");
            }
            else{
                //L.t(getApplicationContext(), "FAILURE");
            }
        }catch(Exception e){
            //L.t(getApplicationContext(),"Could not parse response json");
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
