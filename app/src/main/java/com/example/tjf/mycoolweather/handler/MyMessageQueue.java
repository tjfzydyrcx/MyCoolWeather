package com.example.tjf.mycoolweather.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2018-07-16 0016.
 */

public class MyMessageQueue {
    Lock lock;
    Condition mEmptyQueue;
    Condition mFullQueue;

    MyMessage[] myMessages;
    int putIndex;
    int takeIndex;
    int count;

    public MyMessageQueue() {
        myMessages = new MyMessage[50];
        lock = new ReentrantLock();
        mEmptyQueue = lock.newCondition();
        mFullQueue = lock.newCondition();
    }

    final void enqueueMessage(MyMessage msg) {
        try {
            lock.lock();
            while (count == myMessages.length) {
                try {
                    mFullQueue.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myMessages[putIndex] = msg;
                putIndex = (++putIndex == myMessages.length ? 0 : putIndex);

                count++;
                mEmptyQueue.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    final MyMessage next() {
        MyMessage msg = null;
        try {
            lock.lock();
            while (count == 0) {
                try {
                    mEmptyQueue.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = myMessages[takeIndex];
            myMessages[takeIndex] = null;
            takeIndex = (++takeIndex == myMessages.length ? 0 : takeIndex);
            count--;
            mFullQueue.signalAll();
        } finally {
            lock.unlock();
        }
        return msg;
    }
}
