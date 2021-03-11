package zephyr.agent.bytebuddy.oldAgent;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class DurationMonitor {
    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        Object res = null;
        try {
            res = callable.call();
            return res;
        } finally {
            System.out.println("方法名称：" + method.getName());
            System.out.println("入参内容：" + Arrays.toString(args));
            System.out.println("出参类型：" + method.getReturnType().getName());
            System.out.println("出参结果：" + res);
            System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }
}
