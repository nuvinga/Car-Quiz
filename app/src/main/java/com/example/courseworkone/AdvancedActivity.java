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

public class AdvancedActivity extends AppCompatActivity {

    private boolean timerSwitch;
    private CountDownTimer timer;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private EditText inputBox1;
    private EditText inputBox2;
    private EditText inputBox3;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView attempts;
    private TextView rightAnswer1;
    private TextView rightAnswer2;
    private TextView rightAnswer3;
    private TextView timerText;
    private TextView scoreText;
    private Button submit;
    private String answer1;
    private String answer2;
    private String answer3;
    private String tempHoldAny;
    private int buttonClicks;
    private int score;
    private boolean firstText = false;
    private boolean secondText = false;
    private boolean thirdText = false;
    private boolean timeUp = false;
    private boolean tempTimeUp = false;
    private int[] cars;
    private String[] carNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        timerSwitch = intent.getBooleanExtra("timerSwitch",true);
        Log.i("ADVANCED: TIMER: ", String.valueOf(timerSwitch));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        CarList carInstance = new CarList(3);
        carNames = carInstance.getAllNameList();
        cars = carInstance.getAllResourcesList();

        image1 = (ImageView) findViewById(R.id.advancedImage1);
        image2 = (ImageView) findViewById(R.id.advancedImage2);
        image3 = (ImageView) findViewById(R.id.advancedImage3);
        inputBox1 = (EditText) findViewById(R.id.advancedEditText1);
        inputBox2 = (EditText) findViewById(R.id.advancedEditText2);
        inputBox3 = (EditText) findViewById(R.id.advancedEditText3);
        text1 = (TextView) findViewById(R.id.advancedText1);
        text2 = (TextView) findViewById(R.id.advancedText2);
        text3 = (TextView) findViewById(R.id.advancedText3);
        attempts = (TextView) findViewById(R.id.attempsLeftText);
        rightAnswer1 = (TextView) findViewById(R.id.rightAnswer1);
        rightAnswer2 = (TextView) findViewById(R.id.rightAnswer2);
        rightAnswer3 = (TextView) findViewById(R.id.rightAnswer3);
        timerText = (TextView) findViewById(R.id.advancedCountdown);
        scoreText = (TextView) findViewById(R.id.advancedScore);
        submit = (Button) findViewById(R.id.advancedSubmitButton);
        scoreText.setText(R.string.zero_score);
        if (!timerSwitch) {
            timerText.setVisibility(View.GONE);
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
                    timerText.setText(data);
                }
                @Override
                public void onFinish() {
                    if (buttonClicks <= 3) {
                        tempTimeUp = true;
                        submit.performClick();
                        tempTimeUp = false;
                    } else if (buttonClicks == 4) {
                        tempHoldAny = "All attempts expired!";
                        attempts.setText(tempHoldAny);
                        tempHoldAny = "";
                        timerText.setText(R.string.time_up);
                        timeUp = true;
                        submit.performClick();
                    }
                }
            };
        }
        rightAnswer1.setVisibility(View.GONE);
        rightAnswer2.setVisibility(View.GONE);
        rightAnswer3.setVisibility(View.GONE);
        buttonClicks = 1;
        score = 0;
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

        answer1 = getCarName(num1);
        answer2 = getCarName(num2);
        answer3 = getCarName(num3);
        image1.setImageResource(cars[num1]);
        image2.setImageResource(cars[num2]);
        image3.setImageResource(cars[num3]);
        rightAnswer1.setText(answer1);
        rightAnswer2.setText(answer2);
        rightAnswer3.setText(answer3);
        if (timerSwitch) {
            timeUp = false;
            timer.start();
        }
        tempHoldAny = "LET's START!";
        attempts.setText(tempHoldAny);
        tempHoldAny = "";
        buttonClicks = 1;
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

    public void submitValidator(View view) {
        if (submit.getText().equals(getString(R.string.submit))) {
            if ((!(inputBox1.getText().toString().equals("")) & !firstText) || (!(inputBox2.getText().toString().equals("")) & !secondText)
                    || (!(inputBox3.getText().toString().equals("")) & !thirdText)) {
                submit();
            } else if (timerSwitch && tempTimeUp) {
                submit();
            } else {
                Toast.makeText(getApplicationContext(), ("AT-LEAST ONE INPUT REQUIRED"), Toast.LENGTH_SHORT).show();
            }
        } else if (submit.getText().equals(getString(R.string.button_next_text))) {
            buttonClicks = 1;
            submit.setText(R.string.submit);
            submit.setBackgroundColor(ContextCompat.getColor(this, R.color.beforeButton));
            inputBox1.setEnabled(true);
            inputBox2.setEnabled(true);
            inputBox3.setEnabled(true);
            inputBox1.setText("");
            inputBox2.setText("");
            inputBox3.setText("");
            inputBox1.setTextColor(ContextCompat.getColor(this, R.color.black));
            inputBox2.setTextColor(ContextCompat.getColor(this, R.color.black));
            inputBox3.setTextColor(ContextCompat.getColor(this, R.color.black));
            text1.setText(R.string.advanced_before_string);
            text1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            text2.setText(R.string.advanced_before_string);
            text2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            text3.setText(R.string.advanced_before_string);
            text3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            firstText = false;
            secondText = false;
            thirdText = false;
            inputBox1.setVisibility(View.VISIBLE);
            rightAnswer1.setVisibility(View.INVISIBLE);
            inputBox2.setVisibility(View.VISIBLE);
            rightAnswer2.setVisibility(View.INVISIBLE);
            inputBox3.setVisibility(View.VISIBLE);
            rightAnswer3.setVisibility(View.INVISIBLE);
            generateGame();
        }
    }

    public void submit() {
        boolean processed = false;
        if (buttonClicks <= 3 && !timeUp) {
            String expiredString = "Attempts EXPIRED: "+(buttonClicks);
            attempts.setText(expiredString);
            String input1 = inputBox1.getText().toString().trim().toLowerCase();
            String input2 = inputBox2.getText().toString().trim().toLowerCase();
            String input3 = inputBox3.getText().toString().trim().toLowerCase();
            if (input1.equals(answer1.trim().toLowerCase()) & !firstText) {
                inputBox1.setEnabled(false);
                inputBox1.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                text1.setText(R.string.correct_string);
                text1.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                firstText = true;
                setScore();
            } else if (!firstText){
                if (input1.equals("")) {
                    text1.setText(R.string.enter_answer);
                    text1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    if (timerSwitch) processed = true;
                } else {
                    inputBox1.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    text1.setText(R.string.wrong_string);
                    text1.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    inputBox1.setText("");
                    processed = true;
                }
            }
            if (input2.equals(answer2.trim().toLowerCase()) & !secondText) {
                inputBox2.setEnabled(false);
                inputBox2.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                text2.setText(R.string.correct_string);
                text2.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                secondText = true;
                setScore();
            } else if (!secondText) {
                if (input2.equals("")) {
                    text2.setText(R.string.enter_answer);
                    text2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    if (timerSwitch) processed = true;
                } else {
                    inputBox2.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    text2.setText(R.string.wrong_string);
                    text2.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    inputBox2.setText("");
                    processed = true;
                }
            }
            if (input3.equals(answer3.trim().toLowerCase()) & !thirdText) {
                inputBox3.setEnabled(false);
                inputBox3.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                text3.setText(R.string.correct_string);
                text3.setTextColor(ContextCompat.getColor(this, R.color.correctColor));
                thirdText = true;
                setScore();
            } else if (!thirdText) {
                if (input3.equals("")) {
                    text3.setText(R.string.enter_answer);
                    text3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    if (timerSwitch) processed = true;
                } else {
                    inputBox3.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    text3.setText(R.string.wrong_string);
                    text3.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    inputBox3.setText("");
                    processed = true;
                }
            }
            if (firstText && secondText && thirdText) {
                submit.setText(R.string.button_next_text);
                submit.setBackgroundColor(ContextCompat.getColor(this, R.color.afterButton));
                if (timerSwitch) {
                    timer.cancel();
                }
            }

            if (processed) {
                buttonClicks++;
                if (timerSwitch) {
                    timer.cancel();
                    timer.start();
                }
            }

            if (buttonClicks == 4) {
                if (!firstText) {
                    text1.setText(R.string.wrong_string);
                    text1.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    inputBox1.setVisibility(View.INVISIBLE);
                    rightAnswer1.setVisibility(View.VISIBLE);
                }
                if (!secondText) {
                    text2.setText(R.string.wrong_string);
                    text2.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    inputBox2.setVisibility(View.INVISIBLE);
                    rightAnswer2.setVisibility(View.VISIBLE);
                }
                if (!thirdText) {
                    text3.setText(R.string.wrong_string);
                    text3.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                    inputBox3.setVisibility(View.INVISIBLE);
                    rightAnswer3.setVisibility(View.VISIBLE);
                }
                inputBox1.setEnabled(false);
                inputBox2.setEnabled(false);
                inputBox3.setEnabled(false);
                submit.setText(R.string.button_next_text);
                if (timerSwitch) timer.cancel();
            }

        } else {
            timer.cancel();
            if (!firstText) {
                if (timeUp) {
                    text1.setText(R.string.time_up);
                } else {
                    text1.setText(R.string.wrong_string);
                }
                text1.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                inputBox1.setVisibility(View.INVISIBLE);
                rightAnswer1.setVisibility(View.VISIBLE);
            }
            if (!secondText) {
                if (timeUp) {
                    text2.setText(R.string.time_up);
                } else {
                    text2.setText(R.string.wrong_string);
                }
                text2.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                inputBox2.setVisibility(View.INVISIBLE);
                rightAnswer2.setVisibility(View.VISIBLE);
            }
            if (!thirdText) {
                if (timeUp) {
                    text3.setText(R.string.time_up);
                } else {
                    text3.setText(R.string.wrong_string);
                }
                text3.setTextColor(ContextCompat.getColor(this, R.color.wrongColor));
                inputBox3.setVisibility(View.INVISIBLE);
                rightAnswer3.setVisibility(View.VISIBLE);
            }
            inputBox1.setEnabled(false);
            inputBox2.setEnabled(false);
            inputBox3.setEnabled(false);
            submit.setText(R.string.button_next_text);
        }
    }

    public void exit(View view) {
        onBackPressed();
    }

    public void setScore() {
        score++;
        if (score < 10) {
            tempHoldAny = "0"+(score);
            scoreText.setText(tempHoldAny);
        } else {
            scoreText.setText(String.valueOf(score));
        }
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
