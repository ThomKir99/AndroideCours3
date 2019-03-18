package com.example.thomaskirouac.cours3_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String text2Value= getIntent().getStringExtra("SCENE_2_TEXT");
        Toast.makeText(this,text2Value,Toast.LENGTH_SHORT).show();
    }

}
