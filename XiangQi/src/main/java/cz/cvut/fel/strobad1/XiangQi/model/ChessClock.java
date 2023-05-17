package cz.cvut.fel.strobad1.XiangQi.model;

import javafx.beans.InvalidationListener;

import java.time.Clock;

public class ChessClock extends Thread {

    private long redStartTime;
    private long blackStartTime;
    private long redElapsedTime;
    private long blackElapsedTime;
    private long maximumPlayerTime = 6000_000;
    private long matchStartTime;
    private long totalPauseTime = 0;
    private boolean isPaused = false;
    private boolean isRedTurn = false;
    private long pauseStartTime;



    public ChessClock(long maximumPlayerTime) {
        this. maximumPlayerTime = maximumPlayerTime;
        matchStartTime = System.currentTimeMillis();
        redStartTime = matchStartTime;
        blackStartTime = matchStartTime;
        start();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public long getRedRemainingTime() {
        return maximumPlayerTime - redElapsedTime;
    }

    public long getBlackRemainingTime() {
        return maximumPlayerTime - blackElapsedTime;
    }

    @Override
    public void run() {
        while (redElapsedTime < maximumPlayerTime && blackElapsedTime < maximumPlayerTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPaused) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }
            if (isRedTurn) {
                redElapsedTime = System.currentTimeMillis() - redStartTime - totalPauseTime;
            } else {
                blackElapsedTime = System.currentTimeMillis() - blackStartTime - totalPauseTime;
            }
        }
    }

    public void switchTurn() {
        isRedTurn = !isRedTurn;



        if (isRedTurn) {
            redStartTime = System.currentTimeMillis() - redElapsedTime - totalPauseTime;
        } else {
            blackStartTime = System.currentTimeMillis() - blackElapsedTime - totalPauseTime;
        }
    }

    public void startCountdown() {
        if (isRedTurn) {
            redStartTime = System.currentTimeMillis() - redElapsedTime - totalPauseTime;
        } else {
            blackStartTime = System.currentTimeMillis() - blackElapsedTime - totalPauseTime;
        }
    }

    public void pauseCountdown() {
        if (!isPaused) {
            isPaused = true;
            pauseStartTime = System.currentTimeMillis();

        }
    }

    public void resumeCountdown() {
        if (isPaused) {
            isPaused = false;
            totalPauseTime += System.currentTimeMillis() - pauseStartTime;
            if (isRedTurn) {
                redStartTime = System.currentTimeMillis() - redElapsedTime - totalPauseTime;
            } else {
                blackStartTime = System.currentTimeMillis() - blackElapsedTime - totalPauseTime;
            }
        }
    }

    public long getMatchTime() {
        return System.currentTimeMillis() - matchStartTime;
    }

    public long getTotalPauseTime() {
        return totalPauseTime;
    }

    public long getMaxPlayerTime() {
        return maximumPlayerTime;
    }

    public void setMaxPlayerTime(long maxPlayerTime) {
        this.maximumPlayerTime = maxPlayerTime;
    }




}
