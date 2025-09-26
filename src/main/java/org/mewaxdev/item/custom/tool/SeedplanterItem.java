package org.mewaxdev.item.custom.tool;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.mewaxdev.block.ModBlocks;

import java.util.List;

public class SeedplanterItem extends Item {
	private final int range;
	private final int requiredLevel;
	private final List<Item> seeds;

	public SeedplanterItem(Settings settings, int range, int requiredLevel, List<Item> seeds) {
		super(settings);
		this.range = range;
		this.requiredLevel = requiredLevel;
		this.seeds = seeds;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		ItemStack stack = context.getStack();
		PlayerEntity player = context.getPlayer();

		int start = -range / 2;
		int end = range / 2;

		for (int x = start; x <= end; x++) {
			for (int z = start; z <= end; z++) {
				BlockPos targetPos = pos.add(x, 0, z);
				Block floor = world.getBlockState(targetPos.down()).getBlock();

				if (floor == Blocks.FARMLAND || floor == ModBlocks.FERTILIZED_DIRT) {
					for (Item seedItem : seeds) {
						if (tryPlantSeed(seedItem, world, player, targetPos)) {
							break;
						}
					}
				}
			}
		}

		return ActionResult.SUCCESS;
	}

	private boolean tryPlantSeed(Item seedItem, World world, PlayerEntity player, BlockPos pos) {
		if (!(seedItem instanceof BlockItem blockItem)) return false;

		if (!world.isAir(pos.up())) return false;

		ItemStack seedStack = findSeedInInventory(player, seedItem);
		if (seedStack == null || seedStack.isEmpty()) return false;

		ItemUsageContext ctx = new ItemUsageContext(player, Hand.MAIN_HAND,
				new BlockHitResult(Vec3d.ofCenter(pos), Direction.UP, pos, false));
		ActionResult result = blockItem.useOnBlock(ctx);

		if (result.isAccepted()) {
			seedStack.decrement(1);
			return true;
		}
		return false;
	}

	private ItemStack findSeedInInventory(PlayerEntity player, Item seedItem) {
		for (int i = 0; i < player.getInventory().size(); i++) {
			ItemStack stack = player.getInventory().getStack(i);
			if (stack.isOf(seedItem)) {
				return stack;
			}
		}
		return null;
	}
}
