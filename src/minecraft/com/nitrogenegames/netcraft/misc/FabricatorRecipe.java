package com.nitrogenegames.netcraft.misc;

import net.minecraft.item.ItemStack;

public class FabricatorRecipe {
	public int mainID;
	public int addOn1;
	public int addOn2;
	public int addOn3;
	public boolean multipleAddOns;
	public ItemStack result;
	public FabricatorRecipe(int m, int a1, int a2, int a3, boolean as, ItemStack r) {
		mainID = m;
		addOn1 = a1;
		addOn2 = a2;
		addOn3 = a3;
		multipleAddOns = as;
		result = r;
	}
	public FabricatorRecipe(int m, int a1, int a2, int a3, ItemStack s) {
		this(m, a1, a2, a3, false, s);
	}
	public boolean canFabricateUsing(int main, int a1, int a2, int a3) {
		if(main != mainID) {
			return false;
		}
		if(a1 == addOn1) {
			if(a2 == addOn2) {
				if(a3 == addOn3) {
					return true;
				}
			} else if(a2 == addOn3) {
				if(a3 == addOn2) {
					return true;
				}
			}
		} else if(a1 == addOn2) {
			if(a2 == addOn1) {
				if(a3 == addOn3) {
					return true;
				}
			} else if(a2 == addOn3) {
				if(a3 == addOn1) {
					return true;
				}
			}
		} else if(a1 == addOn3) {
			if(a2 == addOn2) {
				if(a3 == addOn1) {
					return true;
				}
			} else if(a2 == addOn1) {
				if(a3 == addOn2) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
		
	}
}
