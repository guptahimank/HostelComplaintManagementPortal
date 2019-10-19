package himankgupta.hostelcomplaintmanagementportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Lodge_complaint extends AppCompatActivity {
    EditText etcomplaint;
    Spinner spinnerRoomNumber, spinnerType;
    String roomNumber[] = {"Select Room Number", "1", "2", "3", "4", "5", "6"};
    String type[] = {"Select Complaint Type", "Carpentry", "Electricity", "Plumbing", "Air Conditioner", "Geyser", "Others"};
    String selectedRoomNumber, selectedType, roll_no;
    EditText edtTextRollNumber;
    Button btLodgeComplaint,btCancelComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodge_complaint);
        edtTextRollNumber = (EditText) findViewById(R.id.editTextLodgeRollNumber);
        btLodgeComplaint = (Button) findViewById(R.id.buttonLodgeComplaintSubmit);
        btCancelComplaint = (Button)findViewById(R.id.buttonLodgeComplaintCancel);
        etcomplaint = (EditText) findViewById(R.id.editTextComplaint);

        SharedPreferences sharedPreferences = getSharedPreferences("Complaint Box", MODE_PRIVATE);
        roll_no = sharedPreferences.getString("Roll_no", null);
        edtTextRollNumber.setText(roll_no);

        spinnerRoomNumber = (Spinner) findViewById(R.id.spinnerRoomNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, roomNumber);
        spinnerRoomNumber.setAdapter(adapter);


        spinnerType = (Spinner) findViewById(R.id.spinnerComplaintType);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, type);
        spinnerType.setAdapter(adapter1);

        spinnerRoomNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRoomNumber = spinnerRoomNumber.getSelectedItem().toString();
                System.out.println("Selected Room Number:" + selectedRoomNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = spinnerType.getSelectedItem().toString();
                System.out.println("Selected Type:" + selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btLodgeComplaint.setOnClickListener(new View.OnClickListener() {

            InputStream is = null;
            @Override
            public void onClick(View view) {



                if (selectedRoomNumber.equals("Select Room Number")) {
                    Toast.makeText(Lodge_complaint.this, "Please select valid room Number", Toast.LENGTH_LONG).show();
                }
                if (selectedType.equals("Select Complaint Type")) {
                    Toast.makeText(Lodge_complaint.this, "Please select valid complaint Type", Toast.LENGTH_LONG).show();
                } else {
                    String complaintDescription = etcomplaint.getText().toString();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    //Calendar cal = Calendar.getInstance();
                    Date date = new Date();
                    String date1 = dateFormat.format(date);
                    //System.out.println(dateFormat.format(cal));
                  //  Toast.makeText(Lodge_complaint.this, date1, Toast.LENGTH_SHORT).show();
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("description", complaintDescription));
                    nameValuePairs.add(new BasicNameValuePair("roomNumber", selectedRoomNumber));
                    nameValuePairs.add(new BasicNameValuePair("complaintType", selectedType));
                    nameValuePairs.add(new BasicNameValuePair("roll_no", roll_no));
                    nameValuePairs.add(new BasicNameValuePair("date", date1));


                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpClient httpClient = new DefaultHttpClient();
                        HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/lodge.php");
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpClient.execute(httpPost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();

                        StringWriter writer = new StringWriter();
                        IOUtils.copy(is, writer, "UTF-8");
                        String response1 = writer.toString();
                        System.out.println("Response:" + response1);
                        try {
                            JSONObject jsonObject = new JSONObject(response1);
                            String msg = jsonObject.getString("query_result");
                            if(msg.equals("SUCCESS"))
                            {
                                Toast.makeText(getApplicationContext(), "Successfully Entered", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Lodge_complaint.this,Student.class);
                                startActivity(i);
                                finish();
                            } else if (msg.equals("FAILURE")) {
                                Toast.makeText(Lodge_complaint.this, "Try Again Later", Toast.LENGTH_LONG).show();
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

        btCancelComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Lodge_complaint.this,Student.class);
                startActivity(i);
                finish();
            }
        });

    }

}

