package org.mewaxdev.item.custom;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.mewaxdev.block.custom.ModCropBlock;

public class FertilizedDirt extends FarmlandBlock {
	public static final IntProperty MOISTURE = Properties.MOISTURE;
	public static final int MAX_MOISTURE = 7;

	public FertilizedDirt(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(MOISTURE, MAX_MOISTURE));
	}

	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockPos above = pos.up();
		BlockState cropState = world.getBlockState(above);

		if (cropState.getBlock() instanceof ModCropBlock crop) {
			int age = cropState.get(ModCropBlock.AGE);
			if (age < crop.getMaxAge()) {
				world.setBlockState(above, cropState.with(ModCropBlock.AGE, age + 1), Block.NOTIFY_LISTENERS);
			}
		}
	}
}
