package zephyr.agent.bytebuddy.plugin;

public interface Plugin {

    // 名称
    String name();

    // 拦截点
    InterceptPoint[] buildInterceptPoints();

    // 增强
    Class<?> adviceClass();

}
