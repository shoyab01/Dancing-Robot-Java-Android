package com.example.coursera21;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.TimeZoneFormat;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

class MyView extends View {
    private Paint redPaint, bluePaint, greenPaint, lightbluePaint; //paint object for drawing the lines
    private Coordinate[]cube_vertices;//the vertices of a 3D cube
    private Coordinate[]head_vertices, neck_vertices, body_vertices, rrh1_vertices, rrh2_vertices,
            rrh3_vertices, rlh1_vertices, rlh2_vertices, rlh3_vertices, waist_vertices, rrl1_vertices,
            rrl2_vertices, rrl3_vertices, rll1_vertices, rll2_vertices, rll3_vertices;
    public MyView(Context context) {
        super(context, null);
        final MyView thisview=this;

        //redPaint
        redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPaint.setStyle(Paint.Style.FILL);//Stroke
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(2);

        //bluePaint
        bluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bluePaint.setStyle(Paint.Style.FILL);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStrokeWidth(2);

        //greenPaint
        greenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenPaint.setStyle(Paint.Style.FILL);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStrokeWidth(2);

        //lightbluePaint
        lightbluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lightbluePaint.setStyle(Paint.Style.FILL);
        lightbluePaint.setColor(Color.BLUE);
        lightbluePaint.setStrokeWidth(2);

        //create a 3D cube
        cube_vertices = new Coordinate[8];
        cube_vertices[0] = new Coordinate(-1, -1, -1, 1);
        cube_vertices[1] = new Coordinate(-1, -1, 1, 1);
        cube_vertices[2] = new Coordinate(-1, 1, -1, 1);
        cube_vertices[3] = new Coordinate(-1, 1, 1, 1);
        cube_vertices[4] = new Coordinate(1, -1, -1, 1);
        cube_vertices[5] = new Coordinate(1, -1, 1, 1);
        cube_vertices[6] = new Coordinate(1, 1, -1, 1);
        cube_vertices[7] = new Coordinate(1, 1, 1, 1);

        //head initialize
        head_vertices=translate(cube_vertices,6,2.12,5);
        head_vertices=scale(head_vertices,80,80,80);

        //neck initialize
        neck_vertices=translate(cube_vertices,19.2,11,16);
        neck_vertices=scale(neck_vertices,25,25,25);

        //body initialize
        body_vertices = translate(cube_vertices,3,2.5,5);
        body_vertices=scale(body_vertices,160,200,80);

        //robot right hand - 1 initialize
        rrh1_vertices = translate(cube_vertices,5.4,5,5);
        rrh1_vertices=scale(rrh1_vertices,50,75,80);

        //robot right hand - 2 initialize
        rrh2_vertices = translate(cube_vertices,5.4,5.5,5);
        rrh2_vertices=scale(rrh2_vertices,50,100,80);

        //robot right hand - 3 initialize
        rrh3_vertices = translate(cube_vertices,5.4,22,3);
        rrh3_vertices = scale(rrh3_vertices,50,31,120);

        //robot left hand - 1 initialize
        rlh1_vertices = translate(cube_vertices,13.8,5,5);
        rlh1_vertices = scale(rlh1_vertices,50,75,80);

        //robot left hand - 2 initialize
        rlh2_vertices = translate(cube_vertices,13.8,5.5,5);
        rlh2_vertices = scale(rlh2_vertices,50,100,80);

        //robot left hand - 3 initialize
        rlh3_vertices = translate(cube_vertices,13.8,22,3);
        rlh3_vertices = scale(rlh3_vertices,50,31,120);

        //waist initialize
        waist_vertices = translate(cube_vertices,3,15,5);
        waist_vertices = scale(waist_vertices,160,50,80);

        //robot right leg - 1 initialize
        rrl1_vertices = translate(cube_vertices,7.4,9,5);
        rrl1_vertices = scale(rrl1_vertices,50,100,80);

        //robot right leg - 2 initialize
        rrl2_vertices = translate(cube_vertices,7.4,9,5);
        rrl2_vertices = scale(rrl2_vertices,50,125,80);

        //robot right leg - 3 initialize
        rrl3_vertices = translate(cube_vertices,7.4,41.35,3);
        rrl3_vertices = scale(rrl3_vertices,50,31,120);

        //robot left leg - 1 initialize
        rll1_vertices = translate(cube_vertices,11.8,9,5);
        rll1_vertices = scale(rll1_vertices,50,100,80);

        //robot left leg - 2 initialize
        rll2_vertices = translate(cube_vertices,11.8,9,5);
        rll2_vertices = scale(rll2_vertices,50,125,80);

        //robot left leg - 3 initialize
        rll3_vertices = translate(cube_vertices,11.8,41.35,3);
        rll3_vertices = scale(rll3_vertices,50,31,120);

        //RotateRobot(0, 90, 0);

        thisview.invalidate();//update the view

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int i = -45;
                boolean dir = true;
                if(i==-45 && dir == false)
                    dir = true;
                else if(dir == true && i==45)
                    dir = false;
                if(dir)
                {
                    i++;
                    RotateRobot(0, i, 0);
                }
                else
                {
                    i--;
                    RotateRobot(0, i, 0);
                }
                thisview.invalidate();
            }
        };
        timer.scheduleAtFixedRate(task,100,500);

    }

    public void RotateRobot(double thetaX, double thetaY, double thetaZ)
    {
        head_vertices = QuaternionRotate(head_vertices, thetaX, 1,0,0);
        head_vertices = QuaternionRotate(head_vertices, thetaY, 0,1,0);
        head_vertices = QuaternionRotate(head_vertices, thetaZ, 0,0,1);

        neck_vertices = QuaternionRotate(neck_vertices, thetaX, 1,0,0);
        neck_vertices = QuaternionRotate(neck_vertices, thetaY, 0,1,0);
        neck_vertices = QuaternionRotate(neck_vertices, thetaZ, 0,0,1);

        body_vertices = QuaternionRotate(body_vertices, thetaX, 1,0,0);
        body_vertices = QuaternionRotate(body_vertices, thetaY, 0,1,0);
        body_vertices = QuaternionRotate(body_vertices, thetaZ, 0,0,1);

        rrh1_vertices = QuaternionRotate(rrh1_vertices, thetaX, 1,0,0);
        rrh1_vertices = QuaternionRotate(rrh1_vertices, thetaY, 0,1,0);
        rrh1_vertices = QuaternionRotate(rrh1_vertices, thetaZ, 0,0,1);

        rrh2_vertices = QuaternionRotate(rrh2_vertices, thetaX, 1,0,0);
        rrh2_vertices = QuaternionRotate(rrh2_vertices, thetaY, 0,1,0);
        rrh2_vertices = QuaternionRotate(rrh2_vertices, thetaZ, 0,0,1);

        rrh3_vertices = QuaternionRotate(rrh3_vertices, thetaX, 1,0,0);
        rrh3_vertices = QuaternionRotate(rrh3_vertices, thetaY, 0,1,0);
        rrh3_vertices = QuaternionRotate(rrh3_vertices, thetaZ, 0,0,1);

        rlh1_vertices = QuaternionRotate(rlh1_vertices, thetaX, 1,0,0);
        rlh1_vertices = QuaternionRotate(rlh1_vertices, thetaY, 0,1,0);
        rlh1_vertices = QuaternionRotate(rlh1_vertices, thetaZ, 0,0,1);

        rlh2_vertices = QuaternionRotate(rlh2_vertices, thetaX, 1,0,0);
        rlh2_vertices = QuaternionRotate(rlh2_vertices, thetaY, 0,1,0);
        rlh2_vertices = QuaternionRotate(rlh2_vertices, thetaZ, 0,0,1);

        rlh3_vertices = QuaternionRotate(rlh3_vertices, thetaX, 1,0,0);
        rlh3_vertices = QuaternionRotate(rlh3_vertices, thetaY, 0,1,0);
        rlh3_vertices = QuaternionRotate(rlh3_vertices, thetaZ, 0,0,1);

        waist_vertices = QuaternionRotate(waist_vertices, thetaX, 1,0,0);
        waist_vertices = QuaternionRotate(waist_vertices, thetaY, 0,1,0);
        waist_vertices = QuaternionRotate(waist_vertices, thetaZ, 0,0,1);

        rrl1_vertices = QuaternionRotate(rrl1_vertices, thetaX, 1,0,0);
        rrl1_vertices = QuaternionRotate(rrl1_vertices, thetaY, 0,1,0);
        rrl1_vertices = QuaternionRotate(rrl1_vertices, thetaZ, 0,0,1);

        rrl2_vertices = QuaternionRotate(rrl2_vertices, thetaX, 1,0,0);
        rrl2_vertices = QuaternionRotate(rrl2_vertices, thetaY, 0,1,0);
        rrl2_vertices = QuaternionRotate(rrl2_vertices, thetaZ, 0,0,1);

        rrl3_vertices = QuaternionRotate(rrl3_vertices, thetaX, 1,0,0);
        rrl3_vertices = QuaternionRotate(rrl3_vertices, thetaY, 0,1,0);
        rrl3_vertices = QuaternionRotate(rrl3_vertices, thetaZ, 0,0,1);

        rll1_vertices = QuaternionRotate(rll1_vertices, thetaX, 1,0,0);
        rll1_vertices = QuaternionRotate(rll1_vertices, thetaY, 0,1,0);
        rll1_vertices = QuaternionRotate(rll1_vertices, thetaZ, 0,0,1);

        rll2_vertices = QuaternionRotate(rll2_vertices, thetaX, 1,0,0);
        rll2_vertices = QuaternionRotate(rll2_vertices, thetaY, 0,1,0);
        rll2_vertices = QuaternionRotate(rll2_vertices, thetaZ, 0,0,1);

        rll3_vertices = QuaternionRotate(rll3_vertices, thetaX, 1,0,0);
        rll3_vertices = QuaternionRotate(rll3_vertices, thetaY, 0,1,0);
        rll3_vertices = QuaternionRotate(rll3_vertices, thetaZ, 0,0,1);
    }

    private void DrawLinePairs(Canvas canvas, Coordinate[] vertices, int start, int end, Paint paint)
    {//draw a line connecting 2 points
        //canvas - canvas of the view
        //points - array of points
        //start - index of the starting point
        //end - index of the ending point
        //paint - the paint of the line
        canvas.drawLine((int)vertices[start].x,(int)vertices[start].y,(int)vertices[end].x,(int)vertices[end].y,paint);
    }

    private void DrawCube(Canvas canvas, Coordinate[]draw_cube_vertices, Paint c_paint)
    {//draw a cube on the screen
        DrawLinePairs(canvas, draw_cube_vertices, 0, 1, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 1, 3, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 3, 2, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 2, 0, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 4, 5, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 5, 7, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 7, 6, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 6, 4, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 0, 4, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 1, 5, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 2, 6, c_paint);
        DrawLinePairs(canvas, draw_cube_vertices, 3, 7, c_paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw objects on the screen
        super.onDraw(canvas);
        DrawCube(canvas,head_vertices, bluePaint);//draw a cube onto the screen
        DrawCube(canvas,neck_vertices, redPaint);
        DrawCube(canvas,body_vertices,redPaint);
        DrawCube(canvas,rrh1_vertices,bluePaint);
        DrawCube(canvas,rrh2_vertices,greenPaint);
        DrawCube(canvas,rrh3_vertices,bluePaint);
        DrawCube(canvas,rlh1_vertices,bluePaint);
        DrawCube(canvas,rlh2_vertices,greenPaint);
        DrawCube(canvas,rlh3_vertices,lightbluePaint);
        DrawCube(canvas,waist_vertices,redPaint);
        DrawCube(canvas,rrl1_vertices,bluePaint);
        DrawCube(canvas,rrl2_vertices,greenPaint);
        DrawCube(canvas,rrl3_vertices,redPaint);
        DrawCube(canvas,rll1_vertices,bluePaint);
        DrawCube(canvas,rll2_vertices,greenPaint);
        DrawCube(canvas,rll3_vertices,redPaint);
    }
    //*********************************
    //matrix and transformation functions
    public double []GetIdentityMatrix()
    {//return an 4x4 identity matrix
        double []matrix=new double[16];
        matrix[0]=1;matrix[1]=0;matrix[2]=0;matrix[3]=0;
        matrix[4]=0;matrix[5]=1;matrix[6]=0;matrix[7]=0;
        matrix[8]=0;matrix[9]=0;matrix[10]=1;matrix[11]=0;
        matrix[12]=0;matrix[13]=0;matrix[14]=0;matrix[15]=1;
        return matrix;
    }
    public Coordinate Transformation(Coordinate vertex,double []matrix)
    {//affine transformation with homogeneous coordinates
        //i.e. a vector (vertex) multiply with the transformation matrix
        // vertex - vector in 3D
        // matrix - transformation matrix
        Coordinate result=new Coordinate();
        result.x=matrix[0]*vertex.x+matrix[1]*vertex.y+matrix[2]*vertex.z+matrix[3];
        result.y=matrix[4]*vertex.x+matrix[5]*vertex.y+matrix[6]*vertex.z+matrix[7];
        result.z=matrix[8]*vertex.x+matrix[9]*vertex.y+matrix[10]*vertex.z+matrix[11];
        result.w=matrix[12]*vertex.x+matrix[13]*vertex.y+matrix[14]*vertex.z+matrix[15];
        return result;
    }
    public Coordinate[]Transformation(Coordinate []vertices,double []matrix)
    {   //Affine transform a 3D object with vertices
        // vertices - vertices of the 3D object.
        // matrix - transformation matrix
        Coordinate []result=new Coordinate[vertices.length];
        for (int i=0;i<vertices.length;i++)
        {
            result[i]=Transformation(vertices[i],matrix);
            result[i].Normalise();
        }
        return result;
    }
    //***********************************************************
    //Affine transformation
    public Coordinate []translate(Coordinate []vertices,double tx,double ty,double tz)
    {
        double []matrix=GetIdentityMatrix();
        matrix[3]=tx;
        matrix[7]=ty;
        matrix[11]=tz;
        return Transformation(vertices,matrix);
    }
    public Coordinate []QuaternionRotate(Coordinate []vertices, double theta, int abtX, int abtY, int abtZ)
    {
        double w = cos(Math.toRadians(theta/2));
        double x = sin(Math.toRadians(theta/2)) * abtX;
        double y = sin(Math.toRadians(theta/2)) * abtY ;
        double z = sin(Math.toRadians(theta/2)) * abtZ;

        double []matrix = GetIdentityMatrix();
        matrix[0] = w*w + x*x - y*y - z*z;
        matrix[1] = 2*x*y - 2*w*z;
        matrix[2] = 2*x*z + 2*w*y;
        matrix[3] = 0;
        matrix[4] = 2*x*y + 2*w*z;
        matrix[5] = w*w + y*y - x*x - z*z;
        matrix[6] = 2*y*z - 2*w*x;
        matrix[7] = 0;
        matrix[8] = 2*x*z -2*w*y;
        matrix[9] = 2*y*z + 2*w*x;
        matrix[10] = w*w + z*z - x*x - y*y;
        matrix[11] = 0;
        matrix[12] = 0;
        matrix[13] = 0;
        matrix[14] = 0;
        matrix[15] = 1;

        return Transformation(vertices, matrix);
    }
    private Coordinate[]scale(Coordinate []vertices,double sx,double sy,double sz)
    {
        double []matrix=GetIdentityMatrix();
        matrix[0]=sx;
        matrix[5]=sy;
        matrix[10]=sz;
        return Transformation(vertices,matrix);
    }
}
