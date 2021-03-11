package zephyr.agent.bytebuddy.plugin;

import zephyr.agent.bytebuddy.plugin.jvm.JvmPlugin;
import zephyr.agent.bytebuddy.plugin.trace.TracePlugin;

import java.util.ArrayList;
import java.util.List;

public class PluginFactory {

    public static List<Plugin> pluginGroup = new ArrayList<>();

    static {
        //链路监控
        pluginGroup.add(new TracePlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
    }

}
