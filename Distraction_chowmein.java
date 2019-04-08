package comi.hello.distraction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Distraction_chowmein {

    //Declaring...
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;

    private Rect detectCollision;
    Random random = new Random();//Random function

    public  Distraction_chowmein(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.chowmein);//Getting image from drawable folder
        //Set values
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        speed = random.nextInt(6) + 10;//For random speed
        //Set X and Y
        x = screenX;
        y = random.nextInt(maxY) - bitmap.getHeight();
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());//For detecting collision
    }

    //Update speed
    public void update(int playerSpeed) {
        x -= playerSpeed;
        x -= speed;
        if (x < minX - bitmap.getWidth()) {

            speed = random.nextInt(10) + 10;//updating  speed randomly
            x = maxX;
            y = random.nextInt(maxY) - bitmap.getHeight();
        }
        //Detecting collision
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }
    //Set X coordinate
    public void setX(int x){
        this.x = x;
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
}
