package org.mewaxdev.mixin;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.client.texture.Sprite;
import org.mewaxdev.ui.DrawShapeModern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends ScreenHandler> extends HandledScreen<T> {
	@Unique
	private static final int PANEL_COLOR = 0xFF1E1D23;
	@Unique
	private static final int BORDER_COLOR = 0xFF40404D;

	protected AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
	private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
		ci.cancel();

		int startX = this.x - 164;
		int widthRemaining = this.width - startX;

		Collection<StatusEffectInstance> effects = this.client.player.getStatusEffects();
		if (effects.isEmpty() || widthRemaining < 32) return;

		boolean wide = widthRemaining >= 120;
		int step = effects.size() > 5 ? 132 / (effects.size() - 1) : 37;

		Iterable<StatusEffectInstance> ordered = Ordering.natural().sortedCopy(effects);
		MatrixStack matrices = context.getMatrices();
		DrawShapeModern ds = DrawShapeModern.getInstance();

		int y = this.y;
		for (StatusEffectInstance effect : ordered) {
			int panelWidth = wide ? 120 : 32;

			drawRoundedPanel(matrices, ds, startX, y, panelWidth, 22, PANEL_COLOR);

			RegistryEntry<StatusEffect> registryEntry = effect.getEffectType();
			Sprite sprite = this.client.getStatusEffectSpriteManager().getSprite(registryEntry);
			context.drawSprite(startX + (wide ? 6 : 7), y + 2, 1, 18, 18, sprite);

			if (wide) {
				Text name = effect.getEffectType().value().getName();
				if (effect.getAmplifier() >= 1 && effect.getAmplifier() <= 9) {
					name = Text.translatable(name.getString() + " " + (effect.getAmplifier() + 1));
				}
				Text duration = StatusEffectUtil.getDurationText(effect, 1.0F, this.client.world.getTickManager().getTickRate());
				context.drawTextWithShadow(this.textRenderer, name, startX + 26, y + 4, 0xFFFFFF);
				context.drawTextWithShadow(this.textRenderer, duration, startX + 26, y + 12, 0x7F7F7F);
			}

			if (mouseX >= startX && mouseX <= startX + panelWidth && mouseY >= y && mouseY <= y + step) {
				List<Text> tooltip = List.of(
						effect.getEffectType().value().getName(),
						StatusEffectUtil.getDurationText(effect, 1.0F, this.client.world.getTickManager().getTickRate())
				);
				context.drawTooltip(this.textRenderer, tooltip, Optional.empty(), mouseX, mouseY);
			}

			drawRoundedBorder(matrices, ds, startX, y, panelWidth, 22, BORDER_COLOR);

			y += step;
		}
	}

	@Unique
	private void drawRoundedPanel(MatrixStack matrices, DrawShapeModern ds, int x, int y, int width, int height, int fillColor) {
		int radius = 6;

		ds.drawRect(matrices, x, y, width, height, fillColor);
		ds.drawRect(matrices, x - radius, y - radius / 2.0, radius, height + radius, fillColor);
		ds.drawRect(matrices, x + width, y - radius / 2.0, radius, height + radius, fillColor);
		ds.drawRect(matrices, x - radius / 2.0, y - radius, width + radius, radius, fillColor);
		ds.drawRect(matrices, x - radius / 2.0, y + height, width + radius, radius, fillColor);
	}

	@Unique
	private void drawRoundedBorder(MatrixStack matrices, DrawShapeModern ds, int x, int y, int width, int height, int borderColor) {
		ds.drawRect(matrices, x - 2, y, 2, height, borderColor);
		ds.drawRect(matrices, x + width, y, 2, height, borderColor);
		ds.drawRect(matrices, x, y - 2, width, 2, borderColor);
		ds.drawRect(matrices, x, y + height, width, 2, borderColor);
	}
}
