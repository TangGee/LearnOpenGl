package com.yumin.mp3encoder;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private static final boolean DEBUG = true;

    private String verticesShader;
    private String fragmentShader;
    private int uColorLocation;
    private int aPositionLocation;


    AirHockeyRenderer(Context context) {
        fragmentShader = ShaderLoader.readTextFileFromRawResource(context,R.raw.fragment_shader);
        verticesShader = ShaderLoader.readTextFileFromRawResource(context,R.raw.vertices_shader);
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
        uColorLocation = GLES20.glGetUniformLocation(mProgramId, ShaderData.U_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(mProgramId,ShaderData.A_POSITION);
        VerticesData.verticesData.position(0);
        GLES20.glVertexAttribPointer(aPositionLocation,
                VerticesData.POSITION_COMPONENT_COUNT,GL_FLOAT,false,0,
                VerticesData.verticesData);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //画三角形
        GLES20.glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        if (VerticesData.use_triangle_fan) {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6);
        } else {
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
        }

        GLES20.glUniform4f(uColorLocation,1.0f,0f,0f,1.0f);
        //分割线
        GLES20.glDrawArrays(GLES20.GL_LINES,6,2);

        //点1
        GLES20.glUniform4f(uColorLocation,0f,0f,1f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,8,1);

        //点2
        GLES20.glUniform4f(uColorLocation,1.0f,0f,0f,1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS,9,1);
    }
}
