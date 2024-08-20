package me.exz.omniocular.waila;

import static me.exz.omniocular.util.NBTHelper.NBTCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import me.exz.omniocular.handler.ScriptEngineHandler;
import me.exz.omniocular.handler.XMLConfigHandler;
import me.exz.omniocular.util.LogHelper;
import me.exz.omniocular.util.NBTHelper;

@SuppressWarnings({ "CanBeFinal", "UnusedDeclaration" })
public class JSHandler {

    public static ScriptEngine engine;
    public static HashSet<String> scriptSet = new HashSet<>();

    private static class EmptyList extends ArrayList<String> {
    }

    static List<String> EMPTY_LIST = new EmptyList();
    static LoadingCache<Integer, List<String>> cache = CacheBuilder.newBuilder()
        .maximumSize(200)
        .build(new CacheLoader<>() {

            @Override
            public List<String> load(Integer key) {
                return EMPTY_LIST;
            }
        });

    private static final Map<String, String> fluidList = new HashMap<>();
    private static final Map<String, String> displayNameList = new HashMap<>();
    private static EntityPlayer entityPlayer;

    static List<String> getBody(Map<Pattern, Node> patternMap, NBTTagCompound n, String id, EntityPlayer player) {
        entityPlayer = player;
        int hashCode = n.toString()
            .hashCode();
        List<String> list = cache.getUnchecked(hashCode);
        if (list instanceof EmptyList) {

            try {
                String json = "var nbt=" + NBTHelper.NBT2json(n) + ";";
                JSHandler.engine.eval(json);
            } catch (ScriptException e) {
                e.printStackTrace();
            }

            List<String> tips = new ArrayList<>();

            cache.put(hashCode, tips);

            for (Map.Entry<Pattern, Node> entry : patternMap.entrySet()) {
                Matcher matcher = entry.getKey()
                    .matcher(id);
                if (matcher.matches()) {
                    Element item = (Element) entry.getValue();
                    // if (item.getElementsByTagName("head").getLength() > 0) {
                    // Node head = item.getElementsByTagName("head").item(0);
                    // }
                    if (item.getElementsByTagName("line")
                        .getLength() > 0) {
                        String tip;
                        NodeList lines = item.getElementsByTagName("line");
                        for (int i = 0; i < lines.getLength(); i++) {
                            Node line = lines.item(i);
                            String displayname = "";
                            if (line.getAttributes()
                                .getNamedItem("displayname") != null
                                && !line.getAttributes()
                                    .getNamedItem("displayname")
                                    .getTextContent()
                                    .trim()
                                    .isEmpty()) {
                                displayname = StatCollector.translateToLocal(
                                    line.getAttributes()
                                        .getNamedItem("displayname")
                                        .getTextContent());
                            }
                            String functionContent = line.getTextContent();
                            String hash = "S" + NBTHelper.MD5(functionContent);
                            if (!JSHandler.scriptSet.contains(hash)) {
                                JSHandler.scriptSet.add(hash);
                                if (!functionContent.contains("return")) {
                                    functionContent = "return " + functionContent.trim();
                                }
                                String script = "function " + hash + "()" + "{" + functionContent + "}";
                                try {
                                    JSHandler.engine.eval(script);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Invocable invoke = (Invocable) JSHandler.engine;
                            try {
                                String result = String.valueOf(invoke.invokeFunction(hash, ""));
                                if (result.equals("__ERROR__") || result.equals("null")
                                    || result.equals("undefined")
                                    || result.equals("NaN")) {
                                    continue;
                                }
                                if (patternMap == XMLConfigHandler.tooltipPattern) {
                                    tip = "§7" + displayname + ": §f";
                                } else {
                                    tip = XMLConfigHandler.settingList.get("displaynameTileentity")
                                        .replace("DISPLAYNAME", displayname)
                                        .replace("RETURN", result);
                                }
                            } catch (Exception e) {
                                continue;
                                // e.printStackTrace();
                            }
                            if (tip.equals("__ERROR__")) {
                                continue;
                            }
                            tips.addAll(Arrays.asList(tip.split("\n")));
                        }
                    }
                }
            }
            return tips;
        } else return list;
    }

    public static void initEngine() {
        scriptSet.clear();

        engine = ScriptEngineHandler.manager.getEngineByName("js");

        if (engine == null) {
            LogHelper.fatal("no javascript engine.");
            throw new RuntimeException("no javascript engine.");
        }

        setSpecialChar();

        try {
            engine.eval("load(\"nashorn:mozilla_compat.js\");");
        } catch (ScriptException ignored) {}
        try {
            engine.eval("var _JSHandler = Java.type('me.exz.omniocular.waila.JSHandler');");
            engine.eval("function translate(t){return _JSHandler.translate(t)}");
            engine.eval("function translateFormatted(t,obj){return _JSHandler.translateFormatted(t,obj)}");
            engine.eval("function name(n){return _JSHandler.getDisplayName(n.hashCode)}");
            engine.eval("function fluidName(n){return _JSHandler.getFluidName(n)}");
            engine.eval("function holding(){return _JSHandler.playerHolding()}");
            engine.eval("function armor(i){return _JSHandler.playerArmor(i)}");
            engine.eval("function isInHotbar(n){return _JSHandler.haveItemInHotbar(n)}");
            engine.eval("function isInInv(n){return _JSHandler.haveItemInInventory(n)}");
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

    private static void setSpecialChar() {
        String MCStyle = "§";
        engine.put("BLACK", MCStyle + "0");
        engine.put("DBLUE", MCStyle + "1");
        engine.put("DGREEN", MCStyle + "2");
        engine.put("DAQUA", MCStyle + "3");
        engine.put("DRED", MCStyle + "4");
        engine.put("DPURPLE", MCStyle + "5");
        engine.put("GOLD", MCStyle + "6");
        engine.put("GRAY", MCStyle + "7");
        engine.put("DGRAY", MCStyle + "8");
        engine.put("BLUE", MCStyle + "9");
        engine.put("GREEN", MCStyle + "a");
        engine.put("AQUA", MCStyle + "b");
        engine.put("RED", MCStyle + "c");
        engine.put("LPURPLE", MCStyle + "d");
        engine.put("YELLOW", MCStyle + "e");
        engine.put("WHITE", MCStyle + "f");

        engine.put("OBF", MCStyle + "k");
        engine.put("BOLD", MCStyle + "l");
        engine.put("STRIKE", MCStyle + "m");
        engine.put("UNDER", MCStyle + "n");
        engine.put("ITALIC", MCStyle + "o");
        engine.put("RESET", MCStyle + "r");
        String WailaStyle = "¤";
        String WailaIcon = "¥";
        engine.put("TAB", WailaStyle + WailaStyle + "a");
        engine.put("ALIGNRIGHT", WailaStyle + WailaStyle + "b");
        engine.put("ALIGNCENTER", WailaStyle + WailaStyle + "c");
        engine.put("HEART", WailaStyle + WailaIcon + "a");
        engine.put("HHEART", WailaStyle + WailaIcon + "b");
        engine.put("EHEART", WailaStyle + WailaIcon + "c");
        // LogHelper.info("Special Char loaded");
    }

    public static String translate(String t) {
        return StatCollector.translateToLocal(t);
    }

    public static String translateFormatted(String t, Object[] format) {
        return StatCollector.translateToLocalFormatted(t, format);
    }

    public static String playerHolding() {
        ItemStack is = entityPlayer.getHeldItem();
        if (is == null) {
            return "";
        }
        return Item.itemRegistry.getNameForObject(is.getItem());
    }

    public static String playerArmor(int i) {
        if (i < 0 || i > 3) {
            return null;
        }
        ItemStack is = entityPlayer.inventory.armorInventory[i];
        if (is == null) {
            return "";
        }
        return Item.itemRegistry.getNameForObject(is.getItem());
    }

    public static Boolean haveItemInHotbar(String n) {
        int s = entityPlayer.inventory.func_146029_c((Item) Item.itemRegistry.getObject(n));
        return s > -1 && s < 9;
    }

    public static Boolean haveItemInInventory(String n) {
        return entityPlayer.inventory.func_146029_c((Item) Item.itemRegistry.getObject(n)) != -1;
    }

    public static String getDisplayName(String hashCode) {
        try {
            NBTTagCompound nc = NBTCache.get(Integer.valueOf(hashCode));
            ItemStack is = ItemStack.loadItemStackFromNBT(nc);
            return is.getDisplayName();
        } catch (Exception e) {
            return "__ERROR__";
        }
    }

    public static String getFluidName(String uName) {
        if (fluidList.containsKey(uName)) {
            return fluidList.get(uName);
        } else {
            try {
                Fluid f = FluidRegistry.getFluid(uName.toLowerCase());
                FluidStack fs = new FluidStack(f, 1);
                String lName = fs.getLocalizedName();
                fluidList.put(uName, lName);
                return lName;
            } catch (Exception e) {
                return "__ERROR__";
            }
        }
    }
}
