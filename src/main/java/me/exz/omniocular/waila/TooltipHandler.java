package me.exz.omniocular.waila;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerTooltipHandler;
import me.exz.omniocular.IScript;
import me.exz.omniocular.config.Config;
import me.exz.omniocular.handler.XMLConfigHandler;

public class TooltipHandler implements IContainerTooltipHandler {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public List<String> handleTooltip(GuiContainer guiContainer, int i, int i2, List<String> strings) {
        return strings;
    }

    @Override
    public List<String> handleItemDisplayName(GuiContainer guiContainer, ItemStack itemStack, List<String> strings) {

        return strings;
    }

    @Override
    public List<String> handleItemTooltip(GuiContainer guiContainer, ItemStack itemStack, int mouseX, int mouseY,
        List<String> tooltips) {
        if (!Config.enableTooltipInfo) return tooltips;

        if (GuiContainerManager.shouldShowTooltip(guiContainer) && itemStack != null) {
            try {
                NBTTagCompound itemTags = itemStack.getTagCompound();
                if (itemTags != null) {
                    String itemId = Item.itemRegistry.getNameForObject(itemStack.getItem());
                    if (itemId != null) { // FIX: ThaumcraftNEIPlugin will add items, but if it's not on the server, the
                                          // itemId is null.
                        tooltips.addAll(
                            PluginEngine
                                .getWailaBody(IScript.Type.Tooltip, itemTags, itemId, guiContainer.mc.thePlayer));
                        tooltips.addAll(
                            JSEngine
                                .getBody(XMLConfigHandler.tooltipPattern, itemTags, itemId, guiContainer.mc.thePlayer));
                    }
                }
            } catch (Exception e) {
                LOG.warn(
                    "Exception occurred while rendering extra tooltips for {} (tag={})",
                    itemStack,
                    itemStack.getTagCompound(),
                    e);
            }
        }
        return tooltips;
    }
}
