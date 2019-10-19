package himankgupta.hostelcomplaintmanagementportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText etusername, etpassword;
    Button bstudent,bcaretaker, bwarden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etusername = (EditText) findViewById(R.id.editTextUsername);
        etpassword = (EditText) findViewById(R.id.editTextPassword);
        bstudent = (Button) findViewById(R.id.button);
        bcaretaker = (Button) findViewById(R.id.button3);
        bwarden = (Button) findViewById(R.id.button2);

        bstudent.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;
            @Override
            public void onClick(View arg0) {

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                if (username.equals("") || password.equals("")) {
                    String msg = "One or more fields are empty";
                    etusername.setText("");
                    etpassword.setText("");
                    Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                } else {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("roll_no", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));
                    nameValuePairs.add(new BasicNameValuePair("flag","0"));
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/verify.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        StringWriter writer = new StringWriter();
                        IOUtils.copy(is, writer, "UTF-8");
                        String response1 = writer.toString();
                        System.out.println("Response:"+ response1);
                        try {
                            JSONObject jsonObject = new JSONObject(response1);
                            String msg= jsonObject.getString("query_result");
                            if(msg.equals("Welcome"))
                            {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("Complaint Box", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Roll_no",username);
                                editor.putString("Password",password);
                                editor.commit();

                                Intent i=new Intent(LoginActivity.this,Student.class);
                                startActivity(i);
                                etusername.setText("");
                                etpassword.setText("");

                            }
                            else if(msg.equals("Denied"))
                            {
                                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocol", "Log_tag");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                    }


                }
            }

        });


        bcaretaker.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;
            @Override
            public void onClick(View arg0) {

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                if (username.equals("") || password.equals("")) {
                    String msg = "One or more fields are empty";
                    etusername.setText("");
                    etpassword.setText("");
                } else {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("roll_no", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));
                    nameValuePairs.add(new BasicNameValuePair("flag","3"));
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/verify.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        StringWriter writer = new StringWriter();
                        IOUtils.copy(is, writer, "UTF-8");
                        String response1 = writer.toString();
                        System.out.println("Response:"+ response1);
                        try {
                            JSONObject jsonObject = new JSONObject(response1);
                            String msg= jsonObject.getString("query_result");
                            if(msg.equals("Welcome"))
                            {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Intent i=new Intent(LoginActivity.this,Caretaker.class);
                                startActivity(i);
                                etusername.setText("");
                                etpassword.setText("");
                            }
                            else if(msg.equals("Denied"))
                            {
                                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocol", "Log_tag");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                    }


                }
            }

        });


        bwarden.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;
            @Override
            public void onClick(View arg0) {

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();
                if (username.equals("") || password.equals("")) {
                    String msg = "One or more fields are empty";
                    etusername.setText("");
                    etpassword.setText("");
                } else {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("roll_no", username));
                    nameValuePairs.add(new BasicNameValuePair("password", password));
                    nameValuePairs.add(new BasicNameValuePair("flag","2"));
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/verify.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        StringWriter writer = new StringWriter();
                        IOUtils.copy(is, writer, "UTF-8");
                        String response1 = writer.toString();
                        System.out.println("Response:"+ response1);
                        try {
                            JSONObject jsonObject = new JSONObject(response1);
                            String msg= jsonObject.getString("query_result");
                            if(msg.equals("Welcome"))
                            {
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                Intent i=new Intent(LoginActivity.this,Warden.class);
                                startActivity(i);
                                etusername.setText("");
                                etpassword.setText("");
                            }
                            else if(msg.equals("Denied"))
                            {
                                Toast.makeText(LoginActivity.this, "Wrong credentials", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocol", "Log_tag");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                    }


                }
            }

        });

    }


}
