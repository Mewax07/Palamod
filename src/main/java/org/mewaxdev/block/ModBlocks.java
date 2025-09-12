package org.mewaxdev.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.mewaxdev.Palamod;
import org.mewaxdev.block.custom.PaladiumCropBlock;

import java.util.HashMap;
import java.util.Map;

public class ModBlocks {
	private static final Map<String, Block> REGISTERED_BLOCKS = new HashMap<>();

	public static final Block PALADIUM_BLOCK = registerBlock("paladium_block",
			new Block(AbstractBlock.Settings.create()
					.strength(4f)
					.requiresTool()
					.sounds(BlockSoundGroup.METAL)
			));
	public static final Block PALADIUM_BLOCK_STAIRS = registerBlock("paladium_block_stairs",
			new StairsBlock(ModBlocks.PALADIUM_BLOCK.getDefaultState(),
					AbstractBlock.Settings.create()
							.strength(2f)
							.requiresTool()
			));
	public static final Block PALADIUM_BLOCK_SLAB = registerBlock("paladium_block_slab",
			new SlabBlock(AbstractBlock.Settings.create()
					.strength(2f)
					.requiresTool()
			));

	public static final Block RAW_PALADIUM_BLOCK = registerBlock("raw_paladium_block",
			new Block(AbstractBlock.Settings.create()
					.strength(2.8f)
					.requiresTool()
					.sounds(BlockSoundGroup.STONE)
			));

	public static final Block PALADIUM_ORE = registerBlock("paladium_ore",
			new ExperienceDroppingBlock(UniformIntProvider.create(2, 5),
					AbstractBlock.Settings.create()
							.strength(3f)
							.requiresTool()
							.sounds(BlockSoundGroup.STONE)
			));
	public static final Block DEEPSLATE_PALADIUM_ORE = registerBlock("deepslate_paladium_ore",
			new ExperienceDroppingBlock(UniformIntProvider.create(3, 6),
					AbstractBlock.Settings.create()
							.strength(4f)
							.requiresTool()
							.sounds(BlockSoundGroup.DEEPSLATE)
			));

	public static final Block PALADIUM_CROP = registerBlockWithoutBlockItem("paladium_crop",
			new PaladiumCropBlock(AbstractBlock.Settings.create().noCollision()
					.ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)
					.pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.GREEN)
			));

	private static Block registerBlockWithoutBlockItem(String name, Block block) {
		Block registered = Registry.register(Registries.BLOCK, Identifier.of(Palamod.MOD_ID, name), block);
		REGISTERED_BLOCKS.put(name, registered);
		return registered;
	}

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		Block registered = Registry.register(Registries.BLOCK, Identifier.of(Palamod.MOD_ID, name), block);
		REGISTERED_BLOCKS.put(name, registered);
		return registered;
	}

	private static void registerBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, Identifier.of(Palamod.MOD_ID, name), new BlockItem(block, new Item.Settings()));
	}

	public static void registerModBlocks() {
		Palamod.LOGGER.info("Registering Palamod Blocks.");
	}

	public static boolean hasBlock(String name) {
		return REGISTERED_BLOCKS.containsKey(name);
	}

	public static Block getBlock(String name) {
		return REGISTERED_BLOCKS.get(name);
	}
}
