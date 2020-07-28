package hw15;

import org.slf4j.LoggerFactory;

import hw15.generator.TriangleGenerator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;

public class ShiftedSequence {
    private static final Logger logger = LoggerFactory.getLogger(ShiftedSequence.class);

    private final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        new ShiftedSequence().go();
    }

    private void go() throws InterruptedException {
        Thread t1 = new Thread(()->criticalSection(1_000));
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(()->criticalSection(2_000));
        t2.setName("t2");
        t2.start();

        t1.join();
        t2.join();
    }

    private void criticalSection(long sleepMs) {
        final var generator = new TriangleGenerator(1, 10);
        while (!Thread.currentThread().isInterrupted()) {
            lock.lock();
            try {
                final var nextValue = generator.getNext();
                if (nextValue.isEmpty()) {
                    break;
                }
                logger.info("new value {}", nextValue.get());
            } finally {
                lock.unlock();
            }
            sleep(sleepMs);
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
