package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;
import org.mewaxdev.item.ModItems;
import org.mewaxdev.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS)
				.add(ModItems.PALADIUM_INGOT)
				.add(ModItems.RAW_PALADIUM)

				.add(ModItems.PALADIUM_CHISEL)
		;

		getOrCreateTagBuilder(ItemTags.SWORDS)
				.add(ModItems.PALADIUM_SWORD)
		;
		getOrCreateTagBuilder(ItemTags.PICKAXES)
				.add(ModItems.PALADIUM_PICKAXE)
		;
		getOrCreateTagBuilder(ItemTags.AXES)
				.add(ModItems.PALADIUM_AXE)
		;
		getOrCreateTagBuilder(ItemTags.SHOVELS)
				.add(ModItems.PALADIUM_SHOVEL)
		;
		getOrCreateTagBuilder(ItemTags.HOES)
				.add(ModItems.PALADIUM_HOE)
		;
	}
}
