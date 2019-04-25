package com.example.dell.jizumoni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by dell on 2017/11/26.
 */

public class SystemsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systems);
        //实现按钮功能
        Button button=findViewById(R.id.button);
        NumberPicker numberPicker=findViewById(R.id.numberPicker);
        NumberPicker numberPicker2=findViewById(R.id.numberPicker2);
        numberPicker.setValue(10);
        numberPicker2.setValue(2);
        numberPicker.setMinValue(2);
        numberPicker2.setMinValue(2);
        numberPicker.setMaxValue(16);
        numberPicker2.setMaxValue(16);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到相关输入数据
                EditText editText=findViewById(R.id.editText);
                EditText editText2=findViewById(R.id.editText2);
                NumberPicker numberPicker=findViewById(R.id.numberPicker);
                NumberPicker numberPicker2=findViewById(R.id.numberPicker2);
                int k1=numberPicker.getValue();
                int k2=numberPicker2.getValue();
                String input_string=editText.getText().toString();
                String output=convert(input_string,k1,k2);
                editText2.setText(output);
            }
        });
        //10进制转2进制测试
    }
    public static String convert(String input,int k1,int k2){
        int input_10;
        input_10=convertTo10(input,k1);
        StringBuffer output=new StringBuffer();
        int remainder;

        while(input_10/k2 !=0)
        {
            remainder=input_10%k2;
            input_10=input_10/k2;
            output.append(convert10To16(remainder));
        }
        remainder=input_10%k2;
        output.append(convert10To16(remainder));
        output.reverse();
        String out=new String(output);
        return out;
    }//将k1进制转换为k2进制
    public static int convertTo10(String input,int k1) {
        int output=0;
        for(int i=0;i<input.length();i++)
        {
            int base=convert16To10(input.charAt(i));
            output+=Math.pow(k1,input.length()-i-1)*base;
        }
        return output;
    }//任意进制转化为10进制数
    public static char convert10To16(int input) {
        char output;
        switch(input)
        {
            case 10:
                output='A';
                break;
            case 11:
                output='B';
                break;
            case 12:
                output='C';
                break;
            case 13:
                output='D';
                break;
            case 14:
                output='E';
                break;
            case 15:
                output='F';
                break;
            default:
                output=(char)(input+48);
                break;
        }
        return output;
    }//10-15转11-16进制
    public static int convert16To10(char input) {
        int output;
        switch(input)
        {
            case 'A':
            case 'a':
                output=10;
                break;
            case 'B':
            case 'b':
                output=11;
                break;
            case 'C':
            case 'c':
                output=12;
                break;
            case 'D':
            case 'd':
                output=13;
                break;
            case 'E':
            case 'e':
                output=14;
                break;
            case 'F':
            case 'f':
                output=15;
                break;
            default:
                output=input-'0';
                break;
        }
        return output;
    }//16进制字符转10进制
    public static String convert(int input) {
        StringBuffer output=new StringBuffer();
        int remainder;
        while(input/2 !=0)
        {
            remainder=input%2;
            input=input/2;
            output.append(remainder);
        }
        remainder=input%2;
        output.append(remainder);
        output.reverse();
        String out=new String(output);
        return out;
    }//转换函数10进制转2进制测试
}
