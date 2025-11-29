package org.mewaxdev.item.custom.tool;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.mewaxdev.block.ModBlocks;

public class PalamodHoeItem extends HoeItem {
	private final int range;

	public PalamodHoeItem(ToolMaterial material, int range, Settings settings) {
		super(material, settings);
		this.range = range;
	}

	public int getRange() {
		return range;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();

		if (!world.isClient) {
			for (int dx = -range / 2; dx < range / 2; dx++) {
				for (int dz = -range / 2; dz < range / 2; dz++) {
					BlockPos targetPos = pos.add(dx, 0, dz);
					BlockState targetState = world.getBlockState(targetPos);

					if (targetState.isOf(Blocks.DIRT) || targetState.isOf(Blocks.GRASS_BLOCK) || targetState.isOf(Blocks.FARMLAND)) {
						world.setBlockState(targetPos, ModBlocks.FERTILIZED_DIRT.getDefaultState(), 3);
					}
				}
			}
		}

		return ActionResult.SUCCESS;
	}
}
