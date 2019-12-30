package com.asyn.task.myinjectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.asyn.task.library.ContentView;
import com.asyn.task.library.InjectViews;
import com.asyn.task.library.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @InjectViews(R.id.btn)
    private Button button;
    @InjectViews(R.id.tv)
    private TextView tv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @OnClick({R.id.btn,R.id.tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn:
                Toast.makeText(this,"我被点击了btn",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv:
                Toast.makeText(this,"我被点击了tv",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
