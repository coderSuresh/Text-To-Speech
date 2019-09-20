package com.subitech.texttospeech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MyPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        letHerComeToMe();
    }

    //I got a gf OMG
    private void letHerComeToMe() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://fb.me/cderSuresh")); //What a username dude
        startActivity(intent);
        finish();

        //dang Cute
    }
}
