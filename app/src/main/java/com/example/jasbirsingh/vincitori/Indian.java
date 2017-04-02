package com.example.jasbirsingh.vincitori;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class Indian extends AppCompatActivity {
ImageButton but1;
    Button but2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indian);

    but1= (ImageButton) findViewById(R.id.imageButton2);
        but2= (Button) findViewById(R.id.button2);

but1.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(Indian.this,Aadhar2.class);
        startActivity(i);
    }
});



    }
}
