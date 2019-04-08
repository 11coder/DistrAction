package comi.hello.distraction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Student {

    //Declaring...
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;
    private boolean boost;
    private final int GRAVITY = -10;
    private int maxY;
    private int minY;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    private Rect detectCollision;

    public Student(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);//Getting image from drawable folder
        //Set values
        x = 75;
        y = 50;
        speed = 1;
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        boost=false;
        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());//For detecting collision
    }

    //Update boost functions
    public void setBoosting() {
        boost = true;
    }
    public void stopBoosting() {
        boost = false;
    }
    public void update(){

        //updating  speed
        if (boost) {
            speed += 3;
        } else {
            speed -= 5;
        }
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        y -= speed + GRAVITY;
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }
        //Detecting collision
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //Return if colllision is there
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //Get Bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    //Get X and Y coordinates
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Returning speed
    public int getSpeed() {
        return speed;
    }
}
