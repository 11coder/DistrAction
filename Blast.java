package comi.hello.distraction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Blast {

    //Declaring...
    private Bitmap bitmap;
    private int x;
    private int y;

    public Blast(Context context) {

        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.blast);//Getting image from drawable folder

        //Setting values
        x = -300;
        y = -300;
    }
//Set X and Y coordinates
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Get bitmap
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
