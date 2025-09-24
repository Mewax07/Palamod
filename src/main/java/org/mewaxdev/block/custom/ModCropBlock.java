package org.mewaxdev.block.custom;

import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.mewaxdev.block.ModBlocks;

public class ModCropBlock extends CropBlock {
	public static final int MAX_AGE = 4;
	public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

	public ModCropBlock(Settings settings) {
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, net.minecraft.world.BlockView world, BlockPos pos) {
		return floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.FERTILIZED_DIRT);
	}

	@Override
	public IntProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}

