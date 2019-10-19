package himankgupta.hostelcomplaintmanagementportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import himankgupta.hostelcomplaintmanagementportal.adapter.DisplayComplaintStudentAdapter;
import himankgupta.hostelcomplaintmanagementportal.modal.DisplayComplaintModal;

public class Student extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textViewNav,textViewNoComplaint;
    ListView listViewDisplayComplaint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewNav  = (TextView)findViewById(R.id.navigationDrawerStudentName);
        listViewDisplayComplaint = (ListView)findViewById(R.id.listViewDisplayComplaint);
        textViewNoComplaint = (TextView)findViewById(R.id.textViewNoComplaint);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new DisplayComplaints().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.pers_details) {
            Intent i=new Intent(Student.this,EditDetailsStudent.class);
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.change_pass) {
            Intent i=new Intent(Student.this,Change_pass_student.class);
            startActivity(i);

        } else if (id == R.id.new_complaint) {
            Intent intent = new Intent(Student.this,Lodge_complaint.class);
            startActivity(intent);

        }else if (id == R.id.help) {
            Intent i=new Intent(Student.this,Help.class);
            startActivity(i);
        }
        else if (id == R.id.logout) {
            finish();

            Intent intent = new Intent(Student.this,
                    LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class DisplayComplaints extends AsyncTask<Void,Void,String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Student.this);
            progressDialog.show();
            progressDialog.setMessage("Loading Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            InputStream is = null;
            SharedPreferences sharedPreferences = getSharedPreferences("Complaint Box",MODE_PRIVATE);
            String rollNumber = sharedPreferences.getString("Roll_no",null);

            String response1 = null;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("roll_no", rollNumber));

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/studentretrieve.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                is = entity.getContent();

                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");
                response1 = writer.toString();
                System.out.println("Response:" + response1);



            }catch(ClientProtocolException e){
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch(IOException e){
                Log.e("Log_tag", "IOException");
                e.printStackTrace();
            }



            return response1;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.cancel();

            if(result!="null")
            {
                List<DisplayComplaintModal> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    DisplayComplaintModal displayComplaintModal = new DisplayComplaintModal();

                    JSONArray jsonArray = jsonObject.getJSONArray("complaints");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        displayComplaintModal = new DisplayComplaintModal();
                        JSONObject complaints = jsonArray.getJSONObject(i);
                        displayComplaintModal.setComplaint_no(complaints.getString("complaint_no"));
                        displayComplaintModal.setStatus(complaints.getString("status"));
                        displayComplaintModal.setDescription(complaints.getString("description"));
                        displayComplaintModal.setType(complaints.getString("type"));
                        displayComplaintModal.setDate(complaints.getString("date"));
                        list.add(displayComplaintModal);
                    }


                    DisplayComplaintStudentAdapter adapter = new DisplayComplaintStudentAdapter(Student.this,list);
                    listViewDisplayComplaint.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                textViewNoComplaint.setVisibility(View.VISIBLE);
            }


        }
    }
}
