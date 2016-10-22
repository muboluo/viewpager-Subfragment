package com.race.house.subfragmentdeliverdata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_open_next_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_open_next_page = (Button) findViewById(R.id.btn_open_next_page);
        btn_open_next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SubFragmentActivity.class);
                //开启fragment加载界面
                startActivity(intent);
            }
        });
    }
}
