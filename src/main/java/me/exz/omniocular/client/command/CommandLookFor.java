package me.exz.omniocular.client.command;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MovingObjectPosition;

import me.exz.omniocular.config.Config;
import me.exz.omniocular.util.NBTHelper;

public class CommandLookFor extends CommandBase {

    @Override
    public String getCommandName() {
        return "ool";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender instanceof EntityPlayer;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ool";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] array) {
        if (array.length == 1 && array[0].equals("reload")) {
            Config.preprocess();
            return;
        }

        EntityPlayer player = (EntityPlayer) sender;
        Minecraft minecraft = Minecraft.getMinecraft();
        MovingObjectPosition objectMouseOver = minecraft.objectMouseOver;

        switch (objectMouseOver.typeOfHit) {
            case ENTITY: {
                Class<?> pointEntityClass = objectMouseOver.entityHit.getClass();
                if (EntityList.classToStringMapping.containsKey(pointEntityClass)) {
                    player.addChatComponentMessage(
                        new ChatComponentText(EntityList.getEntityString(objectMouseOver.entityHit)));
                }
                break;
            }

            case BLOCK: {
                Block block = player.getEntityWorld()
                    .getBlock(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
                player.addChatComponentMessage(
                    new ChatComponentTranslation(
                        "Name: %s (%s) Class: %s",
                        block.getLocalizedName(),
                        Block.blockRegistry.getNameForObject(block),
                        block.getClass()
                            .getName()));

                TileEntity tileEntity = player.getEntityWorld()
                    .getTileEntity(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ);
                if (tileEntity != null) {
                    NBTTagCompound nbtTagCompound = new NBTTagCompound();
                    tileEntity.writeToNBT(nbtTagCompound);
                    player.addChatComponentMessage(
                        new ChatComponentTranslation(
                            "TE: %s",
                            tileEntity.getClass()
                                .getName()));
                    String[] lines = NBTHelper.NBT2jsonPrettyPrinting(nbtTagCompound)
                        .split("\n");
                    player.addChatComponentMessage(new ChatComponentText("NBT:"));
                    for (String line : lines) {
                        player.addChatComponentMessage(new ChatComponentText(line));
                    }

                }
                break;
            }
            case MISS:
                player.addChatComponentMessage(new ChatComponentTranslation("omniocular.info.NotPointing"));

        }

    }
}
