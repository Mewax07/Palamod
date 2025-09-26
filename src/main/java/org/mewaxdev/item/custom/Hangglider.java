package org.mewaxdev.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.mewaxdev.component.ModDataComponentTypes;

public class Hangglider extends Item {
	public Hangglider(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			boolean active = isActive(stack);
			setActive(stack, !active);
		}

		return new TypedActionResult<>(ActionResult.SUCCESS, stack);
	}

	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!(entity instanceof PlayerEntity player)) return;

		if (isActive(stack)) {
			boolean touchedBlock = player.isOnGround() || player.horizontalCollision;
			if (touchedBlock) {
				if (!world.isClient) {
					setActive(stack, false);
				}
				return;
			}
		}

		if (entity instanceof PlayerEntity && isActive(stack) && selected && !player.isOnGround() && !player.isFallFlying()) {
			if (isActive(stack) && selected && !player.isOnGround() && !player.isFallFlying()) {
				if (player.getVelocity().y < -0.05) {
					player.setVelocity(player.getVelocity().x, -0.05, player.getVelocity().z);
				}
				player.addVelocity(player.getRotationVector().x * 0.02, 0, player.getRotationVector().z * 0.02);
				player.fallDistance = -70;
			}
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return isActive(stack);
	}

	private static boolean isActive(ItemStack stack) {
		Boolean b = stack.get(ModDataComponentTypes.HANGGLIDER_ACTIVE);
		return b != null && b;
	}

	private static void setActive(ItemStack stack, boolean active) {
		stack.set(ModDataComponentTypes.HANGGLIDER_ACTIVE, active);
	}
}
