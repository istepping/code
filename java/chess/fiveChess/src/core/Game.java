package core;

import java.util.Scanner;

import static core.Assist.initBoard;
import static core.Assist.inputChess;
import static core.Assist.outputBoard;
import static utils.StaticVariable.*;

/**
 * 游戏玩法控制类
 * */
public class Game {
    //实例化下棋算法的类
    private Chess chess=new Chess();
    //下棋的令牌拿到令牌的一端下棋，黑棋先下
    private int turn=black_chessman;
    //黑棋先下,游戏入口函数
    public void gameStart(){
        initBoard();//初始化
        //chessboard[8][8]=white_chessman;
        outputBoard();
        int gameOver=0;
        while(gameOver==0)
        {
            if(turn==black_chessman)
            {
                blackChess();//调用下棋方法
                //chess.AIChess(black_chessman);
                //outputBoard();
                turn=white_chessman;//换令牌
            }
            else
            {
                whiteChess();
                turn=black_chessman;
            }
            gameOver=chess.gameOver();//判断游戏是否结束
        }
        if(gameOver==black_win)
        {
            System.out.println("黑棋胜");
        }
        else if(gameOver==white_win)
        {
            System.out.println("白棋胜");
        }
        else
        {
            System.out.println("平局");
        }
    }
    /**
     * 黑棋下棋
     * */
    private void blackChess(){
       //黑子输入
        Scanner input=new Scanner(System.in);
        System.out.print("请输入落子位置(如:2 5):");
        int x=input.nextInt();
        int y=input.nextInt();
        //输入进去，执行错误则返回
        if(!inputChess(x,y,black_chessman))
        {
            System.out.print("请正确输入!");
            blackChess();
        }
        else
        {
            //正确执行,重新输出棋盘
            outputBoard();
        }
    }
    /**
     * 白棋下棋
     * */
    private void whiteChess(){
      chess.robotChess(white_chessman);
      outputBoard();
    }
}
