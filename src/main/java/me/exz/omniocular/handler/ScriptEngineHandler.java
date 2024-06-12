package me.exz.omniocular.handler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;

import cpw.mods.fml.common.FMLCommonHandler;
import me.exz.omniocular.util.LogHelper;

public class ScriptEngineHandler {

    public static ScriptEngineManager manager;

    private static void downLoadFromUrl(String urlStr, File saveFile) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();

        byte[] getData = bos.toByteArray();

        // 文件保存位置
        File saveDir = new File(saveFile.getParent());
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        FileOutputStream fos = new FileOutputStream(saveFile);
        fos.write(getData);
        fos.close();
        inputStream.close();
    }

    private static final String[] repos = new String[] { "https://repo1.maven.org/maven2/", "http://maven.aliyun.com/",
        "https://repo.maven.apache.org/maven2/", "https://mirrors.cloud.tencent.com/repository/maven/",
        "http://maven.netease.com/repository/public/" };

    public static void initScriptEngineManager() {
        if (Double.parseDouble(System.getProperty("java.class.version")) >= 55.0) {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            boolean succeed;

            try {
                classLoader.loadClass("org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory");
                succeed = true;
            } catch (ClassNotFoundException e) {
                succeed = false;
            }

            if (!succeed) {
                File jarFile = new File(Launch.minecraftHome, "/mods/oo/nashorn-core-15.4.jar");
                LogHelper.info("Nashorn core path: " + jarFile);
                if (!jarFile.exists() || !jarFile.isFile()) {
                    LogHelper.info("Nashorn core not exist!");
                    LogHelper.info("Downloading...!");

                    String urlPath = "org/openjdk/nashorn/nashorn-core/15.4/nashorn-core-15.4.jar";

                    boolean downloadSucceed = false;
                    for (String urlBase : repos) {
                        String url = urlBase + urlPath;
                        try {
                            LogHelper.info("Download From: " + url);
                            downLoadFromUrl(url, jarFile);
                            LogHelper.info("Download succeed!");
                            downloadSucceed = true;
                            break;
                        } catch (IOException e) {
                            LogHelper.fatal("Download Failed! " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    if (!downloadSucceed) {
                        LogHelper.fatal("Unable to download file: " + jarFile.getPath());
                        LogHelper.fatal(
                            "You can manually download nashorn-core-15.4.jar it and copy the file to "
                                + jarFile.getPath());
                        FMLCommonHandler.instance()
                            .exitJava(-1, false);
                    }

                }

                if (classLoader.getClass()
                    .getName()
                    .equals("jdk.internal.loader.ClassLoaders$AppClassLoader")) {

                    for (Method method : classLoader.getClass()
                        .getDeclaredMethods()) {
                        if (method.getName()
                            .equals("appendToClassPathForInstrumentation")) {
                            method.setAccessible(true);
                            try {
                                method.invoke(classLoader, jarFile.toString());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                    }
                }
            }
            manager = new ScriptEngineManager(classLoader);
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
