package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
	public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		addDrop(ModBlocks.PALADIUM_BLOCK);
		addDrop(ModBlocks.PALADIUM_BLOCK_STAIRS);
		addDrop(ModBlocks.PALADIUM_BLOCK_SLAB, slabDrops(ModBlocks.PALADIUM_BLOCK_SLAB));

		addDrop(ModBlocks.RAW_PALADIUM_BLOCK);

		addDrop(ModBlocks.PALADIUM_ORE, oreDrops(ModBlocks.PALADIUM_ORE, ModItems.RAW_PALADIUM));
		addDrop(ModBlocks.DEEPSLATE_PALADIUM_ORE, oreDrops(ModBlocks.PALADIUM_ORE, ModItems.RAW_PALADIUM));
	}
}
