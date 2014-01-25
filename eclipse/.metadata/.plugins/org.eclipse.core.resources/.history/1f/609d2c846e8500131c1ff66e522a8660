package com.nitrogenegames.netcraft.misc;

import net.minecraft.item.ItemStack;

public class FabricatorRecipe {
	public int mainID;
	public int addOn1;
	public int addOn2;
	public boolean multipleAddOns;
	public ItemStack result;
	public FabricatorRecipe(int m, int a1, int a2, boolean as, ItemStack r) {
		mainID = m;
		addOn1 = a1;
		addOn2 = a2;
		multipleAddOns = as;
		result = r;
	}
	public FabricatorRecipe(int m, int a1, int a2, ItemStack s) {
		this(m, a1, a2, false, s);
	}
	public boolean canFabricate(int main, int a1, int a2) {
		if(main != mainID) {
			return false;
		}
		if((a1 == addOn1 && a2 == addOn2) || (a1 == addOn2 && a2 == addOn1)) {
			return true;
		} else {
			return false;
		}
		
	}
}
