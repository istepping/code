package utils;
/**
 * 存放静态变量
 * */
public class StaticVariable {
    public static final int board_height=17;//棋盘宽度
    public static final int board_width=17;//棋盘高度
    public static int[][] chessboard=new int[board_width][board_height];//棋盘
    public static int black_chessman=1;//黑棋子,(0表示没有棋子)
    public static int white_chessman=2;//白棋子
    public static final int none_win=0;//没有人赢
    public static final int black_win=1;//黑棋赢
    public static final int white_win=2;//白棋赢
    public static final int all_win=3;//平局
    public static final int score_max=10000;//最大给分
    public static int[][] score=new int[board_width][board_height];//整个棋盘中的每个点的分值(这一点所在的行分值+列分值+右斜+左斜):白子分值与黑子分值差
    //分值规定
    public static final int score1=50000;
    public static final int score2=4320;
    public static final int score3=720;
    public static final int score4=120;
    public static final int score5=20;
    //递归
    public static final int max_deep=3;
}
