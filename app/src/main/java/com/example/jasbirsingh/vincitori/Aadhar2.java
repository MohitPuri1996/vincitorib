package com.example.jasbirsingh.vincitori;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Aadhar2 extends AppCompatActivity {
    private IntentIntegrator qrScan;
    public TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar2);
        qrScan = new IntentIntegrator(this);
        text= (TextView) findViewById(R.id.text);

        qrScan.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json

                    //setting values to textviews
                    parseDetails(result);
                    text.setText(result.getContents());
                    Toast.makeText(getApplicationContext(),"this is after part",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void parseDetails(IntentResult result) {
        Toast.makeText(getApplicationContext(),"in",Toast.LENGTH_LONG).show();
        String res=result.getContents();
        try {
//            DocumentBuilderFactory doc= DocumentBuilderFactory.newInstance();
//            DocumentBuilder db=doc.newDocumentBuilder();
//            InputSource is=new InputSource();
//            is.setCharacterStream(new StringReader(res));
//            Toast.makeText(getApplicationContext(),"in2",Toast.LENGTH_LONG).show();
//            Document d = db.parse(is);
//            Toast.makeText(getApplicationContext(),"in3",Toast.LENGTH_LONG).show();
//            NodeList nodes = d.getElementsByTagName("PrintLetterBarcodeData");
//            Toast.makeText(getApplicationContext(),"in4",Toast.LENGTH_LONG).show();
//            Element line = (Element) nodes.item(0);
//            Toast.makeText(getApplicationContext(),line.getAttribute("uid"),Toast.LENGTH_LONG).show();
//            String uid=line.getAttribute("uid");
//            Toast.makeText(getApplicationContext(),uid,Toast.LENGTH_LONG).show();
            String name="Mohit Puri";
            String uid="5808 0376 9982";
            String gender="MALE";
            String yob="1996";
            String fname="Suresh Puri";
            String loc="Anand Vihar,Uttam Nagar";
            String state="Delhi";
            String pc="110059";
            Toast.makeText(getApplicationContext(),pc+"",Toast.LENGTH_LONG);
            StringBuffer response=new StringBuffer("");
            response.append("UserId"+uid);
            response.append("Name"+name);
            response.append("Gender"+gender);
            response.append("Year of Birth"+yob);
            response.append("Father "+fname);
            response.append("Loc"+loc);
            response.append("State"+state);
            response.append("PC"+pc);
            text.setText(response.toString());



            SharedPreferences preferences=this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("UserId","5808 0376 9982");
            editor.commit();
            Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
            new Submitting().execute(uid,name,gender,yob,fname,loc,state,pc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private class Submitting extends AsyncTask<String,Void,String> {
        ProgressDialog progress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(Aadhar2.this);
            progress.setMessage("Registring....");
            progress.show();
        }

        @Override
        protected String doInBackground(String... string) {
            String uid=string[0];
            String name=string[1];
            String gender=string[2];
            String yob=string[3];
            String house=string[4];
            String loc=string[5];
            String state=string[6];
            String pc=string[7];
            JSONObject jsonObject=new JSONObject();
            String response="";
            try {
                jsonObject.put("uid",uid);
                jsonObject.put("name",name);
                jsonObject.put("gender",gender);
                jsonObject.put("yob",yob);
                jsonObject.put("fname",house);
                jsonObject.put("loc",loc);
                jsonObject.put("state",state);
                jsonObject.put("pc",pc);
                URL url = new URL("https://6824f751.ngrok.io/save-a-user"); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                if(inputStream==null){
                    Log.e("Manual","input stream is null");
                }
                StringBuffer buffer=new StringBuffer();
                String line="";
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                while((line=bufferedReader.readLine())!=null){
                    buffer.append(line+"\n");
                }
                response=buffer.toString();
                Log.d("SubmitActivity",response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);
            Log.d("TAG","sca"+res);
            String r=res.trim();
            if(r.equals("1")){
                Toast.makeText(Aadhar2.this,"User Successfully Registered",Toast.LENGTH_SHORT).show();
                progress.cancel();
                SharedPreferences preferences=Aadhar2.this.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putInt("reg",1);
                editor.commit();
            }
        }
    }






}
