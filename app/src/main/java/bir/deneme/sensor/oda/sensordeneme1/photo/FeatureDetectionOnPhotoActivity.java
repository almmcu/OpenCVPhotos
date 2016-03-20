package bir.deneme.sensor.oda.sensordeneme1.photo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import bir.deneme.sensor.oda.sensordeneme1.R;

public class FeatureDetectionOnPhotoActivity extends AppCompatActivity {

    public static final String TAG = "Photo Activity ";
    MatOfKeyPoint keyPoints;
    MatOfKeyPoint logokeyPoints;

    List<DMatch> matchesList;
    DescriptorMatcher matcher;
    MatOfDMatch matches;
    MatOfDMatch gm;
    LinkedList<DMatch> good_matches;
    private String imgPath1 = "", imgPath2 = "";

    // Opencv Kontrol ve Kod yazma
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    System.loadLibrary("opencv_java");
                    System.loadLibrary("nonfree");
                    imgPath1 = "8ba3hb9l8tqeapr2tu88n45182.jpg";
                    imgPath2 = "8q0gsa9bvqcfqjg3dv372etk0q.jpg";
                    File file1 = new File(Environment.getExternalStorageDirectory(), "openCvPhotos/" + imgPath1);
                    File file2 = new File(Environment.getExternalStorageDirectory(), "openCvPhotos/" + imgPath2);
                    Mat image1, image2;
                    if (file1.exists() && file2.exists()) {
                        // Reesimleri Grayscale olarak okuma
                        image1 = Imgcodecs.imread(file1.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);
                        image2 = Imgcodecs.imread(file2.getAbsolutePath(), Imgcodecs.IMREAD_GRAYSCALE);

                        /**
                         * Keypoints ve bunlardan elde edilecek descriptorların hesaplanması
                         * Bu hesaplamalar için SURF algoritması kullanılıyor.
                         * Bu işlem her iki resim içinde aynı şekilde yapılıyor.
                         * Bundan somra iki elde edilen desriptorlar yardımıyla iki resiminkarşılaştırılması yapışlıyor.
                         **/
                        FeatureDetector SURF = FeatureDetector.create(FeatureDetector.SURF);

                        keyPoints = new MatOfKeyPoint();
                        logokeyPoints = new MatOfKeyPoint();
                        // İki reim içinde keypoints hesabı
                        SURF.detect(image1, keyPoints);
                        SURF.detect(image2, logokeyPoints);

                        Log.e(TAG, "#keypoints " + keyPoints.size());
                        Log.e(TAG, "#logokeypoints " + logokeyPoints.size());

                        DescriptorExtractor SurfExtractor = DescriptorExtractor
                                .create(DescriptorExtractor.SURF);
                        Mat descriptors = new Mat();
                        Mat logoDescriptors = new Mat();
                        // İki resim içinde desriptor hesabı
                        SurfExtractor.compute(image1, keyPoints, descriptors);
                        SurfExtractor.compute(image2, logokeyPoints, logoDescriptors);
                        /**
                         * İki resimin karşılaştırma işlemi burada yapılıyor
                         *
                         * */
                        gm = new MatOfDMatch();
                        matches = new MatOfDMatch();
                        good_matches = new LinkedList<>();

                        double max_dist = 0;
                        double min_dist = 100;

                        matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
                        try {
                            matcher.match(descriptors, logoDescriptors, matches);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        matchesList = matches.toList();
                        // En uzak ve En yakın mesafeler hesaplanıyor.
                        for (int i = 0; i < descriptors.rows(); i++) {
                            Double dist = (double) matchesList.get(i).distance;
                            if (dist < min_dist) min_dist = dist;
                            if (dist > max_dist) max_dist = dist;
                        }
                        // En iyi eşleşen noktalar bulunuyor.
                        // En yakın mesafenin 1.5 katı büyüklüğünde olan bütün mesafeler alınıyor.
                        for (int i = 0; i < descriptors.rows(); i++) {
                            if (matchesList.get(i).distance < 1.5 * min_dist) {
                                good_matches.addLast(matchesList.get(i));
                            }
                        }
                        /**
                         * Eşleşen noktalar bulundu.
                         * Eşleşen noktaların koordinatları bulunacak.
                         *
                         * */

                        /**
                         * !!!!!!!!!!!!!!!!!!!!!!!!!!
                         * Buradan sonra eşleşen noktların doğru bulunup bulunmadığı,
                         * Doğru ise yorumunun nasıl yapılacağı tartışılacak.
                         * Bunun nasıl yapılacağını öğrenmen gerekiyor.
                         * !!!!!!!!!!!!!!!!!!!!!!!!!!
                         * */
                        gm.fromList(good_matches);

                        List<KeyPoint> keypoints_objectList = keyPoints.toList();
                        List<KeyPoint> keypoints_sceneList = logokeyPoints.toList();
                        MatOfPoint2f obj = new MatOfPoint2f();
                        MatOfPoint2f scene = new MatOfPoint2f();
                        LinkedList<Point> objList = new LinkedList<>();
                        LinkedList<Point> sceneList = new LinkedList<>();
                        for (int i = 0; i < good_matches.size(); i++) {
                            objList.addLast(keypoints_objectList.get(good_matches.get(i).queryIdx).pt);
                            sceneList.addLast(keypoints_sceneList.get(good_matches.get(i).trainIdx).pt);
                        }
                        obj.fromList(objList);
                        Mat Mat1 = new Mat();
                        scene.fromList(sceneList);

                        Mat H = Calib3d.findHomography(obj, scene);

                        Mat warpimg = Mat1.clone();
                        org.opencv.core.Size ims = new org.opencv.core.Size(Mat1.cols(), Mat1.rows());
                        // hata veriyor mat1 boş olduğundan diye tahmin ediyorum
                        Imgproc.warpPerspective(Mat1, warpimg, H, ims);

                    }
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_detection_on_photo);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                imgPath1 = extras.getString("IMG_PATH_1");
                imgPath2 = extras.getString("IMG_PATH_2");
            }
        } else {
            imgPath1 = (String) savedInstanceState.getSerializable("IMG_PATH_1");
            imgPath2 = (String) savedInstanceState.getSerializable("IMG_PATH_2");
        }

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


    @Override
    public void onResume() {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}