package hw15;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw15.generator.TriangleGenerator;

public class ShiftedSequence {
    private static final Logger logger = LoggerFactory.getLogger(ShiftedSequence.class);

    private final Lock lock = new ReentrantLock();
    private final CountDownLatch callingThreadBlocker = new CountDownLatch(1);
    private final Condition otherThread;
    private String lastThreadName;

    public ShiftedSequence() {
        otherThread = lock.newCondition();
    }

    public static void main(String[] args) throws InterruptedException {
        new ShiftedSequence().go();
    }

    private void go() throws InterruptedException {

        Thread t1 = new Thread(() -> criticalSection(1_000, true));
        t1.setName("t1");

        Thread t2 = new Thread(() -> criticalSection(2_000, false));
        t2.setName("t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    private void criticalSection(long sleepMs, boolean startFirst) {
        final var generator = new TriangleGenerator(1, 10);
        final var currentThreadName = Thread.currentThread().getName();
        if (!startFirst) {
            try {
                callingThreadBlocker.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            callingThreadBlocker.countDown();
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
