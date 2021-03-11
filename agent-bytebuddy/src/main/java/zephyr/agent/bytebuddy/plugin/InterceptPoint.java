package zephyr.agent.bytebuddy.plugin;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public interface InterceptPoint {

    // 类匹配规则
    ElementMatcher<TypeDescription> buildTypesMatcher();

    // 方法匹配规则
    ElementMatcher<MethodDescription> buildMethodsMatcher();

}
