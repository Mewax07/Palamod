package org.mewaxdev.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.mewaxdev.item.ModItems;
import org.mewaxdev.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		FabricTagProvider<Item>.FabricTagBuilder builder = getOrCreateTagBuilder(ModTags.Items.TRANSFORMABLE_ITEMS);
		for (ModItems.MaterialItems mat : ModItems.getAllMaterials()) {
			if (mat.INGOT != null) builder.add(mat.INGOT);
			if (mat.RAW != null) builder.add(mat.RAW);
			if (mat.HAMMER != null) builder.add(mat.HAMMER);

			if (mat.SWORD != null) getOrCreateTagBuilder(ItemTags.SWORDS).add(mat.SWORD);
			if (mat.BROAD_SWORD != null) getOrCreateTagBuilder(ItemTags.SWORDS).add(mat.BROAD_SWORD);
			if (mat.FAST_SWORD != null) getOrCreateTagBuilder(ItemTags.SWORDS).add(mat.FAST_SWORD);
			if (mat.PICKAXE != null) getOrCreateTagBuilder(ItemTags.PICKAXES).add(mat.PICKAXE);
			if (mat.AXE != null) getOrCreateTagBuilder(ItemTags.AXES).add(mat.AXE);
			if (mat.SHOVEL != null) getOrCreateTagBuilder(ItemTags.SHOVELS).add(mat.SHOVEL);
			if (mat.HOE != null) getOrCreateTagBuilder(ItemTags.HOES).add(mat.HOE);

			if (mat.HELMET != null) getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).add(mat.HELMET);
			if (mat.CHESTPLATE != null) getOrCreateTagBuilder(ItemTags.CHEST_ARMOR).add(mat.CHESTPLATE);
			if (mat.LEGGINGS != null) getOrCreateTagBuilder(ItemTags.LEG_ARMOR).add(mat.LEGGINGS);
			if (mat.BOOTS != null) getOrCreateTagBuilder(ItemTags.FOOT_ARMOR).add(mat.BOOTS);

			if (mat.HELMET != null || mat.CHESTPLATE != null || mat.LEGGINGS != null || mat.BOOTS != null) {
				var builderArmor = getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR);
				if (mat.HELMET != null) builderArmor.add(mat.HELMET);
				if (mat.CHESTPLATE != null) builderArmor.add(mat.CHESTPLATE);
				if (mat.LEGGINGS != null) builderArmor.add(mat.LEGGINGS);
				if (mat.BOOTS != null) builderArmor.add(mat.BOOTS);
			}

			if (mat.INGOT != null) getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS).add(mat.INGOT);

			for (Item custom : mat.CUSTOM_ITEMS.values()) {
				builder.add(custom);
			}
		}

		for (Item item : ModItems.NON_CLASS_REGISTERED_ITEM.values()) {
			builder.add(item);
		}
	}
}
