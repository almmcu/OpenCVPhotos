package bir.deneme.sensor.oda.sensordeneme1.video;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.util.List;

import bir.deneme.sensor.oda.sensordeneme1.R;

public class SurfFeatureDetectionOnVideoActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private Mat                    mRgba;
    private Mat                    mGrayMat;
    private CameraBridgeViewBase   mOpenCvCameraView;

    Mat descriptors ;
    List<Mat> descriptorsList;

    FeatureDetector featureDetector;
    MatOfKeyPoint keyPoints;
    DescriptorExtractor descriptorExtractor;
    DescriptorMatcher descriptorMatcher;

    boolean mIsJavaCamera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surf_feature_detection_on_video);


        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    //Log.i(TAG, "OpenCV loaded successfully");
                    System.loadLibrary("opencv_java");
                    System.loadLibrary("nonfree");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGrayMat = new Mat();
        featureDetector=FeatureDetector.create(FeatureDetector.SIFT);
        descriptorExtractor=DescriptorExtractor.create(DescriptorExtractor.SURF);
        descriptorMatcher=DescriptorMatcher.create(6);
        keyPoints = new MatOfKeyPoint();
        descriptors = new Mat();
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        final Mat rgba = inputFrame.rgba();

        Imgproc.cvtColor(rgba, rgba, Imgproc.COLOR_RGBA2GRAY);
        featureDetector.detect(rgba, keyPoints);
        Features2d.drawKeypoints(rgba, keyPoints, rgba);
        return rgba;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

        /*if (myOpenCvCameraView != null)
            myOpenCvCameraView.disableView();*/
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();

        /*if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();*/
    }
}
