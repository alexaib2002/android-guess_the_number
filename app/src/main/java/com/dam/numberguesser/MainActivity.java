package com.dam.numberguesser;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

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
    private Button retryButton;
    private EditText guessInput;
    private TextView mainTv;
    private TextView subtitleTv;


    private GameState gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // bind to R contents
        guessButton = findViewById(R.id.button);
        guessInput = findViewById(R.id.guessInputText);
        mainTv = findViewById(R.id.mainTv);
        subtitleTv = findViewById(R.id.subtitleTv);
        retryButton = findViewById(R.id.retryButton);
        // init components
        guessHandler = new GuessHandler(this);
        // init callbacks
        guessButton.setOnClickListener(v -> guessHandler
                .onUserGuess(guessInput.getText().toString()));
        retryButton.setOnClickListener(v -> setGameState(GameState.PLAYING));
        // generic inits
        setGameState(GameState.PLAYING);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        switch (gameState) {
            case LOST:
            case WIN:
                guessButton.setVisibility(View.INVISIBLE);
                guessInput.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.VISIBLE);
                break;
            case PLAYING:
                guessButton.setVisibility(View.VISIBLE);
                guessInput.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE); // GONE permite que el elemento no afecte al layout mostrado
                initNewGame();
                break;
        }
        switch (gameState) {
            case PLAYING:
                setBgColor(R.color.main_activity_bg);
                subtitleTv.setText(R.string.playing_state_subtitle);
                break;
            case LOST:
                setBgColor(R.color.lost_state_bg);
                mainTv.setText(R.string.lost_emoji);
                subtitleTv.setText(R.string.lost_state_subtitle);
                break;
            case WIN:
                setBgColor(R.color.win_state_bg);
                mainTv.setText(R.string.win_emoji);
                subtitleTv.setText(R.string.win_state_subtitle);
                break;
        }
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(int resourceId) {
        showMessage(getString(resourceId));
    }

    public void updateTriesLeft(int i) {
        mainTv.setText(String.valueOf(i));
    }

    private void setBgColor(@ColorRes int color) {
        findViewById(android.R.id.content).getRootView().setBackgroundColor(
                ResourcesCompat.getColor(
                        getResources(), color, null));
    }

    private void initNewGame() {
        guessHandler.initAttributes();
    }
}