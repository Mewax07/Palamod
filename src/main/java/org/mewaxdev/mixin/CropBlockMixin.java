package org.mewaxdev.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import org.mewaxdev.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {
	@Inject(method = "randomTick", at = @At("HEAD"))
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
		CropBlock crop = (CropBlock) (Object) this;

		if (world.getBaseLightLevel(pos, 0) >= 9) {
			int age = crop.getAge(state);
			if (age < crop.getMaxAge()) {
				BlockState floor = world.getBlockState(pos.down());

				float moisture = 7.0f;
				if (floor.isOf(ModBlocks.FERTILIZED_DIRT)) {
					moisture *= 2.0f;
				}

				if (random.nextInt((int) (10.0F / moisture) + 1) == 0) {
					world.setBlockState(pos, crop.withAge(age + 1), Block.NOTIFY_LISTENERS);
				}
			}
		}
	}

	@Inject(method = "getAvailableMoisture", at = @At("RETURN"), cancellable = true)
	private static void boostMoisture(Block block, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		BlockState below = world.getBlockState(pos.down());

		if (below.isOf(ModBlocks.FERTILIZED_DIRT)) {
			float base = cir.getReturnValue();
			cir.setReturnValue(base * 2.0f);
		}
	}

	@Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
	protected void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(floor.isOf(Blocks.FARMLAND) || floor.isOf(ModBlocks.FERTILIZED_DIRT));
	}
}
