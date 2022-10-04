package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView, problemTextView, scoreTextView, answerTextView1, answerTextView2, answerTextView3, answerTextView4, goTextView, correctOrWrongTextView;
    LinearLayout upperLayout, middleLayout1, middleLayout2, bottomLayout;
    Button playAgainButton;
    int correctAnswer;
    int currentCorrectAnswerTextView;
    Integer[] allAnswers = new Integer[4];
    int numOfCorrectAnswers;
    int numOfAnsweredQuestions;
    boolean correct;
    MediaPlayer stopSound;


    public void go(View view) {
        upperLayout.setVisibility(View.VISIBLE);
        middleLayout1.setVisibility(View.VISIBLE);
        middleLayout2.setVisibility(View.VISIBLE);
        goTextView = findViewById(R.id.goTextView);
        goTextView.setVisibility(View.INVISIBLE);
        bottomLayout.setVisibility(View.VISIBLE);
        answerTextView1 = findViewById(R.id.answerTextView1);
        answerTextView2 = findViewById(R.id.answerTextView2);
        answerTextView3 = findViewById(R.id.answerTextView3);
        answerTextView4 = findViewById(R.id.answerTextView4);
        scoreTextView = findViewById(R.id.scoreTextView);
        createTask();
        showTime();
        setAllAnswers();
        stopSound = MediaPlayer.create(this,R.raw.horn);
    }

    @SuppressLint("SetTextI18n")
    public void displayTime(long currentTime) {
        timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setText(currentTime + "s");
    }

    public void showTime() {
        long timeLimit = 30000;
        new CountDownTimer(timeLimit, 1000) {

            @Override
            public void onTick(long milliSecondsUntilDone) {
                displayTime(milliSecondsUntilDone / 1000);
            }

            @Override
            public void onFinish() {
                playAgainButton.setVisibility(View.VISIBLE);
                correctOrWrongTextView.setText("Your Score is: " + numOfCorrectAnswers + " from " + numOfAnsweredQuestions);
                timerTextView.setText("0s");
                disableEnable(false);
                stopSound.start();
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    public void createTask() {
        int first = (int) (Math.random() * 100);
        int second = (int) (Math.random() * 100);
        correctAnswer = first + second;
        allAnswers[3] = correctAnswer;
        problemTextView = findViewById(R.id.problemTextView);
        problemTextView.setText(first + " + " + second);
    }

    public void createAnswers() {
        int firstAns = (int) (Math.random() * 100);
        int secondAns = (int) (Math.random() * 100);
        int thirdAns = (int) (Math.random() * 100);
        allAnswers[0] = firstAns;
        allAnswers[1] = secondAns;
        allAnswers[2] = thirdAns;
    }

    public void selectAnswer(View view) {
        numOfAnsweredQuestions += 1;
        if (view.getId() == currentCorrectAnswerTextView) {
            numOfCorrectAnswers += 1;
            correct = true;
        }
        showResult();
        showScore();
        createTask();
        setAllAnswers();
    }

    @SuppressLint("SetTextI18n")
    public void setAllAnswers() {
        createAnswers();
        List<Integer> listOfAnswers = Arrays.asList(allAnswers);
        Collections.shuffle(listOfAnswers);
        listOfAnswers.toArray();
        for (int i = 0; i < 4; i++) {
            if (allAnswers[i] == correctAnswer) {
                switch (i) {
                    case 0:
                        currentCorrectAnswerTextView = R.id.answerTextView1;
                        break;
                    case 1:
                        currentCorrectAnswerTextView = R.id.answerTextView2;
                        break;
                    case 2:
                        currentCorrectAnswerTextView = R.id.answerTextView3;
                        break;
                    case 3:
                        currentCorrectAnswerTextView = R.id.answerTextView4;
                        break;
                }
            }
        }
        answerTextView1.setText(Integer.toString(allAnswers[0]));
        answerTextView2.setText(Integer.toString(allAnswers[1]));
        answerTextView3.setText(Integer.toString(allAnswers[2]));
        answerTextView4.setText(Integer.toString(allAnswers[3]));
    }

    @SuppressLint("SetTextI18n")
    public void showResult() {
        if (correct) {
            correctOrWrongTextView.setText("Correct!");
        }
        else {
            correctOrWrongTextView.setText("Wrong!");
        }
        correct = false;
    }

    @SuppressLint("SetTextI18n")
    public void showScore() {
        scoreTextView.setText(numOfCorrectAnswers + "/" + numOfAnsweredQuestions);
    }

    public void playAgain(View view) {
        playAgainButton.setVisibility(View.INVISIBLE);
        showTime();
        createTask();
        setAllAnswers();
        numOfCorrectAnswers = 0;
        numOfAnsweredQuestions = 0;
        showScore();
        correctOrWrongTextView.setText("");
        disableEnable(true);
    }

    public void disableEnable(boolean disOrEn) {
        answerTextView1.setEnabled(disOrEn);
        answerTextView2.setEnabled(disOrEn);
        answerTextView3.setEnabled(disOrEn);
        answerTextView4.setEnabled(disOrEn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upperLayout = findViewById(R.id.upperLayout);
        upperLayout.setVisibility(View.INVISIBLE);
        middleLayout1 = findViewById(R.id.middleLayout1);
        middleLayout1.setVisibility(View.INVISIBLE);
        middleLayout2 = findViewById(R.id.middleLayout2);
        middleLayout2.setVisibility(View.INVISIBLE);
        bottomLayout = findViewById(R.id.bottomLayout);
        bottomLayout.setVisibility(View.INVISIBLE);
        playAgainButton = findViewById(R.id.playAgain);
        playAgainButton.setVisibility(View.INVISIBLE);
        correctOrWrongTextView = findViewById(R.id.correctOrWrongTextView);
    }
}