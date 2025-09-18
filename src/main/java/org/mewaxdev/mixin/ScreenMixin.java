package org.mewaxdev.mixin;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.recipe.book.RecipeBook;
import org.mewaxdev.environment.RecipeBookHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public abstract class ScreenMixin {
	@Inject(
			method = "addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;",
			at = @At("HEAD"),
			cancellable = true
	)
	private <T extends Element & Drawable & Selectable> void addDrawableChild(T drawableElement, CallbackInfoReturnable<T> cir) {
		if (RecipeBookHelper.isRecipeButton((Screen)(Object)this, drawableElement)) {
			cir.setReturnValue(drawableElement);
		}
	}
}
