package zephyr.agent.bytebuddy.trace;

public class TraceContext {

    private static final ThreadLocal<String> threadLocalTrace = new ThreadLocal<>();

    public static void clear(){
        threadLocalTrace.remove();
    }

    public static String getTraceId(){
        return threadLocalTrace.get();
    }

    public static void setTraceId(String traceId){
        threadLocalTrace.set(traceId);
    }

}
