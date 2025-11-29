package org.mewaxdev.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.mewaxdev.component.ModDataComponentTypes;

public class Hangglider extends Item {
	public Hangglider(Settings settings) {
		super(settings);
	}

	private static boolean isActive(ItemStack stack) {
		Boolean b = stack.get(ModDataComponentTypes.HANGGLIDER_ACTIVE);
		return b != null && b;
	}

	private static void setActive(ItemStack stack, boolean active) {
		stack.set(ModDataComponentTypes.HANGGLIDER_ACTIVE, active);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		if (!world.isClient) {
			setActive(stack, !isActive(stack));
		}

		return new TypedActionResult<>(ActionResult.SUCCESS, stack);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (!(entity instanceof PlayerEntity player)) return;

		boolean active = isActive(stack);

		if (active && (player.isOnGround() || player.horizontalCollision)) {
			if (!world.isClient) {
				setActive(stack, false);
			}
			return;
		}

		if (!active || !selected || player.isOnGround() || player.isFallFlying()) return;

		Vec3d vel = player.getVelocity();

		if (vel.y < -0.10) {
			player.setVelocity(vel.x, -0.10, vel.z);
		}

		player.addVelocity(player.getRotationVector().x * 0.03, 0, player.getRotationVector().z * 0.03);

		player.fallDistance = 0;

		if (player.isSneaking()) {
			player.addVelocity(player.getRotationVector().x * 0.03, -0.10, player.getRotationVector().z * 0.03);
		}

		if (player.isTouchingWater()) {
			player.addVelocity(player.getRotationVector().x * 0.10, player.isSneaking() ? -0.10 : 0.02, player.getRotationVector().z * 0.10);
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return isActive(stack);
	}
}
