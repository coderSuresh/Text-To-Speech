package com.subitech.texttospeech;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MyIntro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showMe();
    }

    private void showMe() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About Me");
        builder.setMessage(R.string.about_me);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MyIntro.this, "Thank you.", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                startActivity(new Intent(MyIntro.this, MyPage.class));
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MyIntro.this, "You are bad guy.", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
                finish();
            }
        });

        builder.create().show();
    }
}
