package com.example.dell.jizumoni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dell.jizumoni.operate.AdditionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button4=findViewById(R.id.button4);
        Button button5=findViewById(R.id.button5);
        Button button6=findViewById(R.id.button6);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(MainActivity.this,SystemsActivity.class);
                startActivity(go);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(MainActivity.this,EncodeActivity.class);
                startActivity(go);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(MainActivity.this,AdditionActivity.class);
                startActivity(go);
            }
        });
    }
}
//include、
// activity 连接问题：fragment,tab页
//numberPicker使用ok
//在界面文件限制输入ok
//提示对话框。ok
//软件设计
//约束布局,activity
//设置弹出键盘格式ok
//优化设置: