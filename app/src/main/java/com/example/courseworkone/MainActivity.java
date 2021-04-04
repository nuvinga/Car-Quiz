package com.example.courseworkone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private boolean timerSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openIdentifyMakeActivity(View view){
        Intent makeIntent = new Intent(this, IdentifyMakeActivity.class);
        timerSwitch = ((Switch)findViewById(R.id.timer_switch)).isChecked();
        makeIntent.putExtra("timerSwitch", timerSwitch);
        Log.i("MAIN_PASSING_TIMER_DATA", String.valueOf(timerSwitch)); // for testing only
        startActivity(makeIntent);
    }

    public void openHintsActivity(View view) {
        Intent hintsIntent = new Intent(this, HintsActivity.class);
        timerSwitch = ((Switch)findViewById(R.id.timer_switch)).isChecked();
        hintsIntent.putExtra("timerSwitch", timerSwitch);
        Log.i("MAIN_PASSING_TIMER_DATA", String.valueOf(timerSwitch)); // for testing only
        startActivity(hintsIntent);
    }

    public void openIdentifyImageActivity(View view) {
        Intent imageIntent = new Intent(this, IdentifyImageActivity.class);
        timerSwitch = ((Switch)findViewById(R.id.timer_switch)).isChecked();
        imageIntent.putExtra("timerSwitch", timerSwitch);
        Log.i("MAIN_PASSING_TIMER_DATA", String.valueOf(timerSwitch)); // for testing only
        startActivity(imageIntent);
    }

    public void openAdvancedActivity(View view) {
        Intent advancedIntent = new Intent(this, AdvancedActivity.class);
        timerSwitch = ((Switch)findViewById(R.id.timer_switch)).isChecked();
        advancedIntent.putExtra("timerSwitch", timerSwitch);
        Log.i("MAIN_PASSING_TIMER_DATA", String.valueOf(timerSwitch)); // for testing only
        startActivity(advancedIntent);
    }

    public void help (View view) {
        final AlertDialog.Builder help = new AlertDialog.Builder(this);
        help.setTitle("How to operate Car Quiz- W1761350");
        help.setMessage("This application was developed by Nuvin Godakanda Arachchi, 2019443, W1761350" +
                " as partial completion of the BEng(Hons) Software Engineering degree program provided " +
                "bt the University of Westminster at IIT, Sri Lanka.\n\n1) Identify Car Make \nShows the" +
                " user a image and a spinner, where they are to select the image shown. \n\n2) Hints Game" +
                "\nThis also shows a image, however with a text box. The user is to type in a single letter" +
                ", where if is included in the make name will replace the hyphens. Only 3 attempts are " +
                "provided. \n\n3) Identify car Image\nThis is the VV of the 1st function, where the user is " +
                "to select the image of the car described in the description.\n\n4) Advanced level"+" \nThe user" +
                " is provided with three images and co-responding text boxes, where the user is to type the" +
                " name of the car show. Only three attempts are allowed.\n\n\nThank you.");
        help.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                help.setCancelable(true);
            }
        });
        help.show();
    }

}