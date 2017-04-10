package com.example.admin.wmsapp.Activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.admin.wmsapp.Activity.Model.Login;
import com.example.admin.wmsapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.example.admin.wmsapp.Activity.Model.Constants.APPID;
import static com.example.admin.wmsapp.Activity.Model.Constants.EMAIL;
import static com.example.admin.wmsapp.Activity.Model.Constants.EXPTOKEN;
import static com.example.admin.wmsapp.Activity.Model.Constants.NAME;
import static com.example.admin.wmsapp.Activity.Model.Constants.ORGID;
import static com.example.admin.wmsapp.Activity.Model.Constants.ORGNAME;
import static com.example.admin.wmsapp.Activity.Model.Constants.ROLE;


public class LoginActivity extends AppCompatActivity {
    static EditText editUsername,editUserPassword;

    String app_name="WMS",token="",user,password;

    public  final String BASE_URL="http://192.168.100.40:1337/pFactory/";

      LinearLayout loginLayout;

   /* SharedPreferences sharedpreferences;*/
   // {"_id":"58b7e3e7edbefd04c89a98cb","email":"pavitra.rastogi@adnatesolutions.com","name":"Pavitra Rastogi","_orgName":"Adnate","_orgId":"adnate","exp":1490875793,"iat":1490270993}
   /* public static final String MyPREFERENCES = "MyPrefs" */;
    public static  String role="";
    public static  String name="" ;
    public static  String email ="";
    public static  String _orgId="";
    public static  String _orgName="";
    public static  String exp="";
    public static  String _a="";
    public static String _r="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        editUsername= (EditText) findViewById(R.id.editUserName);
        editUserPassword= (EditText) findViewById(R.id.editUserPassword);
        loginLayout= (LinearLayout) findViewById(R.id.loginLayout);
        loginLayout.setAlpha(0.5f);



    }

    public void login(View v){
        user=editUsername.getText().toString();
        password=editUserPassword.getText().toString();
        new SendPostRequest().execute();

    }


     class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

               URL url = new URL("http://192.168.100.40:1337/pFactory/mlogin");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email",user);
                postDataParams.put("password",password);
                postDataParams.put("app",app_name);
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(
                            new InputStreamReader(
                                    conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

            try {
                JSONObject jsonObject=new JSONObject(result.toString());

              token=jsonObject.getString("token");
               role=jsonObject.getString("role");

                Log.d("token","token"+token);
               /* Log.d("role","role"+role);*/
            } catch (JSONException e) {
                e.printStackTrace();
            }



          String[] jwtParts = token.split("\\.");
          //bytes= Base64.decode(token.getBytes(),Base64.DEFAULT);
            //String str = new String(jwtPayload, StandardCharsets.UTF_8);

            byte[] result1 = Base64.decode(jwtParts[1],Base64.DEFAULT);
            System.out.println(Arrays.toString(result1));

            String decodedString = new String(result1);
            try {
                JSONObject accessToken=new JSONObject(decodedString);

                 name=accessToken.getString("name");
                 email=accessToken.getString("email");
                 _orgName=accessToken.getString("_orgName");
                 _orgId=accessToken.getString("_orgId");
                 exp=accessToken.getString("exp");
                 _a=accessToken.getString("_a");
                _r=accessToken.getString("_r");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);

                preferences.edit()
                    .putString(EXPTOKEN,exp)
                    .putString(NAME,name)
                    .putString(EMAIL,email)
                    .putString(ROLE,_r)
                    .putString(ORGID,_orgId)
                    .putString(ORGNAME,_orgName)
                    .putString(APPID,_a)
                    .commit();

                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Login Exception Some filed not found", Toast.LENGTH_SHORT).show();
            }


            Log.d("result1","result1"+result1);
           // {"_id":"58b7e3e7edbefd04c89a98cb","email":"pavitra.rastogi@adnatesolutions.com","name":"Pavitra Rastogi","_orgName":"Adnate","_orgId":"adnate","exp":1490875793,"iat":1490270993}



        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }




    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent  intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
