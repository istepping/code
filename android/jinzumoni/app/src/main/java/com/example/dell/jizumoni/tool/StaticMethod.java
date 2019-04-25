package com.example.dell.jizumoni.tool;


import android.support.v7.app.AlertDialog;

import static com.example.dell.jizumoni.SystemsActivity.convert;
import static com.example.dell.jizumoni.tool.StaticVariable.BACK_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.CPT_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.SHIFT_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.SOURCE_CODE;

/**
 * Created by dell on 2017/12/9.
 */
public class StaticMethod {
    public static char backConvert(char input_char){//取反函数,0/1转换
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
    public static String sourceCodeConvert(String input_string,long toCode) {
        if(input_string.charAt(0)=='0')
        {
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
        return output_string;
    }//原码转化为任意编码
    public static String convertToSourceCode(long currentCode,String input_string) {
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
        return output_string;
    }//任意编码转化为原码
}
