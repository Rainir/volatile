package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger counterThree = new AtomicInteger(0);
    public static AtomicInteger counterFour = new AtomicInteger(0);
    public static AtomicInteger counterFive = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String value : texts) {
                if (isPalindrome(value) && !isOneLitter(value)) incrementCounter(value.length());
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String value : texts) {
                if (isOneLitter(value)) incrementCounter(value.length());
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String value : texts) {
                if (isAscendingLetter(value) && !isOneLitter(value)) incrementCounter(value.length());
            }
        });

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + counterThree);
        System.out.println("Красивых слов с длиной 4: " + counterFour);
        System.out.println("Красивых слов с длиной 5: " + counterFive);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String value) {
        return value.equals(new StringBuilder(value).reverse().toString());
    }

    public static boolean isOneLitter(String value) {
        String[] text = value.split("");
        for (int i = 1; i < text.length; i++) {
            if(!text[0].equals(text[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingLetter(String value) {
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) < value.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            counterThree.incrementAndGet();
        } else if (textLength == 4) {
            counterFour.incrementAndGet();
        } else if (textLength == 5) {
            counterFive.incrementAndGet();
        }
    }
}