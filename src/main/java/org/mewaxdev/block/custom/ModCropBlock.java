package org.mewaxdev.block.custom;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.mewaxdev.block.ModBlocks;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ModCropBlock extends CropBlock {
	public static final int MAX_AGE = 4;
	public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

	public ModCropBlock(Settings settings) {
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		CropBlock crop = this;

		if (world.getBaseLightLevel(pos, 0) >= 9) {
			int age = crop.getAge(state);
			if (age < crop.getMaxAge()) {
				BlockState floor = world.getBlockState(pos.down());

				if (floor.isOf(ModBlocks.FERTILIZED_DIRT)) {
					float f = CropBlock.getAvailableMoisture(crop, world, pos);

					if (random.nextInt((int)(10.0F / f) + 1) == 0) {
						world.setBlockState(pos, crop.withAge(age + 1), Block.NOTIFY_LISTENERS);
					}
				}
			}
		}
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

