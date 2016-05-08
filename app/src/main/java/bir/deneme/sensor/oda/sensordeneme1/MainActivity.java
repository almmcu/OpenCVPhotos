package bir.deneme.sensor.oda.sensordeneme1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import bir.deneme.sensor.oda.sensordeneme1.accdeneme.AccelerometerExample;
import bir.deneme.sensor.oda.sensordeneme1.camera.FeatureDetectionOnPhotoActivity2;
import bir.deneme.sensor.oda.sensordeneme1.camera.TakePhotoActivity;
import bir.deneme.sensor.oda.sensordeneme1.image.ImageActivity;
import bir.deneme.sensor.oda.sensordeneme1.photo.CmeraComponentActivity;
import bir.deneme.sensor.oda.sensordeneme1.photo.FeatureDetectionOnPhotoActivity;
import bir.deneme.sensor.oda.sensordeneme1.video.SurfFeatureDetectionOnVideoActivity;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        button = (Button) findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                TextView txt = (TextView) findViewById(R.id.textView1);

                int min = 0;
                int max = 4;

                Random r = new Random();
                int i1 = r.nextInt(max - min ) + min;
                String[] yemek = new String[100];
                yemek[0] = "P";
                yemek[1] = "A R";
                yemek[2] = "T";
                yemek[3] = "Y Y";

                txt.setText("\n\n\n------ >%d".concat(yemek[i1]));
                Intent  intent = new Intent(getApplicationContext(), SurfFeatureDetectionOnVideoActivity.class);
//                Intent  intent = new Intent(getApplicationContext(), CmeraComponentActivity.class);
                startActivity(intent);




            }

        });

        button = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                TextView txt = (TextView) findViewById(R.id.textView1);
                txt.setText("");

                Intent  intent = new Intent(getApplicationContext(), TakePhotoActivity.class);
//                intent = new Intent(getApplicationContext(), FeatureDetectionOnPhotoActivity2.class);
                try {

                   /* Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                    i.putExtra("IMG_PATH_1", "1.jpg");
                    i.putExtra("IMG_PATH_2", "2.jpg");
                    i.putExtra("NE_TARAF", true);

                    startActivity(i);*/
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        button = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                Intent  intent = new Intent(getApplicationContext(), AccelerometerExample.class);
//                intent = new Intent(getApplicationContext(), FeatureDetectionOnPhotoActivity2.class);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
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

        return super.onOptionsItemSelected(item);
    }
}
