package com.itcast.kafka.producer;

/**
 * 将生产者一直运行下去
 */
public class Running {

    public static void main(String[] args) {

        for (int i = 1; i < 33; i++) {

            new Thread(new Configuration("" + i + "")).start();
        }
    }
}
