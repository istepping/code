package com.example.dell.jizumoni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.dell.jizumoni.SystemsActivity.convert;
import static com.example.dell.jizumoni.tool.StaticVariable.BACK_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.CPT_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.SHIFT_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.SOURCE_CODE;

/**
 * Created by dell on 2017/12/3.
 */

public class EncodeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        Button button2 = findViewById(R.id.button2);
        final EditText editText3 = findViewById(R.id.editText3);
        final EditText editText4 = findViewById(R.id.editText4);
        final EditText editText5 = findViewById(R.id.editText5);
        final EditText editText6 = findViewById(R.id.editText6);
        final EditText editText7 = findViewById(R.id.editText7);
        final TextView textView10 = findViewById(R.id.textView10);
        //设置spinner
        final Spinner spinner = findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("原码");
        list.add("补码");
        list.add("反码");
        list.add("移码");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        //按钮事件
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获得输入类型和输入信息
                long selectedItemId = (spinner.getSelectedItemId());
                String input_string = editText3.getText().toString();
                //确保信息正确：二进制数据，(EditText的digits属性)
                //处理信息
                String source_code=convertToSourceCode(selectedItemId,input_string);
                String complement_code=sourceCodeConvert(source_code,CPT_CODE);
                String back_code=sourceCodeConvert(source_code,BACK_CODE);
                String shift_code=sourceCodeConvert(source_code,SHIFT_CODE);
                //输出信息
                editText4.setText(source_code);
                editText5.setText(complement_code);
                editText6.setText(back_code);
                editText7.setText(shift_code);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    textView10.setText("原码");
                } else if (id == 1) {
                    textView10.setText("补码");
                } else if (id == 2) {
                    textView10.setText("反码");
                } else {
                    textView10.setText("移码");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView10.setText("原码");
            }
        });
    }
    public  String convertToSourceCode(long currentCode,String input_string) {
        if(input_string.charAt(0)=='0')
        {
            //正数形式都一样
            return input_string;
        }
        String output_string=new String();
        StringBuffer output_stringBuffer=new StringBuffer();
        if(currentCode==SOURCE_CODE)
        {
            output_string=input_string;
        }
        else if(currentCode==CPT_CODE)
        {
             output_string=sourceCodeConvert(input_string,CPT_CODE);//补码的补码是原码
        }
        else if(currentCode==BACK_CODE)
        {
            output_string=sourceCodeConvert(input_string,BACK_CODE);//反码的反码是原码
        }
        else if(currentCode==SHIFT_CODE)
        {
            String temp_string;
            //符号位取反，得到补码
            for(int i=0;i<input_string.length();i++)
            {
                if(i==0)
                {
                    output_stringBuffer.append(backConvert(input_string.charAt(i)));
                }
                else
                {
                    output_stringBuffer.append(input_string.charAt(i));
                }
            }
            temp_string=output_stringBuffer.toString();
            output_string=sourceCodeConvert(temp_string,CPT_CODE);//补码的补码是原码
        }
        else
        {
            new AlertDialog.Builder(this).setTitle("错误!")
                    .setMessage("参数错误!")
                    .setPositiveButton("确定",null)
                    .show();
        }
        return output_string;
    }//任意编码转化为原码
    public  String sourceCodeConvert(String input_string,long toCode) {
        if(input_string.charAt(0)=='0')
        {
            //正数形式都一样
            return input_string;
        }
        StringBuffer output_stringBuffer=new StringBuffer();
        String output_string=new String();
        if(toCode==SOURCE_CODE)
        {
            output_string=input_string;
        }

        else if(toCode==CPT_CODE)
        {
            String temp_string=sourceCodeConvert(input_string, BACK_CODE);//得到反码
            String temp2_string=convert(temp_string,2,10);//将二进制转换为10进制
            int temp_int=Integer.parseInt(temp2_string);
            temp_int+=1;//反码加一就是补码
            String temp3_string=Integer.toString(temp_int);
            output_string=convert(temp3_string,10,2);//得到二进制补码
            if(output_string.length()<temp_string.length())//消除转化为10进制时改变了二进制位数
            {
               StringBuffer temp4_stringBuffer=new StringBuffer(output_string);
               while(temp4_stringBuffer.length()<temp_string.length())
               {
                   temp4_stringBuffer.insert(0,'0');
               }
               output_string=temp4_stringBuffer.toString();
            }
        }

        else if(toCode==BACK_CODE)
        {
            for(int i=0;i<input_string.length();i++)
            {
                if(i==0)
                {
                    output_stringBuffer.append(input_string.charAt(i));
                }
                else
                {
                    output_stringBuffer.append(backConvert(input_string.charAt(i)));
                }
            }
            output_string=output_stringBuffer.toString();
        }

        else if(toCode==SHIFT_CODE)
        {
             String temp_string=sourceCodeConvert(input_string,CPT_CODE);//得到补码
            //补码符号位取反即是移码。
            for(int i=0;i<temp_string.length();i++)
            {
                if(i==0)
                {
                    output_stringBuffer.append(backConvert(temp_string.charAt(i)));
                }
                else
                {
                    output_stringBuffer.append(temp_string.charAt(i));
                }
            }
            output_string=output_stringBuffer.toString();
        }

        else {
            new AlertDialog.Builder(this).setTitle("错误!")
            .setMessage("参数错误!")
            .setPositiveButton("确定",null)
            .show();
        }

        return output_string;
    }//原码转化为任意编码
    public  char backConvert(char input_char){//取反函数,0/1转换
        char output_char;
        switch (input_char)
        {
            case '0':
                output_char='1';
                break;
            case '1':
                output_char='0';
                break;
                default:
                    output_char='0';
                    break;
        }
        return output_char;
    }//取反函数,0/1转换
}
