package org.mewaxdev.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;
import org.mewaxdev.ui.DrawShapeModern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
	// public static final Identifier SLOT_TEXTURE = Identifier.of(Palamod.MOD_ID, "textures/gui/slot/texture.png");

	@Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
	private void onDrawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		ci.cancel();

		MatrixStack matrices = new MatrixStack();
		DrawShapeModern ds = DrawShapeModern.getInstance();

		int panelColor = 0xFF1E1D23;
		int borderColor = 0xFF40404D;

		int w = 176, h = 166;
		int cx = (MinecraftClient.getInstance().getWindow().getScaledWidth() - w) / 2;
		int cy = (MinecraftClient.getInstance().getWindow().getScaledHeight() - h) / 2;

		double outerRadius = 8;
		double innerRadius = 3;
		double outerInnerRadius = innerRadius * 1.5;

		ds.drawRect(matrices, cx, cy, w, h, panelColor);

		ds.drawRect(matrices, cx - outerRadius, cy - outerInnerRadius, outerRadius, h + outerInnerRadius * 2, panelColor);
		ds.drawRect(matrices, cx + w, cy - outerInnerRadius, outerRadius, h + outerInnerRadius * 2, panelColor);
		ds.drawRect(matrices, cx - outerInnerRadius, cy - outerRadius, w + outerInnerRadius * 2, outerRadius, panelColor);
		ds.drawRect(matrices, cx - outerInnerRadius, cy + h, w + outerInnerRadius * 2, outerRadius, panelColor);

		ds.drawRect(matrices, cx - innerRadius, cy, innerRadius, h, borderColor);
		ds.drawRect(matrices, cx + w, cy, innerRadius, h, borderColor);
		ds.drawRect(matrices, cx, cy - innerRadius, w, innerRadius, borderColor);
		ds.drawRect(matrices, cx, cy + h, w, innerRadius, borderColor);
	}
}
