package com.example.producerconsumerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void simpleThBClick(View v){
        editText.setText("Simple Thread Execution\n");

        BasicRunnable r1 = new BasicRunnable("Thread 1", 1000);
        BasicRunnable r2 = new BasicRunnable("Thread 2", 1000);
        BasicRunnable r3 = new BasicRunnable("Thread 3", 1000);


        //Manually starting threads
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);

        /*
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        //these thread will wait for t1 to complete
        t1.start();
        t2.start();
        t3.start();
    }

    public void execServBClick(View v){
        editText.setText("Using ExecutorService\n");

        BasicRunnable r1 = new BasicRunnable("Thread 1", 1000);
        BasicRunnable r2 = new BasicRunnable("Thread 2", 1000);
        BasicRunnable r3 = new BasicRunnable("Thread 3", 1000);

        //Using ExecutorService to manage threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(r1);
        executorService.execute(r2);
        executorService.execute(r3);
    }

    public void threadPoolBClick(View v){
        editText.setText("Using ThreadPoolExecutor\n");

        BasicRunnable r1 = new BasicRunnable("Thread 1", 1000);
        BasicRunnable r2 = new BasicRunnable("Thread 2", 1000);
        BasicRunnable r3 = new BasicRunnable("Thread 3", 1000);

        /*
         * Gets the number of available cores
         * (not always the same as the maximum number of cores)
         */
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

        editText.append("Number of Cores: " + NUMBER_OF_CORES+"\n");

        // Instantiates the queue of Runnables as a LinkedBlockingQueue
        final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
        workQueue.add(r1);
        workQueue.add(r2);
        workQueue.add(r3);
        // Sets the amount of time an idle thread waits before terminating
        final int KEEP_ALIVE_TIME = 1;
        // Sets the Time Unit to seconds
        final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        // Creates a thread pool manager
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue
        );

        threadPoolExecutor.prestartAllCoreThreads();
        //threadPoolExecutor.prestartCoreThread();
    }

    public void prodecerConsumerThBClick(View v){
        editText.setText("Basic Producer Consumer\n");
        Package aPackage = new Package();
        (new Thread(new Producer(aPackage))).start();
        (new Thread(new Consumer(aPackage, this))).start();

    }

    class BasicRunnable implements Runnable{
        private String name;
        private int millis;
        public BasicRunnable(String name, int millis) {
            this.name = name;
            this.millis = millis;
        }

        @Override
        public void run() {
            for(int i=0;i<5;i++){
                final int finalI = i;
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        editText.append(name+" is running "+ finalI+"\n");
                    }
                });
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
