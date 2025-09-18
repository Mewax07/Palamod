package org.mewaxdev.environment;

import net.minecraft.client.gui.Element;
import net.minecraft.util.Identifier;
import org.mewaxdev.mixin.TexturedButtonWidgetAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Selectable;

@Environment(EnvType.CLIENT)
public class RecipeBookHelper {
    private static final String RECIPE_BUTTON_TEXTURE_PATH = "recipe_book/button";

	public static <T extends Element & Drawable & Selectable> boolean isRecipeButton(Screen screen, T drawableElement) {
		if (screen instanceof HandledScreen && drawableElement instanceof TexturedButtonWidget) {
			var textures = ((TexturedButtonWidgetAccessor) drawableElement).getTextures();
			Identifier id = textures.get(true, false);
			return id.getPath().equals(RECIPE_BUTTON_TEXTURE_PATH);
		}
		return false;
	}
}
