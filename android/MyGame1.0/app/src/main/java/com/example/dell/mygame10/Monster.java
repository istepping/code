package com.example.dell.mygame10;
import android.app.Activity;
import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
/**
 * Created by dell on 2017/8/2.
 */
public class Monster {
    public static final int TYPE_BOMB=1;
    public static final int TYPE_FLY=2;
    public static final int TYPE_MAN=3;
    public static final int TYPE_ALL=3;
    public static final int BULLET_COUNT=10;
    public int bulletSpeed=BULLET_COUNT;
    private int type=TYPE_BOMB;
    private int x=0;
    private int y=0;//怪物坐标
    private boolean isDie=false;//是否死亡
    private int startX=0;
    private int startY=0;//绘制怪物左上角坐标
    private int endX=0;
    private int endY=0;//绘制怪物右下角坐标
    int drawCount=0;// 该变量控制用于控制动画刷新的速度
    private int drawIndex=0;
    private int dieMaxDrawCount=Integer.MAX_VALUE;
    private List<Bullet> bulletList=new ArrayList<>();
    public Monster(int type) {
        this.type=type;
        if(type==TYPE_BOMB||type==TYPE_MAN)
        {
            y=ViewManager.SCREEN_HEIGHT * 75 / 100;
        }
        else if (type==TYPE_FLY)
        {
            y= com.example.dell.mygame10.ViewManager.SCREEN_HEIGHT*50/100-Graphics.rand((int)(com.example.dell.mygame10.ViewManager.scale*100));//
        }
        x= com.example.dell.mygame10.ViewManager.SCREEN_WIDTH+Graphics.rand(com.example.dell.mygame10.ViewManager.SCREEN_WIDTH>>1)-(com.example.dell.mygame10.ViewManager.SCREEN_WIDTH>>2);
    }

    public void draw(Canvas canvas) {
      if(canvas==null)
      {
          return;
      }
        switch (type)
        {
            case TYPE_BOMB:
                drawAni(canvas,isDie? com.example.dell.mygame10.ViewManager.bomb2Image: com.example.dell.mygame10.ViewManager.bombImage);
                break;
            case TYPE_FLY:
                drawAni(canvas,isDie?com.example.dell.mygame10.ViewManager.flyDieImage:com.example.dell.mygame10.ViewManager.flyImage);
                break;
            case TYPE_MAN:
                drawAni(canvas,isDie?com.example.dell.mygame10.ViewManager.manDieImage:com.example.dell.mygame10.ViewManager.manImage);
                break;
            default:
                break;
        }
    }//Canvas绘制类
    public void addBullet() {
        int bulletType=getBulletType();
        if(bulletType<=0)
        {
            return;
        }
        int drawX=x;
        int drawY=y-(int)(ViewManager.scale*60);
        if(type==TYPE_FLY)
        {
            drawY=y-(int)(ViewManager.scale*30);
        }
        Bullet bullet=new Bullet(bulletType,drawX,drawY,Player.DIR_LEFT);
        bulletList.add(bullet);
    }
    public void updateShift(int shift) {
        x-=shift;
        for(Bullet bullet:bulletList)
        {
            if(bullet==null)
            {
                continue;
            }
            bullet.setX(bullet.getX()-shift);
        }
    }
    public void drawBullet(Canvas canvas) {
        List<Bullet> deleteList =new ArrayList<>();
        Bullet bullet=null;
        for(int i=0;i<bulletList.size();i++)
        {
            bullet = bulletList.get(i);
            if (bullet == null)
            {
                continue;
            }
            if(bullet.getX()<0||bullet.getX()>ViewManager.SCREEN_WIDTH)
            {
                deleteList.add(bullet);
            }
        }
        bulletList.removeAll(deleteList);
        Bitmap bitmap;
        for(int i=0;i<bulletList.size();i++)
        {
            bullet=bulletList.get(i);
            if(bullet==null)
            {
                continue;
            }
            bitmap=bullet.getBitmap();
            if(bitmap==null)
            {
                continue;
            }
            bullet.move();
            Graphics.drawMatrixImage(canvas,bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),bullet.getDir()==Player.DIR_RIGHT? Graphics.TRANS_MIRROR:Graphics.TRANS_NONE,bullet.getX(),bullet.getY(),0,Graphics.TIMES_SCALE);
        }
    }
    public void checkBullet() {
        List<Bullet> delBulletList=new ArrayList<>();
        for(Bullet bullet:bulletList)
        {
            if(bullet==null||!bullet.isEffect())
            {
                continue;
            }
            if(GameView.player.isHurt(bullet.getX(),bullet.getX(),bullet.getY(),bullet.getY()))
            {
                bullet.setEffect(false);
                delBulletList.add(bullet);
                if(type==TYPE_MAN)
                {
                    GameView.player.setHp(GameView.player.getHp()-1);
                    MonsterManager.KILL_NUMBER=0;
                }
                else
                {
                    GameView.player.setHp(GameView.player.getHp()-5);
                    MonsterManager.KILL_NUMBER=0;
                }
            }
        }
        bulletList.removeAll(delBulletList);
    }

    public void drawAni(Canvas canvas, Bitmap[] bitmapArr) {
        if(canvas==null || bitmapArr==null)
        {
            return;
        }
        if(isDie&&dieMaxDrawCount==Integer.MAX_VALUE)
        {
            dieMaxDrawCount=bitmapArr.length;
        }
        drawIndex=drawIndex%bitmapArr.length;//当前绘制第几帧
        Bitmap bitmap=bitmapArr[drawIndex];
        if(bitmap==null||bitmap.isRecycled())
        {
            return;
        }
        int drawX=x;//怪物坐标
        if(isDie)//死亡进行x坐标调整
        {
            if(type==TYPE_BOMB)
            {
                drawX=x-(int)(ViewManager.scale*50);
            }
            else if(type==TYPE_MAN)
            {
                drawX=x+(int)(ViewManager.scale*50);
            }
        }
        int drawY=y-bitmap.getHeight();
        Graphics.drawMatrixImage(canvas,bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),Graphics.TRANS_NONE,drawX,drawY,0,Graphics.TIMES_SCALE);//0,20
        startX=drawX;
        startY=drawY;
        endX=startX+bitmap.getWidth();
        endY=startY+bitmap.getHeight();
        drawCount++;//动画刷新的速度
        if(drawCount>=(type==TYPE_MAN? 6:4))
        {
            if(bulletSpeed<=0)
            {
                addBullet();
                bulletSpeed=BULLET_COUNT;
            }
            drawIndex++;
            bulletSpeed--;
            drawCount=0;
        }
        if(isDie)
        {
            dieMaxDrawCount--;
        }
        drawBullet(canvas);
    }
    public boolean isHurt(int x,int y)
    {
        return x>=startX && x<=endX && y>=startY && y<=endY;
    }
    public int getType()
    {
        return type;
    }
    public int getBulletType() {
        switch (type)
        {
            case TYPE_BOMB:
                return 0;
            case TYPE_FLY:
                return Bullet.BULLET_TYPE_3;
            case TYPE_MAN:
                return Bullet.BULLET_TYPE_2;
            default:
                return 0;
        }
    }
    public int getX()
    {
        return x;
    }
    public void setDie(boolean die)
    {
        isDie=die;
    }
    public int getStartX()
    {
        return startX;
    }
    public int getStartY()
    {
        return startY;
    }
    public int getEndX()
    {
        return endX;
    }
    public int getEndY()
    {
        return endY;
    }
    public int getDieMaxDrawCount()
    {
        return dieMaxDrawCount;
    }
}












