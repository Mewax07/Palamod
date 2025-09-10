package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(ModBlocks.PALADIUM_BLOCK)
				.add(ModBlocks.PALADIUM_BLOCK_STAIRS)
				.add(ModBlocks.PALADIUM_BLOCK_SLAB)

				.add(ModBlocks.RAW_PALADIUM_BLOCK)

				.add(ModBlocks.PALADIUM_ORE)
				.add(ModBlocks.DEEPSLATE_PALADIUM_ORE)
		;

		getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
				.add(ModBlocks.PALADIUM_BLOCK)
				.add(ModBlocks.PALADIUM_BLOCK_STAIRS)
				.add(ModBlocks.PALADIUM_BLOCK_SLAB)

				.add(ModBlocks.RAW_PALADIUM_BLOCK)

				.add(ModBlocks.PALADIUM_ORE)
				.add(ModBlocks.DEEPSLATE_PALADIUM_ORE)
		;

		getOrCreateTagBuilder(ModTags.Blocks.NEEDS_PALADIUM_TOOL)
				.addTag(BlockTags.NEEDS_IRON_TOOL)
		;
	}
}
