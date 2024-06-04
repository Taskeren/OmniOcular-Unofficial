package me.exz.omniocular.util;

import static me.exz.omniocular.util.NBTHelper.NBTCache;

import java.lang.reflect.Type;
import java.util.Map;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class NBTSerializer implements JsonSerializer<NBTBase> {

    @Override
    public JsonElement serialize(NBTBase src, Type typeOfSrc, JsonSerializationContext context) {
        switch (src.getId()) {
            case 0 -> {
                return JsonNull.INSTANCE;
            }
            case 1 -> {
                return new JsonPrimitive(((NBTTagByte) src).func_150290_f());
            }
            case 2 -> {
                return new JsonPrimitive(((NBTTagShort) src).func_150289_e());
            }
            case 3 -> {
                return new JsonPrimitive(((NBTTagInt) src).func_150287_d());
            }
            case 4 -> {
                return new JsonPrimitive(((NBTTagLong) src).func_150291_c());
            }
            case 5 -> {
                return new JsonPrimitive(((NBTTagFloat) src).func_150288_h());
            }
            case 6 -> {
                return new JsonPrimitive(((NBTTagDouble) src).func_150286_g());
            }
            case 7 -> {
                JsonArray jsonArrayByte = new JsonArray();
                for (byte b : ((NBTTagByteArray) src).func_150292_c()) {
                    jsonArrayByte.add(new JsonPrimitive(b));
                }
                return jsonArrayByte;
            }
            case 8 -> {
                return new JsonPrimitive(((NBTTagString) src).func_150285_a_());
            }
            case 9 -> {
                JsonArray jsonArrayList = new JsonArray();
                for (Object nbtListItem : ((NBTTagList) src).tagList) {
                    jsonArrayList.add(NBTHelper.gson1.toJsonTree(nbtListItem));
                }
                return jsonArrayList;
            }
            case 10 -> {
                JsonObject jsonObject = new JsonObject();
                NBTTagCompound nbtTagCompound = (NBTTagCompound) src;
                int hashCode = nbtTagCompound.hashCode();
                NBTCache.put(hashCode, nbtTagCompound);
                jsonObject.add("hashCode", new JsonPrimitive(hashCode));
                // noinspection unchecked
                Map<String, NBTBase> tagMap = nbtTagCompound.tagMap;
                for (Map.Entry<String, NBTBase> entry : tagMap.entrySet()) {
                    jsonObject.add(entry.getKey(), NBTHelper.gson1.toJsonTree(entry.getValue()));
                }
                return jsonObject;
            }
            case 11 -> {
                JsonArray jsonArrayInt = new JsonArray();
                for (int i : ((NBTTagIntArray) src).func_150302_c()) {
                    jsonArrayInt.add(new JsonPrimitive(i));
                }
                return jsonArrayInt;
            }
            default -> {
                return null;
            }
        }
    }
}
