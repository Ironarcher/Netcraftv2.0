package com.nitrogenegames.netcraft.item;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;


public class ItemCrafting extends Item {
	private boolean spark = false;
	public ItemCrafting(int par1, boolean shiny) {
		super(par1); //Returns super constructor: par1 is ID
		setCreativeTab(Netcraft.netcrafttab); //Tells the game what creative mode tab it goes in
		spark = shiny;
	}

	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(Netcraft.modid + ":" + this.getUnlocalizedName());
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack)
	{
	    return spark;
	}
}