package com.example.courseworkone;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class IdentifyImageActivity extends AppCompatActivity {

    private int[] cars;
    private String[] carNames;
    private boolean timerSwitch;
    private String carName;
    private int correctInt;
    private TextView text;
    private TextView countdown;
    private TextView promptText;
    private Button nextButton;
    private CountDownTimer timer;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private boolean image1State = false;
    private boolean image2State = false;
    private boolean image3State = false;
    private int previous1 = 0;
    private int previous2 = 0;
    private int previous3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        timerSwitch = intent.getBooleanExtra("timerSwitch",true);
        Log.i("IMAGE: TIMER: ", String.valueOf(timerSwitch));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_image);

        CarList carInstance = new CarList(3);
        cars = carInstance.getAllResourcesList();
        carNames = carInstance.getAllNameList();

        nextButton = findViewById(R.id.ImageNextButton);
        nextButton.setVisibility(View.GONE);
        text = (TextView) findViewById(R.id.randomCar);
        countdown = (TextView) findViewById(R.id.imageTimerText);
        promptText = (TextView) findViewById(R.id.imagePromptText);
        image1 = (ImageView) findViewById(R.id.identify_image_1);
        image2 = (ImageView) findViewById(R.id.identify_image_2);
        image3 = (ImageView) findViewById(R.id.identify_image_3);
        if (!timerSwitch) {
            countdown.setVisibility(View.GONE);
        } else {
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
                    promptText.setText(R.string.timer_victory);
                    nextButton.setVisibility(View.VISIBLE);
                    image1.setEnabled(false);
                    image2.setEnabled(false);
                    image3.setEnabled(false);
                    promptText.setTextSize(18);
                    if (image1State) {
                        image1.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.rightAnswerBackgroundColor));
                    } else if (image2State) {
                        image2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.rightAnswerBackgroundColor));
                    } else if (image3State) {
                        image3.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.rightAnswerBackgroundColor));
                    }
                }
            };
        }
        generateGame();
    }

    public void generateGame() {
        Random random = new Random(); // randomly generates a number and uses index to get image
        int num1, num2, num3;

            num1 = random.nextInt(cars.length);
            do {
                num2 = random.nextInt(cars.length);
            } while (num1 == num2);

            do {
                num3 = random.nextInt(cars.length);
            } while (!((num1 != num3) & (num2 != num3)));

        previous1 = num1;
        previous2 = num2;
        previous3 = num3;
        correctInt = random.nextInt(3)+1;
        image1.setImageResource(cars[num1]);
        image2.setImageResource(cars[num2]);
        image3.setImageResource(cars[num3]);
        switch (correctInt) {
            case 1: {
                carName = getCarName(num1);
                image1State = true;
                break;
            }
            case 2: {
                carName = getCarName(num2);
                image2State = true;
                break;
            }
            case 3: {
                carName = getCarName(num3);
                image3State = true;
                break;
            }
        }
        Toast.makeText(getApplicationContext(), carName,Toast.LENGTH_SHORT).show();
        text.setText(carName);
        if (timerSwitch) timer.start();
    }

    private String getCarName(int index) {

        int nameIndex = 0;

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
        return carNames[nameIndex];
    }

    public void imageOneClick(View view) {
        nextButton.setVisibility(View.VISIBLE);
        image1.setEnabled(false);
        image2.setEnabled(false);
        image3.setEnabled(false);
        promptText.setTextSize(21);
        if (timerSwitch) timer.cancel();
        if (image1State) {
            promptText.setText(R.string.correct_string);
            promptText.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
            image1.setBackgroundColor(ContextCompat.getColor(this, R.color.correctBackgroundColor));
        } else {
            promptText.setText(R.string.wrong_string);
            promptText.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
            image1.setBackgroundColor(ContextCompat.getColor(this, R.color.wrongBackgroundColor));
            if (correctInt == 2) {
                image2.setBackgroundColor(ContextCompat.getColor(this, R.color.rightAnswerBackgroundColor));
            } else {
                image3.setBackgroundColor(ContextCompat.getColor(this, R.color.rightAnswerBackgroundColor));
            }
        }
    }

    public void imageTwoClick(View view) {
        nextButton.setVisibility(View.VISIBLE);
        image1.setEnabled(false);
        image2.setEnabled(false);
        image3.setEnabled(false);
        promptText.setTextSize(21);
        if (timerSwitch) timer.cancel();
        if (image2State) {
            promptText.setText(R.string.correct_string);
            promptText.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
            image2.setBackgroundColor(ContextCompat.getColor(this, R.color.correctBackgroundColor));
        } else {
            promptText.setText(R.string.wrong_string);
            promptText.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
            image2.setBackgroundColor(ContextCompat.getColor(this, R.color.wrongBackgroundColor));
            if (correctInt == 1) {
                image1.setBackgroundColor(ContextCompat.getColor(this, R.color.rightAnswerBackgroundColor));
            } else {
                image3.setBackgroundColor(ContextCompat.getColor(this, R.color.rightAnswerBackgroundColor));
            }
        }
    }

    public void imageThreeClick(View view) {
        nextButton.setVisibility(View.VISIBLE);
        image1.setEnabled(false);
        image2.setEnabled(false);
        image3.setEnabled(false);
        promptText.setTextSize(21);
        if (timerSwitch) timer.cancel();
        if (image3State) {
            promptText.setText(R.string.correct_string);
            promptText.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
            image3.setBackgroundColor(ContextCompat.getColor(this, R.color.correctBackgroundColor));
        } else {
            promptText.setText(R.string.wrong_string);
            promptText.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
            image3.setBackgroundColor(ContextCompat.getColor(this, R.color.wrongBackgroundColor));
            if (correctInt == 1) {
                image1.setBackgroundColor(ContextCompat.getColor(this, R.color.rightAnswerBackgroundColor));
            } else {
                image2.setBackgroundColor(ContextCompat.getColor(this, R.color.rightAnswerBackgroundColor));
            }
        }
    }

    public void next(View view) {
        image1.setBackgroundColor(1);
        image2.setBackgroundColor(1);
        image3.setBackgroundColor(1);
        promptText.setText(R.string.image_description);
        promptText.setTextSize(14);
        promptText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        image1.setEnabled(true);
        image2.setEnabled(true);
        image3.setEnabled(true);
        image1State = false;
        image2State = false;
        image3State = false;
        nextButton.setVisibility(View.GONE);
        generateGame();
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
