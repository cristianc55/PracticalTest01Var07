package ro.pub.cs.systems.eim.practicaltest01var07;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private final Context context;
    private boolean isRunning = true;

    private final Random random = new Random();

    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int fourthNumber;


    public ProcessingThread(Context context, int firstNumber2, int secondNumber2, int thirdNumber2, int fourthNumber2) {
        this.context = context;

        Log.d("rip", "firstNumber: " + firstNumber +
                " secondNumber: " + secondNumber + " thirdNumber: " + thirdNumber
                + " fourthNumber: " + fourthNumber);

        firstNumber = firstNumber2;
        secondNumber = secondNumber2;
        thirdNumber = thirdNumber2;
        fourthNumber = fourthNumber2;


    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();

        intent.putExtra("message", new String("first number " + firstNumber
                + " second number " + secondNumber + " third number " + thirdNumber + " fourth number " + fourthNumber));
        context.sendBroadcast(intent);
//        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
