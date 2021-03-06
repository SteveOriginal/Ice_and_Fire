package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.StatCollector;
import com.github.alexthe666.iceandfire.core.ModItems;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class ItemModHoe extends ItemHoe {

	public ItemModHoe(ToolMaterial toolmaterial, String gameName, String name) {
		super(toolmaterial);
		this.setTranslationKey(name);
		this.setCreativeTab(IceAndFire.TAB);
		this.setRegistryName(IceAndFire.MODID, gameName);
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
		ItemStack mat = this.toolMaterial.getRepairItemStack();
		if(this.toolMaterial == ModItems.silverTools){
			NonNullList<ItemStack> silverItems = OreDictionary.getOres("ingotSilver");
			for(ItemStack ingot : silverItems){
				if(OreDictionary.itemMatches(repair, ingot, false)){
					return true;
				}
			}
		}
		if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
		return super.getIsRepairable(toRepair, repair);
	}
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (this == ModItems.silver_hoe) {
			if (target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
				target.attackEntityFrom(DamageSource.MAGIC, 2);
			}
		}
		if (this.toolMaterial == ModItems.myrmexChitin) {
			if (target.getCreatureAttribute() != EnumCreatureAttribute.ARTHROPOD) {
				target.attackEntityFrom(DamageSource.GENERIC, 4);
			}
			if (target instanceof EntityDeathWorm) {
				target.attackEntityFrom(DamageSource.GENERIC, 4);
			}
		}
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (this == ModItems.silver_hoe) {
			tooltip.add(TextFormatting.GREEN + StatCollector.translateToLocal("silvertools.hurt"));
		}
		if (this == ModItems.myrmex_desert_hoe || this == ModItems.myrmex_jungle_hoe) {
			tooltip.add(TextFormatting.GREEN + StatCollector.translateToLocal("myrmextools.hurt"));
		}
	}
}
