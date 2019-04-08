package comi.hello.distraction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView  extends SurfaceView implements Runnable {

    //Declaring...
    Context context;
    volatile boolean playing;
    private Thread gameThread=null;
    private Student player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private Distraction_chowmein chowmein;
    private Distraction_od od;
    private Destination des;
    private int count;
    private Distraction_pizza pizza;
    private int screenX;
    private int res=0;
    private ArrayList<Background> stars = new
            ArrayList<Background>();
    private boolean isGameOver ;
    private int score;
    static MediaPlayer gameOnsound;
    int highScore[] = new int[4];
    SharedPreferences sharedPreferences;
    private Blast blast;


    public GameView(Context context, int screenX, int screenY) {
        super(context);
       //Getting player
        player = new Student(context, screenX, screenY);

        //Getting holder and paint
        surfaceHolder = getHolder();
        paint = new Paint();
        int starNums = 100;
        //Getting background
        for (int i = 0; i < starNums; i++) {
            Background c  = new Background(screenX, screenY);
            stars.add(c);
        }

        //Getting different elements of game
        chowmein = new Distraction_chowmein(context, screenX, screenY);
        od = new Distraction_od(context, screenX, screenY);
        pizza = new Distraction_pizza(context, screenX, screenY);
        blast=new Blast(context);
        des = new Destination(context, screenX, screenY);
        this.screenX = screenX;
        isGameOver = false;//Flag for game over
        count = 0;
        score = 0;
        this.context = context;

        gameOnsound = MediaPlayer.create(context,R.raw.track);//Setting background music
        //Giving default value 0 to all scores using shared preferences
        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",Context.MODE_PRIVATE);
        highScore[0] = sharedPreferences.getInt("score1",0);
        highScore[1] = sharedPreferences.getInt("score2",0);
        highScore[2] = sharedPreferences.getInt("score3",0);
        highScore[3] = sharedPreferences.getInt("score4",0);
        gameOnsound.start();//start music
    }
//Function to update all functions while running
    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

//Update function for upadating all characters
    private void update() {

        score++;//Incrementing score
        player.update();//Updating player
        //Updating background according to background
        for (Background c : stars) {
            c.update(player.getSpeed());
        }
        //Setting blast image
        blast.setX(-250);
        blast.setY(-250);
        //updating chowmien and checking for collision with player
        chowmein.update(player.getSpeed());
        if (Rect.intersects(player.getDetectCollision(), chowmein.getDetectCollision())) {//If intersects
            //Setting blast image
            blast.setX(chowmein.getX());
            blast.setY(chowmein.getY());
            chowmein.setX(-200);
            playing = false;
            isGameOver = true;
            res=0;
            gameOnsound.stop();//Stop music
          //Checking whether score is greater than four high scores
            for(int i=0;i<4;i++){
                if(highScore[i]<score){
                     final int finalI = i;
                     highScore[i] = score;
                     break;
                     }
            }
            SharedPreferences.Editor e = sharedPreferences.edit();//Edit shared preferences
            for(int i=0;i<4;i++){
                int j = i+1;
                e.putInt("score"+j,highScore[i]);//Saving  score
                }
                e.apply();
            }

        pizza.update(player.getSpeed());//Update pizza
//Check for collision with player
        if(Rect.intersects(player.getDetectCollision(),pizza.getDetectCollision())){//If intersects

            //Setting blast image
            blast.setX(pizza.getX());
            blast.setY(pizza.getY());
            playing = false;
            isGameOver = true;
            res=0;
            gameOnsound.stop();//Stop music
            //Checking whether score is greater than four high scores
            for(int i=0;i<4;i++){
                if(highScore[i]<score){

                    final int finalI = i;
                    highScore[i] = score;
                    break;
                }
            }

            SharedPreferences.Editor e = sharedPreferences.edit();//Edit shared preferences
            for(int i=0;i<4;i++){
                int j = i+1;
                e.putInt("score"+j,highScore[i]);//Saving  score
            }
            e.apply();
        }
        od.update(player.getSpeed());//Updating od
//Check for collision with player
        if(Rect.intersects(player.getDetectCollision(),od.getDetectCollision())){//If intersects

            //Setting blast image
            blast.setX(od.getX());
            blast.setY(od.getY());
            playing = false;
            isGameOver = true;
            res=0;
            gameOnsound.stop();//Stop music
            //Checking whether score is greater than four high scores
            for(int i=0;i<4;i++){
                if(highScore[i]<score){

                    final int finalI = i;
                    highScore[i] = score;
                    break;
                }
            }

            SharedPreferences.Editor e = sharedPreferences.edit();//Edit shared preferences
            for(int i=0;i<4;i++){
                int j = i+1;
                e.putInt("score"+j,highScore[i]);//Saving  score
            }
            e.apply();
        }
        des.update(player.getSpeed());//Updating destination classroom
        if (Rect.intersects(player.getDetectCollision(), des.getDetectCollision())) {
            des.setX(-200);
            count++;//Incrementing count
            //If count is 5 then you reached the destination
            if (count == 5) {
                playing = false;
                isGameOver = true;
                res=1;
                gameOnsound.stop();//Stop music
                //Checking whether score is greater than four high scores
                for(int i=0;i<4;i++){
                    if(highScore[i]<score){
                        final int finalI = i;
                        highScore[i] = score;
                        break;
                        }
                }
                SharedPreferences.Editor e = sharedPreferences.edit();//Edit shared preferences
                for(int i=0;i<4;i++){
                    int j = i+1;
                    e.putInt("score"+j,highScore[i]);//Saving  score
                    }
                    e.apply();
            }
        }
    }

    //Draw function
    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();//Locking canvas
            //Setting color
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            //Making stars of background
            for (Background c : stars) {
                paint.setStrokeWidth(3.0f);
                canvas.drawPoint(c.getX(), c.getY(), paint);
            }
            //Drawing score
            paint.setTextSize(30);
            canvas.drawText("Score:"+score,100,50,paint);
//Drawing each character...
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
            canvas.drawBitmap(
                    chowmein.getBitmap(),
                    chowmein.getX(),
                    chowmein.getY(),
                    paint
            );
            canvas.drawBitmap(
                    des.getBitmap(),
                    des.getX(),
                    des.getY(),
                    paint
            );
            canvas.drawBitmap(
                    pizza.getBitmap(),
                    pizza.getX(),
                    pizza.getY(),
                    paint
            );
            canvas.drawBitmap(
                    od.getBitmap(),
                    od.getX(),
                    od.getY(),
                    paint
            );


            canvas.drawBitmap(
                    blast.getBitmap(),
                    blast.getX(),
                    blast.getY(),
                    paint
            );
            //Game over
            if(isGameOver){
                paint.setTextSize(50);
                paint.setTextAlign(Paint.Align.CENTER);

                int yPos=(int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
               //Giving message as per the result
                if(res==0)
                canvas.drawText("Game Over!You got distracted!",canvas.getWidth()/2,yPos,paint);
               else if(res==1)
                   canvas.drawText("Congrats!You reached class!",canvas.getWidth()/2,yPos,paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);//Unlock canvas
        }
    }

    //Control function
    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Pause function
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    //Resume function
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    //OnTouchEvent function for boosting speed of player as per the touch on screen
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();

                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();

                break;
        }
        if(isGameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context,MainActivity.class));//Back to main menu
            }
        }
        return true;
    }

    //Stop music function
    public static void stopMusic(){
        gameOnsound.stop();
    }
}

