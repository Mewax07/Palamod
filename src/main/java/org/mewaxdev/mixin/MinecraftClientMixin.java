package org.mewaxdev.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "setScreen", at = @At("TAIL"))
    private void setScreen(@Nullable Screen screen, CallbackInfo ci) {
        if (screen instanceof RecipeBookProvider provider && screen instanceof HandledScreen<?> handled) {
            RecipeBookWidget widget = provider.getRecipeBookWidget();
            HandledScreenAccessor accessor = (HandledScreenAccessor) screen;

            if (widget.isOpen()) {
                widget.reset();
                widget.toggleOpen();
                accessor.setX(widget.findLeftEdge(screen.width, accessor.getBackgroundWidth()));
            }
        }
    }
}
