package zephyr.agent.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import zephyr.agent.bytebuddy.plugin.InterceptPoint;
import zephyr.agent.bytebuddy.plugin.Plugin;
import zephyr.agent.bytebuddy.plugin.PluginFactory;

import java.lang.instrument.Instrumentation;
import java.util.List;

public class Agent {

    public static void premain(final String agentArgs, final Instrumentation inst) {
        System.out.println("基于javaagent链路追踪");
        System.out.println("==========================================================\r\n");
        AgentBuilder agentBuilder = new AgentBuilder.Default();

        List<Plugin> pluginGroup = PluginFactory.pluginGroup;
        for (Plugin plugin : pluginGroup) {
            InterceptPoint[] interceptPoints = plugin.buildInterceptPoints();
            for (InterceptPoint point : interceptPoints) {
                agentBuilder = agentBuilder.type(point.buildTypesMatcher()).transform((builder, typeDescription, classLoader, javaModule) -> {
                    builder = builder.visit(Advice.to(plugin.adviceClass()).on(point.buildMethodsMatcher()));
                    return builder;
                });
            }
        }

        agentBuilder.installOn(inst);
    }
}
