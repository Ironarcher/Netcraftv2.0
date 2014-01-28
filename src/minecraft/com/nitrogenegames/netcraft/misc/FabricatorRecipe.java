package com.nitrogenegames.netcraft.misc;

import net.minecraft.item.ItemStack;

public class FabricatorRecipe {
	public int mainID = -1;
	public int addOn1 = -1;
	public int addOn2 = -1;
	public int addOn3 = -1;
	public boolean tripleRecipe;
	public boolean singleRecipe;
	public boolean doubleRecipe;
	public ItemStack result;
	public FabricatorRecipe(int m, int a1, int a2, int a3, String slotsFilled, ItemStack r) {
		mainID = m;
		addOn1 = a1;
		addOn2 = a2;
		addOn3 = a3;
		if(slotsFilled == "tripleRecipe"){
			tripleRecipe = true;
		}
		if(slotsFilled == "doubleRecipe"){
			doubleRecipe = true;
		}
		if(slotsFilled == "singleRecipe"){
			singleRecipe = true;
		} else{
			System.out.println("FATAL ERROR! FIX BUG IN NETCRAFT CLASS IMMEDIATELY!");
		}
		result = r;
	}
	public FabricatorRecipe(int m, int a1, int a2, int a3, ItemStack s) {
		this(m, a1, a2, a3, "tripleRecipe", s);
	}
	public boolean canFabricateUsing(int main, int a1, int a2, int a3) {
		if(tripleRecipe){
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
			
	} else if(doubleRecipe){
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
		} else{
			return false;
		}
	} else if(singleRecipe){
		if(a1 == addOn1 || a1 == addOn2 || a1 == addOn3){
			return true;
		} else{
			return false;
		}
	}
	return false;
	}
}
