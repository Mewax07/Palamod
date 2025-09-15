package org.mewaxdev.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.mewaxdev.component.ModDataComponentTypes;

import java.util.Objects;

public class PickaxeOfGod extends MiningToolItem {
	private static final int XP_PER_BLOCK = 3;
	private static final int XP_PER_LEVEL = 100;
	private static final int MAX_LEVEL = 19;

	public PickaxeOfGod(ToolMaterial material, Item.Settings settings) {
		super(material, BlockTags.PICKAXE_MINEABLE, settings.maxDamage(999));
	}


	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && isXpBlock(state.getBlock())) {
			Integer currentXp = stack.get(ModDataComponentTypes.XP_LEVEL);
			if (currentXp == null) currentXp = 0;

			currentXp += XP_PER_BLOCK;
			stack.set(ModDataComponentTypes.XP_LEVEL, currentXp);

			int newLevel = Math.min(MAX_LEVEL, currentXp / XP_PER_LEVEL);
			stack.set(ModDataComponentTypes.LEVEL, newLevel);

			Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler()).sendChatMessage("POG break new block " + newLevel + " current xp " + currentXp);
		}

		return super.postMine(stack, world, state, pos, miner);
	}

	public static int getLevel(ItemStack stack) {
		Integer level = stack.get(ModDataComponentTypes.LEVEL);
		return level != null ? level : 0;
	}

	public static int getXp(ItemStack stack) {
		Integer xp = stack.get(ModDataComponentTypes.XP_LEVEL);
		return xp != null ? xp : 0;
	}

	public static float getXpProgress(ItemStack stack) {
		int xp = getXp(stack);
		int level = getLevel(stack);
		int xpIntoLevel = xp - (level * XP_PER_LEVEL);
		return (float) xpIntoLevel / (float) XP_PER_LEVEL;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		// no-op => pas de dégât d'usure
	}

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		return true;
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return Math.round(getXpProgress(stack) * 13);
	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		return 0x00FF00;
	}

	public static boolean isXpBlock(Block block) {
		return block == Blocks.STONE ||
				block == Blocks.DIRT ||
				block == Blocks.SAND ||
				block == Blocks.SNOW_BLOCK ||
				block == Blocks.GRAVEL ||
				block == Blocks.OAK_LOG ||
				block == Blocks.OAK_LEAVES ||
				block.getDefaultState().isIn(BlockTags.COPPER_ORES) ||
				block.getDefaultState().isIn(BlockTags.COAL_ORES) ||
				block.getDefaultState().isIn(BlockTags.IRON_ORES) ||
				block.getDefaultState().isIn(BlockTags.GOLD_ORES) ||
				block.getDefaultState().isIn(BlockTags.LAPIS_ORES) ||
				block.getDefaultState().isIn(BlockTags.REDSTONE_ORES) ||
				block.getDefaultState().isIn(BlockTags.EMERALD_ORES) ||
				block.getDefaultState().isIn(BlockTags.DIAMOND_ORES);
	}
}