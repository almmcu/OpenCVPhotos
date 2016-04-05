package bir.deneme.sensor.oda.sensordeneme1.photo;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.security.Policy;

import bir.deneme.sensor.oda.sensordeneme1.R;

public class CmeraComponentActivity extends AppCompatActivity {

    android.hardware.Camera mCamera;
    int focal_length;
    Policy.Parameters params;
    File mfile;
    android.hardware.Camera.Parameters cameraParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmera_component);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCamera = android.hardware.Camera.open();
        launchTakePhoto();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void launchTakePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraParameters = mCamera.getParameters();
        android.hardware.Camera.CameraInfo myInfo = new android.hardware.Camera.CameraInfo();
        float   l = cameraParameters.getFocalLength();
        System.out.println(l);

    }

}
