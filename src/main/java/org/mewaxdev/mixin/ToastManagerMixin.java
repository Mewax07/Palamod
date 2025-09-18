package org.mewaxdev.mixin;

import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToastManager.class)
public abstract class ToastManagerMixin {
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void add(Toast toast, CallbackInfo ci) {
        if (toast instanceof RecipeToast) {
            ci.cancel();
        }
    }
}
