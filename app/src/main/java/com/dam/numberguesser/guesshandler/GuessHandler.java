package com.dam.numberguesser.guesshandler;

import android.util.Range;

import com.dam.numberguesser.MainActivity;

public class GuessHandler {

    public static final int START_TRIES = 5;

    private final MainActivity mainActivity;

    private int numberToGuess;
    private int triesLeft;

    private static final Range<Integer> ALLOWED_RAN = new Range<Integer>(0, 100);

    public GuessHandler(MainActivity mainActivity) {
        // generate random number
        numberToGuess = (int) (Math.random() * 100);
        triesLeft = START_TRIES;

        this.mainActivity = mainActivity;
    }

    public void onUserGuess(String guess) {
        System.out.printf("User tries %s\n", guess);
        int guessInt = validateInputNum(guess);

        if (!ALLOWED_RAN.contains(guessInt)) { return; }

        if (guessInt == numberToGuess) {
            mainActivity.showMessage("You got it!");
            mainActivity.setGameState(GameState.WIN);
            return;
        }

        if (guessInt > numberToGuess) {
            mainActivity.showMessage("Too high!");
        } else {
            mainActivity.showMessage("Too low!");
        }
        updateTries();
    }

    private int validateInputNum(String strNum) {
        int num = -1;
        try {
            num = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            mainActivity.showMessage("Number must be between 0 and 100!");
        }
        return num;
    }

    private void updateTries() {
        mainActivity.updateTriesLeft(triesLeft--);
        if (triesLeft <- 0) {
            mainActivity.setGameState(GameState.LOST);
        }
    }

}
