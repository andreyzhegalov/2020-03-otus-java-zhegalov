package hw04.gc;

import com.sun.management.GarbageCollectionNotificationInfo;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

/*
-Xms256m
-Xmx256m
-verbose:gc
-XX:+UseG1GC
*/

public class Tester {
    public static void main(String[] args) throws InterruptedException {
        switchOnMonitoring();
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo
                            .from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:"
                            + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
