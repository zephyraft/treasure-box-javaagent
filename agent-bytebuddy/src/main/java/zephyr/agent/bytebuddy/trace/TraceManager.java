package zephyr.agent.bytebuddy.trace;

import java.util.Stack;

public class TraceManager {

    private static final ThreadLocal<Stack<Span>> threadLocalTrace = new ThreadLocal<>();

    private static Span createSpan() {
        Stack<Span> stack = threadLocalTrace.get();
        if (stack == null) {
            stack = new Stack<>();
            threadLocalTrace.set(stack);
        }
        String traceId;
        if (stack.isEmpty()) {
            traceId = TraceContext.getTraceId();
            if (traceId == null) {
                traceId = "nvl";
                TraceContext.setTraceId(traceId);
            }
        } else {
            Span span = stack.peek();
            traceId = span.getTraceId();
            TraceContext.setTraceId(traceId);
        }
        return new Span(traceId);
    }

    public static Span createEntrySpan() {
        Span span = createSpan();
        Stack<Span> stack = threadLocalTrace.get();
        stack.push(span);
        return span;
    }


    public static Span getExitSpan() {
        Stack<Span> stack = threadLocalTrace.get();
        if (stack == null || stack.isEmpty()) {
            TraceContext.clear();
            return null;
        }
        return stack.pop();
    }

    public static Span getCurrentSpan() {
        Stack<Span> stack = threadLocalTrace.get();
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }
}
