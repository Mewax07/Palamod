package org.mewaxdev.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;

public class ModCropBlock extends CropBlock {
	public static final int MAX_AGE = 4;
	public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

	public ModCropBlock(Settings settings) {
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
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

