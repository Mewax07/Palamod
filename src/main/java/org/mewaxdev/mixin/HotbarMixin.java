package org.mewaxdev.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.mewaxdev.PalamodClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HotbarMixin {
	private static final Identifier HOTBAR_SLOT = Identifier.of("palamod", "textures/gui/hotbar/slot.png");
	private static final Identifier HOTBAR_SLOT_SELECTED = Identifier.of("palamod", "textures/gui/hotbar/slot_selected.png");

	private static final Identifier HEART_EMPTY = Identifier.of("palamod", "textures/gui/hotbar/heart_empty.png");
	private static final Identifier HEART_FULL = Identifier.of("palamod", "textures/gui/hotbar/heart_full.png");
	private static final Identifier HEART_ABSORPTION = Identifier.of("palamod", "textures/gui/hotbar/heart_absorption.png");
	private static final Identifier HEART_POISON = Identifier.of("palamod", "textures/gui/hotbar/heart_poison.png");
	private static final Identifier HEART_WITHER = Identifier.of("palamod", "textures/gui/hotbar/heart_wither.png");
	private static final Identifier HEART_MOUNT = Identifier.of("palamod", "textures/gui/hotbar/heart_mount.png");

	private static final Identifier ARMOR_EMPTY = Identifier.of("palamod", "textures/gui/hotbar/armor_empty.png");
	private static final Identifier ARMOR_FULL = Identifier.of("palamod", "textures/gui/hotbar/armor_full.png");

	private static final Identifier FOOD_EMPTY = Identifier.of("palamod", "textures/gui/hotbar/food_empty.png");
	private static final Identifier FOOD_FULL = Identifier.of("palamod", "textures/gui/hotbar/food_full.png");
	private static final Identifier FOOD_HUNGER = Identifier.of("palamod", "textures/gui/hotbar/food_hunger.png");

	private static final Identifier BUBBLE = Identifier.of("palamod", "textures/gui/hotbar/bubble.png");
	private static final Identifier BUBBLE_EXPLODE = Identifier.of("palamod", "textures/gui/hotbar/bubble_explode.png");

	private static final Identifier HURT_OVERLAY = Identifier.of("palamod", "textures/gui/hotbar/hurt_damaged_overlay.png");

	@Shadow
	private MinecraftClient client;

	@Shadow
	private void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed) {
	}

	@Shadow
	private int getHeartRows(int heartCount) {
		return 0;
	}

	@Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
	private void renderCustomHotbar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		ci.cancel();
		drawCustomHotbar(context, tickCounter);
	}

	@Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
	private void renderCustomStatusBars(DrawContext context, CallbackInfo ci) {
		ci.cancel();
		drawCustomStatusBars(context);
	}

	@Unique
	private void drawCustomHotbar(DrawContext context, RenderTickCounter tickCounter) {
		PlayerEntity player = client.player;
		if (player == null) return;

		int centerX = client.getWindow().getScaledWidth() / 2;
		int hotbarX = centerX - 91;
		int hotbarY = client.getWindow().getScaledHeight() - 22;

		int seed = 1;

		for (int i = 0; i < 9; i++) {
			boolean selectedSlot = player.getInventory().selectedSlot == i;
			Identifier texture = selectedSlot ? HOTBAR_SLOT_SELECTED : HOTBAR_SLOT;

			int slotX = hotbarX + i * 20;
			int slotY = selectedSlot ? hotbarY - 2 : hotbarY;

			context.drawTexture(texture, slotX, slotY, 0, 0, 20, selectedSlot ? 22 : 20, 20, selectedSlot ? 22 : 20);

			ItemStack itemStack = player.getInventory().main.get(i);
			if (!itemStack.isEmpty()) {
				int itemX = slotX + 2;
				int itemY = slotY + (selectedSlot ? 3 : 2);
				this.renderHotbarItem(context, itemX, itemY, tickCounter, player, itemStack, seed++);
			}
		}

		ItemStack offhandStack = player.getOffHandStack();
		if (!offhandStack.isEmpty()) {
			Arm mainArm = player.getMainArm();
			Arm offhandArm = mainArm.getOpposite();

			int offhandX, offhandY;

			if (offhandArm == Arm.LEFT) {
				offhandX = hotbarX - 29;
				offhandY = hotbarY;

				context.drawTexture(HOTBAR_SLOT, offhandX, offhandY, 0, 0, 20, 20, 20, 20);

				int itemX = offhandX + 2;
				int itemY = offhandY + 2;
				this.renderHotbarItem(context, itemX, itemY, tickCounter, player, offhandStack, seed++);

			} else {
				offhandX = hotbarX + 9 * 20 + 9;
				offhandY = hotbarY;

				context.drawTexture(HOTBAR_SLOT, offhandX, offhandY, 0, 0, 20, 20, 20, 20);

				int itemX = offhandX + 2;
				int itemY = offhandY + 2;
				this.renderHotbarItem(context, itemX, itemY, tickCounter, player, offhandStack, seed++);
			}
		}
	}

	@Unique
	private void drawCustomStatusBars(DrawContext context) {
		PlayerEntity player = client.player;
		if (player == null) return;

		int screenWidth = client.getWindow().getScaledWidth();
		int screenHeight = client.getWindow().getScaledHeight();

		int centerX = screenWidth / 2;
		int baseY = screenHeight - 39;

		drawHealthBar(context, player, centerX, baseY);

		drawArmorBar(context, player, centerX, baseY);

		drawFoodBar(context, player, centerX, baseY);

		drawAirBar(context, player, centerX, baseY);
	}

	@Unique
	private void drawHealthBar(DrawContext context, PlayerEntity player, int centerX, int baseY) {
		int health = MathHelper.ceil(player.getHealth());
		int maxHealth = MathHelper.ceil(player.getMaxHealth());
		int absorption = MathHelper.ceil(player.getAbsorptionAmount());

		boolean isPoison = player.hasStatusEffect(StatusEffects.POISON);
		boolean isWither = player.hasStatusEffect(StatusEffects.WITHER);

		int heartCount = MathHelper.ceil((maxHealth + absorption) / 2.0F);
		int rows = getHeartRows(heartCount);

		for (int row = 0; row < rows; row++) {
			int rowY = baseY - row * 10;
			for (int i = 0; i < 10; i++) {
				int heartIndex = row * 10 + i;
				if (heartIndex >= heartCount) break;

				int heartX = centerX - 91 + i * 8;

				Identifier heartTexture = HEART_EMPTY;

				if (heartIndex * 2 + 1 < health) {
					heartTexture = HEART_FULL;
				} else if (heartIndex * 2 + 1 == health) {
					heartTexture = HEART_FULL;
				} else if (heartIndex * 2 + 1 <= maxHealth) {
					heartTexture = HEART_EMPTY;
				} else {
					heartTexture = HEART_ABSORPTION;
				}

				if (isWither) {
					heartTexture = HEART_WITHER;
				} else if (isPoison && heartTexture == HEART_FULL) {
					heartTexture = HEART_POISON;
				}

				context.drawTexture(heartTexture, heartX + 1, rowY, 0, 0, 8, 8, 8, 8);
			}
		}
	}

	@Unique
	private void drawArmorBar(DrawContext context, PlayerEntity player, int centerX, int baseY) {
		int armor = player.getArmor();
		if (armor <= 0) return;

		int armorY = baseY - 10;

		for (int i = 0; i < 10; i++) {
			int armorX = centerX - 91 + i * 8;

			Identifier armorTexture;
			if (i * 2 + 1 < armor) {
				armorTexture = ARMOR_FULL;
			} else if (i * 2 + 1 == armor) {
				armorTexture = ARMOR_FULL;
			} else {
				armorTexture = ARMOR_EMPTY;
			}

			context.drawTexture(armorTexture, armorX, armorY, 0, 0, 9, 9, 9, 9);
		}
	}

	@Unique
	private void drawFoodBar(DrawContext context, PlayerEntity player, int centerX, int baseY) {
		int food = player.getHungerManager().getFoodLevel();
		float saturation = player.getHungerManager().getSaturationLevel();
		boolean isHungry = player.getHungerManager().getSaturationLevel() <= 0.0F &&
				client.world.getTime() % (food * 3 + 1) == 0;

		int foodY = baseY;

		for (int i = 0; i < 10; i++) {
			int foodX = centerX + 82 - i * 8;

			Identifier foodTexture;
			if (i * 2 + 1 < food) {
				foodTexture = isHungry ? FOOD_HUNGER : FOOD_FULL;
			} else if (i * 2 + 1 == food) {
				foodTexture = isHungry ? FOOD_HUNGER : FOOD_FULL;
			} else {
				foodTexture = FOOD_EMPTY;
			}

			context.drawTexture(foodTexture, foodX, foodY, 0, 0, 9, 9, 9, 9);
		}
	}

	@Unique
	private void drawAirBar(DrawContext context, PlayerEntity player, int centerX, int baseY) {
		int air = player.getAir();
		int maxAir = player.getMaxAir();

		if (air >= maxAir) return;

		int airBubbles = MathHelper.ceil((double) (air - 2) * 10.0 / (double) maxAir);
		int airY = baseY - 10;

		for (int i = 0; i < 10; i++) {
			int bubbleX = centerX + 82 - i * 8;

			Identifier bubbleTexture;
			if (i < airBubbles) {
				bubbleTexture = BUBBLE;
			} else if (i == airBubbles && air > 0) {
				bubbleTexture = BUBBLE_EXPLODE;
			} else {
				continue;
			}

			context.drawTexture(bubbleTexture, bubbleX, airY, 0, 0, 9, 9, 9, 9);
		}
	}
}
