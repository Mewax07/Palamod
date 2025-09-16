package org.mewaxdev.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.mewaxdev.component.ModDataComponentTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PickaxeOfGod extends MiningToolItem {
	private static final int[] LEVEL_XP = new int[101];
	private static final int XP_PER_BLOCK = 3;

	static {
		for (int i = 1; i <= 100; i++) {
			if (i <= 5) LEVEL_XP[i] = 100;
			else if (i <= 10) LEVEL_XP[i] = 150;
			else if (i <= 15) LEVEL_XP[i] = 200;
			else if (i <= 20) LEVEL_XP[i] = 300;
			else if (i <= 25) LEVEL_XP[i] = 400;
			else if (i <= 30) LEVEL_XP[i] = 500;
			else if (i <= 35) LEVEL_XP[i] = 600;
			else if (i <= 40) LEVEL_XP[i] = 700;
			else if (i <= 45) LEVEL_XP[i] = 800;
			else if (i <= 50) LEVEL_XP[i] = 1000;
			else if (i <= 55) LEVEL_XP[i] = 1200;
			else if (i <= 60) LEVEL_XP[i] = 1500;
			else if (i <= 65) LEVEL_XP[i] = 1800;
			else if (i <= 70) LEVEL_XP[i] = 2000;
			else if (i <= 75) LEVEL_XP[i] = 2200;
			else if (i <= 80) LEVEL_XP[i] = 2500;
			else if (i <= 85) LEVEL_XP[i] = 3000;
			else if (i <= 90) LEVEL_XP[i] = 3500;
			else if (i <= 95) LEVEL_XP[i] = 4000;
			else LEVEL_XP[i] = 5000;
		}
	}

	public PickaxeOfGod(ToolMaterial material, Item.Settings settings) {
		super(material, BlockTags.PICKAXE_MINEABLE, settings.maxDamage(999));
	}

	private static int computeLevel(int totalXp) {
		int lvl = 0;
		for (int i = 1; i <= 100; i++) {
			if (totalXp >= LEVEL_XP[i]) {
				totalXp -= LEVEL_XP[i];
				lvl = i;
			} else {
				break;
			}
		}
		return lvl;
	}

	@Override
	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (!world.isClient && isXpBlock(state.getBlock())) {
			Integer currentXp = stack.get(ModDataComponentTypes.XP_LEVEL);
			if (currentXp == null) currentXp = 0;

			int oldLevel = getLevel(stack);
			currentXp += XP_PER_BLOCK;
			stack.set(ModDataComponentTypes.XP_LEVEL, currentXp);

			int newLevel = computeLevel(currentXp);
			stack.set(ModDataComponentTypes.LEVEL, newLevel);

			if (oldLevel != newLevel) {
				applyLevelBonuses(stack, newLevel, world);
				Objects.requireNonNull(MinecraftClient.getInstance().getNetworkHandler())
						.sendChatMessage("POG Level Up! New level: " + newLevel);
			}
		}

		return true;
	}

	private void applyLevelBonuses(ItemStack stack, int level, World world) {
		ItemEnchantmentsComponent.Builder enchantments = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);

		RegistryEntry<Enchantment> efficiencyEntry = world.getRegistryManager()
				.get(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
				.getEntry(Enchantments.EFFICIENCY)
				.orElse(null);

		RegistryEntry<Enchantment> fortuneEntry = world.getRegistryManager()
				.get(net.minecraft.registry.RegistryKeys.ENCHANTMENT)
				.getEntry(Enchantments.FORTUNE)
				.orElse(null);

		if (level >= 41 && efficiencyEntry != null) {
			enchantments.add(efficiencyEntry, 1);
		}
		if (level >= 46 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 2);
		}
		if (level >= 51 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 3);
		}
		if (level >= 56 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 4);
		}
		if (level >= 61 && efficiencyEntry != null) {
			enchantments.set(efficiencyEntry, 5);
		}
		if (level >= 66 && efficiencyEntry != null && fortuneEntry != null) {
			enchantments.set(efficiencyEntry, 5);
			enchantments.add(fortuneEntry, 1);
		}
		if (level >= 71 && efficiencyEntry != null && fortuneEntry != null) {
			enchantments.set(efficiencyEntry, 5);
			enchantments.set(fortuneEntry, 2);
		}
		if (level >= 76 && efficiencyEntry != null && fortuneEntry != null) {
			enchantments.set(efficiencyEntry, 5);
			enchantments.set(fortuneEntry, 3);
		}

		stack.set(net.minecraft.component.DataComponentTypes.ENCHANTMENTS, enchantments.build());
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

		if (level >= 100) return 1.0f;

		int xpForLevel = LEVEL_XP[level + 1];
		int totalXpBefore = 0;
		for (int i = 1; i <= level; i++) totalXpBefore += LEVEL_XP[i];

		int xpIntoLevel = xp - totalXpBefore;
		return (float) xpIntoLevel / (float) xpForLevel;
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int lvl = getLevel(stack);
		target.damage(attacker.getWorld().getDamageSources().mobAttack(attacker), 2.0f + lvl * 0.5f);
		return true;
	}

	@Override
	public float getMiningSpeed(ItemStack stack, BlockState state) {
		int lvl = getLevel(stack);
		Block block = state.getBlock();

		if (lvl >= 11 && (block == Blocks.SNOW_BLOCK || block == Blocks.OAK_LEAVES)) return 9999f;
		if (lvl >= 21 && (block == Blocks.NETHERRACK || block == Blocks.CACTUS)) return 9999f;
		if (lvl >= 26 && (block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.GRAVEL ||
				block == Blocks.SOUL_SAND || block == Blocks.RED_SAND || block == Blocks.ICE)) return 9999f;
		if (lvl >= 31 && block == Blocks.WHITE_WOOL) return 9999f;
		if (lvl >= 41 && block == Blocks.SANDSTONE) return 9999f;
		if (lvl >= 51 && block == Blocks.TERRACOTTA) return 9999f;
		if (lvl >= 56 && block == Blocks.STONE) return 9999f;

		float baseSpeed = super.getMiningSpeed(stack, state);
		return baseSpeed + lvl * 0.5f;
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
		float progress = getXpProgress(stack);
		if (progress < 0.5f) {
			float t = progress / 0.5f;
			return interpolateColor(0xFF0000, 0xFFFF00, t);
		} else {
			float t = (progress - 0.5f) / 0.5f;
			return interpolateColor(0xFFFF00, 0x00FF00, t);
		}
	}

	private int interpolateColor(int startColor, int endColor, float t) {
		int sr = (startColor >> 16) & 0xFF;
		int sg = (startColor >> 8) & 0xFF;
		int sb = startColor & 0xFF;
		int er = (endColor >> 16) & 0xFF;
		int eg = (endColor >> 8) & 0xFF;
		int eb = endColor & 0xFF;
		int r = (int)(sr + (er - sr) * t);
		int g = (int)(sg + (eg - sg) * t);
		int b = (int)(sb + (eb - sb) * t);
		return (r << 16) | (g << 8) | b;
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