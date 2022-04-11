package com.example.hw1;

public class GameManager {

    private final int NUM_OF_LIVES = 3;
    private int lives;


    public GameManager(){
        this.lives = NUM_OF_LIVES;
    }


    public int getLives() {
        return lives;
    }

    public void reduceLives() {
       this.lives--;
    }


}

