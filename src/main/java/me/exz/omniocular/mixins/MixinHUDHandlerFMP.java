package me.exz.omniocular.mixins;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.handlers.HUDHandlerFMP;
import me.exz.omniocular.waila.FMPHandler;

@Mixin(value = HUDHandlerFMP.class, remap = false)
public abstract class MixinHUDHandlerFMP {

    @Inject(
        method = "getWailaBody",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/nbt/NBTTagCompound;getString(Ljava/lang/String;)Ljava/lang/String;"))
    private void getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config, CallbackInfoReturnable<List<String>> cir, @Local NBTTagCompound nbt) {
        FMPHandler.getWailaBody(currenttip, nbt, accessor.getPlayer());
    }
}
