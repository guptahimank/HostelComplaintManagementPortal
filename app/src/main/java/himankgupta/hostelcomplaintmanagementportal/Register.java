package himankgupta.hostelcomplaintmanagementportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    EditText etname, etroll_no, etpassword;
    Button bsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        etname = (EditText) findViewById(R.id.editname);
        etroll_no = (EditText) findViewById(R.id.editroll_no);
        etpassword = (EditText) findViewById(R.id.editpassword);
        bsubmit = (Button) findViewById(R.id.button6);

        bsubmit.setOnClickListener(new View.OnClickListener(){

            InputStream is = null;
            @Override
            public void onClick(View arg0){

                String name = ""+etname.getText().toString();
                String roll_no = ""+etroll_no.getText().toString();
                String password = ""+etpassword.getText().toString();
                if(name.equals("") ||
                        roll_no.equals("") ||
                        password.equals("")){
                    String msg = "One or more fields are empty";
                    etname.setText("");
                    etpassword.setText("");
                    etroll_no.setText("");

                    Toast.makeText(Register.this,msg,Toast.LENGTH_SHORT).show();
                }
                else {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("name", name));
                    nameValuePairs.add(new BasicNameValuePair("roll_no", roll_no));
                    nameValuePairs.add(new BasicNameValuePair("password", password));


                    try {
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/tutorial.php");
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
                           if(msg.equals("Already Existed"))
                           {
                               Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                           }
                           else if(msg.equals("SUCCESS"))
                           {
                               Toast.makeText(getApplicationContext(), "Data Entered Successfully", Toast.LENGTH_LONG).show();
                               Intent i=new Intent(Register.this,LoginActivity.class);
                               startActivity(i);
                           }
                           else if(msg.equals("FAILURE"))
                           {
                               Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

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
/*
public class Register extends ActionBarActivity {

    EditText et_name, et_rollno, et_email, et_mobileno, et_dob, et_hostel, et_roomno, et_block, et_password, et_confirmpassword;
    Button bsubmit;

    public static String url = "YOUR_URL_HERE_PHP_FILE";
    String name, email, phone;
    InputStream is = null;
    String exceptionMessage = "There seems to be some problem connecting to database. " +
            "Please check your Internet Connection and try again.";
    String successMessage = "Data has been entered successfully";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

            et_name = (EditText) findViewById(R.id.editText10);
            et_rollno = (EditText) findViewById(R.id.editText11);
            et_email = (EditText) findViewById(R.id.editText13);
            et_mobileno = (EditText) findViewById(R.id.editText14);
            et_dob = (EditText) findViewById(R.id.editText15);
            et_hostel = (EditText) findViewById(R.id.editText16);
            et_roomno = (EditText) findViewById(R.id.editText19);
            et_block = (EditText) findViewById(R.id.editText20);
            et_password = (EditText) findViewById(R.id.editText17);
            et_confirmpassword = (EditText) findViewById(R.id.editText18);
            bsubmit = (Button) findViewById(R.id.button6);
            bsubmit.setOnClickListener(new View.onclickListner(){


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = et_name.getText().toString();
                email = et_email.getText().toString();
                phone = et_mobileno.getText().toString();

                if(name.equals("") ||
                        email.equals("") ||
                        phone.equals("")){
                    String msg = "One or more fields are empty";
                    etName.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                }
                else{

                    List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                    nameValuePairList.add(new BasicNameValuePair("name", name));
                    nameValuePairList.add(new BasicNameValuePair("email", email));
                    nameValuePairList.add(new BasicNameValuePair("phone", phone));

                    try{
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost(url);
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HttpEntity httpEntity = httpResponse.getEntity();
                        is = httpEntity.getContent();
                        etName.setText("");
                        etEmail.setText("");
                        etPhone.setText("");
                        Toast.makeText(getApplicationContext(), successMessage, Toast.LENGTH_SHORT).show();
                        is.close();
                    }catch(IOException e){
                        Toast.makeText(getApplicationContext(), exceptionMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        else if( id == R.id.action_retrieve_data){
            Intent it = new Intent(MainActivity.this, RetrieveData.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }
}
*/