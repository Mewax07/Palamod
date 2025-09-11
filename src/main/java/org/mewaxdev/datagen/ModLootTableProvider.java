package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import org.mewaxdev.block.ModBlocks;
import org.mewaxdev.block.custom.PaladiumCropBlock;
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

		BlockStatePropertyLootCondition.Builder builder2 = BlockStatePropertyLootCondition.builder(ModBlocks.PALADIUM_CROP)
				.properties(StatePredicate.Builder.create().exactMatch(PaladiumCropBlock.AGE, PaladiumCropBlock.MAX_AGE));
		this.addDrop(ModBlocks.PALADIUM_CROP, this.cropDrops(ModBlocks.PALADIUM_CROP, ModItems.PALA_FLOWER, ModItems.PALADIUM_SEEDS, builder2));
	}
}
