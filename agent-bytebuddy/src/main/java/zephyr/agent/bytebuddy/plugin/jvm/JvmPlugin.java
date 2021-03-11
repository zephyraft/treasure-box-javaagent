package zephyr.agent.bytebuddy.plugin.jvm;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import zephyr.agent.bytebuddy.plugin.InterceptPoint;
import zephyr.agent.bytebuddy.plugin.Plugin;

public class JvmPlugin implements Plugin {
    @Override
    public String name() {
        return "jvm";
    }

    @Override
    public InterceptPoint[] buildInterceptPoints() {
        return new InterceptPoint[]{
                new InterceptPoint() {
                    @Override
                    public ElementMatcher<TypeDescription> buildTypesMatcher() {
                        return ElementMatchers.namedOneOf("zephyr.agent.bytebuddy.ApiTest", "zephyr.agent.bytebuddy.ApiTest2");
                    }

                    @Override
                    public ElementMatcher<MethodDescription> buildMethodsMatcher() {
                        return ElementMatchers.isMethod()
                                .and(ElementMatchers.any())
                                .and(ElementMatchers.not(ElementMatchers.nameStartsWith("main")));
                    }
                }
        };
    }

    @Override
    public Class<?> adviceClass() {
        return JvmAdvice.class;
    }
}
