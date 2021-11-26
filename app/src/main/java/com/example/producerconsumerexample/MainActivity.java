package com.example.producerconsumerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void simpleThBClick(View v){
        editText.setText("");
        BasicRunnable r1 = new BasicRunnable("Thread 1", 2000);
        BasicRunnable r2 = new BasicRunnable("Thread 2", 1500);
        BasicRunnable r3 = new BasicRunnable("Thread 3", 1000);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //these thread will wait for t1 to complete
        t2.start();
        t3.start();
    }

    public void prodecerConsumerThBClick(View v){
        editText.setText("");
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop, this))).start();

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
