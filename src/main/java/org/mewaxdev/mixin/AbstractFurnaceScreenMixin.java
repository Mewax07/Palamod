package org.mewaxdev.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.mewaxdev.ui.DrawShapeModern;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFurnaceScreen.class)
public abstract class AbstractFurnaceScreenMixin<T extends AbstractFurnaceScreenHandler> extends HandledScreen<T> implements RecipeBookProvider {
	@Shadow
	private boolean narrow;

	@Shadow
	@Final
	public AbstractFurnaceRecipeBookScreen recipeBook;

	@Shadow
	@Final
	private Identifier litProgressTexture;

	@Shadow
	@Final
	private Identifier burnProgressTexture;

	@Unique
	private static final int PANEL_COLOR = 0xFF1E1D23;
	@Unique
	private static final int BORDER_COLOR = 0xFF40404D;

	public AbstractFurnaceScreenMixin(T handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Inject(method = "init", at = @At("HEAD"), cancellable = true)
	private void onInit(CallbackInfo ci) {
		ci.cancel();
		super.init();
		this.narrow = this.width < 379;
		this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, this.handler);
		this.x = (this.width - this.backgroundWidth) / 2;
		this.recipeBook.reset();
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

		int mainWidth = 190;
		int mainHeight = 180;
		int mainX = (screenWidth - mainWidth) / 2;
		int mainY = (screenHeight - mainHeight) / 2;

		drawRoundedPanel(matrices, ds, mainX, mainY, mainWidth, mainHeight, PANEL_COLOR, BORDER_COLOR);
		renderSlots(matrices, ds);

		int i = this.x;
		int j = this.y;

		if (this.handler.isBurning()) {
			int k = 14;
			int l = MathHelper.ceil(this.handler.getFuelProgress() * 13.0F) + 1;
			context.drawGuiTexture(this.litProgressTexture, 14, 14, 0, 14 - l, i + 56, j + 36 + 14 - l, 14, l);
		}

		int k = 24;
		int l = MathHelper.ceil(this.handler.getCookProgress() * 24.0F);
		context.drawGuiTexture(this.burnProgressTexture, 24, 16, 0, 0, i + 79, j + 34, l, 16);
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
