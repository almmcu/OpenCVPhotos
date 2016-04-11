package bir.deneme.sensor.oda.sensordeneme1.accdeneme;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import bir.deneme.sensor.oda.sensordeneme1.R;

public class AccelerometerExample extends AppCompatActivity implements SensorEventListener {

    TextView txtAcc;
    StringBuilder builder = new StringBuilder();

    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer_example);
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

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        txtAcc = (TextView) findViewById(R.id.txtAccle);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange > 1){
            direction[0] = "SOL";
        }
        else if (xChange < -1){
            direction[0] = "SAG";
        }

        if (yChange > 2){
            direction[1] = "YUKARI";
        }
        else if (yChange < -2){
            direction[1] = "ASAGI";
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
        builder.append(direction[1]);

        txtAcc.setText(builder.toString());

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
