package ru.sbt.gameoflife.concurrent.v2;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
* Created by dmitry on 14.12.17
*/
public class ExecutorsPool {
    private final Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private final Set<Runnable> inProgress = new HashSet<>();
    private volatile boolean isRunning = true;

    public ExecutorsPool(int nThreads) {
        for (int i = 0; i < nThreads; i++) {
            new Thread(new CellWorker()).start();
        }
    }

    public void execute(Runnable command) {
        if (isRunning) {
            workQueue.offer(command);
        }
    }

    public void waitAllTasks() {
        while (workQueue.size() > 0 || inProgress.size() > 0) { }
    }



    public void shutdown() {
        isRunning = false;
    }

    private final class CellWorker implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                Runnable nextTask = workQueue.poll();
                synchronized (inProgress) {
                    inProgress.add(nextTask);
                }
                if (nextTask != null) {
                    nextTask.run();
                }
                synchronized (inProgress) {
                    inProgress.remove(nextTask);
                }
            }
        }
    }
}
