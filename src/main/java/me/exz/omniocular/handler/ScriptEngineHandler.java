package me.exz.omniocular.handler;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import me.exz.omniocular.util.LogHelper;

public class ScriptEngineHandler {

    static ScriptEngineManager manager;

    public static void initScriptEngineManager() {
        if (Double.parseDouble(System.getProperty("java.class.version")) >= 55.0) {

            File jarPath = new File(System.getProperty("user.dir") + "/mods/oo/nashorn-core-15.4.jar");
            LogHelper.info("nashorn path: " + jarPath);
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();

            if (classLoader.getClass()
                .getName()
                .equals("jdk.internal.loader.ClassLoaders$AppClassLoader")) {

                for (Method method : classLoader.getClass()
                    .getDeclaredMethods()) {
                    if (method.getName()
                        .equals("appendToClassPathForInstrumentation")) {
                        method.setAccessible(true);
                        try {
                            method.invoke(classLoader, jarPath.toString());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
                manager = new ScriptEngineManager(classLoader);
            }
        } else {
            try {
                LaunchClassLoader launchClassLoader = Launch.classLoader;
                launchClassLoader.addClassLoaderExclusion("jdk.nashorn");

                Class<?> clazz = launchClassLoader.loadClass("jdk.nashorn.api.scripting.NashornScriptEngineFactory");

                manager = new ScriptEngineManager(launchClassLoader);
                manager.registerEngineName("js", (ScriptEngineFactory) clazz.newInstance());
            } catch (ClassNotFoundException e) {
                LogHelper.info("The Java has not Nashorn Javascript Engine!");
            } catch (InstantiationException | IllegalAccessException e) {
                LogHelper.info("Nashorn Javascript Engine create failed!");
            }
        }

        if (manager == null) manager = new ScriptEngineManager();

        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory f : factories) {
            LogHelper.info("Available Engine: " + f.getLanguageName() + " " + f.getEngineName() + " " + f.getNames());
        }
        LogHelper.info("Java Home: " + System.getProperty("java.home"));
    }
}
