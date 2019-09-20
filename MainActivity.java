package com.subitech.texttospeech;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button btn;
    private Button btnCheck;
    private Button btnReset;
    private RadioGroup rbGroup;
    private RadioButton rbChina;
    private RadioButton rbUk;
    private RadioButton rbGerman;
    private RadioButton rbUs;
    private RadioButton rbKorea;
    private RadioButton rbItaly;
    private int result;
    private TextToSpeech tts;
    private SeekBar seekPitch;
    private SeekBar seekSpeed;
    private TextView seeSpeed;
    private TextView seePitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //@Author coderSuresh

        btn = findViewById(R.id.btn);
        btnReset = findViewById(R.id.btn_reset);
        seekPitch = findViewById(R.id.seek_pitch);
        seekSpeed = findViewById(R.id.seek_speed);
        btnCheck = findViewById(R.id.btn_check);
        editText = findViewById(R.id.et);

        seeSpeed = findViewById(R.id.see_speed);
        seePitch = findViewById(R.id.see_pitch);

        // references for radio buttons
        rbGroup = findViewById(R.id.rg);
        rbChina = findViewById(R.id.rb_china);
        rbUk = findViewById(R.id.rb_uk);
        rbGerman = findViewById(R.id.rb_german);
        rbUs = findViewById(R.id.rb_us);
        rbKorea = findViewById(R.id.rb_korea);
        rbItaly = findViewById(R.id.rb_italy);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speak();
                    }
                });

        resetPitch();

        tts =
                new TextToSpeech(
                        this,
                        new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {

                                if (status == TextToSpeech.SUCCESS) {

                                    btnCheck.setOnClickListener(
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    checkLang();
                                                }
                                            });

                                    if (result == TextToSpeech.LANG_MISSING_DATA
                                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                        /*Toast.makeText(this, "Language not supported", Toast.LENGTH_LONG).show();*/
                                        Log.e("Tts", "Lang not supported");
                                    } else {
                                        btn.setEnabled(true);
                                    }
                                } else {
                                    /*Toast.makeText(this, "Initialization Failed", Toast.LENGTH_SHORT).show();*/
                                    Log.e("tts", "initialization Failed");
                                }
                            }
                        });

        showPercent();

     /*   FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_me:
                startActivity(new Intent(MainActivity.this, MyIntro.class));
                return true;

            default:
                return onOptionsItemSelected(item);
        }
    }


    public void speak() {

        String text = editText.getText().toString();

        float pitch = (float) seekPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float) seekSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        if (text.isEmpty()) {

            Toast.makeText(this, "Please enter some text", Toast.LENGTH_SHORT).show();
        }

        tts.setPitch(pitch);
        tts.setSpeechRate(speed);

        try {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } catch (Exception e) {
            Toast.makeText(this, "can't speak " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void checkLang() {
        if (rbChina.isChecked()) {
            result = tts.setLanguage(Locale.CHINA);

            Toast.makeText(this, "Language: China", Toast.LENGTH_SHORT).show();

        } else if (rbUs.isChecked()) {
            result = tts.setLanguage(Locale.US);

            Toast.makeText(this, "Language: US (Root)", Toast.LENGTH_SHORT).show();
        } else if (rbKorea.isChecked()) {
            result = tts.setLanguage(Locale.KOREA);

            Toast.makeText(this, "Language: Korean", Toast.LENGTH_SHORT).show();
        } else if (rbGerman.isChecked()) {
            result = tts.setLanguage(Locale.GERMAN);
            Toast.makeText(this, "Language: German", Toast.LENGTH_SHORT).show();
        } else if (rbItaly.isChecked()) {
            result = tts.setLanguage(Locale.ITALY);
            Toast.makeText(this, "Language: Italy", Toast.LENGTH_SHORT).show();
        } else if (rbUk.isChecked()) {
            result = tts.setLanguage(Locale.UK);
            Toast.makeText(this, "Language: UK", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid language", Toast.LENGTH_LONG).show();
        }
    }

    public void resetPitch() {
        btnReset.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tts.setPitch(50);
                        tts.setSpeechRate(50);
                        seekPitch.setProgress(50);
                        seekSpeed.setProgress(50);
                    }
                });
    }

    public void showPercent() {
        seekPitch.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    public void onStopTrackingTouch(SeekBar bar) {
                        int value = bar.getProgress();
                    }

                    public void onStartTrackingTouch(SeekBar bar) {
                    }

                    public void onProgressChanged(SeekBar bar, int paramInt, boolean paramBoolean) {

                        seePitch.setText(String.format(Locale.US, "%d%%", paramInt));
                    }
                });

        seekSpeed.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    public void onStopTrackingTouch(SeekBar bar) {
                        int value = bar.getProgress();
                    }

                    public void onStartTrackingTouch(SeekBar bar) {
                    }

                    public void onProgressChanged(SeekBar bar, int paramInt, boolean paramBoolean) {
                        seeSpeed.setText(String.format(Locale.US, "%d%%", paramInt));
                    }
                });
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
