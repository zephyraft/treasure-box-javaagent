package zephyr.agent.bytebuddy.plugin.trace;

import net.bytebuddy.asm.Advice;
import zephyr.agent.bytebuddy.trace.Span;
import zephyr.agent.bytebuddy.trace.TraceContext;
import zephyr.agent.bytebuddy.trace.TraceManager;

import java.util.UUID;

public class TraceAdvice {

    @Advice.OnMethodEnter()
    public static void enter(@Advice.Origin("#t") String className,
                             @Advice.Origin("#m") String methodName) {
        Span currentSpan = TraceManager.getCurrentSpan();
        if (null == currentSpan) {
            String traceId = UUID.randomUUID().toString();
            TraceContext.setTraceId(traceId);
        }
        TraceManager.createEntrySpan();
    }

    @Advice.OnMethodExit()
    public static void exit(@Advice.Origin("#t") String className,
                            @Advice.Origin("#m") String methodName) {
        Span exitSpan = TraceManager.getExitSpan();
        if (null == exitSpan) return;
        System.out.println("链路追踪(MQ)：" + exitSpan.getTraceId() + " " +
                className + "." + methodName + " 耗时：" + (System.currentTimeMillis() -
                exitSpan.getEnterTime().getTime()) + "ms");
    }

}
