package core;

import utils.StaticVariable;

import static core.Assist.*;
import static utils.StaticVariable.*;

/**
 * 存放算法函数
 */
public class Chess {
    //区域坐标
    private int Lx = 0;
    private int Ly = 0;
    private int Rx = 0;
    private int Ry = 0;
    //记录落子位置
    private int x = 0;
    private int y = 0;
    //递归次数，遍历深度
    private int deep = 0;//3:机器, 2:人,  1:机器
    private boolean max=true;//是否是max层

    /**
     * created by stone3 on 2018-7-5
     * 机器落子算法 博弈树 剪枝 评分系统
     */
    public void robotChess(int chessman) {
        scaleMap(chessboard);
//        Lx=1;
//        Ly=1;
//        Rx=4;
//        Ry=4;
        print("区域位置:"+Lx+","+Ly+" "+Rx+","+Ry);
        //搜索到最佳位置
        DFS(chessman,-1000,1000,0,0);
        //落子
        if (!inputChess(x, y, chessman)) {
            System.out.println("系统异常");
        }
    }
    //两种棋子的评分
//    private int black_score=0;
//    private int white_score=0;

    /**
     * created by stone3 on 2018-7-5
     * 博弈树 递归
     *
     * @return 评分
     */
    private int DFS(int turn,int alpha,int beta,int x,int y) {
        //print("DFS");
        //退出递归
        //print("x,y="+x+","+y);
        deep++;
        if(deep==max_deep){
            //print("maxDeep");
            return mark(x,y)-StaticVariable.score[x][y];
        }
        if(turn==white_chessman){
            //print("white");
            for(int i=Lx;i<=Rx;i++)
                for(int j=Lx;j<=Rx;j++){
                //遍历每一种走法
                    if(chessboard[i][j]==0){
                        print("W(i,j)"+i+","+j);
                        chessboard[i][j]=white_chessman;//落子
                        //更新评分数组
                        int mark=score[i][j];
                        score[i][j]=mark(i,j);
                        int max_min=DFS(black_chessman,alpha,beta,i,j);//传递下去
                        //还原
                        deep--;
                        chessboard[i][j]=0;
                        score[i][j]=mark;
                        if(max_min>alpha){
                            //找最大值
                            alpha=max_min;
                            //记录位置
                            //print("x="+x+",y="+y);
                            if(deep==1){
                                this.x=i;
                                this.y=j;
                            }
                        }
                        if(alpha>beta){
                            //剪枝
                            print("-");
                            return alpha;
                        }
                    }
                }
                //print("deep="+deep);
                return alpha;
        }
        else{
            //print("black");
            for(int i=Lx;i<=Rx;i++)
                for(int j=Lx;j<=Rx;j++){
                    //遍历每一种走法
                    if(chessboard[i][j]==0){
                        print("B(i,j)"+i+","+j);
                        chessboard[i][j]=black_chessman;//落子
                        //更新评分数组
                        int mark=score[i][j];
                        score[i][j]=mark(i,j);
                        int max_min=DFS(white_chessman,alpha,beta,i,j);//传递下去
                        deep--;
                        //还原
                        chessboard[i][j]=0;
                        score[i][j]=mark;
                        if(max_min<beta){
                            //找最大值
                            beta=max_min;
                        }
                        if(alpha>beta){
                            //剪枝
                            print("-");
                            return beta;
                        }
                    }
                }
               //print("deep="+deep);
                return beta;
        }
}

    /**
     * AI算法下棋
     */
    public void AIChess(int chessman) {
        //临时棋盘
        int[][] chess_board = new int[board_width][board_height];
        arrayCopy(chessboard, chess_board);
        //最佳落子判断
        int maxScore = -10000;
        //记录位置
        int x = 0;
        int y = 0;
        //System.out.println("AI算法开始");
        //划分算法区域
        scaleMap(chess_board);
        //System.out.println("区域划分完成");
        //System.out.println(Lx+","+Ly+" "+Rx+","+Ry);
        for (int i = Lx; i <= Rx; i++)
            for (int j = Ly; j <= Ry; j++) {
                if (chess_board[i][j] == 0) {
                    chess_board[i][j] = chessman;//第一次落子
                    for (int i2 = Lx; i2 < Rx; i2++)
                        for (int j2 = Ly; j2 < Ry; j2++) {
                            if (chess_board[i2][j2] == 0) {
                                if (chessman == black_chessman) {
                                    chess_board[i2][j2] = white_chessman;//第二次落子
                                } else {
                                    chess_board[i2][j2] = black_chessman;
                                }
                                for (int i3 = Lx; i3 < Rx; i3++)
                                    for (int j3 = Ly; j3 < Ry; j3++) {
                                        if (chess_board[i3][j3] == 0) {
                                            chess_board[i3][j3] = chessman;//第三次落子
                                            int score1 = getScore(chessman, chess_board);
                                            int score2;
                                            if (chessman == black_chessman) {
                                                score2 = getScore(white_chessman, chess_board);
//                                                if (win(chess_board, white_chessman)) {
//                                                    //优先产生5子，进攻转为防御
//                                                    //System.out.println("防守!");
//                                                    score2 += score_max;
//                                                }
                                            } else {
                                                score2 = getScore(black_chessman, chess_board);
                                            }
                                            if (score1 - score2 > maxScore) {
                                                maxScore = score1 - score2;
                                                x = i;
                                                y = j;
                                            }
//                                            int score=mark(i3,j3);
//                                            if(score>maxScore){
//                                                maxScore=score;
//                                                x=i;
//                                                y=j;
//                                            }
                                            //还原棋盘
                                            //System.out.println("还原棋盘");
                                            arrayCopy(chessboard, chess_board);
                                        }
                                    }
                            }
                        }
                }
            }
        //落子
        System.out.println("x:" + x + "y:" + y + "chessman:" + chessman);
        if (!inputChess(x, y, chessman)) {
            System.out.println("系统异常");
        }
    }

    /**
     * 游戏结束判断0:未结束,1:黑棋赢,2:白棋赢,3:平局
     */
    public int gameOver() {
        //白棋赢,黑棋赢,平局
        if (win(chessboard, black_chessman)) {
            return black_win;
        } else if (win(chessboard, white_chessman)) {
            return white_win;
        } else if (all_chess(chessboard)) {
            return all_win;
        }
        return none_win;
    }

    /**
     * 判断棋子是否胜(gameOver调用)chessman:棋子类型
     **/
    private boolean win(int[][] chessboard, int chessman) {
        //行遍历
        for (int i = 0; i < board_height; i++) {
            for (int j = 0; j < board_width - 4; j++) {
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && chessboard[i][j + 3] == chessman && chessboard[i][j + 4] == chessman) {
                    return true;
                }
            }
        }
        //列遍历
        for (int i = 0; i < board_width; i++) {
            for (int j = 0; j < board_height - 4; j++) {
                if (chessboard[j][i] == chessman && chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == chessman && chessboard[j + 3][i] == chessman && chessboard[j + 4][i] == chessman) {
                    return true;
                }
            }
        }
        //右斜遍历
        for (int i = 4; i < board_width; i++) {
            for (int j = 0; j < board_height - 4; j++) {
                if (chessboard[j][i] == chessman && chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == chessman && chessboard[j + 3][i - 3] == chessman && chessboard[j + 4][i - 4] == chessman) {
                    return true;
                }
            }
        }
        //左斜遍历
        for (int i = board_width - 5; i >= 0; i--) {
            for (int j = 0; j < board_height - 4; j++) {
                if (chessboard[j][i] == chessman && chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == chessman && chessboard[j + 3][i + 3] == chessman && chessboard[j + 4][i + 4] == chessman) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否下满
     */
    private boolean all_chess(int[][] chessboard) {
        for (int i = 0; i < board_width; i++)
            for (int j = 0; j < board_height; j++) {
                if (chessboard[i][j] == 0) {
                    //有空位置，没下满
                    return false;
                }
            }
        return true;
    }

    /**
     * 评分系统
     *
     * @param chessman   棋子类型
     * @param chessboard 推算的棋盘(对这个棋盘进行打分)
     */
    public int getScore(int chessman, int[][] chessboard) {
        int sum = 0;//总分
        //行遍历
        for (int i = 0; i < board_height; i++) {
            for (int j = 0; j < board_width - 4; j++) {
                //5子(最佳落子)
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && chessboard[i][j + 3] == chessman && chessboard[i][j + 4] == chessman) {
                    sum += 2000;
                }
                //死子1
                if (chessboard[i][j] == chessman && (j == 0 || (chessboard[i][j - 1] > 0 && chessboard[i][j - 1] != chessman)) && (chessboard[i][j + 1] > 0 && chessboard[i][j + 1] != chessman)) {
                    sum += 40;
                }
                //死子2
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && (j == 0 || (chessboard[i][j - 1] > 0 && chessboard[i][j - 1] != chessman)) && (chessboard[i][j + 2] > 0 && chessboard[i][j + 2] != chessman)) {
                    sum += 30;
                }
                //死子3
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && (j == 0 || (chessboard[i][j - 1]) > 0 && chessboard[i][j - 1] != chessman) && (chessboard[i][j + 3] > 0 && chessboard[i][j + 3] != chessman)) {
                    sum += 20;
                }
                //死子4
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && chessboard[i][j + 3] == chessman && (j == 0 || (chessboard[i][j - 1]) > 0 && chessboard[i][j - 1] != chessman) && chessboard[i][j + 4] != chessman) {
                    sum += 10;
                }
                //活子1
                if (chessboard[i][j] == chessman && j > 0 && chessboard[i][j - 1] == 0 && chessboard[i][j + 1] == 0) {
                    sum += 50;
                }
                //活子2
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && j > 0 && chessboard[i][j - 1] == 0 && chessboard[i][j + 2] == 0) {
                    sum += 100;
                }
                //活子3
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && j > 0 && chessboard[i][j - 1] == 0 && chessboard[i][j + 3] == 0) {
                    sum += 600;
                }
                //活子4
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && chessboard[i][j + 3] == chessman && j > 0 && chessboard[i][j - 1] == 0 && chessboard[i][j + 4] == 0) {
                    sum += 1000;
                }
                //半死1
                if (chessboard[i][j] == chessman && (j == 0 || (chessboard[i][j - 1] > 0 && chessboard[i][j - 1] != chessman)) && chessboard[i][j + 1] == 0) {
                    sum += 50;
                }
                //半死2
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && (j == 0 || (chessboard[i][j - 1] > 0 && chessboard[i][j - 1] != chessman)) && chessboard[i][j + 2] == 0) {
                    sum += 60;
                }
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && (j == 0 || (chessboard[i][j - 1] > 0 && chessboard[i][j - 1] != chessman)) && chessboard[i][j + 3] == 0) {
                    sum += 100;
                }
                if (chessboard[i][j] == chessman && chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == chessman && chessboard[i][j + 3] == chessman && (j == 0 || (chessboard[i][j - 1] > 0 && chessboard[i][j - 1] != chessman)) && chessboard[i][j + 4] == 0) {
                    sum += 800;
                }
                //半活2
                if (chessboard[i][j] == chessman && chessboard[i][j + 2] == chessman && chessboard[i][j + 1] == 0 && j > 0 && chessboard[i][j + 3] == 0) {
                    sum += 200;
                }
                //半活3
                if (chessboard[i][j] == chessman && j > 0 && chessboard[i][j - 1] == 0 && chessboard[i][j + 3] == 0 && ((chessboard[i][j + 1] == chessman && chessboard[i][j + 2] == 0 && chessboard[i][j + 3] == chessman) || (chessboard[i][j + 1] == 0 && chessboard[i][j + 2] == chessman && chessboard[i][j + 3] == chessman))) {
                    sum += 500;
                }
            }
        }
        //列遍历
        for (int i = 0; i < board_width; i++) {
            for (int j = 0; j < board_height - 4; j++) {
                if (chessboard[j][i] == chessman && chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == chessman && chessboard[j + 3][i] == chessman && chessboard[j + 4][i] == chessman) {
                    sum += 2000;
                }
                //活
                if (chessboard[j][i] == chessman && j > 0 && chessboard[j - 1][i] == 0) {
                    if (chessboard[j + 1][i] == 0) {
                        sum += 50;
                    }
                    if (chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == 0) {
                        sum += 100;
                    }
                    if (chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == chessman && chessboard[j + 3][i] == 0) {
                        sum += 600;
                    }
                    if (chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == chessman && chessboard[j + 3][i] == chessman && chessboard[j + 4][i] == 0) {
                        sum += 1000;
                    }
                }
                //半活
                if (chessboard[j][i] == chessman && (j == 0 || chessboard[j - 1][i] != 0)) {
                    if (chessboard[j + 1][i] == 0) {
                        sum += 50;
                    }
                    if (chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == 0) {
                        sum += 60;
                    }
                    if (chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == chessman && chessboard[j + 3][i] == 0) {
                        sum += 100;
                    }
                    if (chessboard[j + 1][i] == chessman && chessboard[j + 2][i] == chessman && chessboard[j + 3][i] == chessman && chessboard[j + 4][i] == 0) {
                        sum += 800;
                    }
                }
            }
        }
        //右斜遍历
        for (int i = 4; i < board_width; i++) {
            for (int j = 0; j < board_height - 4; j++) {
                if (chessboard[j][i] == chessman && chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == chessman && chessboard[j + 3][i - 3] == chessman && chessboard[j + 4][i - 4] == chessman) {
                    sum += 2000;
                }
                //活
                if (chessboard[j][i] == chessman && j > 0 && i < board_height - 1 && chessboard[j - 1][i + 1] == 0) {
                    if (chessboard[j + 1][i - 1] == 0) {
                        sum += 50;
                    }
                    if (chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == 0) {
                        sum += 100;
                    }
                    if (chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == chessman && chessboard[j + 3][i - 3] == 0) {
                        sum += 300;
                    }
                    if (chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == chessman && chessboard[j + 3][i - 3] == chessman && chessboard[j + 4][i - 4] == 0) {
                        sum += 600;
                    }
                }
                //半活
                if (chessboard[j][i] == chessman && ((j == 0 || i == 0) || (i > 0 && j > 0 && chessboard[j - 1][i + 1] > 0))) {
                    if (chessboard[j + 1][i - 1] == 0) {
                        sum += 50;
                    }
                    if (chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 1] == 0) {
                        sum += 60;
                    }
                    if (chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == chessman && chessboard[j + 3][i - 3] == 0) {
                        sum += 70;
                    }
                    if (chessboard[j + 1][i - 1] == chessman && chessboard[j + 2][i - 2] == chessman && chessboard[j + 3][i - 3] == chessman && chessboard[j + 4][i - 4] == 0) {
                        sum += 80;
                    }
                }
            }
        }
        //左斜遍历
        for (int i = board_width - 5; i >= 0; i--) {
            for (int j = 0; j < board_height - 4; j++) {
                if (chessboard[j][i] == chessman && chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == chessman && chessboard[j + 3][i + 3] == chessman && chessboard[j + 4][i + 4] == chessman) {
                    sum += 2000;
                }
                //System.out.println("j="+j+",i="+i);
                if ((chessboard[j][i] == chessman && j > 0 && i > 0) && chessboard[j - 1][i - 1] == 0) {
                    // System.out.println(i+","+j);
                    if (chessboard[j + 1][i + 1] == 0) {
                        sum += 50;
                    }
                    if (chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == 0) {
                        sum += 100;
                    }
                    if (chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == chessman && chessboard[j + 3][i + 3] == 0) {
                        sum += 300;
                    }
                    if (chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == chessman && chessboard[j + 3][i + 3] == chessman && chessboard[j + 4][i + 4] == 0) {
                        sum += 600;
                    }
                }
                if (chessboard[j][i] == chessman && ((j == 0 || i == 0) || (i > 0 && j > 0 && chessboard[j - 1][i - 1] > 0))) {
                    if (chessboard[j + 1][i + 1] == 0) {
                        sum += 50;
                    }
                    if (chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == 0) {
                        sum += 60;
                    }
                    if (chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == chessman && chessboard[j + 3][i + 3] == 0) {
                        sum += 70;
                    }
                    if (chessboard[j + 1][i + 1] == chessman && chessboard[j + 2][i + 2] == chessman && chessboard[j + 3][i + 3] == chessman && chessboard[j + 4][i + 4] == 0) {
                        sum += 80;
                    }
                }
            }
        }
        return sum;
    }

    /**
     * 动态评分算法
     *
     * @param i          横坐标
     * @param j          纵坐标
     * @param chessboard 棋盘
     * @return 此处棋子的得分
     */
    private int calScore(int[][] chessboard, int i, int j) {
        int score = 0;
        int chessman = chessboard[i][j];
        //两大类,活,半活
        if (chessman == 0) {
            return score;
        }
        //行
        if (j > 0) {
            if (chessboard[i][j + 1] == 0) {
                score += 50;
            }
        }
        return score;
    }

    /**
     * 判断一列为空
     *
     * @param x 横坐标
     */
    private boolean none_y(int x, int[][] chessborad) {
        for (int y = 0; y < board_height; y++) {
            if (chessborad[x][y] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 区域化算法->优化遍历
     */
    private void scaleMap(int[][] chessboard2) {
        int[][] chessboard = new int[board_width][board_height];
        arrayCopy(chessboard2, chessboard);
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0, x4 = 0, y4 = 0;//四点坐标,四个维度扫描找到最小区域
        //结束扫描标志
        boolean over1 = false;
        boolean over2 = false;
        boolean over3 = false;
        boolean over4 = false;
        for (int i = 0; i < board_width; i++) {
            for (int j = 0; j < board_height; j++) {
                //上->下扫描
                //System.out.println("i="+i+" j="+j);
                if (chessboard[j][i] != 0 && !over1) {
                    x1 = j;
                    y1 = i;
                    over1 = true;
                }
                //左->右扫描
                if (chessboard[i][j] != 0 && !over2) {
                    x2 = i;
                    y2 = j;
                    over2 = true;
                }
                if (over1 && over2) {
                    break;
                }
            }
            if (over1 && over2) {
                break;
            }
        }
        for (int i = board_width - 1; i >= 0; i--) {
            for (int j = board_height - 1; j >= 0; j--) {
                //右->左
                if (chessboard[i][j] != 0 && !over3) {
                    x3 = i;
                    y3 = j;
                    over3 = true;
                }
                //下->上
                if (chessboard[j][i] != 0 && !over4) {
                    x4 = j;
                    y4 = i;
                    over4 = true;
                }
                if (over3 && over4) {
                    break;
                }
            }
            if (over3 && over4) {
                break;
            }
        }
        //System.out.println(x1+","+y1+" "+x2+","+y2+" "+x3+","+y3+" "+x4+","+y4+" ");
        //选出两个标记点
        Lx = (x2 < x1) ? x2 : x1;
        Ly = (y2 < y1) ? y2 : y1;
        Rx = (x3 > x4) ? x3 : x4;
        Ry = (y3 > y4) ? y3 : y4;
        //满棋子,一定可以扩大(基于game over判断,一定有空位),扩大,保证区域内可以落子
        int sum = noneMap(chessboard);//数字大小反应的程序效率；进行控制
        if(sum<5){
            if (Lx > 0) {
                Lx--;
            }
            if (Ly > 0) {
                Ly--;
            }
            if (Rx < board_width) {
                Rx++;
            }
            if (Ry < board_height) {
                Ry++;
            }
        }
        if (sum > 17 * 5) {
            //新算法来划分区域
            //changeMap(chessboard);
            int min = minArea(chessboard);
            System.out.print("算出最小区域:" + min);
            if (min == 1 || min == 2) {
                chessboard[Rx][Ry] = 0;
            } else {
                chessboard[Lx][Ly] = 0;
            }
            scaleMap(chessboard);
        }
        //System.out.println(Lx+","+Ly+" "+Rx+","+Ry);
    }

    private int noneMap(int[][] chessboard) {
        int num = 0;
        for (int i = Lx; i <= Rx; i++)
            for (int j = Ly; j <= Ry; j++) {
                if (chessboard[i][j] == 0) {
                    num++;//空位计数
                }
            }
        return num;
    }

    /**
     * 计算局部区域空格数
     */
    private int noneMap(int[][] chessboard, int x1, int y1, int x2, int y2) {
        int num = 0;
        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++) {
                if (chessboard[i][j] == 0) {
                    num++;//空位计数
                }
            }
        return num;
    }

    /**
     * 遇到大量空白落子区时的新划分算法
     */
    private void changeMap(int[][] chessboard) {
        int aera1 = noneMap(chessboard, 0, 0, 8, 8);//左上角
        int aera2 = noneMap(chessboard, 8, 0, 16, 8);//右上角
        int aera3 = noneMap(chessboard, 0, 8, 8, 16);//左下角
        int aera4 = noneMap(chessboard, 8, 8, 16, 16);//右下角
        int min = min(aera1, aera2, aera3, aera4);
        if (min == 1) {
            Lx = 0;
            Ly = 0;
            Rx = 8;
            Ry = 8;
        }
        if (min == 2) {
            Lx = 8;
            Ly = 0;
            Rx = 16;
            Ry = 8;
        }
        if (min == 3) {
            Lx = 0;
            Ly = 8;
            Rx = 8;
            Ry = 16;
        }
        if (min == 4) {
            Lx = 8;
            Ly = 8;
            Rx = 16;
            Ry = 16;
        }
    }

    /**
     * 返回最小区域
     */
    private int minArea(int[][] chessboard) {
        int aera1 = noneMap(chessboard, 0, 0, 8, 8);//左上角
        int aera2 = noneMap(chessboard, 8, 0, 16, 8);//右上角
        int aera3 = noneMap(chessboard, 0, 8, 8, 16);//左下角
        int aera4 = noneMap(chessboard, 8, 8, 16, 16);//右下角
        return min(aera1, aera2, aera3, aera4);
    }
    /**
     * 判断某一棋子是否达到目的
     * */
}
