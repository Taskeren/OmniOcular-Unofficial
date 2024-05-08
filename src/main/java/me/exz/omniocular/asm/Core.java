package me.exz.omniocular.asm;

import java.io.File;
import java.util.Map;

import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.7.10")
public class Core implements IFMLLoadingPlugin {

    public static File minecraftDir;

    public Core() {
        if (minecraftDir != null) return; // get called twice, once for IFMLCallHook

        minecraftDir = (File) FMLInjectionData.data()[6];
    }

    @Override
    public String[] getASMTransformerClass() {
        // LogHelper.info("getASMTransformerClass");
        return new String[] { Transformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return CoreContainer.class.getName();
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
