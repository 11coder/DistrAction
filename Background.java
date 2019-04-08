package comi.hello.distraction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Background {

    //Declaring...
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed ;
    private int maxX;
    private int minX=0;

    private int maxY;
    private int minY=0;
    Random random = new Random();//Random function

    public Background(int screenX, int screenY) {

        //Setting values
        maxX=screenX;
        maxY=screenY;

        speed = random.nextInt(10);//For random speed
        x = random.nextInt(screenX);
        y = random.nextInt(screenY);
    }

    //Update
    public void update(int playerSpeed) {

        x -= playerSpeed;
        //Random appearance of stars and their random speed
        if (x < 0) {
            y = random.nextInt(maxY);
            x=random.nextInt(maxX);
            speed = random.nextInt(15);
        }
    }

    //Get X and Y coordinates
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
