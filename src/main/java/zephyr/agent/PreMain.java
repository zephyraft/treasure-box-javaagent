package zephyr.agent;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

public class PreMain {

    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
//        System.out.println(Arrays.toString(inst.getAllLoadedClasses()));

        inst.addTransformer(new ProfilingTransformer());
        System.out.println("this is my agent");
    }
    //如果代理类没有实现上面的方法，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
