package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;


public class Activity_Game extends AppCompatActivity {
    private final int numOfRow =5;
    private final int numOfCol =3;

    private Handler handler;
    private int timer = 1000;
    private int direction = -1;
    private final int animalDir = 0;
    private int score =0;

    private int locRowAnimal;
    private int locColAnimal;
    private int locRowHunter;
    private int locColHunter;

    private ImageView[][] matrixImage;
    private ImageView[] game_IMG_hearts;
    private MaterialButton[] game_BTN_arrows;
    private GameManager gameManager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        init();
        gameManager = new GameManager();
        runProg();
    }


    private void init(){
        textView.setText(String.valueOf(score));
        // to place the character
        startPos();

    }

    private void startPos(){
        locRowAnimal = 4;
        locColAnimal = 1;
        locRowHunter = 0;
        locColHunter = 1;
        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.VISIBLE);
        matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_hunter);
        matrixImage[locRowHunter][locColHunter].setVisibility(View.VISIBLE);
    }


    private boolean checkIfOk(int direction, int character) {
        switch (direction) {
            case 0: //up
                return ((character == animalDir) ? locRowAnimal : locRowHunter) > 0;
            case 1: //left
                return ((character == animalDir) ? locColAnimal : locColHunter) > 0;
            case 2: //right
                return ((character == animalDir) ? locColAnimal : locColHunter) < (numOfCol - 1);
            case 3: //down
                return ((character == animalDir) ? locRowAnimal : locRowHunter) < (numOfRow - 1);

        }
        return false;
    }

    private void runProg() {
        buttonsListener();
        TickTok();
        randomHunterPos();

    }

    private void buttonsListener() {
        for (int i = 0; i < game_BTN_arrows.length; ++i) {
            int finalI = i;
            game_BTN_arrows[i].setOnClickListener(v -> animalMove(finalI));
        }
    }

    private void TickTok() {
        handler = new Handler();
        handler.postDelayed(() -> {
            if (direction != -1) {
                animalMove(direction);
            }
            score+= 1;
            textView.setText(String.valueOf(score));
            runProg();
        }, timer);
    }

    private void StopTickTok() {
        handler.removeCallbacksAndMessages(null);
    }


    private void animalMove(int i){
        // init is: locRow = 4;
        //          locCol = 1;

        switch(i){

            case 0: // up

                    if (checkIfOk(0, animalDir)){
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.INVISIBLE);
                        locRowAnimal--;
                        direction = 0;
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.VISIBLE);
                        getHit();
                    }

                break;

            case 1: // left
                    if (checkIfOk(1, animalDir)){
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.INVISIBLE);
                        locColAnimal--;
                        direction = 1;
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.VISIBLE);
                        getHit();
                    }

                break;

            case 2: // right
                    if (checkIfOk(2, animalDir)){
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.INVISIBLE);
                        locColAnimal++;
                        direction = 2;
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.VISIBLE);
                        getHit();
                    }

                break;

            case 3: // down
                    if (checkIfOk(3, animalDir)){
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.INVISIBLE);
                        locRowAnimal++;
                        direction = 3;
                        matrixImage[locRowAnimal][locColAnimal].setVisibility(View.VISIBLE);
                        getHit();
                    }

                break;
        }
    }

    private void lifeSetter() {
        gameManager.reduceLives();
        if (gameManager.getLives() == 2)
            game_IMG_hearts[2].setVisibility(View.INVISIBLE);

        else if (gameManager.getLives() == 1)
            game_IMG_hearts[1].setVisibility(View.INVISIBLE);

        else if (gameManager.getLives() == 0) {
            game_IMG_hearts[0].setVisibility(View.INVISIBLE);
            endGame();
        }
    }

    private void getHit(){
        if ((locRowHunter == locRowAnimal) && (locColHunter == locColAnimal)){
            direction = -1;
            lifeSetter();
            matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_heart);
            matrixImage[locRowHunter][locColHunter].setVisibility(View.INVISIBLE);
            startPos();

        }

    }


    private void randomHunterPos(){
        // define the range
        int max = 3;
        int min = 0;
        int range = max - min + 1;

         int rand = (int)(Math.random() * range) + min;

         switch (rand){
             case 0: // up
                 if (checkIfOk(0,1)){
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.INVISIBLE);
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_lion);
                     locRowHunter--;
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_hunter);
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.VISIBLE);
                     getHit();
                 }

                 break;
             case 1: // left
                 if (checkIfOk(1,1)){
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.INVISIBLE);
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_lion);
                     locColHunter--;
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_hunter);
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.VISIBLE);
                     getHit();
                 }
                 break;
             case 2: // right
                 if (checkIfOk(2,1)){
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.INVISIBLE);
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_lion);
                     locColHunter++;
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_hunter);
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.VISIBLE);
                     getHit();
                 }
                 break;

             case 3: // down
                 if (checkIfOk(3,1)){
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.INVISIBLE);
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_lion);
                     locRowHunter++;
                     matrixImage[locRowHunter][locColHunter].setImageResource(R.drawable.ic_hunter);
                     matrixImage[locRowHunter][locColHunter].setVisibility(View.VISIBLE);
                     getHit();
                 }
                 break;
         }
        }

    private void endGame() {
        StopTickTok();
        Toast.makeText(getApplicationContext(),"Game is over", Toast.LENGTH_LONG).show();
        finish();
        //wait 1 second to show the Toast before the program ended
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                System.exit(0);
            }
        }, 1000);
    }


    private void findViews() {
        matrixImage = new ImageView[][]{
                {findViewById(R.id.image00), findViewById(R.id.image01), findViewById(R.id.image02)},
                {findViewById(R.id.image10), findViewById(R.id.image11), findViewById(R.id.image12)},
                {findViewById(R.id.image20), findViewById(R.id.image21), findViewById(R.id.image22)},
                {findViewById(R.id.image30), findViewById(R.id.image31), findViewById(R.id.image32)},
                {findViewById(R.id.image40), findViewById(R.id.image41), findViewById(R.id.image42)}};

        textView= (TextView) findViewById(R.id.textView);

        game_BTN_arrows = new MaterialButton[]{
                findViewById(R.id.game_BTN_up),    // 0
                findViewById(R.id.game_BTN_left),  // 1
                findViewById(R.id.game_BTN_right), // 2
                findViewById(R.id.game_BTN_down)   // 3
        };

        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };
    }
}

