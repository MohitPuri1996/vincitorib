package com.example.jasbirsingh.vincitori;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Foreigner extends AppCompatActivity {
ImageButton ocr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreigner);
        ocr= (ImageButton) findViewById(R.id.imageButton4);
        ocr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Foreigner.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
