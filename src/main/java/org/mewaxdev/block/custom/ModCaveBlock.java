package org.mewaxdev.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ModCaveBlock extends Block {
	public ModCaveBlock(Settings settings) {
		super(settings);
	}

	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return 15;
	}

	@Override
	protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0F;
	}

	@Override
	protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}
}
