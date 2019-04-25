package com.leiSun.grammar.test;

import com.leiSun.grammar.IO.File;

import java.io.IOException;

/**
 * @author 孙磊 on 2018/7/23
 * @version 1.0
 * @apiNote 测试函数
 */
public class Test1 {
    public static void main(String[] args)throws IOException {
        /**文件流*/
        File file=new File();
        file.fileOutputStream();
        file.fileInputStream();
    }
}
