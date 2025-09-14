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
		for (ModBlocks.MaterialBlocks mat : ModBlocks.getAllMaterials()) {
			if (mat.BLOCK != null) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(mat.BLOCK);
			if (mat.STAIRS != null) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(mat.STAIRS);
			if (mat.SLAB != null) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(mat.SLAB);
			if (mat.RAW_BLOCK != null) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(mat.RAW_BLOCK);
			if (mat.ORE != null) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(mat.ORE);
			if (mat.DEEPSLATE_ORE != null) getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(mat.DEEPSLATE_ORE);

			if (mat.BLOCK != null) getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(mat.BLOCK);
			if (mat.STAIRS != null) getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(mat.STAIRS);
			if (mat.SLAB != null) getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(mat.SLAB);
			if (mat.RAW_BLOCK != null) getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(mat.RAW_BLOCK);
			if (mat.ORE != null) getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(mat.ORE);
			if (mat.DEEPSLATE_ORE != null) getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(mat.DEEPSLATE_ORE);
		}

		getOrCreateTagBuilder(ModTags.Blocks.NEEDS_PALADIUM_TOOL)
				.addTag(BlockTags.NEEDS_IRON_TOOL);
	}
}
