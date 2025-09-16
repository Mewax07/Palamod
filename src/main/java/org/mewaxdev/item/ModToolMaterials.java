package org.mewaxdev.item;

import com.google.common.base.Suppliers;
import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import org.mewaxdev.util.ModTags;

import java.util.function.Supplier;

public enum ModToolMaterials implements ToolMaterial {
	AMETHYST(ModTags.Blocks.INCORRECT_FOR_PALADIUM_TOOL,
			1999, 5.0f, 3.0f, 20, () -> Ingredient.ofItems(ModItems.AMETHYST.INGOT)),
	TITANE(ModTags.Blocks.INCORRECT_FOR_PALADIUM_TOOL,
			2999, 5.5f, 3.5f, 20, () -> Ingredient.ofItems(ModItems.TITANE.INGOT)),
	PALADIUM(ModTags.Blocks.INCORRECT_FOR_PALADIUM_TOOL,
			4999, 8.0f, 6.0f, 20, () -> Ingredient.ofItems(ModItems.PALADIUM.INGOT)),
	GREEN_PALADIUM(ModTags.Blocks.INCORRECT_FOR_PALADIUM_TOOL,
			4999, 8.0f, 6.0f, 20, () -> Ingredient.ofItems(ModItems.GREEN_PALADIUM.INGOT)),
	ENDIUM(ModTags.Blocks.INCORRECT_FOR_PALADIUM_TOOL,
			4999, 9.3f, 7.3f, 22, () -> Ingredient.ofItems(ModItems.ENDIUM.INGOT)),
	GOD_PICKAXE(ModTags.Blocks.INCORRECT_FOR_PALADIUM_TOOL,
			999, 5.0f, 5.0f, 20, () -> Ingredient.ofItems(ModItems.GOD_PICKAXE)),
	;

	private final TagKey<Block> inverseTag;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	ModToolMaterials(final TagKey<Block> inverseTag, final int itemDurability,final float miningSpeed,
					 final float attackDamage, final int enchantability,final Supplier<Ingredient> repairIngredient) {
		this.inverseTag = inverseTag;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = Suppliers.memoize(repairIngredient::get);
	}

	@Override
	public int getDurability() {
		return this.itemDurability;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return this.miningSpeed;
	}

	@Override
	public float getAttackDamage() {
		return this.attackDamage;
	}

	@Override
	public TagKey<Block> getInverseTag() {
		return this.inverseTag;
	}

	@Override
	public int getEnchantability() {
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}
}
