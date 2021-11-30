package com.example.producerconsumerexample;

import android.content.Context;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Consumer implements Runnable {
    private Package aPackage;
    private Context context;
    private String message;

    public Consumer(Package aPackage, Context context) {
        this.aPackage = aPackage;
        this.context = context;
    }

    public void run() {
        Random random = new Random();
        for (message = aPackage.take(); !message.equals("DONE"); message = aPackage.take()) {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    EditText editText = ((AppCompatActivity) context).findViewById(R.id.editText);
                    editText.append("MESSAGE RECEIVED: "+message+"\n");
                }
            });
            try {
                Thread.sleep(random.nextInt(5000));
            } catch (InterruptedException e) {
            }
        }
    }
}
