package core;

import static utils.StaticVariable.*;

/**
 * created by stone3 on 2018-7-3
 * 存放辅助函数
 * */
public class Assist {
    public static void print(Object o){
        System.out.println(o);
    }
    //初始化棋盘
    public static void initBoard() {
       for (int i=0;i<board_width;i++)
           for (int j=0;j<board_height;j++)
           {
               chessboard[i][j]=0;//棋盘为空
           }
    }
    public static void outputBoard() {
        System.out.print(" ");
        for (int i=0;i<board_width;i++)
        {
            System.out.print("  "+i);
        }
        System.out.println();
        for (int i=0;i<board_height;i++)
        {
            System.out.print(i);
            for (int j=0;j<board_width;j++)
            {
                if(j>=10)
                {
                    System.out.print(" ");
                }
                if(!(i>=10 && j==0))
                {
                    System.out.print(" ");
                }
                if(chessboard[j][i]==black_chessman)
                {
                    System.out.print(" *");//代表黑棋
                }
                else if(chessboard[j][i]==white_chessman)
                {
                    System.out.print(" #");//代表白棋
                }
                else
                {
                    System.out.print("  ");//代表无棋子
                }
            }
            System.out.println();//换行
        }

    }
    //输入棋子位置函数(x,y)坐标位置,BW:棋子类型
    public static boolean inputChess(int x,int y,int BW){
        //检测x,y合法性.
        if((x>=0 && x<=16) && (y>=0 && y<=16) && (chessboard[x][y]==0))
        {
            chessboard[x][y]=BW;
            //print("inputChess:落子"+x+","+y);
            score[x][y]=mark(x,y);//更新数组
            //print("这个地方能不能输出?");
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * 数组copy函数
     * */
    public static void arrayCopy(int[][] array1,int[][] array2) {
        for(int i=0;i<board_width;i++)
            for(int j=0;j<board_height;j++) {
                array2[i][j]=array1[i][j];
            }
    }
    public static int min(int aera1,int aera2,int aera3,int aera4) {
        int min=1;
        int minValue=aera1;
        if(aera2<minValue)
        {
            min=2;
            minValue=aera2;
        }
        if(aera3<minValue)
        {
            min=3;
            minValue=aera3;
        }
        if(aera4<minValue)
        {
            min=4;
            minValue=aera4;
        }
        return min;
    }
    /**
     * 对score数组赋值的算法
     * */
    public static int mark(int x,int y){
        int white_score=rowMark(x,y,white_chessman)+verticalMark(x,y,white_chessman)+rMark(x,y,white_chessman)+lMark(x,y,white_chessman);
        int black_score=rowMark(x,y,black_chessman)+verticalMark(x,y,black_chessman)+rMark(x,y,black_chessman)+lMark(x,y,black_chessman);
        return white_score-black_score;
    }
    /**
     * created by stone3 on 2018-7-5
     * 行评分 具体评分思路参照安排好的评分表,附子文件中
     * @param x 行坐标
     * @param y 列坐标
     * @param chessman 棋子类型
     * */
    public static int rowMark(int x,int y,int chessman){
        int score=0;
        for(int i=0;i<board_width;i++){
            //只对有棋子进行评分相比之前效率高,if else 比全部是if效率高
            //print("当前横坐标:"+i);
            //if(i==8){
                //print(chessboard[i-1][y]);
                //print(chessboard[i-2][y]);
           // }
            if(chessboard[i][y]==chessman){
                //print("当前有落子:"+i+","+y);
                if((i==0 || chessboard[i-1][y]>0) && i+4<board_width){
                    //左侧堵住,参照评分表(1)(7)(9)(10)(11)
                        if(chessboard[i+1][y]==chessman && chessboard[i+2][y]==chessman && chessboard[i+3][y]==chessman){
                            if(chessboard[i+4][y]==chessman) {
                                score+=score1;
                                i+=4;//加速扫描
                                //print("(1)");
                                continue;
                            }
                            else if(chessboard[i+4][y]==0){
                                score+=score3;
                                i+=4;//加速扫描
                                //print("(7)");
                                continue;
                            }
                        }//(1)和(7)
                        if(chessboard[i+4][y]==chessman && ((chessboard[i+1][y]==0 && chessboard[i+2][y]==chessman && chessboard[i+3][y]==chessman) || (chessboard[i+1][y]==chessman && chessboard[i+2][y]==0 && chessboard[i+3][y]==chessman) || (chessboard[i+1][y]==chessman && chessboard[i+2][y]==chessman && chessboard[i+3][y]==0))){
                            score+=score3;
                            i+=4;
                            //print("9,10,17");
                        }//(9)(10)(11)
                }
                else if(i>0 && chessboard[i-1][y]==0 && i+4<board_width && (i==1 || chessboard[i-2][y]>0)){
                    //左侧有一个空格 (2),(3),(5),(6),(8),(14)
                    if(chessboard[i+1][y]==0 && chessboard[i+2][y]==chessman && chessboard[i+4][y]==0){
                        if(chessboard[i+3][y]==0){
                            score+=score4;//(14)
                            i+=4;
                            //print("(14)");
                        }
                        else if(chessboard[i+3][y]==chessman){
                            score+=score3;//(6)
                            i+=4;
                            //print("(6)");
                        }
                    }
                    else if(chessboard[i+1][y]==chessman){
                        if(chessboard[i+2][y]==0 && chessboard[i+3][y]==chessman && chessboard[i+4][y]==0){
                            score+=score3;
                            i+=4;
                            //print("(5)");
                        }//(5)
                        else if(chessboard[i+2][y]==chessman){
                            if(chessboard[i+3][y]==0 && chessboard[i+4][y]==0){
                                score+=score3;
                                i+=4;
                                //print("(3)");
                            }//(3)
                            else if(chessboard[i+3][y]==chessman){
                                if(chessboard[i+4][y]!=chessman && chessboard[i+4][y]!=0){
                                    score+=score3;
                                    i+=4;
                                }
                                else if(chessboard[i+4][y]==0){
                                    score+=score2;
                                    i+=4;
                                }
                            }
                        }
                    }
                }
                else if(i>1 && chessboard[i-1][y]==0 && chessboard[i-2][y]==0 && i+3<board_width && chessboard[i+3][y]==0){
                    //左侧有两个空格
                    //print("当前考虑,两个空格");
                    if(chessboard[i+1][y]==0){
                        if(chessboard[i+2][y]==0){
                            //print("(16)");
                            score+=score5;
                            i+=3;
                        }//(16)
                        else if(chessboard[i+2][y]==chessman){
                            score+=score4;
                            i+=3;
                        }//(13)
                    }
                    else if(chessboard[i+1][y]==chessman){
                        if(chessboard[i+2][y]==chessman){
                            score+=score3;
                            i+=3;
                        }//(4)
                        else if(chessboard[i+2][y]==0){
                            score+=score4;
                            i+=3;
                        }//(12)
                    }
                }
            }
        }
        return score;
    }
    /**
     * created by stone3 on 2018-7-5
     * 列评分 具体评分思路参照安排好的评分表,附子文件中
     * @param x 行坐标
     * @param y 列坐标
     * @param chessman 棋子类型
     * */
    public static int verticalMark(int x,int y,int chessman){
        int score=0;
        for(int i=0;i<board_height;i++){
            //只对有棋子进行评分相比之前效率高,if else 比全部是if效率高
            if(chessboard[x][i]==chessman){
                if((i==0 || chessboard[x][i-1]>0) && i+4<board_height){
                    //左侧堵住,参照评分表(1)(7)(9)(10)(11)
                    if(chessboard[x][i+1]==chessman && chessboard[x][i+2]==chessman && chessboard[x][i+3]==chessman){
                        if(chessboard[x][i+4]==chessman) {
                            score+=score1;
                            i+=4;//加速扫描
                            continue;
                        }
                        else if(chessboard[x][i+4]==0){
                            score+=score3;
                            i+=4;//加速扫描
                            continue;
                        }
                    }//(1)和(7)
                    if(chessboard[x][i+4]==chessman && ((chessboard[x][i+1]==0 && chessboard[x][i+2]==chessman && chessboard[x][i+3]==chessman) || (chessboard[x][i+1]==chessman && chessboard[x][x+2]==0 && chessboard[x][i+3]==chessman) || (chessboard[x][i+1]==chessman && chessboard[x][i+2]==chessman && chessboard[x][i+3]==0))){
                        score+=score3;
                        i+=4;
                    }//(9)(10)(11)
                }
                else if(i>0 && chessboard[x][i-1]==0 && i+4<board_height && (i==1 || chessboard[x][i-2]>0)){
                    //左侧有一个空格 (2),(3),(5),(6),(8),(14)
                    if(chessboard[x][i+1]==0 && chessboard[x][i+2]==chessman && chessboard[x][i+4]==0){
                        if(chessboard[x][i+3]==0){
                            score+=score4;//(14)
                            i+=4;
                        }
                        else if(chessboard[x][i+3]==chessman){
                            score+=score3;//(6)
                            i+=4;
                        }
                    }
                    else if(chessboard[x][i+1]==chessman){
                        if(chessboard[x][i+2]==0 && chessboard[x][i+3]==chessman && chessboard[x][i+4]==0){
                            score+=score3;
                            i+=4;
                        }//(5)
                        else if(chessboard[x][i+2]==chessman){
                            if(chessboard[x][i+3]==0 && chessboard[x][i+4]==0){
                                score+=score3;
                                i+=4;
                            }//(3)
                            else if(chessboard[x][i+3]==chessman){
                                if(chessboard[x][i+4]!=chessman && chessboard[x][i+4]!=0){
                                    score+=score3;
                                    i+=4;
                                }
                                else if(chessboard[x][i+4]==0){
                                    score+=score2;
                                    i+=4;
                                }
                            }
                        }
                    }
                }
                else if(i>1 && chessboard[x][i-1]==0 && chessboard[x][i-2]==0 && i+3<board_width && chessboard[x][i+3]==0){
                    //左侧有两个空格
                    if(chessboard[x][i+1]==0){
                        if(chessboard[x][i+2]==0){
                            score+=score5;
                            i+=3;
                        }//(16)
                        else if(chessboard[x][i+2]==chessman){
                            score+=score4;
                            i+=3;
                        }//(13)
                    }
                    else if(chessboard[x][i+1]==chessman){
                        if(chessboard[x][i+2]==chessman){
                            score+=score3;
                            i+=3;
                        }//(4)
                        else if(chessboard[x][i+2]==0){
                            score+=score4;
                            i+=3;
                        }//(12)
                    }
                }
            }
        }
        return score;
    }
    /**
     * created by stone3 on 2018-7-5
     * 右斜评分 具体评分思路参照安排好的评分表,附子文件中
     * @param x 行坐标
     * @param y 列坐标
     * @param chessman 棋子类型
     * */
    public static int rMark(int x,int y,int chessman){
        while(y!=0 && x!=board_width-1){
            x++;
            y--;
        }//回到起始点,下一点x-1,y+1上一点:x+1,y-1;
        int score=0;
        int board_height=0;//记录遍历长度，这里变量名和高度一样，省的改后面代码变量
        if(y==0){
            board_height=x+1;
        }
        else{
            board_height=board_width-y;
        }
        for(int i=0;i<board_height;i++){
            //只对有棋子进行评分相比之前效率高,if else 比全部是if效率高
            if(chessboard[x][y]==chessman){
                if((i==0 || chessboard[x+1][y-1]>0) && i+4<board_height){
                    //左侧堵住,参照评分表(1)(7)(9)(10)(11)
                    if(chessboard[x-1][y+1]==chessman && chessboard[x-2][y+2]==chessman && chessboard[x-3][y+3]==chessman){
                        if(chessboard[x-4][y+4]==chessman) {
                            score+=score1;
                            i+=4;//加速扫描
                            continue;
                        }
                        else if(chessboard[x-4][y+4]==0){
                            score+=score3;
                            i+=4;//加速扫描
                            continue;
                        }
                    }//(1)和(7)
                    if(chessboard[x-4][y+4]==chessman && ((chessboard[x-1][y+1]==0 && chessboard[x-2][y+2]==chessman && chessboard[x-3][y+3]==chessman) || (chessboard[x-1][y+1]==chessman && chessboard[x-2][y+2]==0 && chessboard[x-3][y+3]==chessman) || (chessboard[x-1][y+1]==chessman && chessboard[x-2][y+2]==chessman && chessboard[x-3][y+3]==0))){
                        score+=score3;
                        i+=4;
                    }//(9)(10)(11)
                }
                else if(i>0 && chessboard[x+1][y-1]==0 && i+4<board_height && (i==1 || chessboard[x+2][y-2]>0)){
                    //左侧有一个空格 (2),(3),(5),(6),(8),(14)
                    if(chessboard[x-1][y+1]==0 && chessboard[x-2][y+2]==chessman && chessboard[x-4][y+4]==0){
                        if(chessboard[x-3][y+3]==0){
                            score+=score4;//(14)
                            i+=4;
                        }
                        else if(chessboard[x-3][y+3]==chessman){
                            score+=score3;//(6)
                            i+=4;
                        }
                    }
                    else if(chessboard[x-1][y+1]==chessman){
                        if(chessboard[x-2][y+2]==0 && chessboard[x-3][y+3]==chessman && chessboard[x-4][y+4]==0){
                            score+=score3;
                            i+=4;
                        }//(5)
                        else if(chessboard[x-2][y+2]==chessman){
                            if(chessboard[x-3][y+3]==0 && chessboard[x-4][y+4]==0){
                                score+=score3;
                                i+=4;
                            }//(3)
                            else if(chessboard[x-3][y+3]==chessman){
                                if(chessboard[x-4][y+4]!=chessman && chessboard[x-4][y+4]!=0){
                                    score+=score3;
                                    i+=4;
                                }
                                else if(chessboard[x-4][y+4]==0){
                                    score+=score2;
                                    i+=4;
                                }
                            }
                        }
                    }
                }
                else if(i>1 && chessboard[x+1][y-1]==0 && chessboard[x+2][y-2]==0 && i+3<board_height && chessboard[x-3][y+3]==0){
                    //左侧有两个空格
                    if(chessboard[x-1][y+1]==0){
                        if(chessboard[x-2][y+2]==0){
                            score+=score5;
                            i+=3;
                        }//(16)
                        else if(chessboard[x-2][y+2]==chessman){
                            score+=score4;
                            i+=3;
                        }//(13)
                    }
                    else if(chessboard[x-1][y+1]==chessman){
                        if(chessboard[x-2][y+2]==chessman){
                            score+=score3;
                            i+=3;
                        }//(4)
                        else if(chessboard[x-2][y+2]==0){
                            score+=score4;
                            i+=3;
                        }//(12)
                    }
                }
            }
            //下一个点
            x--;
            y++;
        }
        return score;
    }
    /**
     * created by stone3 on 2018-7-5
     * 左斜评分 具体评分思路参照安排好的评分表,附录文件中
     * @param x 行坐标
     * @param y 列坐标
     * @param chessman 棋子类型
     * */
    public static int lMark(int x,int y,int chessman){
        while(x!=0 && y!=0){
            x--;
            y--;
        }//回到起始点,下一点x+1,y+1上一点:x-1,y-1;
        int score=0;
        int board_height=0;//记录遍历长度，这里变量名和高度一样，省的改后面代码变量
        if(x==0){
            board_height=board_width-y;
        }
        else{
            board_height=board_width-x;
        }
        //print("L:x,y,length="+x+","+y+","+board_height);//T
        for(int i=0;i<board_height;i++){
            //只对有棋子进行评分相比之前效率高,if else 比全部是if效率高
            if(chessboard[x][y]==chessman){
                //print("L:找到有落子的点");
                //print("i="+i+x+y);
                if((i==0 || (x-1>=0 && y-1>=0 && chessboard[x-1][y-1]>0)) && i+4<board_height){
                    //左侧堵住,参照评分表(1)(7)(9)(10)(11)
                    if(chessboard[x+1][y+1]==chessman && chessboard[x+2][y+2]==chessman && chessboard[x+3][y+3]==chessman){
                        if(chessboard[x+4][y+4]==chessman) {
                            score+=score1;
                            i+=4;//加速扫描
                            continue;
                        }
                        else if(chessboard[x+4][y+4]==0){
                            score+=score3;
                            i+=4;//加速扫描
                            continue;
                        }
                    }//(1)和(7)
                    if(chessboard[x+4][y+4]==chessman && ((chessboard[x+1][y+1]==0 && chessboard[x+2][y+2]==chessman && chessboard[x+3][y+3]==chessman) || (chessboard[x+1][y+1]==chessman && chessboard[x+2][y+2]==0 && chessboard[x+3][y+3]==chessman) || (chessboard[x+1][y+1]==chessman && chessboard[x+2][y+2]==chessman && chessboard[x+3][y+3]==0))){
                        score+=score3;
                        i+=4;
                    }//(9)(10)(11)
                }
                else if(i>0 && x-1>=0 && y-1>=0 && chessboard[x-1][y-1]==0 && i+4<board_height && (i==1 || chessboard[x-2][y-2]>0)){
                    //左侧有一个空格 (2),(3),(5),(6),(8),(14)
                    //print("L:一个空格考虑");
                    if(chessboard[x+1][y+1]==0 && chessboard[x+2][y+2]==chessman && chessboard[x+4][y+4]==0){
                        if(chessboard[x+3][y+3]==0){
                            score+=score4;//(14)
                            i+=4;
                        }
                        else if(chessboard[x+3][y+3]==chessman){
                            score+=score3;//(6)
                            i+=4;
                        }
                    }
                    else if(chessboard[x+1][y+1]==chessman){
                        if(chessboard[x+2][y+2]==0 && chessboard[x+3][y+3]==chessman && chessboard[x+4][y+4]==0){
                            score+=score3;
                            i+=4;
                        }//(5)
                        else if(chessboard[x+2][y+2]==chessman){
                            if(chessboard[x+3][y+3]==0 && chessboard[x+4][y+4]==0){
                                score+=score3;
                                i+=4;
                            }//(3)
                            else if(chessboard[x+3][y+3]==chessman){
                                if(chessboard[x+4][y+4]!=chessman && chessboard[x+4][y+4]!=0){
                                    score+=score3;
                                    i+=4;
                                }
                                else if(chessboard[x+4][y+4]==0){
                                    score+=score2;
                                    i+=4;
                                }
                            }
                        }
                    }
                }
                else if(i>1 && x-2>=0 && y-2>=0 && chessboard[x-1][y-1]==0 && chessboard[x-2][y-2]==0 && i+3<board_height && chessboard[x+3][y+3]==0){
                    //左侧有两个空格
                    //print("L:两个空格考虑");
                    if(chessboard[x+1][y+1]==0){
                        if(chessboard[x+2][y+2]==0){
                            score+=score5;
                            i+=3;
                        }//(16)
                        else if(chessboard[x+2][y+2]==chessman){
                            score+=score4;
                            i+=3;
                        }//(13)
                    }
                    else if(chessboard[x+1][y+1]==chessman){
                        if(chessboard[x+2][y+2]==chessman){
                            score+=score3;
                            i+=3;
                        }//(4)
                        else if(chessboard[x+2][y+2]==0){
                            score+=score4;
                            i+=3;
                        }//(12)
                    }
                }
            }
            //下一个点
            x++;
            y++;
        }
        return score;
    }
    /**
     * 判断是否是第一个子，在这个评分算法中没有使用到
     * @author stone3 未完成
     * */
    public static boolean firstChessman(int x,int y){
        return true;
    }
    /**
     * created by stone3 on 2018-7-5
     * 棋局判断，只对第一个子评分如"+0000+"只对第一个0评分：基于行列评分方法,总是从左向右扫描获上下的有序计算，所以第一个字必定优先得到。
     * @author stone3 未完成
     * */
    public static boolean five(int x,int y){
        //"00000"
        int chessman=chessboard[x][y];
        if(chessman==0){
            return false;
        }
        return true;
    }
}
