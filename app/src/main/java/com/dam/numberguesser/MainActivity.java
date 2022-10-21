package com.dam.numberguesser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.numberguesser.guesshandler.GameState;
import com.dam.numberguesser.guesshandler.GuessHandler;

public class MainActivity extends AppCompatActivity {

    private GuessHandler guessHandler;

    private Button guessButton;
    private EditText guessInput;
    private TextView mainTv;
    private TextView subtitleTv;


    private GameState gameState = GameState.PLAYING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind to R contents
        guessButton = findViewById(R.id.button);
        guessInput = findViewById(R.id.guessInputText);
        mainTv = findViewById(R.id.mainTv);
        subtitleTv = findViewById(R.id.subtitleTv);
        // init components
        guessHandler = new GuessHandler(this);
        // init callbacks
        guessButton.setOnClickListener(v -> guessHandler.onUserGuess(guessInput.getText().toString()));
        // generic inits
        updateTriesLeft(GuessHandler.START_TRIES); // FIXME hardcoded
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        if (gameState == GameState.PLAYING) {
            return;
        }
        guessButton.setVisibility(View.INVISIBLE);
        guessInput.setVisibility(View.INVISIBLE);
        switch (gameState) {
            case LOST:
                findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.RED); // FIXME hardcoded value
                mainTv.setText("\uD83D\uDE13");
                subtitleTv.setText("Oops, you lost!");
                break;
            case WIN:
                findViewById(android.R.id.content).getRootView().setBackgroundColor(Color.GREEN); // FIXME hardcoded value
                mainTv.setText("\uD83D\uDE0E");
                subtitleTv.setText("Congratulations! You won!");
                break;
        }
    }

    public void showMessage(String message) {
        // create toast to notify user when inputs out of range number
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void updateTriesLeft(int i) {
        mainTv.setText(String.valueOf(i));
    }
}