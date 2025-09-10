package org.mewaxdev.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.mewaxdev.Palamod;

public class ModTags {
	public static class Blocks {
		public static final TagKey<Block> NEEDS_PALADIUM_TOOL = createTag("needs_paladium_tool");
		public static final TagKey<Block> INCORRECT_FOR_PALADIUM_TOOL = createTag("incorrect_for_paladium_tool");

		private static TagKey<Block> createTag(String name) {
			return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Palamod.MOD_ID, name));
		}
	}

	public static class Items {
		public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");

		private static TagKey<Item> createTag(String name) {
			return TagKey.of(RegistryKeys.ITEM, Identifier.of(Palamod.MOD_ID, name));
		}
	}
}
