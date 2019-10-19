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
import android.widget.AdapterView;
import android.widget.ListView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import himankgupta.hostelcomplaintmanagementportal.adapter.DisplayComplaintCaretakerAdapter;
import himankgupta.hostelcomplaintmanagementportal.modal.DisplayComplaintCaretakerModal;

public class Caretaker extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listViewComplaints;
    TextView textViewNoComplaintCaretaker;
    String selectedComplaintNo, selectedComplaintStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewComplaints = (ListView) findViewById(R.id.listViewDisplayComplaintCaretaker);
        textViewNoComplaintCaretaker = (TextView) findViewById(R.id.textViewNoComplaintCaretaker);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new DisplayComplaints().execute();
        listViewComplaints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                TextView complaintID = (TextView) view.findViewById(R.id.textViewComplaintID2);
                selectedComplaintNo = complaintID.getText().toString();
                Toast.makeText(Caretaker.this, selectedComplaintNo, Toast.LENGTH_LONG).show();

                final Spinner selectedValue = (Spinner) view.findViewById(R.id.spinnerComplaintStatus2);
                selectedValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        selectedComplaintStatus = selectedValue.getSelectedItem().toString();

                        Log.e("Selected Values:", selectedComplaintNo + selectedComplaintStatus);

                        if (selectedComplaintNo != null && selectedComplaintStatus != null) {
                            // update status
                            new UpdateComplaints().execute();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


            }
        });

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
        getMenuInflater().inflate(R.menu.caretaker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.edit_pers) {
            Intent i = new Intent(Caretaker.this, Edit_details_caretaker.class);
            startActivity(i);
        } else if (id == R.id.change_pass) {
            Intent i = new Intent(Caretaker.this, Change_pass_caretaker.class);
            startActivity(i);

        } else if (id == R.id.help) {
            Intent i = new Intent(Caretaker.this, Help.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            finish();

            Intent intent = new Intent(Caretaker.this,
                    LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class DisplayComplaints extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Caretaker.this);
            progressDialog.show();
            progressDialog.setMessage("Loading Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            InputStream is = null;
            SharedPreferences sharedPreferences = getSharedPreferences("Complaint Box", MODE_PRIVATE);
            String rollNumber = sharedPreferences.getString("Roll_no", null);

            String response1 = null;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/cartakerretrievecomplaints.php");
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                is = entity.getContent();

                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");
                response1 = writer.toString();
                System.out.println("Response:" + response1);


            } catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Log_tag", "IOException");
                e.printStackTrace();
            }


            return response1;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.cancel();

            if (result != null) {
                List<DisplayComplaintCaretakerModal> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    DisplayComplaintCaretakerModal displayComplaintModal = new DisplayComplaintCaretakerModal(Caretaker.this);

                    JSONArray jsonArray = jsonObject.getJSONArray("complaints");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        displayComplaintModal = new DisplayComplaintCaretakerModal(Caretaker.this);
                        JSONObject complaints = jsonArray.getJSONObject(i);
                        displayComplaintModal.setComplaint_no(complaints.getString("complaint_no"));
                        displayComplaintModal.setStatus(complaints.getString("status"));
                        displayComplaintModal.setDescription(complaints.getString("description"));
                        displayComplaintModal.setType(complaints.getString("type"));
                        displayComplaintModal.setDate(complaints.getString("date"));
                        list.add(displayComplaintModal);
                    }


                    DisplayComplaintCaretakerAdapter adapter = new DisplayComplaintCaretakerAdapter(Caretaker.this, list);
                    listViewComplaints.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                textViewNoComplaintCaretaker.setVisibility(View.VISIBLE);
            }


        }
    }

    class UpdateComplaints extends AsyncTask<Void, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Caretaker.this);
            progressDialog.show();
            progressDialog.setMessage("Updating Data");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            InputStream is = null;

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("complaint_no", selectedComplaintNo));
            if (selectedComplaintStatus.equals("In Progress")) {
                selectedComplaintStatus = "2";
            } else if (selectedComplaintStatus.equals("Finished")) {
                selectedComplaintStatus = "3";
            }
            nameValuePairs.add(new BasicNameValuePair("status", selectedComplaintStatus));

            String response1 = null;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://guptahimank01.000webhostapp.com/updatecomplaintstatus.php");
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                is = entity.getContent();

                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");
                response1 = writer.toString();
                System.out.println("Response:" + response1);


            } catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Log_tag", "IOException");
                e.printStackTrace();
            }


            return response1;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.cancel();

            if (result != null) {

                try {
                    JSONObject jsonObject = new JSONObject(result);

                    String str = jsonObject.getString("query_result");

                    if (str.equals("SUCCESS"))
                        Toast.makeText(Caretaker.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    else if (str.equals("FAILURE")) {
                        Toast.makeText(Caretaker.this, "Try Again", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
