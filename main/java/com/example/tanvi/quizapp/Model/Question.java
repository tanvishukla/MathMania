package com.example.tanvi.quizapp.Model;

/**
 * Created by tanvi on 10/1/2016.
 */
public class Question {

    private int quesID;
    private int operand1;
    private int operand2;
    private String operation;
    private int answer;
    private long time_remaining;

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getQuesID() {
        return quesID;
    }

    public void setQuesID(int quesID) {
        this.quesID = quesID;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getTime_remaining() {
        return time_remaining;
    }

    public void setTime_remaining(long time_remaining) {
        this.time_remaining = time_remaining;
    }
}
