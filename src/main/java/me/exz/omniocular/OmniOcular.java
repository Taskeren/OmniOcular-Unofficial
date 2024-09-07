package me.exz.omniocular;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import me.exz.omniocular.reference.Reference;

@SuppressWarnings({ "UnusedParameters", "UnusedDeclaration" })
@Mod(
    modid = Reference.MOD_ID,
    name = Reference.MOD_NAME,
    version = Reference.VERSION,
    guiFactory = "me.exz.omniocular.client.gui.GuiFactory",
    dependencies = "required-after:Waila;required-after:NotEnoughItems")

public class OmniOcular {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.waila.EntityHandler.callbackRegister");
        FMLInterModComms.sendMessage("Waila", "register", "me.exz.omniocular.waila.TileEntityHandler.callbackRegister");
    }
}
