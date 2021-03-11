package zephyr.agent.asm;

import java.util.HashSet;
import java.util.Set;

public class ProfilingFilter {

    private static final Set<String> classNameSet = new HashSet<>();

    static {
        classNameSet.add("zephyr/agent/ApiTest");
        classNameSet.add("zephyr/asm/agent/ApiTest");
    }

    public static boolean isNotNeedInject(String className) {
        return !classNameSet.contains(className);
    }
}
