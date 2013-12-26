package com.nitrogenegames.netcraft.item;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;


public class ItemCoil extends Item {
	public ItemCoil(int id) {
			super(id); //Returns super constructor: par1 is ID
			setCreativeTab(Netcraft.netcrafttab); //Tells the game what creative mode tab it goes in
	}
}