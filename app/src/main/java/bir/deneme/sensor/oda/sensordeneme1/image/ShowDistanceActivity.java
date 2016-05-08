package bir.deneme.sensor.oda.sensordeneme1.image;

import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

import bir.deneme.sensor.oda.sensordeneme1.R;
import bir.deneme.sensor.oda.sensordeneme1.kmeans.Point;

public class ShowDistanceActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private String imgPath1 = "";
    HashMap<Point, Double> distMap = new HashMap<>();
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_distance);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                imgPath1 = extras.getString("IMG_PATH_1");
                distMap = (HashMap<Point, Double>) extras.get("DİST_MAP");

            }
        } else {
            imgPath1  = (String) savedInstanceState.getSerializable("IMG_PATH_1");

        }
        String path = Environment.getExternalStorageDirectory() + "/AutoExperiment2/" + imgPath1;
        BitmapDrawable d = new BitmapDrawable(path);
        relativeLayout = (RelativeLayout) findViewById(R.id.showRelativelayout);
        relativeLayout.setBackgroundDrawable(d);

        TextView txtMeasure;


        Iterator<Point> keySetIterator = distMap.keySet().iterator();
        while(keySetIterator.hasNext()){
            Point key = keySetIterator.next();
            System.out.println("key: " + key + " value: " + distMap.get(key));
            txtMeasure = new TextView(this);
            txtMeasure.setText("----->" + distMap.get(key));




            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layout.setX((float) (key.getX() / 1.275));
            layout.setY((float) ((key.getY()) / 1.1475));
            layout.setBackground(getDrawable(R.drawable.text_bg));
            layout.addView(txtMeasure);

            relativeLayout.addView(layout);

        }
    }

}
