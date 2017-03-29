package com.example.tanvi.quizapp.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tanvi.quizapp.R;

public class QuizActivity extends AppCompatActivity {

    public static final String OPTION_SELECTED = null;
    private RadioGroup quizOption;
    private RadioButton radioButtonSelected;
    private Button btnStart, btnQuit;
    private int selectedRadioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        addStartButtonListener();
        addQuitButtonListener();

    }

    private void addQuitButtonListener() {

        btnQuit = (Button) findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    private void addStartButtonListener() {

        quizOption = (RadioGroup) findViewById(R.id.quizOption);
        btnStart = (Button) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedRadioId = quizOption.getCheckedRadioButtonId();
                radioButtonSelected = (RadioButton) findViewById(selectedRadioId);
                String selectedButtonText = (String) radioButtonSelected.getText();
                Intent intent = new Intent(QuizActivity.this, QuestionActivity.class );
                intent.putExtra(OPTION_SELECTED, selectedButtonText);
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }
}
