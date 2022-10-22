package com.example.myapplication;
import java.util.Random;

public class Dice {
    private int onTop;

    public Dice(){
        this.randomDice();
    }

    public int randomDice(){
        Random random = new Random();
        this.onTop = random.nextInt(6) + 1;
        return this.onTop;
    }

    public int getOnTop() {
        return onTop;
    }
}
