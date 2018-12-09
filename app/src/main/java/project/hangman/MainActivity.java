package project.hangman;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    Button beginnerButton;
    Button advancedButton;


    //String url ="https://wordsapiv1.p.rapidapi.com/words/?random=true";
    String gameWord = "not null";
    String potentialWord = "also not null";
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String url ="https://wordsapiv1.p.rapidapi.com/words/?random=true";

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            Log.d("Response", response.getString("word"));
                            potentialWord= response.getString("word");
                            Log.d("Potential**********", potentialWord);
                            int spaceOccurrence = potentialWord.indexOf(" ");
                            Log.d("SPACE INDEX********", String.valueOf(spaceOccurrence));
                            if (spaceOccurrence != -1) {
                                potentialWord = potentialWord.substring(0, spaceOccurrence);
                            }
                            gameWord = potentialWord;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.w("Response", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-RapidAPI-Key", "CiQsyIyhApmshwVvH76egK5yYwYvp161sI2jsnEQ86QFhSqxhp");
                Log.d("Response", params.toString());
                return params;
            }
        };

        queue.add(request);


        beginnerButton = findViewById(R.id.beginner); //change button to name of the beginner button thing
        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to a new android page thing
                Toast.makeText(getApplicationContext(), "Starting beginner game", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                //beginnerWord hardcoded to String testing
                myIntent.putExtra("beginnerWord", gameWord);
                myIntent.putExtra("difficulty", 0);
                MainActivity.this.startActivity(myIntent);
            }
        });

        advancedButton = findViewById(R.id.advanced);
        advancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to a new android page thing
                Toast.makeText(getApplicationContext(), "Starting advanced game", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                //beginnerWord hardcoded to String testing
                myIntent.putExtra("advancedWord", gameWord);
                myIntent.putExtra("difficulty", 1);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //Needs to be run as an AsyncTask bc it requires network access
        //new WordMaker().execute();

    }

    private String wordPull(final int gameDifficulty) {
        final String url ="https://wordsapiv1.p.rapidapi.com/words/?random=true";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        Log.d("Response", response.toString());
                        try {
                            Log.d("Response", response.getString("word"));
                            potentialWord = response.getString("word");
                            Log.d("Potential**********", potentialWord);
                            int spaceOccurrence = potentialWord.indexOf(" ");
                            Log.d("SPACE INDEX********", String.valueOf(spaceOccurrence));
                            if (spaceOccurrence != -1) {
                                potentialWord = potentialWord.substring(0, spaceOccurrence);
                            }
                            Log.d("After space", potentialWord);
                            flag = MainActivity.wordDifficultyCheck(potentialWord, gameDifficulty);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.w("Response", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("X-RapidAPI-Key", "CiQsyIyhApmshwVvH76egK5yYwYvp161sI2jsnEQ86QFhSqxhp");
                Log.d("Response", params.toString());
                return params;
            }
        };
        do {
            queue.add(request);
        } while (!flag);
        gameWord = potentialWord;
        return gameWord;
    }

    private static boolean wordDifficultyCheck(String word, int difficulty) {
        int maxBeginnerLength = 7;
        if (difficulty == 0) {
            return word.length() < maxBeginnerLength;
        }
        return word.length() > maxBeginnerLength;
    }
}
