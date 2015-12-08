package com.example.gode_user01.text2speechdemo;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public final static String DEBUG_TAG = "MainActivity";
    private TextToSpeech tts;
    private ArrayList<String> ESLPhrases;
    private boolean Load = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        words();
        view();
    }

    private void initialize() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Load = true;
            }
        });
    }


    private void words(){
        ESLPhrases = new ArrayList<String>();
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.words));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            ESLPhrases.add(line);
        }
        Log.d(DEBUG_TAG, "ESLPhrases.size() = " + ESLPhrases.size() );
    }

    private void view(){
        ListView lv = (ListView) findViewById(R.id.alphabetlistview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.items_activity, ESLPhrases);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                handleClick(index);
            }
        });
    }

    private void handleClick(int index) {
        String text = ESLPhrases.get(index);
        if (Load) {
            tts.setSpeechRate(0.6f);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}