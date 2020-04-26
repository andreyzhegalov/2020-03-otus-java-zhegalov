package hw04.gc;

import java.lang.management.ManagementFactory;
import java.util.ArrayDeque;

/*
-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
*/

/*
1)
    default, time: 83 sec (82 without Label_1)
2)
    -XX:MaxGCPauseMillis=100000, time: 82 sec //Sets a target for the maximum GC pause time.
3)
    -XX:MaxGCPauseMillis=10, time: 91 sec

4)
-Xms2048m
-Xmx2048m
    time: 81 sec

5)
-Xms5120m
-Xmx5120m
    time: 80 sec

5)
-Xms20480m
-Xmx20480m
    time: 81 sec (72 without Label_1)

*/
/*
-Xms256m
-Xmx256m
-verbose:gc
-XX:+UseG1GC
-XX:+UParallelGC
*/

class UnitOfWork {
    final private byte payload[];
    private long cnt;

    public UnitOfWork(int loadSize) {
        this.payload = new byte[loadSize];
        cnt = 0;
    }

    public void someWork() {
        cnt++;
    }
}

public class AppModel {
    private int taskPrice = 1_000; // стоимость одной задачи [байт]
    private int taskCnt = 1_000; // количество новых задач
    private double ratio; // 1 - все пришло и ушло. > 1 - приходит больше, <1 - уходит больше
    private ArrayDeque<UnitOfWork> queue = new ArrayDeque<>();

    AppModel(double ratio) {
        this.ratio = ratio;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1)
            throw new RuntimeException("No ratio parametr");

        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        var model = new AppModel(Double.valueOf(args[0]));

        System.out.println("Ratio = " + model.getRatio());
        System.out.println("Incoming task " + model.newTaskCnt());
        System.out.println("Outcomming task " + model.readyTaskCnt());

        int initTaskCnt = 10_000; // Начальное состояние очереди
        model.setInitSize(initTaskCnt);
        model.run();
    }

    int newTaskCnt() {
        return taskCnt;
    }

    int readyTaskCnt() {
        double inverseRatio = 2 - ratio;
        return (int) (inverseRatio * taskCnt);
    }

    void setInitSize(int workSize) {
        queue.clear();
        while (workSize > 0) {
            queue.add(new UnitOfWork(taskPrice));
            workSize--;
        }
    }

    void run() throws InterruptedException {

        while (true) {

            for (int r = 0; r < readyTaskCnt(); r++) {
                if (queue.isEmpty())
                    break;
                queue.remove();
            }

            for (int n = 0; n < newTaskCnt(); n++) {
                queue.add(new UnitOfWork(taskPrice));
            }

            for (UnitOfWork element : queue) {
                element.someWork();
            }

            System.out.println("Curent queue size  " + queue.size());
            Thread.sleep(100);
        }
    }

    public double getRatio() {
        return ratio;
    }
}
