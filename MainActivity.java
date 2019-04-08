package comi.hello.distraction;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //Declaring buttons of menu
    private ImageButton btnHelp;
    private ImageButton btnScore;
    private ImageButton btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Connecting buttons with xml file by their id
        btnPlay = (ImageButton) findViewById(R.id.Play);
        btnScore = (ImageButton) findViewById(R.id.Score);
        btnHelp = (ImageButton) findViewById(R.id.Help);
        //Opening new activities with each respective button
        btnPlay.setOnClickListener(new View.OnClickListener()
                                                                                     {
                                                                                         public void onClick(View v)
                                                                                         { Intent Open=new Intent(MainActivity.this,GameActivity.class);
                                                                                             startActivity(Open);
                                                                                         }
                                                                                     }
        );
        btnScore.setOnClickListener(new View.OnClickListener()
                                    {
                                        public void onClick(View v)
                                        {

                                            Intent Open=new Intent(MainActivity.this,HighScore.class);
                                            startActivity(Open);
                                        }
                                    }
        );
        btnHelp.setOnClickListener(new View.OnClickListener()
                                   {
                                       public void onClick(View v)
                                       {

                                           Intent Open=new Intent(MainActivity.this,GameHelp.class);
                                           startActivity(Open);
                                       }
                                   }
        );
    }
    //Giving menu for exit using AlertDialog Builder
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
