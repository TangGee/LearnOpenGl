package com.yumin.mp3encoder;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRendererV5 implements GLSurfaceView.Renderer {

    private static final boolean DEBUG = true;

    private String verticesShader;
    private String fragmentShader;
    private int aPositionLocation;
    private int aColorLocation;
    private int uPositionMatrixLocation;
    private float[] projectionMatrix = new float[16];


    AirHockeyRendererV5(Context context) {
        fragmentShader = ShaderLoader.readTextFileFromRawResource(context,R.raw.fragment_shaderv2);
        verticesShader = ShaderLoader.readTextFileFromRawResource(context,R.raw.vertices_shaderv5);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0,0,0,0);
        int shaderFragmentId = ShaderHelper.compileFragmentShader(fragmentShader);
        int shaderVertexId = ShaderHelper.compileVertexShader(verticesShader);
        int mProgramId = ShaderHelper.linkProgram(shaderVertexId,shaderFragmentId);
        if (DEBUG) {
            if(!ShaderHelper.validateProgram(mProgramId)){
                Log.e("AAAA","fuck");
            }
        }
        ShaderHelper.useProgram(mProgramId);

        aPositionLocation = GLES20.glGetAttribLocation(mProgramId,ShaderData.A_POSITION);
        VerticesDataV5.verticesData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation,
                VerticesDataV5.POSITION_COMPONENT_COUNT,GL_FLOAT,false,
                VerticesDataV5.VERTICES_STRIDE,
                VerticesDataV5.verticesData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);

        aColorLocation = GLES20.glGetAttribLocation(mProgramId,ShaderData.A_COLOR);
        VerticesDataV5.verticesData.position(VerticesDataV5.POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation,
                VerticesDataV5.COLOR_COMPONENT_COUNT,GL_FLOAT,false,
                VerticesDataV5.VERTICES_STRIDE,
                VerticesDataV5.verticesData);
        GLES20.glEnableVertexAttribArray(aColorLocation);

        uPositionMatrixLocation = GLES20.glGetUniformLocation(mProgramId,ShaderData.U_MATRIX);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0,0,width,height);

        float aspectRatio = width>height?width*1.0f/height:height*1.0f/width;
        if (width>height) {
            Matrix.orthoM(projectionMatrix,0,-aspectRatio,aspectRatio,
                    -1,1,-1,1);
        } else {
            Matrix.orthoM(projectionMatrix,0,-1,1,
                    -aspectRatio,aspectRatio,-1,1);
        }
        GLES20.glUniformMatrix4fv(uPositionMatrixLocation,1,
                false,projectionMatrix,0);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //画三角形
        if (VerticesDataV5.use_triangle_fan) {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);
        } else {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
        }

        //分割线
        GLES20.glDrawArrays(GLES20.GL_LINES,6,2);

        //点1
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);

        //点2
        GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);
    }
}
