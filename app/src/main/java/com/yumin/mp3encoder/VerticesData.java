package com.yumin.mp3encoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VerticesData {

    public static final boolean use_triangle_fan = true;

    private static final int BYTE_PRE_FLOAT = 4;
    public static final FloatBuffer verticesData;
    public static final int POSITION_COMPONENT_COUNT = 2;
    public static final int COLOR_COMPONENT_COUNT = 3;

    public static final int VERTICES_STRIDE = (POSITION_COMPONENT_COUNT+COLOR_COMPONENT_COUNT)*BYTE_PRE_FLOAT;

     private static float[] tableVerticesWithTriangles = {
            //三角形1
            -0.5f,-0.5f,
            0.5f,0.5f,
            -0.5f,0.5f,

            //三角形2
            -0.5f,-0.5f,
            0.5f,-0.5f,
            0.5f,0.5f,

            //中线
            -0.5f,0f,
            0.5f,0f,

            0f,-0.25f,
            0f,0.25f
    };

     //三角形扇
    private static float[] tableVerticesWithTriangles_Fan = {
            //三角形扇
             0,0, 1,1,1,
             -0.5f,-0.5f, 0.7f,0.7f,0.7f,
             0.5f,-0.5f, 0.7f,0.7f,0.7f,
             0.5f,0.5f, 0.7f,0.7f,0.7f,
             -0.5f,0.5f, 0.7f,0.7f,0.7f,
             -0.5f,-0.5f, 0.7f,0.7f,0.7f,

            //中线
            -0.5f,0f,1,0,0,
            0.5f,0f, 1,0,0,

            0f,-0.25f,0,0,1,
            0f,0.25f,1,0,0
    };

    static {
        if (use_triangle_fan) {
            verticesData = ByteBuffer.allocateDirect(
                    tableVerticesWithTriangles_Fan.length*BYTE_PRE_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            verticesData.put(tableVerticesWithTriangles_Fan);
        } else {
            verticesData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length*BYTE_PRE_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            verticesData.put(tableVerticesWithTriangles);
        }
    }
}
