package com.example.tanvi.quizapp.Model;

import java.util.Random;

/**
 * Created by tanvi on 10/2/2016.
 */
public class RandomNumGen {

    private int num;
    Random random = new Random();

    public int genNum(String operation){

    if(operation.equals("Addition"))
    {
        return random.nextInt(10);
    }

    else if(operation.equals("Subtraction")){

        return random.nextInt(10);

    }
    else if(operation.equals("Multiplication")){

        return random.nextInt(10);
    }

    else
        return 0;
    }

}
