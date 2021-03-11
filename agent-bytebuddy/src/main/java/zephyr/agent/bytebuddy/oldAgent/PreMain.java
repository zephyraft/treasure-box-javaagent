package zephyr.agent.bytebuddy.oldAgent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import zephyr.agent.bytebuddy.plugin.jvm.JvmStack;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class PreMain {

    private static final int PRINT_TIMES = 5;
    private static ScheduledFuture<?> scheduledFuture = null;

    public static void premain(final String agentArgs, final Instrumentation inst) {
        System.out.println("this is my agent, agentArgs= " + agentArgs);
        AtomicInteger atomicInteger = new AtomicInteger(0);


        // 方法耗时监控
        new AgentBuilder
                .Default()
                .type(named("zephyr.agent.bytebuddy.ApiTest"))
                .transform(
                        (builder, typeDescription, classLoader, module) -> {
                            System.out.println(typeDescription);
                            return builder.method(any())
                                    .intercept(
                                            MethodDelegation
                                                    .to(DurationMonitor.class)
                                    );
                        }
                ).installOn(inst);

        // 内存和gc情况打印
        scheduledFuture = Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            JvmStack.printMemoryInfo();
            JvmStack.printGCInfo();
            System.out.println("===================================================================================================");
            System.out.println(atomicInteger.get());
            if (atomicInteger.getAndIncrement() > PRINT_TIMES) {
                // 执行一定次数后退出
                scheduledFuture.cancel(false);
                System.exit(0);
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
}
