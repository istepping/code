package main;

import core.Chess;
import core.Game;

import static core.Assist.*;
import static utils.StaticVariable.black_chessman;
import static utils.StaticVariable.chessboard;
import static utils.StaticVariable.white_chessman;

public class Test2 {
    /**
     * @author stone3 on 2018/7/7
     * @apiNote mark函数测试
     */
    public static void main(String[] args){
        initBoard();
        chessboard[8][8]=black_chessman;
        Game game=new Game();
        Chess chess=new Chess();
        outputBoard();
        //分别测试ok
        System.out.println(rowMark(8,8,black_chessman));
        print(verticalMark(8,8,black_chessman));
        print(lMark(8,8,black_chessman));
        print(rMark(8,8,black_chessman));
        //一个空格测试
        chessboard[10][8]=black_chessman;
        System.out.println(rowMark(8,8,black_chessman));
        print(verticalMark(8,8,black_chessman));
        print(lMark(8,8,black_chessman));
        print(rMark(8,8,black_chessman));
    }
}
