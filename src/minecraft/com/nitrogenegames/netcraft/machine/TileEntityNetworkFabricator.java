package com.nitrogenegames.netcraft.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityNetworkFabricator extends TileEntity implements ISidedInventory
{

	private String localizedName;
	
	//slots 0,1,2 are the top 3 from left to right, slot 3 is the bottom slot
	private ItemStack[] slots = new ItemStack[3];
	
	private static final int[] slots_top = new int[]{0,1,2};
	private static final int[] slots_bottom = new int[]{3};
	private static final int[] slots_sides = new int[]{0,1,2};
	
	//speed of the machine, less is faster
	public int fabricatorSpeed = 200;
	
	//How long left before fabricated
	public int fabricatorTime;
	
	//The fabrication time for the current ingredients
	public int currentItemFabricateTime;
	
	public int getSizeInventory(){
		return this.slots.length;
	}
	
	public void setGuiDisplayName(String displayName){
		this.localizedName = displayName;
	}
	
	public String getInvName(){
		return this.isInvNameLocalized() ? this.localizedName : "container.networkFabricator";
	}
	
	public boolean isInvNameLocalized(){
		return this.localizedName != null && this.localizedName.length() > 0;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		
	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
	}

	public void updateEntity(){
		//if energy > 0{
		//	eu = eu - 10 or something
		//}
		/*
		if(!this.worldObj.isRemote){
			if(this.canFabricate()){
				this.currentItemFabricateTime = this.fabricatorTime = getItemFabricateTime(this.slots[3], this.slots[0], this.slots[1], this.slots[2]);
				
			}
		}
		*/
	}
	
	public static int getItemFabricateTime(ItemStack baseitem, ItemStack topitemleft, ItemStack topitemmiddle, ItemStack topitemright){
		if(baseitem != null){
			if(topitemleft != null || topitemmiddle != null || topitemright != null){
				if(baseitem.itemID == ic2.api.item.Items.getItem("advancedAlloy").itemID){
					if(topitemleft.itemID == ic2.api.item.Items.getItem("electronicCircuit").itemID || topitemleft.itemID == ic2.api.item.Items.getItem("electronicCircuit").itemID || topitemleft.itemID == ic2.api.item.Items.getItem("electronicCircuit").itemID){
						return 200;
					}
				}
			//return in ticks (20 per second)
			return 0;
			}
			else{
				return 0;
			}
		} else{
			return 0;
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return i == 3 ? false : true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return var1 == 0 ? slots_bottom : (var1 == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j != 0 || i != 0 || i != 1 || i != 2;
	}

	
}