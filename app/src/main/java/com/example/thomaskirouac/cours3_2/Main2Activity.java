package com.example.thomaskirouac.cours3_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
String text2Value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setListener();
    }

    private void setListener() {
        findViewById(R.id.btn_goToScene3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             EditText editText = findViewById(R.id.editText_scene2);
             text2Value = editText.getText().toString();
                moveToConnectActivity();
            }
        });
    }
    private void moveToConnectActivity(){
        Intent intent = new Intent(this,Main3Activity.class);
        intent.putExtra( "SCENE_2_TEXT",text2Value);
        startActivity(intent);
    }
}
