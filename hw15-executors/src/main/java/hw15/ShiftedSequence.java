package hw15;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw15.generator.TriangleGenerator;

public class ShiftedSequence {
    private static final Logger logger = LoggerFactory.getLogger(ShiftedSequence.class);
    private static final String THREAD1_NAME = "t1";
    private static final String THREAD2_NAME = "t2";

    private final Lock lock = new ReentrantLock();
    private final Condition otherThread;
    private String lastThreadName = THREAD2_NAME;

    public ShiftedSequence() {
        otherThread = lock.newCondition();
    }

    public static void main(String[] args) throws InterruptedException {
        new ShiftedSequence().go();
    }

    private void go() throws InterruptedException {

        Thread t1 = new Thread(() -> criticalSection(1_000));
        t1.setName(THREAD1_NAME);

        Thread t2 = new Thread(() -> criticalSection(2_000));
        t2.setName(THREAD2_NAME);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private void criticalSection(long sleepMs) {
        final var generator = new TriangleGenerator(1, 10);
        final var currentThreadName = Thread.currentThread().getName();
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            try {
                while (currentThreadName.equals(lastThreadName)) {
                    try {
                        otherThread.await();
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                }
                lastThreadName = currentThreadName;
                otherThread.signalAll();

                final var nextValue = generator.getNext();
                if (nextValue.isEmpty()) {
                    break;
                } else {
                    logger.info("new value {}", nextValue.get());
                    sleep(sleepMs);
                }

            } finally {
                lock.unlock();
            }
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
