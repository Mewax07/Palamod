package org.mewaxdev.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

public record SmeltingEnchantmentEffect() implements EnchantmentEntityEffect {
	public static final MapCodec<SmeltingEnchantmentEffect> CODEC = MapCodec.unit(SmeltingEnchantmentEffect::new);

	@Override
	public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
		if (!(user instanceof net.minecraft.entity.player.PlayerEntity player)) return;

		BlockPos blockPos = BlockPos.ofFloored(pos);
		Block block = world.getBlockState(blockPos).getBlock();

		ItemStack dropped = new ItemStack(block.asItem());
		if (dropped.isEmpty()) return;

		DynamicRegistryManager registryManager = world.getRegistryManager();

		Optional<RecipeEntry<SmeltingRecipe>> recipe = world.getRecipeManager()
				.listAllOfType(RecipeType.SMELTING)
				.stream()
				.filter(r -> !r.value().getIngredients().isEmpty()
						&& r.value().getIngredients().get(0).test(dropped))
				.findFirst();

		recipe.ifPresent(smeltingRecipe -> {
			ItemStack result = smeltingRecipe.value().getResult(registryManager).copy();
			if (!result.isEmpty()) {
				block.onBreak(world, blockPos, world.getBlockState(blockPos), player);
				world.breakBlock(blockPos, false);
				Block.dropStack(world, blockPos, result);
			}
		});
	}

	@Override
	public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
		return CODEC;
	}
}
