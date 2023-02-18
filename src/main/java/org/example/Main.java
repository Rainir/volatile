package org.example;

import java.util.Arrays;
import java.util.Collections;
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

        Thread thread1 = new Thread(() -> isPalindrome(texts));

        Thread thread2 = new Thread(() -> isOneLitter(texts));

        Thread thread3 = new Thread(() -> isAscendingLetter(texts));

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

    public static void isPalindrome(String[] texts) {
        String[] values = new String[10];
        int counter = 0;
        for (String value : texts) {
            String[] text = value.split("");
            String[] textContrary = text.clone();
            Collections.reverse(Arrays.asList(textContrary));
            if (Arrays.equals(text, textContrary)) {
                if (counter < 10) {
                    values[counter] = value;
                    counter++;
                }
                if (text.length == 3) {
                    counterThree.incrementAndGet();
                } else if (text.length == 4) {
                    counterFour.incrementAndGet();
                } else if (text.length == 5) {
                    counterFive.incrementAndGet();
                }
            }
        }
        System.out.println("Примеры слов палиндромов:");
        System.out.println(Arrays.toString(values) + "\n");
    }

    public static void isOneLitter(String[] texts) {
        String[] values = new String[10];
        int counter = 0;
        for (String value : texts) {
            String[] text = value.split("");
            for (int i = 0; i < text.length; i++) {
                if(text[0].equals(text[i])) {
                    if (i == text.length - 1) {
                        if (counter < 10) {
                            values[counter] = value;
                            counter++;
                        }
                        if (text.length == 3) {
                            counterThree.incrementAndGet();
                        } else if (text.length == 4) {
                            counterFour.incrementAndGet();
                        } else if (text.length == 5) {
                            counterFive.incrementAndGet();
                        }
                    }
                } else {
                    break;
                }
            }
        }
        System.out.println("Примеры слов состоящих из одной буквы:");
        System.out.println(Arrays.toString(values) + "\n");
    }

    public static void isAscendingLetter(String[] texts) {
        String[] values = new String[10];
        int counter = 0;
        for (String value : texts) {
            String[] text = value.split("");
            breakPoint:
            for (int i = 0; i < text.length; i++) {
                if (i < text.length - 1) {
                    switch (text[i]) {
                        case "a" -> {
                            if (!text[i + 1].equals("a")) {
                                continue;
                            } else if (!text[i + 1].equals("b")) {
                                continue;
                            } else if (!text[i + 1].equals("c")) {
                                continue;
                            }
                        }
                        case "b" -> {
                            if (text[i + 1].equals("a")) {
                                break breakPoint;
                            } else if (!text[i + 1].equals("b")) {
                                continue;
                            } else if (!text[i + 1].equals("c")) {
                                continue;
                            }
                        }
                        case "c" -> {
                            if (!text[i + 1].equals("c")) {
                                break breakPoint;
                            }
                        }
                        default -> {
                            continue;
                        }
                    }
                } else if (i == text.length - 1) {
                    if (counter < 10) {
                        values[counter] = value;
                        counter++;
                    }
                    if (text.length == 3) {
                        counterThree.incrementAndGet();
                    } else if (text.length == 4) {
                        counterFour.incrementAndGet();
                    } else if (text.length == 5) {
                        counterFive.incrementAndGet();
                    }
                }
            }
        }
        System.out.println("Примеры слов с буквами по возрастанию:");
        System.out.println(Arrays.toString(values) + "\n");
    }
}