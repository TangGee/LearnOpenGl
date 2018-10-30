package com.yumin.mp3encoder;

import android.opengl.GLES20;

import com.yumin.mp3encoder.utils.Logger;

public class ShaderHelper {
    private static Logger logger = new Logger("ShaderHelper",true);

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GLES20.GL_VERTEX_SHADER,shaderCode);
    }


    public static int compileFragmentShader(String shaderCode) {
        return compileShader(GLES20.GL_FRAGMENT_SHADER,shaderCode);
    }
    private static int compileShader(int type, String shaderCode) {
        int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId==0) {
            logger.w("clould not create shader:"+GLES20.glGetError());
            return 0;
        }
        GLES20.glShaderSource(shaderObjectId,shaderCode);
        GLES20.glCompileShader(shaderObjectId);

        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId,GLES20.GL_COMPILE_STATUS,compileStatus,0);
        logger.i("shader compile status "+(compileStatus[0]+"")
                +" "+GLES20.glGetShaderInfoLog(shaderObjectId));
        if (compileStatus[0] ==0) { //编译失败
            GLES20.glDeleteShader(shaderObjectId);
            logger.w("glCompileShader error");
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertextId,int fragmengId) {
        int programId = GLES20.glCreateProgram();
        if (programId ==0) {
            logger.w("cloud not create new program");
            return 0;
        }

        GLES20.glAttachShader(programId,vertextId);
        GLES20.glAttachShader(programId,fragmengId);
        GLES20.glLinkProgram(programId);

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programId,GLES20.GL_LINK_STATUS,linkStatus,0);
        logger.i("Results of linking program:\n" + GLES20.glGetProgramInfoLog(programId));
        if (linkStatus[0]==0) {
            GLES20.glDeleteProgram(programId);
            logger.w("link faild");
            return 0;
        }
        return programId;
    }

    public static boolean validateProgram(int programId) {
        GLES20.glValidateProgram(programId);
        int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programId,GLES20.GL_VALIDATE_STATUS,validateStatus,0);
        logger.i("Result of validating program: "+ validateStatus[0]
                +" \nLog:"+GLES20.glGetProgramInfoLog(programId));
        return (validateStatus[0]!=0);
    }

    public static void useProgram(int programId) {
        GLES20.glUseProgram(programId);
    }
}
