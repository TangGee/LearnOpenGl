package com.yumin.mp3encoder;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


// 注意这知识个demo 正常情况应该把界面和业务分离
public class MainActivity extends AppCompatActivity {

    private  GLSurfaceView mGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!GLESUtils.isSupportGLES20(this)) {
            finish();
            Toast.makeText(this,"设备不支持",Toast.LENGTH_SHORT).show();
            return;
        }

        mGLSurfaceView= new GLSurfaceView(this);
        setContentView(mGLSurfaceView);

        //use 2.0
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new AirHockeyRendererV5(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGLSurfaceView!=null) {
            mGLSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGLSurfaceView!=null) {
            mGLSurfaceView.onResume();
        }
    }
}
