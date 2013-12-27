package com.nitrogenegames.netcraft.machine;

//import ic2.api.energy.tile.IEnergyTile;
//import ic2.api.energy.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.*;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCore extends TileEntity implements ISidedInventory {
	
	private ItemStack[] inv = new ItemStack[1];
	public boolean powered = false;
	public int coreEnergyNeeded = 0;

	@Override
    public int getSizeInventory() {
            return inv.length;
    }

	@Override
    public ItemStack getStackInSlot(int slot) {
       //System.out.println(slot + " debug message");
            return inv[slot];

    }

	@Override
    public ItemStack decrStackSize(int slot, int amt) {
            ItemStack stack = getStackInSlot(slot);
           if (stack != null) {
                   if (stack.stackSize <= amt) {
                          setInventorySlotContents(slot, null);
                    } else {
                       stack = stack.splitStack(amt);
                         if (stack.stackSize == 0) {
                                   setInventorySlotContents(slot, null);
                           }
                   }
           }
           return stack;
    }

	@Override
    public ItemStack getStackInSlotOnClosing(int slot) {
            ItemStack stack = getStackInSlot(slot);
            if (stack != null) {
                    setInventorySlotContents(slot, null);
            }
            return stack;
    }

	@Override
    public void setInventorySlotContents(int slot, ItemStack stack) {

            inv[slot] = stack;

            if (stack != null && stack.stackSize > getInventoryStackLimit()) {
                    stack.stackSize = getInventoryStackLimit();
            }               
    }

	@Override
    public String getInvName() {
            return "ironarcher.tileentitynetcraft";
    }



	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
    public int getInventoryStackLimit() {
            return 64;
    }

	@Override
    public boolean isUseableByPlayer(EntityPlayer player) {
            return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
            player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

	@Override
    public void openChest() {}

    @Override
    public void closeChest() {}
    
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
            super.readFromNBT(tagCompound);
            
            NBTTagList tagList = tagCompound.getTagList("Inventory");
            for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < inv.length) {
                            inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
}
			if(tagCompound.hasKey("POWER")){
				powered = tagCompound.getBoolean("POWER");
			} else{
				powered = false;
			}
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
            super.writeToNBT(tagCompound);
                            
            NBTTagList itemList = new NBTTagList();
            for (int i = 0; i < inv.length; i++) {
                    ItemStack stack = inv[i];
                    if (stack != null) {
                            NBTTagCompound tag = new NBTTagCompound();
                            tag.setByte("Slot", (byte) i);
                            stack.writeToNBT(tag);
                            itemList.appendTag(tag);
                    }
            }
            tagCompound.setTag("Inventory", itemList);
            tagCompound.setBoolean("POWER", powered);
    }
    


	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

}
