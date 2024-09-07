package me.exz.omniocular.waila;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import me.exz.omniocular.IScript;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.util.LogHelper;

public class PluginEngine {

    static Map<Pattern, IScript> entityScripts = new HashMap<>();
    static Map<Pattern, IScript> tileEntityScripts = new HashMap<>();
    static Map<Pattern, IScript> FMLScripts = new HashMap<>();
    static Map<Pattern, IScript> TooltipScripts = new HashMap<>();

    public static void init() {
        for (String string : Config.scriptClassName) {
            try {
                Class<?> clazz = Class.forName(string);
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                IScript script = (IScript) constructor.newInstance();
                IScript.Type type = script.registerType();
                switch (type) {
                    case Entity -> entityScripts.put(script.registerIdPattern(), script);
                    case TileEntity -> tileEntityScripts.put(script.registerIdPattern(), script);
                    case Tooltip -> TooltipScripts.put(script.registerIdPattern(), script);
                    case FMP -> FMLScripts.put(script.registerIdPattern(), script);
                    default -> LogHelper.error("Invalid script type: " + type);
                }
            } catch (ClassNotFoundException e) {
                LogHelper.warn("Script class: " + string + " not found");
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException
                | IllegalAccessException e) {
                LogHelper.warn("Script class: " + string + " can't be initialized");
            }

        }
    }

    public static List<String> getWailaBody(IScript.Type type, NBTTagCompound nbt, String id, EntityPlayer player) {
        Map<Pattern, IScript> scripts = switch (type) {
            case Entity -> entityScripts;
            case TileEntity -> tileEntityScripts;
            case Tooltip -> TooltipScripts;
            case FMP -> FMLScripts;
        };

        List<String> body = new LinkedList<>();
        scripts.forEach((pattern, iScript) -> {
            Matcher matcher = pattern.matcher(id);
            if (matcher.find()) {
                body.addAll(iScript.getLines(nbt, id, player));
            }
        });
        return body;
    }
}
