package comi.hello.distraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class GameHelp extends AppCompatActivity {
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_help);
        back= (Button) findViewById(R.id.back);
        //Exit the game using back button
        back.setOnClickListener(new View.OnClickListener()
                                {
                                    public void onClick(View v)
                                    {
                                        finish();
                                    }
                                }
        );
    }
}
