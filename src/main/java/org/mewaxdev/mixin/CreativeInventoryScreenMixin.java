package org.mewaxdev.mixin;

import net.fabricmc.fabric.api.client.itemgroup.v1.FabricCreativeInventoryScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.mewaxdev.ui.DrawShapeModern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> implements FabricCreativeInventoryScreen {
	@Unique
	private static final int PANEL_COLOR = 0xFF1E1D23;
	@Unique
	private static final int BORDER_COLOR = 0xFF40404D;

	@Shadow
	private static ItemGroup selectedTab;

	@Shadow
	protected abstract int getTabY(ItemGroup group);

	@Shadow
	protected abstract int getTabX(ItemGroup group);

	@Shadow
	private TextFieldWidget searchBox;

	public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
	public void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
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

		this.searchBox.render(context, mouseX, mouseY, delta);

		for (ItemGroup itemGroup : getItemGroupsOnPage(getCurrentPage())) {
			renderTabIcon(context, itemGroup);
		}

		if (selectedTab.getType() == ItemGroup.Type.INVENTORY) {
			assert this.client != null;
			assert this.client.player != null;
			InventoryScreen.drawEntity(context, this.x + 73, this.y + 6, this.x + 105, this.y + 49, 20, 0.0625F, mouseX, mouseY, this.client.player);
		}
	}

	@Unique
	protected void renderTabIcon(DrawContext context, ItemGroup group) {
		MatrixStack matrices = context.getMatrices();
		DrawShapeModern ds = DrawShapeModern.getInstance();

		boolean selected = group == selectedTab;
		boolean topRow = group.getRow() == ItemGroup.Row.TOP;

		int j = this.x + getTabX(group);
		int k = this.y + getTabY(group);

		int fillColor = selected ? 0xFF2A2930 : 0xFF1E1D23;
		int borderColor = selected ? 0xFF5A5A70 : BORDER_COLOR;

		ds.drawRect(matrices, j + 1, k + 1, 24, 30, fillColor);

		ds.drawRect(matrices, j + 1, k, 24, 1, borderColor);
		ds.drawRect(matrices, j + 1, k + 31, 24, 1, borderColor);
		ds.drawRect(matrices, j, k + 1, 1, 30, borderColor);
		ds.drawRect(matrices, j + 25, k + 1, 1, 30, borderColor);

		ItemStack itemStack = group.getIcon();
		int iconX = j + 5;
		int iconY = k + (topRow ? 9 : 7);

		context.drawItem(itemStack, iconX, iconY);
		context.drawItemInSlot(this.textRenderer, itemStack, iconX, iconY);
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
