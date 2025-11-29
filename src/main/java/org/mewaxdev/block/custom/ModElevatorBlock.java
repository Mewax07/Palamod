package org.mewaxdev.block.custom;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.mewaxdev.block.ModBlocks;

public class ModElevatorBlock extends Block {
	public ModElevatorBlock() {
		super(AbstractBlock.Settings.create()
				.strength(1f, 10f)
				.requiresTool()
		);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		if (!world.isClient && entity.isPlayer()) {
			PlayerEntity player = (PlayerEntity) entity;

			if (!player.isOnGround() && !player.isSneaking()) {
				BlockPos targetBlock = findBlockAbove(world, pos);
				if (targetBlock != null) {
					teleportPlayer((ServerWorld) world, player, targetBlock.up());
				}
			} else if (player.isSneaking()) {
				BlockPos targetBlock = findBlockBelow(world, pos);
				if (targetBlock != null) {
					teleportPlayer((ServerWorld) world, player, targetBlock.up());
				}
			}
		}

		super.onSteppedOn(world, pos, state, entity);
	}

	private BlockPos findBlockAbove(World world, BlockPos startPos) {
		for (int i = 1; i <= 7; i++) {
			BlockPos checkPos = startPos.up(i);
			if (world.getBlockState(checkPos).isSolidBlock(world, checkPos)) {
				if (world.getBlockState(checkPos).isOf(ModBlocks.ELEVATOR_BLOCK)) {
					return checkPos;
				}
			}
		}
		return null;
	}

	private BlockPos findBlockBelow(World world, BlockPos startPos) {
		for (int i = 1; i <= 7; i++) {
			BlockPos checkPos = startPos.down(i);
			if (world.getBlockState(checkPos).isSolidBlock(world, checkPos)) {
				if (world.getBlockState(checkPos).isOf(ModBlocks.ELEVATOR_BLOCK)) {
					return checkPos;
				}
			}
		}
		return null;
	}

	private void teleportPlayer(ServerWorld world, PlayerEntity player, BlockPos targetPos) {
		Vec3d position = new Vec3d(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

		TeleportTarget target = new TeleportTarget(world, position, player.getVelocity(), player.getYaw(), player.getPitch(), TeleportTarget.NO_OP);
		player.teleportTo(target);
		player.fallDistance = 0;
	}
}
