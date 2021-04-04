package com.example.courseworkone;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class HintsActivity extends AppCompatActivity {

    private boolean timerSwitch;
    private boolean timeUp;
    private String[] carNames;
    private int[] cars ;
    private boolean intermediateTimeUp = false;
    private TextView countdown;
    private TextView hyphenText;
    private TextView guessText;
    private TextView attemptText;
    private CountDownTimer timer;
    private String correctName;
    private String tempAttempt;
    private StringBuilder showString;
    private ImageView imageView;
    private EditText textField;
    private Button button;
    private int buttonTrack = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        timerSwitch = intent.getBooleanExtra("timerSwitch",true);
        Log.i("HINTS: TIMER: ", String.valueOf(timerSwitch));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hints_activity);

        CarList carInstance = new CarList(3);
        carNames = carInstance.getAllNameList();
        cars = carInstance.getAllResourcesList();

        hyphenText = (TextView) findViewById(R.id.hintBlankText);
        guessText = (TextView) findViewById(R.id.hintsGuessText);
        imageView = (ImageView) findViewById(R.id.hintsImageView);
        countdown = (TextView) findViewById(R.id.hintsCountdown);
        attemptText = (TextView) findViewById(R.id.attemptText);
        textField = (EditText) findViewById(R.id.hintTextBox);
        button = (Button) findViewById(R.id.hintsSubmit);

        if (!timerSwitch) { // if timer is off, text goes out
            countdown.setVisibility(View.INVISIBLE);
        } else {
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
                public void onFinish() {
                    if (buttonTrack < 3) {
                        intermediateTimeUp = true;
                        button.performClick();
                        timer.start(); // restarts the timer
                        intermediateTimeUp = false;
                    } else if (buttonTrack == 3) {
                        tempAttempt = "TIME OUT \nAll Attempts Expired";
                        attemptText.setText(tempAttempt);
                        countdown.setText(R.string.time_up);
                        timeUp = true;
                        completeLevel(false); // since the timer wont finish if answers correct
                    }
                }
            };
        }
        gameGenerator();
    }

    public void gameGenerator() {
        attemptText.setText(""); // removing text if any
        hyphenText.setTextColor(ContextCompat.getColor(this, R.color.hintTextColor));
        textField.setText("");
        if (timerSwitch) timer.start(); // if the timer is active, starts timer
        timeUp = false;
        intermediateTimeUp = false;

        int index, nameIndex = 0;
        Random random = new Random(); // randomly generates a number and uses index to get image
        index = random.nextInt(cars.length);
        ///Toast.makeText(this, index+" "+cars.length, Toast.LENGTH_SHORT).show();
        //String number = String.valueOf(index);
        //int intgers = number.charAt(0);
        //Log.e("INTEGER", String.valueOf(intgers));
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
        correctName = carNames[nameIndex];
        Toast.makeText(getApplicationContext(),correctName,Toast.LENGTH_SHORT).show();
        char[] letterList = correctName.toCharArray();
        showString = new StringBuilder(); // string-builder will manage the string displayed
        for (char letter: letterList) { // each string will have a space for clarity in display
            if (String.valueOf(letter).equals(" ")) {
                showString.append("  ");
            } else {
                showString.append("- ");
            }
        }
        hyphenText.setText(showString);
        imageView.setImageResource(cars[index]);
    }

    public void submit(View view) {
        if (button.getText().equals(getString(R.string.submit))) {
            boolean complete = true;
            if (buttonTrack <= 3) {
                String character = textField.getText().toString();
                textField.getText().clear();
                if (character.length() == 1) {
                    int charCount = 0;
                    boolean found = false;
                    for (int i = 0; i < (correctName.length() * 2); i = i + 2) {
                        if (String.valueOf(correctName.toLowerCase().charAt(charCount)).equals(character)) {
                            showString.replace(i, i + 2, (character + " "));
                            found = true;
                        }
                        if (String.valueOf(showString.charAt(i)).equals("-")) {
                            complete = false;
                        }
                        charCount++;
                    }
                    if (!found) {
                        Toast.makeText(getApplicationContext(), ("Attempt wrong. Try again"), Toast.LENGTH_SHORT).show();
                        tempAttempt = "Attempts Expired: "+buttonTrack;
                        attemptText.setText(tempAttempt);
                        if (timerSwitch) {
                            timer.cancel();
                            timer.start();
                        }
                        buttonTrack++;
                    } else {
                        if (timerSwitch) {
                            timer.cancel();
                            timer.start();
                        }
                        hyphenText.setText(showString);
                    }
                    if (complete) {
                        buttonTrack = 1;
                        completeLevel(true);
                        if (timerSwitch) timer.cancel();
                    } else if (buttonTrack == 4) {
                        buttonTrack = 1;
                        completeLevel(false);
                        if (timerSwitch) timer.cancel();
                    }
                } else if (intermediateTimeUp) {
                    tempAttempt = "TIME OUT \nAttempts Expired: "+buttonTrack; // if attempts are left
                    attemptText.setText(tempAttempt);
                    buttonTrack++;
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a letter before submitting", Toast.LENGTH_SHORT).show();
                }
            } else {
                completeLevel(false);
            }
        } else if (button.getText().equals(getString(R.string.button_next_text))) {
            textField.setVisibility(View.VISIBLE);
            button.setText(R.string.submit);
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.beforeButton));
            guessText.setTextSize(14);
            guessText.setText(R.string.hintPrompt);
            guessText.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
            gameGenerator();
            gameGenerator();
            buttonTrack = 1;
        }
    }

    public void completeLevel(boolean completionFlag) {
        if (completionFlag) {
            textField.setVisibility(View.GONE);
            if (timerSwitch) timer.cancel();
            button.setText(R.string.button_next_text); // changing text label and color to next
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.afterButton));
            guessText.setText(R.string.correct_string);
            guessText.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
            guessText.setTextSize(22);
        } else {
            if (timeUp) {
                guessText.setText(R.string.timer_victory);
            } else {
                if (timerSwitch) timer.cancel();
                guessText.setText(R.string.wrong_string);
            }
            guessText.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
            guessText.setTextSize(22);
            textField.setVisibility(View.GONE);
            button.setText(R.string.button_next_text); // changing text label and color to next
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.afterButton));
            int charCount = 0;
            for (int i = 0; i < (correctName.length() * 2); i = i + 2) {
                showString.replace(i, i + 2, (correctName.toLowerCase().charAt(charCount)+" "));
                charCount++;
            }
            hyphenText.setText(showString);
            hyphenText.setTextColor(ContextCompat.getColor(this, R.color.answerColor));
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
