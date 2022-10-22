package com.dam.numberguesser.guesshandler;

import android.util.Range;

import com.dam.numberguesser.MainActivity;
import com.dam.numberguesser.R;

public class GuessHandler {

    public static final int START_TRIES = 5;
    private static final int MIN_RAN_NUM = 0;
    private static final int MAX_RAN_NUM = 100;

    private final MainActivity mainActivity;

    private int numberToGuess;
    private int triesLeft;

    private static final Range<Integer> ALLOWED_RAN = new Range<Integer>(MIN_RAN_NUM, MAX_RAN_NUM);

    public GuessHandler(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initAttributes();
    }

    public void initAttributes() {
        // generate random number
        numberToGuess = (int) (Math.random() * 100);
        triesLeft = START_TRIES;
        updateTries();
    }

    public void onUserGuess(String guess) {
        int guessInt = validateInputNum(guess);

        if (!ALLOWED_RAN.contains(guessInt)) { return; }

        if (guessInt == numberToGuess) {
            mainActivity.setGameState(GameState.WIN);
            return;
        }

        if (guessInt > numberToGuess) {
            mainActivity.showMessage(R.string.high_text_message);
        } else {
            mainActivity.showMessage(R.string.low_text_message);
        }
        --triesLeft;
        updateTries();
    }

    private int validateInputNum(String strNum) {
        int num = -1;
        try {
            num = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            mainActivity.showMessage(R.string.invalid_text_message);
        }
        return num;
    }

    private void updateTries() {
        mainActivity.updateTriesLeft(triesLeft);
        if (triesLeft <= 0) {
            mainActivity.setGameState(GameState.LOST);
        }
    }

}
