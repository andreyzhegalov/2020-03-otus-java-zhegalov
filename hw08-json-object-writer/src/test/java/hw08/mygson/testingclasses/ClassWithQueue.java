package hw08.mygson.testingclasses;

import java.util.Queue;

public class ClassWithQueue {
    final Queue<?> queue;

    public ClassWithQueue(Queue<?> queue) {
        this.queue = queue;
    }
}
