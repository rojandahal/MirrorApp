package com.project.mirrorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import static com.project.mirrorapp.CameraInstance.getCameraInstance;

public class MainActivity extends AppCompatActivity {

    private String TAG = "Main Activity";
    private Camera mCamera;
    private CameraPreview mPreview;
    private int cameraID;
    private Button switchSide;
    private boolean frontFacing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchSide = findViewById(R.id.switchButton);
        cameraID = findBackFacingCamera();
        openCamera();

        switchSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.switchButton:
                        if(frontFacing){
                            Log.d(TAG, "onClick:Front Camera is ON Changing to BAck ");
                            cameraID = findBackFacingCamera();
                            openCamera();
                        }else {
                            Log.d(TAG, "onClick:Back Camera is ON Changing to Front ");
                            cameraID = findFrontFacingCamera();
                            openCamera();
                        }
                        break;
                }
            }
        });

    }

    private void openCamera(){
        // Create an instance of Camera
        mCamera = getCameraInstance(cameraID);

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(   this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.removeAllViews();
        preview.addView(mPreview);

        mPreview.setCameraDisplayOrientationAndSize(cameraID);

    }
    private int findBackFacingCamera() {
        frontFacing = false;
        int foundId = -1;
        int numCams = Camera.getNumberOfCameras();
        for (int camId = 0; camId < numCams; camId++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(camId, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                foundId = camId;
                break;
            }
        }
        return foundId;
    }

    private int findFrontFacingCamera() {
        frontFacing = true;
        int foundId = -1;
        int numCams = Camera.getNumberOfCameras();
        for (int camId = 0; camId < numCams; camId++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(camId, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                foundId = camId;
                break;
            }
        }
        return foundId;
    }

}