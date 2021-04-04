package com.example.courseworkone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class IdentifyMakeActivity extends AppCompatActivity {

    private boolean timerSwitch;
    private Spinner spinner;
    private String correctCarName;
    private TextView countdown;
    private TextView answerShow;
    private Button identifyButton;
    private TextView text;
    private ImageView imageView;
    private int previousIndex = 0;
    private int[] cars;
    private String[] carNames;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        timerSwitch = intent.getBooleanExtra("timerSwitch",true);
        Log.i("MAKE: TIMER: ", String.valueOf(timerSwitch)); // for testing
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_make);

        //getting data from
        CarList carInstance = new CarList(1);
        cars = carInstance.getAllResourcesList();
        carNames = carInstance.getAllNameList();

        // initializing all elements used
        answerShow = (TextView) findViewById(R.id.correctAsnwerText);
        countdown = (TextView) findViewById(R.id.countdownText);
        text = (TextView) findViewById(R.id.guessText);
        imageView = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        identifyButton = (Button) findViewById(R.id.submitButton);

        // dynamically allocating data to the dropdown
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, carNames);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //making the unwanted texts invisible
        answerShow.setVisibility(View.GONE);
        if (!timerSwitch) { // if timer is off, countdown text is invisible
            countdown.setVisibility(View.INVISIBLE);
        } else { // if timer on, timer instantiated
            countdown.setVisibility(View.VISIBLE);
            // timer for 20000ms(20s) with intervals of 1000ml (1s)
            timer = new CountDownTimer(20500, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String data;
                    if (millisUntilFinished >= 10000) { // showing appropriate countdown texts
                        data = ("00:" + (millisUntilFinished / 1000));
                    } else {
                        data = "00:0" + (millisUntilFinished / 1000);
                    }
                    countdown.setText(data);
                }
                @Override
                public void onFinish() { // function equal to a press of submit button
                    if (identifyButton.getText().equals(getString(R.string.button_before))) {
                        identifyButton.performClick();
                        countdown.setText(R.string.time_up);
                    }
                }
            };
        }
        gameGenerator(); // calls for generator
    }

    public void gameGenerator() {
        if (timerSwitch) timer.start();
        int index, nameIndex = 0;
        Random random = new Random(); // randomly generates a number and uses index to get image
        do {
            index = random.nextInt(cars.length);
            Toast.makeText(this, index+" "+cars.length, Toast.LENGTH_SHORT).show();
            String number = String.valueOf(index);
            int intgers = number.charAt(0);
            Log.e("INTEGER", String.valueOf(intgers));
            if ((index > 0 )&&(index < 5)) {
                nameIndex = 0;
            } else if ((index >4 )&&(index < 10)){
                nameIndex = 1;
            } else if ((index >9 )&&(index < 15)) {
                nameIndex = 2;
            } else if ((index >14 )&&(index < 20)) {
                nameIndex = 3;
            }else if ((index >19 )&&(index < 25)) {
                nameIndex = 4;
            }else if ((index >24 )&&(index < 30)) {
                nameIndex = 5;
            }
        } while (previousIndex == (cars[index]));

        previousIndex = cars[index];
        imageView.setImageResource(cars[index]);
        correctCarName = carNames[nameIndex]; // stores correct name
    }

    public void identify(View view) {
        if (identifyButton.getText().equals(getString(R.string.button_before))) {
            identifyButton.setText(R.string.button_next_text); // changing text label and color to next
            identifyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.afterButton));

            String answer = String.valueOf(spinner.getSelectedItem()); // extracting selection
            if (answer.equals(correctCarName)) { // if correct answer
                // changing text, color and size to correct
                text.setText(R.string.correct_string);
                text.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                text.setTextSize(25);
                spinner.setEnabled(false); // disabling spinner
            } else { // if in-correct answer
                // changing text, color and size to wrong
                text.setText(R.string.wrong_string);
                text.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                text.setTextSize(25);
                spinner.setVisibility(View.INVISIBLE); // replacing spinner with text to show answer
                answerShow.setVisibility(View.VISIBLE);
                String answerText = "Correct Answer: " + correctCarName;
                answerShow.setText(answerText);
            }
        } else if (identifyButton.getText().equals(getString(R.string.button_next_text))) {
            identifyButton.setText(R.string.button_before);
            identifyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.beforeButton));
            text.setText(R.string.select_prompt);
            text.setTextSize(18);
            text.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
            spinner.setVisibility(View.VISIBLE);
            answerShow.setVisibility(View.GONE);
            spinner.setSelection(0);
            spinner.setEnabled(true);
            gameGenerator();
        }
    }

    public void exit(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (timerSwitch) timer.cancel();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (timerSwitch) timer.cancel();
        return super.onSupportNavigateUp();
    }

}