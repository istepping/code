package com.example.dell.mygame10;
import android.graphics.Bitmap;
/**
 * Created by dell on 2017/8/9.
 */
public class Bullet {
    public static final int BULLET_TYPE_1=1;
    public static final int BULLET_TYPE_2=2;
    public static final int BULLET_TYPE_3=3;
    public static final int BULLET_TYPE_4=4;
    private int type;
    private int x;
    private int y;
    private int dir;
    private int yAccelerate =0;
    private boolean isEffect=true;
    public Bullet(int bulletType,int drawX,int drawY,int d) {
        type=bulletType;
        x=drawX;
        y=drawY;
        dir=d;
    }
    public void move() {
        if(type==BULLET_TYPE_1)
        {
            x+=4*getSpeedX();
            y+=getSpeedY();
        }
        else
        {
            x+=getSpeedX();
            y+=getSpeedY();
        }
    }
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return y;
    }
    public int getDir()
    {
        return dir;
    }
    public boolean isEffect()
    {
        return isEffect;
    }
    public void setEffect(boolean isEffect)
    {
        this.isEffect = isEffect;
    }
    public int getyAccelerate()
    {
        return yAccelerate;
    }
    public void setyAccelerate(int yAccelerate)
    {
        this.yAccelerate = yAccelerate;
    }
    public Bitmap getBitmap() {
        switch (type)
        {
            case BULLET_TYPE_1:
                return ViewManager.bulletImage[0];
            case BULLET_TYPE_2:
                return ViewManager.bulletImage[1];
            case BULLET_TYPE_3:
                return ViewManager.bulletImage[2];
            case BULLET_TYPE_4:
                return ViewManager.bulletImage[3];
            default:
                return null;
        }
    }
    public int getSpeedX() {
        int sign=dir==Player.DIR_RIGHT? 1:-1;
        switch (type)
        {
            case BULLET_TYPE_1:
                return (int)(ViewManager.scale*12)*sign;
            case BULLET_TYPE_2:
                return (int)(ViewManager.scale*8)*sign;
            case BULLET_TYPE_3:
                return (int)(ViewManager.scale*8)*sign;
            case BULLET_TYPE_4:
                return (int)(ViewManager.scale*8)*sign;
            default:
                return (int)(ViewManager.scale*8)*sign;
        }
    }
    public int getSpeedY() {
        if(yAccelerate !=0)
        {
            return yAccelerate;
        }
        switch (type)
        {
            case BULLET_TYPE_1:
                return 0;
            case BULLET_TYPE_2:
                return 0;
            case BULLET_TYPE_3:
                return (int) (com.example.dell.mygame10.ViewManager.scale * 6);
            case BULLET_TYPE_4:
                return 0;
            default:
                return 0;
        }
    }
}
