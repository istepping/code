package com.xmw.qiyun.util.button;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class CountDownTimerUtil extends CountDownTimer {

    private CountDownTimerListener mCountDownTimerListener;

    public CountDownTimerUtil(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mCountDownTimerListener != null) {
            mCountDownTimerListener.startCount(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (mCountDownTimerListener != null) {
            mCountDownTimerListener.finishCount();
        }
    }

    public void setCountDownTimerListener(CountDownTimerListener listener) {
        mCountDownTimerListener = listener;
    }

    public interface CountDownTimerListener {

        void startCount(long millsUtilFinished);

        void finishCount();
    }
}