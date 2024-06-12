package me.exz.omniocular;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@LateMixin
public class LateMixinPlugin implements ILateMixinLoader {
    @Override
    public String getMixinConfig() {
        return "mixins.OmniOcularUnofficial.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        return Collections.singletonList("MixinHUDHandlerFMP");
    }
}
