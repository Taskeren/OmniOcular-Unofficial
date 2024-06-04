package me.exz.omniocular.waila;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerTooltipHandler;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.XMLConfigHandler;

public class TooltipHandler implements IContainerTooltipHandler {

    @Override
    public List<String> handleTooltip(GuiContainer guiContainer, int i, int i2, List<String> strings) {
        return strings;
    }

    @Override
    public List<String> handleItemDisplayName(GuiContainer guiContainer, ItemStack itemStack, List<String> strings) {

        return strings;
    }

    @Override
    public List<String> handleItemTooltip(GuiContainer guiContainer, ItemStack itemStack, int i, int i2,
        List<String> currenttip) {
        if (!Config.enableTooltipInfo) return currenttip;

        if (guiContainer != null && GuiContainerManager.shouldShowTooltip(guiContainer) && itemStack != null) {
            NBTTagCompound n = itemStack.getTagCompound();

            if (n != null) {
                currenttip.addAll(
                    1,
                    JSHandler.getBody(
                        XMLConfigHandler.tooltipPattern,
                        n,
                        Item.itemRegistry.getNameForObject(itemStack.getItem()),
                        guiContainer.mc.thePlayer));

            }
        }
        return currenttip;
    }
}
