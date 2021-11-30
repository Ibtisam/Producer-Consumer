package com.example.producerconsumerexample;

import java.util.Random;

public class Producer implements Runnable {
    private Package aPackage;

    public Producer(Package aPackage) {
        this.aPackage = aPackage;
    }

    public void run() {
        String importantInfo[] = {
                "Mares eat oats",
                "Does eat oats",
                "Little lambs eat ivy",
                "A kid will eat ivy too"
        };
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            aPackage.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(2500));
            } catch (InterruptedException e) {
            }
        }
        aPackage.put("DONE");
    }
}
