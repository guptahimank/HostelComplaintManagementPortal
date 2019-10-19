package himankgupta.hostelcomplaintmanagementportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        Thread th =new Thread(){

            @Override
            public void run()
            {
                try
                {
                    sleep(1500);

                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                Intent obj = new Intent(Splash_Screen.this,Front_Page.class);
                startActivity(obj);
                }
            }
        };
        th.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
