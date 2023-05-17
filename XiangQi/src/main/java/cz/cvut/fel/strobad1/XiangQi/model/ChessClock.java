package cz.cvut.fel.strobad1.XiangQi.model;

import java.util.logging.Logger;

public class ChessClock extends Thread {


    private Match match;


    private long redStartTime;
    private long blackStartTime;
    private long redElapsedTime;
    private long blackElapsedTime;


    private long maximumTimeForBoth;
    private long maximumRedTime;
    private long maximumBlackTime;


    private long matchStartTime;


    private long loadedRedTime;
    private long loadedBlackTime;

    private long totalPauseTime = 0;
    private boolean isPaused = false;
    private boolean isRedTurn = false;
    private long pauseStartTime;


    Logger logger = Logger.getLogger(ChessClock.class.getName());


    public ChessClock(long maximumTimeForBoth, Match match) {
        this.maximumTimeForBoth = maximumTimeForBoth;
        matchStartTime = System.currentTimeMillis();
        redStartTime = matchStartTime;
        blackStartTime = matchStartTime;
        start();
    }

    //Constructor for loading from a save.
    public ChessClock(long remainingRedTime,long remainingBlackTime, Match match) {
        this.maximumRedTime = maximumRedTime;
        matchStartTime = System.currentTimeMillis();
        redStartTime = matchStartTime;
        blackStartTime = matchStartTime;

        match.getGameBoard().isRedTurn();

        loadedRedTime = remainingRedTime;
        loadedBlackTime = remainingBlackTime;
        start();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public long getRedRemainingTime() {
        return maximumRedTime - redElapsedTime;
    }

    public long getBlackRemainingTime() {
        return maximumRedTime - blackElapsedTime;
    }

    @Override
    public void run() {

        while (redElapsedTime < maximumRedTime && blackElapsedTime < maximumRedTime) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isPaused) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                    logger.severe("Thread Interrupted error!");
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
            logger.info("Countdown paused.");
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

            logger.info("Countdown resumed.");
        }
    }

    public long getMatchTime() {
        return System.currentTimeMillis() - matchStartTime;
    }

    public long getTotalPauseTime() {
        return totalPauseTime;
    }

    public long getMaxPlayerTime() {
        return maximumRedTime;
    }

    public void setMaxPlayerTime(long maxPlayerTime) {
        this.maximumRedTime = maxPlayerTime;
    }




}
