package zephyr.agent.bytebuddy.plugin.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.List;

public class JvmStack {

    private static final long MiB = 1024 * 1024L;

    public static void printMemoryInfo() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();
        String info = String.format("\nheap init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                headMemory.getInit() / MiB + "MiB",
                headMemory.getMax() / MiB + "MiB",
                headMemory.getUsed() / MiB + "MiB",
                headMemory.getCommitted() / MiB + "MiB",
                headMemory.getUsed() * 100 / headMemory.getCommitted() + "%"
        );
        System.out.print(info);
        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();
        info = String.format("nonheap init: %s\t max: %s\t used: %s\t committed: %s\t use rate: %s\n",
                nonheadMemory.getInit() / MiB + "MiB",
                nonheadMemory.getMax() / MiB + "MiB",
                nonheadMemory.getUsed() / MiB + "MiB",
                nonheadMemory.getCommitted() / MiB + "MiB",
                nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%"
        );
        System.out.println(info);
    }

    public static void printGCInfo() {
        List<GarbageCollectorMXBean> garbages =
                ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            String info = String.format("name: %s\t count:%s\t took:%s\t pool name:%s",
                    garbage.getName(),
                    garbage.getCollectionCount(),
                    garbage.getCollectionTime(),
                    Arrays.deepToString(garbage.getMemoryPoolNames()));
            System.out.println(info);
        }
    }
}
