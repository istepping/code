package com.example.dell.jizumoni.operate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dell.jizumoni.R;
import com.example.dell.jizumoni.tool.StaticMethod;
import com.example.dell.jizumoni.tool.StaticVariable;

import java.util.ArrayList;
import java.util.List;

import static com.example.dell.jizumoni.tool.StaticVariable.BACK_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.CPT_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.CPT_CODE_DEVEDE;
import static com.example.dell.jizumoni.tool.StaticVariable.CPT_CODE_MULTIPLY;
import static com.example.dell.jizumoni.tool.StaticVariable.LEFT_SHIFT;
import static com.example.dell.jizumoni.tool.StaticVariable.RIGHT_SHIFT;
import static com.example.dell.jizumoni.tool.StaticVariable.SOURCE_CODE;
import static com.example.dell.jizumoni.tool.StaticVariable.SOURCE_CODE_ADDITION;
import static com.example.dell.jizumoni.tool.StaticVariable.SOURCE_CODE_DEVIDE;
import static com.example.dell.jizumoni.tool.StaticVariable.SOURCE_CODE_MULTIPLY;
/**
 * Created by dell on 2017/12/11.
 */

public class AdditionActivity extends AppCompatActivity {
    private static char C='0';//C位，是否有进位
    String inputA;
    String inputB;
    String output;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        final EditText editText8=findViewById(R.id.editText8);
        final EditText editText9=findViewById(R.id.editText9);
        final EditText editText10=findViewById(R.id.editText10);
        final Spinner spinner2 = findViewById(R.id.spinner2);
        final TextView textView4=findViewById(R.id.textView4);
        final TextView textView5=findViewById(R.id.textView5);
        final TextView textView11=findViewById(R.id.textView11);
        final TextView textView12=findViewById(R.id.textView12);
        Button button3=findViewById(R.id.button3);
        List<String> list = new ArrayList<>();
        list.add("原码加法");
        list.add("原码乘法");
        list.add("补码乘法");
        list.add("原码除法");
        list.add("补码除法");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(id==0)
                {
                    textView12.setText("原码加法");
                    textView4.setText("[A]原");
                    editText8.setHint("输入A的原码");
                    textView5.setText("[B]原");
                    editText9.setHint("输入B的原码");
                    textView11.setText("[A+B]补");
                    editText10.setHint("A加B的补码值");
                }
                else if(id==1)
                {
                    textView12.setText("原码乘法");
                    textView4.setText("[A]原");
                    editText8.setHint("输入A的原码");
                    textView5.setText("[B]原");
                    editText9.setHint("输入B的原码");
                    textView11.setText("[A•B]原");
                    editText10.setHint("A乘B的原码值");
                }
                else if(id==2)
                {
                    textView12.setText("补码乘法");
                    textView4.setText("[A]补");
                    editText8.setHint("输入A的补码");
                    textView5.setText("[B]补");
                    editText9.setHint("输入B的补码");
                    textView11.setText("[A•B]补");
                    editText10.setHint("A乘B的补码值");
                }
                else if(id==3)
                {
                    textView12.setText("原码除法");
                    textView4.setText("[A]原");
                    editText8.setHint("输入A的原码");
                    textView5.setText("[B]原");
                    editText9.setHint("输入B的原码");
                    textView11.setText("[A/B]原");
                    editText10.setHint("A加B的原码值");
                }
                else
                {
                    textView12.setText("补码除法");
                    textView4.setText("[A]补");
                    editText8.setHint("输入A的补码");
                    textView5.setText("[B]补");
                    editText9.setHint("输入B的补码");
                    textView11.setText("[A/B]补");
                    editText10.setHint("A除B的补码值");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setTitle("选择运算类型");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("按钮","单击了");
                inputA=editText8.getText().toString();
                inputB=editText9.getText().toString();
                long selectedItemId=spinner2.getSelectedItemId();
                if(!checkInput(inputA,inputB))
                {
                    Log.d("输入","有误");
                    editText10.setText("输入有误!");
                    return;
                }
                if(selectedItemId==SOURCE_CODE_ADDITION)
                {
                    addition(editText10);
                }
                else if(selectedItemId==SOURCE_CODE_MULTIPLY)
                {
                   sourceCodeOneMultiply(editText10);
                }
                else if(selectedItemId==CPT_CODE_MULTIPLY)
                {
                    CPTCodeOneMultiply(editText10);
                }
                else if(selectedItemId==SOURCE_CODE_DEVIDE)
                {
                    sourceCodeDivide(editText10);
                }
                else if(selectedItemId==CPT_CODE_DEVEDE)
                {
                    CPTCodeDivide(editText10);
                }
            }
        });
    }
    public void addition(EditText editText10) {
            output=add(inputA,inputB);
            //输出
            if(checkOutput(inputA,inputB,output))
            {
                editText10.setText("溢出错误");
            }
            else
            {
                editText10.setText(output);
            }
    }//加法运算
    public void sourceCodeOneMultiply(EditText editText10) {
        output=multiply(inputA,inputB,SOURCE_CODE_MULTIPLY);
        editText10.setText(output);
    }//原码一位乘
    public void CPTCodeOneMultiply(EditText editText10){
        output=multiply(inputA,inputB,CPT_CODE_MULTIPLY);
        editText10.setText(output);
    }//补码一位乘
    public void sourceCodeDivide(EditText editText10) {
        Log.d("sourceCodeDivide"," ");
        output=divide(inputA,inputB,SOURCE_CODE_DEVIDE);
        editText10.setText(output);
    }//原码除法
    public  void CPTCodeDivide(EditText editText10) {
        output=divide(inputA,inputB,CPT_CODE_DEVEDE);
        editText10.setText(output);
    }//补码除法
    //辅助函数
    private static boolean checkInput(String inputA,String inputB) {
        if(inputA.length()==inputB.length())
        {
            return true;
        }
        else
        {
            return false;
        }
    }//保证输入可计算
    public static String arithmeticShift(String input,int codeType,int flag,int bits) {
        String output_string;
        StringBuilder output_stringBuffer=new StringBuilder(input);
        StringBuilder temp_stringBuffer=new StringBuilder(input);//获得除符号位的二进制。算数移位
        temp_stringBuffer.deleteCharAt(0);
        char symbolBit=output_stringBuffer.charAt(0);//符号位
        char complementCode='0';//添补的代码
        if(symbolBit=='0')
        {
            //正数
            complementCode='0';
        }
        else
        {
            if(codeType==SOURCE_CODE)
            {
                complementCode='0';
            }
            else if(codeType==CPT_CODE)
            {
                if(flag==LEFT_SHIFT)
                {
                    complementCode='0';
                }
                else
                {
                    complementCode='1';
                }
            }
            else if(codeType==BACK_CODE)
            {
                complementCode='1';
            }
        }
        //移位位数bits
        int bit=bits;
        if(flag==LEFT_SHIFT)
        {
            //左移实现接口
            while(bit>0)
            {
                temp_stringBuffer.deleteCharAt(0);
                temp_stringBuffer.insert(temp_stringBuffer.length()-1,complementCode);
                bit--;
            }
        }
        else if(flag==RIGHT_SHIFT)
        {
            while(bit>0)
        {
            //低位丢弃，高位补数
            //temp_stringBuffer.deleteCharAt(temp_stringBuffer.length()-1);,为了计算方便这里的移位不删除后面的位数
            temp_stringBuffer.insert(0,complementCode);
            bit--;
        }
        }
        temp_stringBuffer.insert(0,symbolBit);//插入符号位
        output_string=temp_stringBuffer.toString();
        return output_string;
    }//算术移位，input:输入的二进制，codeType:input的码制，flag移位的方式，bits移位的个数，右移保留后面位数，不删除
    public static String logicShift(String input,int flag,int bits) {
        StringBuffer output=new StringBuffer(input);
        if(flag==LEFT_SHIFT)
        {
         //高位丢弃，低位添0
           while(bits>0)
           {
               output.deleteCharAt(0);
               output.append('0');
               bits--;
           }
        }
        else
        {
            //高位添0，地位丢弃
            while(bits>0)
            {
                output.insert(0,'0');//为了方便乘法运算这里不删除低位
                bits--;
            }
        }
        String output_string=output.toString();
        return output_string;
    }
    public static char xor(char A,char B) {
        if(A==B)
            return '0';
        else
            return '1';
    }//异或运算
    //加法辅助函数
    public  static String add(String inputA,String inputB) {
        StringBuffer inputA_stringBuffer=new StringBuffer(inputA);
        StringBuffer inputB_stringBuffer=new StringBuffer(inputB);
        while(inputA_stringBuffer.length()<inputB_stringBuffer.length())
        {
            inputA_stringBuffer.append('0');//使二者长度相同，满足向加条件相加
        }
        while(inputA_stringBuffer.length()>inputB_stringBuffer.length())
        {
            inputB_stringBuffer.append('0');
        }
        inputA=inputA_stringBuffer.toString();
        inputB=inputB_stringBuffer.toString();
        String output;
        StringBuilder output_stringBuffer=new StringBuilder();
        //获取A,B补码
        String inputACPT= StaticMethod.sourceCodeConvert(inputA, StaticVariable.CPT_CODE);
        String inputBCPT=StaticMethod.sourceCodeConvert(inputB,StaticVariable.CPT_CODE);
        char operateA;
        char operateB;
        char outputC;
        C='0';//清零
        for(int i=inputACPT.length()-1;i>=0;i--)
        {
            operateA=inputACPT.charAt(i);
            operateB=inputBCPT.charAt(i);
            outputC=operateSimple(operateA,operateB);
            output_stringBuffer.insert(0,outputC);
        }
        output=output_stringBuffer.toString();
        return output;
    }//补码加法
    public static String addSimple(String inputA,String inputB) {
        StringBuffer inputA_stringBuffer=new StringBuffer(inputA);
        StringBuffer inputB_stringBuffer=new StringBuffer(inputB);
        while(inputA_stringBuffer.length()<inputB_stringBuffer.length())
        {
            inputA_stringBuffer.append('0');//使二者长度相同，满足向加条件相加
        }
        while(inputA_stringBuffer.length()>inputB_stringBuffer.length())
        {
            inputB_stringBuffer.append('0');
        }
        inputA=inputA_stringBuffer.toString();
        inputB=inputB_stringBuffer.toString();
        String output;
        StringBuilder output_stringBuffer=new StringBuilder();
        char operateA;
        char operateB;
        char outputC;
        C='0';//清零
        for(int i=inputA.length()-1;i>=0;i--)
        {
            operateA=inputA.charAt(i);
            operateB=inputB.charAt(i);
            outputC=operateSimple(operateA,operateB);
            output_stringBuffer.insert(0,outputC);
        }
        output=output_stringBuffer.toString();
        return output;
    }//简单两个二进制数想加
    public static boolean checkOutput(String inputA,String inputB,String output){
        //两个正数相加得负数或者两个负数相加得正数
        if((inputA.charAt(0)=='0' && inputB.charAt(0)=='0' && output.charAt(0)=='1')||(inputA.charAt(0)=='1' && inputB.charAt(0)=='1'&& output.charAt(0)=='0'))
        {
            return true;
        }
        {
            return false;
        }
    }//溢出判断
    private static char operateSimple(char A,char B) {
        //四种情况 加进位
        if(C=='0')
        {
            if(A=='0' && B=='0')
            {
                C='0';
                return '0';
            }
            else if((A=='0' && B=='1')||(A=='1'&& B=='0'))
            {
                C='0';
                return '1';
            }
            else
            {
                C='1';
                return '0';
            }
        }
        else
        {
            if(A=='0' && B=='0')
            {
                C='0';
                return '1';
            }
            else if((A=='0' && B=='1')||(A=='1'&& B=='0'))
            {
                C='1';
                return '0';
            }
            else
            {
                C='1';
                return '1';
            }
        }
    }//计算一位相加
    //乘法辅助函数
    public static String multiply(String inputA,String inputB,int flag) {
        String output_string;
        //移位运算，异或运算
        //计算符号
        char A=inputA.charAt(0);
        char B=inputB.charAt(0);
        char sysmbolBit=xor(A,B);
        if(flag==SOURCE_CODE_MULTIPLY)
        {
            //得到两个绝对值
            StringBuilder A_stringBuffer=new StringBuilder(inputA);//11110
            StringBuilder B_stringBuffer=new StringBuilder(inputB);//11101
            B_stringBuffer.deleteCharAt(0);//删除符号位1101
            A_stringBuffer.deleteCharAt(0);
            A_stringBuffer.insert(0,'0');//绝对值化01110
            int n=B_stringBuffer.length();
            StringBuffer C_stringBuffer=new StringBuffer();//部分积
            String C_string;
            //初始化0
            for(int i=0;i<inputA.length();i++)
            {
                C_stringBuffer.append('0');//00000
            }
            C_string=C_stringBuffer.toString();
            Log.d("A_stringBuffer"," "+A_stringBuffer);
            Log.d("B_stringBuffer"," "+B_stringBuffer);
            while(n>0)//4
            {
                if (B_stringBuffer.charAt(n - 1) == '1') {
                    String A_string = A_stringBuffer.toString();
                    Log.d("向加前"," "+A_string+" "+C_string);
                    C_string = addSimple(A_string, C_string);
                }
                //右移一位
                Log.d("移位前"," "+C_string);
                C_string = logicShift(C_string, RIGHT_SHIFT, 1);
                Log.d("C_string"," "+C_string);
                n--;
            }
            StringBuffer C_stringBuffer2=new StringBuffer(C_string);
            C_stringBuffer2.deleteCharAt(0);
            C_stringBuffer2.insert(0,sysmbolBit);//更正符号位
            output_string=C_stringBuffer2.toString();
        }
        else
        {
            output_string=booth(inputA,inputB);
        }
        return output_string;
    }//flag,乘法类型
    public static String booth(String inputA,String inputB) {
        String output=new String();
        String minusA=minusCPT(inputA);
        char Y='0';//附加位
        minusA=doubleSymbol(minusA);//双符号化
        Log.d("[-x]补"," "+minusA);
        //初始化部分积
        StringBuffer output_buffer=new StringBuffer();
        output_buffer.append('0');
        for(int i=0;i<inputA.length();i++)
        {
            output_buffer.append('0');
        }
        output=output_buffer.toString();
        //初始化加数
        String inputA_string=new String(inputA);
        inputA_string=doubleSymbol(inputA_string);
        Log.d("[x]补"," "+inputA_string);
        for(int i=inputB.length()-1;i>=0;i--)
        {
            if(inputB.charAt(i)=='0' && Y=='1')
            {
                output=addSimple(output,inputA_string);
            }
            else if(inputB.charAt(i)=='1' && Y=='0')
            {
                output=addSimple(output,minusA);
            }
            Y=inputB.charAt(i);
            if(i!=0)
            {
                Log.d("移位前"," "+output);
                output=arithmeticShift(output,CPT_CODE,RIGHT_SHIFT,1);
                Log.d("移位后"," "+output);
            }
        }
        StringBuffer output_buffer2=new StringBuffer(output);
        output_buffer2.deleteCharAt(0);//删除一个符号位
        output=output_buffer2.toString();
        return output;
    }
    public static String minusCPT(String input) {
        String source_code=StaticMethod.convertToSourceCode(CPT_CODE,input);
        source_code=minusCode(source_code);
        String output=source_code;
        output=StaticMethod.sourceCodeConvert(output,CPT_CODE);
        return output;
    }//由[x]补得到[-x],
    public static String minusCode(String input) {
        StringBuffer input_buffer=new StringBuffer(input);
        if(input_buffer.charAt(0)=='0')
        {
            input_buffer.deleteCharAt(0);
            input_buffer.insert(0,'1');
        }
        else
        {
            input_buffer.deleteCharAt(0);
            input_buffer.insert(0,'0');
        }
        String output=input_buffer.toString();
        return output;
    }//[x]->[-x]
    public static String doubleSymbol(String input){
        StringBuffer output=new StringBuffer(input);
        if(output.charAt(0)=='0')
        {
            output.insert(0,'0');
        }
        else
        {
            output.insert(0,'1');
        }
        String output_string=output.toString();
        return output_string;
    }//建立双符号位
    //除法辅助函数
    public static String divide(String inputA,String inputB,int flag) {
        String output;
        StringBuffer output_buffer=new StringBuffer();
        if(flag==SOURCE_CODE_DEVIDE)
        {
            char A=inputA.charAt(0);
            char B=inputB.charAt(0);
            char sysmbolBit=xor(A,B);
            String minusA=sourceToMinusCPT(inputB);
            Log.d("minusA"," "+minusA);
            String addB=sourceToCPT(inputB);
            String input_string=new String(inputA);
            input_string=sourceToAB(input_string);//得到绝对值
            Log.d("绝对值"," "+input_string);
            input_string=addSimple(input_string,minusA);
            Log.d("input_string"," "+input_string);
            for(int i=0;i<inputA.length();i++)
            {
                if(input_string.charAt(0)=='1')
                {
                    //负数
                    output_buffer.append('0');
                    input_string=logicShift(input_string,LEFT_SHIFT,1);
                    Log.d("logicShift"," "+input_string+"addB "+addB);
                    input_string=addSimple(input_string,addB);
                    Log.d("上商"," 0"+"相加后"+input_string);
                }
                else
                {
                    //正数,
                    output_buffer.append('1');
                    input_string=logicShift(input_string,LEFT_SHIFT,1);
                    input_string=addSimple(input_string,minusA);
                    Log.d("上商"," 1"+"相加后"+input_string);
                }
            }
            if(output_buffer.charAt(0)!=sysmbolBit)
            {
                output_buffer.deleteCharAt(0);
                output_buffer.insert(0,sysmbolBit);
            }

        }
        else
        {
            String input=new String(inputA);
            String addA=new String(inputB);
            String minusB=CPTToMinusCPT(inputB);
            if(input.charAt(0)==inputB.charAt(0))
            {
                input=addSimple(input,minusB);
            }
            else
            {
                input=addSimple(input,addA);
            }
            for(int i=0;i<inputA.length();i++)
            {
                if(input.charAt(0)==inputB.charAt(0))
                {
                    output_buffer.append('1');
                    input=logicShift(input,LEFT_SHIFT,1);
                    input=addSimple(input,minusB);
                }
                else
                {
                    output_buffer.append('0');
                    input=logicShift(input,LEFT_SHIFT,1);
                    input=addSimple(input,addA);
                }
            }
        }
        output=output_buffer.toString();
        return output;
    }//除法类型
    public static String sourceToMinusCPT(String input) {
        Log.d("input"," "+input);
        String source=sourceToAB(input);
        source=minusCode(source);
        Log.d("source"," "+source);
        String CPT_code=StaticMethod.sourceCodeConvert(source,CPT_CODE);
        return CPT_code;
    }//[x]原->[-x*]补
    public static String sourceToCPT(String input) {
        String output=sourceToAB(input);
        Log.d("绝对值"," "+output);
        output=StaticMethod.sourceCodeConvert(output,CPT_CODE);
        Log.d("output"," "+output);
        return output;
    }//[x]原->[x*]补
    public static String sourceToAB(String input) {
        StringBuffer output_buffer=new StringBuffer(input);
        if(input.charAt(0)=='1')
        {
         output_buffer.deleteCharAt(0);
         output_buffer.insert(0,'0');
        }
        String output=output_buffer.toString();
        return output;
    }//[x]->[x]的绝对值
    public static String CPTToMinusCPT(String input) {
        String output=StaticMethod.convertToSourceCode(CPT_CODE,input);
        output=minusCode(output);
        output=StaticMethod.sourceCodeConvert(output,CPT_CODE);
        return output;
    }//[x]补->[-x]补
}
