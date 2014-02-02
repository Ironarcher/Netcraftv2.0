package com.nitrogenegames.netcraft.misc;

import net.minecraft.item.ItemStack;

public class FabricatorRecipe {
	public int mainID = -1;
	public int addOn1 = -1;
	public int addOn2 = -1;
	public int addOn3 = -1;
	public ItemStack result;
	public int count = 3;
	public FabricatorRecipe(int m, int a1, int a2, int a3, ItemStack r) {
		mainID = m;
		addOn1 = a1;
		addOn2 = a2;
		addOn3 = a3;
		if(addOn1 == -1) {
			count -= 1;
		}
		if(addOn2 == -1) {
			count -= 1;
		}
		if(addOn3 == -1) {
			count -= 1;
		}
		result = r;
	}
	public boolean canFabricateUsing(int main, int a1, int a2, int a3) {
		if(count == 3){
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
			
	} else if(count == 2){
		if(main != mainID) {
			return false;
		}
		if(a1 == addOn1){
			if(a2==addOn2){
				return true;
			} else if (a2 == addOn3){
				return true;
			}
		} else if (a1 == addOn2){
			if(a2==addOn1){
				return true;
			} else if (a2 == addOn3){
				return true;
			}
		} else if (a1 == addOn3){
			if(a2==addOn2){
				return true;
			} else if (a3 == addOn3){
				return true;
			}
		} else if (a2 == addOn1){
			return a3 == addOn2 || a3 == addOn3;
		} else if (a2 == addOn2){
			return a3 == addOn1 || a3 == addOn3;
		} else if (a2 == addOn3){
			return a3 == addOn2 || a3 == addOn1;
		} else{
			return false;
		}
	} else if(count == 1){
		if(a1 == addOn1 || a1 == addOn2 || a1 == addOn3 || a2 == addOn1 || a2 == addOn2 || a2 == addOn3 || a3 == addOn1 || a3 == addOn2 || a3 == addOn3){
			return true;
		} else{
			return false;
		}
	}
	return false;
	}
}
