package cz.cvut.fel.strobad1.XiangQi.Model;


public class Clock extends Thread {


    Thread redTimerThread;
    Thread blackTimerThread;
    private boolean isRedTurn;
    private long redStartTime;
    private long blackStartTime;
    private long redCurrentTime;
    private long blackCurrentTime;
    private long maximumPlayerTime = 600000;

    private long matchStartTime;
    private long matchEndTime;


    private boolean isPaused = false;

    private long pauseTime;
    private long totalPauseTime;




    public Clock() {
        matchStartTime = System.currentTimeMillis();

        redStartTime = System.currentTimeMillis();
        blackStartTime = System.currentTimeMillis();

        isRedTurn = true;
        isPaused = false;
    }

    /**
     * Starts or resumes the countdown of the timer
     */
    public void startCountdown() {

        if (isRedTurn) {
            redStartTime = System.currentTimeMillis() - redCurrentTime; // adjust the start time to account for any previously elapsed time
            redTimerThread = new Thread(this);
            redTimerThread.start();
        } else {
            blackStartTime = System.currentTimeMillis() - blackCurrentTime;
            blackTimerThread = new Thread(this);
            blackTimerThread.start();
        }

    }


    /**
     * pauses the countdown of the timer
     */

    public void pauseCountdown() {

        pauseTime = System.currentTimeMillis();
        isPaused = true;

    }

    public void resumeCountdown(){
        isPaused=false;
        blackStartTime += (System.currentTimeMillis() - pauseTime);
        redStartTime += (System.currentTimeMillis() - pauseTime);
        totalPauseTime += pauseTime;
    }

    public void switchTurn() {
        isRedTurn = !isRedTurn;
        if (isRedTurn) {
            redStartTime = System.currentTimeMillis();
        } else {
            blackStartTime = System.currentTimeMillis();
        }
    }


    public long getMatchEndTime() {
        return matchEndTime;
    }

    @Override
    public void run() {
        while (redCurrentTime < maximumPlayerTime && blackCurrentTime < maximumPlayerTime) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isRedTurn) {
                redCurrentTime = System.currentTimeMillis() - redStartTime;
            } else {
                blackCurrentTime = System.currentTimeMillis() - blackStartTime;
            }
        }
        matchEndTime = System.currentTimeMillis() - (matchStartTime + totalPauseTime);
    }



}



