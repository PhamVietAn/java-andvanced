package Ex06;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class DeadlockDetector {

    public static void detectDeadlock() {

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        long[] threadIds = bean.findDeadlockedThreads();

        System.out.println("Đang quét deadlock...");

        if (threadIds == null) {
            System.out.println("Không phát hiện deadlock.");
        } else {
            System.out.println("Phát hiện DEADLOCK!");
        }
    }
}

