package com.nitrogenegames.netcraft.item;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.*;
import net.minecraft.creativetab.CreativeTabs;


public class ItemUpgrade extends Item {
public ItemUpgrade(int id) {
	super(id);
		setCreativeTab(Netcraft.netcrafttab);
	}
}