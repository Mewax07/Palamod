package org.mewaxdev.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.mewaxdev.ui.DrawShapeModern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin extends HandledScreen<CraftingScreenHandler> {
	@Shadow
	private boolean narrow;

	@Shadow
	private final RecipeBookWidget recipeBook = new RecipeBookWidget();

	@Unique
	private static final int PANEL_COLOR = 0xFF1E1D23;
	@Unique
	private static final int BORDER_COLOR = 0xFF40404D;

	public CraftingScreenMixin(CraftingScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "init", at = @At("HEAD"), cancellable = true)
	private void onInit(CallbackInfo ci) {
		ci.cancel();
		super.init();
		this.narrow = this.width < 379;
		this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
		this.x = (this.width - this.backgroundWidth) / 2;
		this.titleX = 29;
	}

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		ci.cancel();

		super.render(context, mouseX, mouseY, delta);

		this.drawMouseoverTooltip(context, mouseX, mouseY);
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
		renderSlots(matrices, ds);
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
