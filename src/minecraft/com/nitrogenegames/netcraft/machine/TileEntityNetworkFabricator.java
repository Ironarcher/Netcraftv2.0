package com.nitrogenegames.netcraft.machine;

import com.nitrogenegames.netcraft.Netcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityNetworkFabricator extends TileEntity implements ISidedInventory
{

	private String localizedName;
	
	//slots 0,1,2 are the top 3 from left to right, slot 3 is the bottom slot
	private ItemStack[] slots = new ItemStack[4];
	
	private static final int[] slots_top = new int[]{0,1,2};
	private static final int[] slots_bottom = new int[]{3};
	private static final int[] slots_sides = new int[]{0,1,2};
	
	//speed of the machine, less is faster
	public int furnaceCookTime = 200;
	
	//How long left before fabricated
	public int furnaceBurnTime;
	
	//The fabrication time for the current ingredients
	public int currentItemBurnTime;
	
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


	    /**
	     * Returns the stack in slot i
	     */
	    public ItemStack getStackInSlot(int par1)
	    {
	        return this.slots[par1];
	    }

	    /**
	     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	     * new stack.
	     */
	    public int getCookProgressScaled(int par1)
	    {
	        return this.furnaceCookTime * par1 / 200;
	    }

	    @SideOnly(Side.CLIENT)

	    /**
	     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
	     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
	     */
	    public int getBurnTimeRemainingScaled(int par1)
	    {
	        if (this.currentItemBurnTime == 0)
	        {
	            this.currentItemBurnTime = 200;
	        }

	        return this.currentItemBurnTime/2;
	    }

	    /**
	     * Returns true if the furnace is currently burning
	     */
	    public boolean isBurning()
	    {
	        return true;
	    }

	    /**
	     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
	     * ticks and creates a new spawn inside its implementation.
	     */
	    public void updateEntity()
	    {
	        boolean flag1 = false;

	        if (!this.worldObj.isRemote)
	        {

	            if (this.isBurning() && this.canSmelt())
	            {
	                this.furnaceCookTime += 20; //EDITABLE

	                if (this.furnaceCookTime == 200)
	                {
	                    this.furnaceCookTime = 0;
	                    this.smeltItem();
	                    flag1 = true;
	                }
	            }
	            else
	            {
	                this.furnaceCookTime = 0;
	            }

	                flag1 = true;
	                //BlockFurnace.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	        }

	        if (flag1)
	        {
	            this.onInventoryChanged();
	        }
	    }

	    /**
	     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
	     */
	    private boolean canSmelt()
	    {
	        if (this.slots[1] == null || this.slots[0] == null || this.slots[2] == null)
	        {
	            return false;
	        }
	        else
	        {
	            ItemStack itemstack = 	Netcraft.getFabricatorResult(slots[0].itemID, slots[1].itemID, slots[2].itemID);
	            if (itemstack == null) return false;
	            if (this.slots[3] == null) return true;
	            if (!this.slots[3].isItemEqual(itemstack)) return false;
	            int result = slots[3].stackSize + itemstack.stackSize;
	            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
	        }
	    }
	    public ItemStack decrStackSize(int par1, int par2)
	    {
	        if (this.slots[par1] != null)
	        {
	            ItemStack itemstack;

	            if (this.slots[par1].stackSize <= par2)
	            {
	                itemstack = this.slots[par1];
	                this.slots[par1] = null;
	                return itemstack;
	            }
	            else
	            {
	                itemstack = this.slots[par1].splitStack(par2);

	                if (this.slots[par1].stackSize == 0)
	                {
	                    this.slots[par1] = null;
	                }

	                return itemstack;
	            }
	        }
	        else
	        {
	            return null;
	        }
	    }

	    /**
	     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	     * like when you close a workbench GUI.
	     */
	    public ItemStack getStackInSlotOnClosing(int par1)
	    {
	        if (this.slots[par1] != null)
	        {
	            ItemStack itemstack = this.slots[par1];
	            this.slots[par1] = null;
	            return itemstack;
	        }
	        else
	        {
	            return null;
	        }
	    }

	    /**
	     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	     */
	    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	    {
	        this.slots[par1] = par2ItemStack;

	        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
	        {
	            par2ItemStack.stackSize = this.getInventoryStackLimit();
	        }
	    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
		
	}

	@Override
	public void closeChest() {
		
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



    public void smeltItem()
    {
        if (canSmelt())
        {
            ItemStack itemstack = Netcraft.getFabricatorResult(slots[0].itemID, slots[1].itemID, slots[2].itemID);

            if (this.slots[3] == null)
            {
                this.slots[3] = itemstack.copy();
            }
            else if (this.slots[3].isItemEqual(itemstack))
            {
                slots[3].stackSize += itemstack.stackSize;
            }

            --this.slots[0].stackSize;
            --this.slots[1].stackSize;
            --this.slots[2].stackSize;

            if (this.slots[0].stackSize <= 0)
            {
                this.slots[0] = null;
            }
            if (this.slots[1].stackSize <= 0)
            {
                this.slots[1] = null;
            }
            if (this.slots[2].stackSize <= 0)
            {
                this.slots[2] = null;
            }
        }
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
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.slots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length)
            {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.slots.length; ++i)
        {
            if (this.slots[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.slots[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
    }

	
}
