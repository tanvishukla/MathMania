package com.example.tanvi.quizapp.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanvi.quizapp.Model.Question;
import com.example.tanvi.quizapp.Model.RandomNumGen;
import com.example.tanvi.quizapp.R;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView operand1, operand2, operator, questionCount, secsRemaning;
    Button button[] = new Button[11];
    EditText userAns;
    ImageView indication;
    Question question = new Question();
    Question constQuestion;
    RandomNumGen random = new RandomNumGen();

    private static int key=1;
    private static int score=0;
    private static String option=null;
    private boolean isQuizRunning=true;
    private static boolean isConfigChanged=false;
    final Handler handler = new Handler();
    Runnable run1;
    CountDownTimer timer;
    int flag=0;
    private long time_remaning;
    private long time_saved=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_question_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        if(savedInstanceState!=null){


            constQuestion = new Question();
            constQuestion.setOperand1(savedInstanceState.getInt("Operand1"));
            constQuestion.setOperand2(savedInstanceState.getInt("Operand2"));
            constQuestion.setOperation(savedInstanceState.getString("Operation"));
            constQuestion.setQuesID(savedInstanceState.getInt("Key"));
            key=savedInstanceState.getInt("Key");
            score=savedInstanceState.getInt("Score");
            time_saved =savedInstanceState.getLong("Time");
            isConfigChanged=true;

        }

        else {

            Intent intent = getIntent();
            option = intent.getStringExtra(QuizActivity.OPTION_SELECTED);

        }
        initQuestionView();
        getQuestionData();
        setQuestionView();
        timer = new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(time_saved!=0)
                {

                    long n1= time_saved-1000;
                    long n2= 6000-millisUntilFinished;
                    n1=n1/1000;
                    n2=n2/1000;

                    if(n1==n2){
                        time_saved=0;
                        this.cancel();
                        this.onFinish();
                    }

                }
                else
                secsRemaning.setText(Long.toString(millisUntilFinished/1000)+" secs remaining");
                time_remaning=millisUntilFinished;


                if (checkAnswer()){
                    run1 = new Runnable() {
                        @Override
                        public void run() {
                            timer.cancel();
                            timer.onFinish();
                        }
                    };
                    indication.setImageResource(R.drawable.correct_ans);
                    handler.post(run1);

                }

            else {

                    if(flag == 1){
                        indication.setImageDrawable(null);
                        flag=0;
                    }
                    else {

                        indication.setImageResource(R.drawable.wrong_ans);
                    }

                }

            }

            @Override
            public void onFinish() {

                if(!isQuizRunning){
                    this.cancel();
                    showAlert("End of Quiz !! Your Score is : " + score, false);
                    return;

                }

                getQuestionData();
                setQuestionView();
                this.start();

            }
        }.start();

    }

    private void getQuestionData() {

        if(isConfigChanged)
        {
            if(constQuestion!=null){
                question=constQuestion;
                isConfigChanged=false;
            }
            else
                Toast.makeText((getApplicationContext()),"No Data found in Const Question", Toast.LENGTH_SHORT).show();

        }
        else
        {
            int num1, num2,temp;
            num1=random.genNum(option);
            num2=random.genNum(option);
            indication.setImageDrawable(null);
            if(option.equals("Subtraction")&& num1<num2)
            {
                temp=num1;
                num1=num2;
                num2=temp;
            }

            question.setQuesID(key);
            question.setOperand1(num1);
            question.setOperand2(num2);
            question.setOperation(getOpSign(option));

        }


    }

    private void initQuestionView() {

        operand1= (TextView) findViewById(R.id.operand1);
        operand2= (TextView) findViewById(R.id.operand2);
        userAns = (EditText) findViewById(R.id.userAns);
        questionCount = (TextView) findViewById(R.id.questionCountText);
        indication = (ImageView) findViewById(R.id.imageView);
        operator = (TextView) findViewById(R.id.operator);
        secsRemaning= (TextView) findViewById(R.id.secsRemaining);
        button[0] = (Button) findViewById(R.id.button1);
        button[1] = (Button) findViewById(R.id.button2);
        button[2] = (Button) findViewById(R.id.button3);
        button[3] = (Button) findViewById(R.id.button4);
        button[4] = (Button) findViewById(R.id.button5);
        button[5] = (Button) findViewById(R.id.button6);
        button[6] = (Button) findViewById(R.id.button7);
        button[7] = (Button) findViewById(R.id.button8);
        button[8] = (Button) findViewById(R.id.button9);
        button[9] = (Button) findViewById(R.id.button10);
        button[10] = (Button) findViewById(R.id.btnEnter);
        for(int i=0;i<11;i++){

            button[i].setOnClickListener(QuestionActivity.this);

        }

    }
    private void setQuestionView() {


        operand1.setText(Integer.toString(question.getOperand1()));
        operand2.setText(Integer.toString(question.getOperand2()));
        operator.setText(question.getOperation());
        userAns.setText("");
        questionCount.setText("Question "+Integer.toString(question.getQuesID())+" of 10");
        key++;


        if(key>10)
        {
                        isQuizRunning = false;

        }
        else
            isQuizRunning = true;

    }

    private String getOpSign(String option) {
        String opChar=null;
        switch (option){

            case "Addition": opChar="+";
                break;

            case "Subtraction": opChar="-";
                break;

            case "Multiplication": opChar="*";
                break;


        }
        return opChar;
    }


    @Override
    public void onClick(View v) {

        int numberinputid = v.getId();
        switch(numberinputid){

            case R.id.button1 : addInput("0");
                break;
            case R.id.button2 : addInput("1");
                break;
            case R.id.button3 : addInput("2");
                break;
            case R.id.button4 : addInput("3");
                break;
            case R.id.button5 : addInput("4");
                break;
            case R.id.button6 : addInput("5");
                break;
            case R.id.button7 : addInput("6");
                break;
            case R.id.button8 : addInput("7");
                break;
            case R.id.button9 : addInput("8");
                break;
            case R.id.button10 : addInput("9");
                break;
            case R.id.btnEnter : if(key<11){

                if(checkAnswer()) {

                    indication.setImageResource(R.drawable.correct_ans);
                    handler.post(run1);

                }

                else  {

                    if(flag == 1){
                        indication.setImageDrawable(null);
                        flag=0;
                    }
                    else {

                        indication.setImageResource(R.drawable.wrong_ans);
                        handler.post(run1);
                    }
                }

            }
            else{
                timer.cancel();
                showAlert("End of Quiz !! Your Score is : " + score, false);
            }
                break;


        }

    }

    public void addInput(String n){

        String prevVal = String.valueOf(userAns.getText());
        userAns.append(n);
        Log.i("tag","User ans is : "+userAns.getText());



    }

    public boolean checkAnswer(){

        int num=calcAnswer();
        int first_digit =num/10;
        Log.i("first",Integer.toString(first_digit));

        if(userAns.getText().toString().equals(Integer.toString(num))) {
            score++;
            return true;
        }
        else if(userAns.getText().toString().equals(Integer.toString(first_digit))||userAns.getText().toString().isEmpty()){
            flag=1;
            return false;

        }
        else
        {
            return false;
        }


    }

    public int calcAnswer(){

        int firstNum=question.getOperand1();
        int secNum=question.getOperand2();
        String operation=question.getOperation();

        Log.i("message",question.getOperand1()+" "+question.getOperand2()+" "+question.getOperation());

        switch(operation){

            case "+" : question.setAnswer(firstNum+secNum);
                break;
            case "-" : question.setAnswer(firstNum-secNum);
                break;
            case "*" : question.setAnswer(firstNum*secNum);
                break;


        }
        return question.getAnswer();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean nav;
        switch (item.getItemId()){

            case android.R.id.home :
                nav=true;
                timer.cancel();
                showAlert("Do you wish to quit the quiz ?",true);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showAlert(String message, boolean nav){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(QuestionActivity.this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){


            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent parentActivityIntent = new Intent(QuestionActivity.this, QuizActivity.class);
                parentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(parentActivityIntent);
                finish();

            }
        });

        if(nav)
        {
            alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    return;

                }
            });

        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        key=1;
        score=0;
    }


    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        Log.i("TAG","In onSaveInstanceState");
        timer.cancel();
        outState.putInt("Score",score);
        outState.putInt("Operand1",question.getOperand1());
        outState.putInt("Operand2",question.getOperand2());
        outState.putString("Operation",question.getOperation());
        outState.putInt("Key",question.getQuesID());
        outState.putLong("Time",time_remaning);
        Log.i("Time","----------In on save---------- time rem"+Long.toString(outState.getLong("Time")));
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }


    public void doNegativeClick() {
    }

    public void doPositiveClick() {
    }
}
