package com.steven.testopencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_change;
    private ImageView mImageView;

    private String TAG="mCallback";
    private BaseLoaderCallback mBaseLoaderCallback=new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    Log.i(TAG, "成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.i(TAG, "加载失败");
                    break;
            }
        }
    };
    private Bitmap srcBitmap;
    private Bitmap grayBitmap;
    private boolean isFirst=true;
    private boolean flag=true;

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_10,this,mBaseLoaderCallback);
        }else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mBaseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_change=findViewById(R.id.bt_change);
        mImageView=findViewById(R.id.iv_pic);

        bt_change.setOnClickListener(this);
    }

    private void changeSrc2Gray(){
        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.totoro);
        grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Utils.bitmapToMat(srcBitmap, rgbMat);//convert original bitmap to Mat, R G B.
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY,1);//rgbMat to gray grayMat
        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
        Log.i(TAG, "procSrc2Gray sucess...");
    }

    @Override
    public void onClick(View view) {
        if(isFirst){
            changeSrc2Gray();
            isFirst = false;
        }
        if(flag){
            mImageView.setImageBitmap(grayBitmap);
            bt_change.setText("查看原图");
            flag = false;
        }else{
            mImageView.setImageBitmap(srcBitmap);
            bt_change.setText("灰度化");
            flag = true;
        }
    }
}
