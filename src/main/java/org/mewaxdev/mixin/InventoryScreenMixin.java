package org.mewaxdev.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.mewaxdev.ui.DrawShapeModern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends HandledScreen<PlayerScreenHandler> {

	@Shadow
	private float mouseX;
	@Shadow
	private float mouseY;

	@Shadow
	public static void drawEntity(DrawContext context, int x1, int y1, int x2, int y2, int size, float f, float mouseX, float mouseY, LivingEntity entity) {
	}

	@Unique
	private static final int PANEL_COLOR = 0xFF1E1D23;
	@Unique
	private static final int BORDER_COLOR = 0xFF40404D;

	public InventoryScreenMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
	private void onDrawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		ci.cancel();

		MatrixStack matrices = new MatrixStack();
		DrawShapeModern ds = DrawShapeModern.getInstance();

		int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
		int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

		int mainWidth = 210;
		int mainHeight = 180;
		int mainX = (screenWidth - mainWidth) / 2;
		int mainY = (screenHeight - mainHeight) / 2;

		drawRoundedPanel(matrices, ds, mainX, mainY, mainWidth, mainHeight, PANEL_COLOR, BORDER_COLOR);
		drawMainInventory(matrices, ds, mainX + 20, mainY + 230);
		renderSlots(matrices, ds);

		int i = this.x;
		int j = this.y;
		drawEntity(context, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.mouseX, this.mouseY, this.client.player);
	}

	@Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
	private void removeAllText(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
		ci.cancel();
	}

	@Unique
	private void drawRoundedPanel(MatrixStack matrices, DrawShapeModern ds, int x, int y, int width, int height, int fillColor, int borderColor) {
		int radius = 8;

		ds.drawRect(matrices, x, y, width, height, fillColor);

		ds.drawRect(matrices, x - radius, y - (double) radius / 2, radius, height + radius, fillColor);
		ds.drawRect(matrices, x + width, y - (double) radius / 2, radius, height + radius, fillColor);
		ds.drawRect(matrices, x - (double) radius / 2, y - radius, width + radius, radius, fillColor);
		ds.drawRect(matrices, x - (double) radius / 2, y + height, width + radius, radius, fillColor);

		ds.drawRect(matrices, x - 2, y, 2, height, borderColor);
		ds.drawRect(matrices, x + width, y, 2, height, borderColor);
		ds.drawRect(matrices, x, y - 2, width, 2, borderColor);
		ds.drawRect(matrices, x, y + height, width, 2, borderColor);
	}

	@Unique
	private void drawMainInventory(MatrixStack matrices, DrawShapeModern ds, int x, int y) {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 9; col++) {
				int slotX = x + col * 25;
				int slotY = y + row * 25;
				ds.drawRect(matrices, slotX, slotY, 22, 22, PANEL_COLOR);
				ds.drawRect(matrices, slotX, slotY, 22, 1, BORDER_COLOR);
				ds.drawRect(matrices, slotX, slotY, 1, 22, BORDER_COLOR);
				ds.drawRect(matrices, slotX + 21, slotY, 1, 22, BORDER_COLOR);
				ds.drawRect(matrices, slotX, slotY + 21, 22, 1, BORDER_COLOR);
			}
		}
	}

	@Unique
	private void renderSlots(MatrixStack matrices, DrawShapeModern ds) {
		for (Slot slot : this.handler.slots) {
			int x = this.x + slot.x - 1;
			int y = this.y + slot.y - 1;

			ds.drawRect(matrices, x, y, 18, 18, PANEL_COLOR);
			ds.drawRect(matrices, x, y, 18, 1, BORDER_COLOR);
			ds.drawRect(matrices, x, y, 1, 18, BORDER_COLOR);
			ds.drawRect(matrices, x + 17, y, 1, 18, BORDER_COLOR);
			ds.drawRect(matrices, x, y + 17, 18, 1, BORDER_COLOR);
		}
	}
}