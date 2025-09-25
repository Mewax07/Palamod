package org.mewaxdev.item.custom.tool;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.mewaxdev.block.ModBlocks;

import java.util.List;

public class SeedplanterItem extends Item {
    private final int range;
	private final int requiredLevel;
	private final List<Block> seeds;

	public SeedplanterItem(Settings settings, int range, int requiredLevel, List<Block> seeds) {
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

		int start = -range / 2;
        int end = range / 2;

        for (int x = start; x <= end; x++) {
            for (int z = start; z <= end; z++) {
                BlockPos targetPos = pos.add(x, 0, z);
                Block floor = world.getBlockState(targetPos.down()).getBlock();

				if(floor == Blocks.FARMLAND || floor == ModBlocks.FERTILIZED_DIRT) {
					for(Block seed : seeds) {
                        if(canPlant(seed, world, targetPos)) {
                            world.setBlockState(targetPos.up(), seed.getDefaultState());
                            break;
                        }
                    }
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    private boolean canPlant(Block seed, World world, BlockPos pos) {
        return world.isAir(pos.up());
    }
}
