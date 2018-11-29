package project.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    //number of characters in the word
    private int numCharacters;
    //number of correct guesses
    private int numCorrect;
    //number of body parts
    private int numBodyParts = 6;
    //current body part (0 = head, 2 = body... 5 = one leg)
    private int currentBodyPart;

    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goes back to the home page
                Toast.makeText(getApplicationContext(),"Going home", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(GameActivity.this, MainActivity.class);
                GameActivity.this.startActivity(myIntent);
            }
        });
        this.displayWrongLetters("Wrong Letters");
    }
    private void displayWrongLetters(String letters) {
        TextView wrongLetters;
        wrongLetters = findViewById(R.id.wrongLetters);
        wrongLetters.setText(letters);
    }
}
