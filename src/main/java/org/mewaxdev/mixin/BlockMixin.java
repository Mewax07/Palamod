package org.mewaxdev.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.mewaxdev.enchantment.ModEnchantmentEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(
			method = "getDroppedStacks",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void smeltDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack tool, CallbackInfoReturnable<List<ItemStack>> cir) {
		List<ItemStack> originalDrops = cir.getReturnValue();
		if (originalDrops == null || originalDrops.isEmpty()) return;

		if (!EnchantmentHelper.hasAnyEnchantmentsWith(tool, ModEnchantmentEffects.SMELT_DROPS)) {
			return;
		}

		List<ItemStack> newDrops = new ArrayList<>();
		for (ItemStack drop : originalDrops) {
			SingleStackRecipeInput input = new SingleStackRecipeInput(drop.copy());

			Optional<RecipeEntry<SmeltingRecipe>> recipe =
					world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, input, world);

			if (recipe.isPresent()) {
				ItemStack smelted = recipe.get().value().getResult(world.getRegistryManager()).copy();
				if (!smelted.isEmpty()) {
					smelted.setCount(drop.getCount() * smelted.getCount());
					newDrops.add(smelted);
					continue;
				}
			}
			newDrops.add(drop);
		}

		cir.setReturnValue(newDrops);
	}
}