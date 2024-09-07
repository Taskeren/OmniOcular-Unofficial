package me.exz.omniocular;

import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface IScript {

    enum Type {
        TileEntity,
        Entity,
        Tooltip,
        FMP
    }

    /**
     * 返回脚本注册的类型，脚本将接受该类型的所有输入
     * 
     * @return 脚本类型
     */
    Type registerType();

    /**
     * 注册脚本处理的Id匹配规则
     * 
     * @return 匹配规则
     */
    Pattern registerIdPattern();

    /**
     * 返回提示框主体
     * 
     * @param nbt nbt输入
     * @return waila内容
     */
    List<String> getLines(NBTTagCompound nbt, String id, EntityPlayer player);
}
