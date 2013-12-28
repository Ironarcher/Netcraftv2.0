package com.nitrogenegames.netcraft.machine;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.nitrogenegames.netcraft.gui.GuiCore;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import ic2.api.energy.prefab.BasicSink;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.*;
import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.*;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityCore extends TileEntity implements IEnergySink, ISidedInventory {

	public int energy = 5;
	public boolean isUsingPower = false;
	//ISidedInventory
	private int id = 0;
	public int maxenergy = 10000;
	private boolean init;
	//private BasicSink electricSlicer = new BasicSink(this, 32, 3);
	private ItemStack[] inv = new ItemStack[2];
	public boolean powered = false;
	public int coreEnergyNeeded = 0;

	@Override
    public int getSizeInventory() {
            return inv.length;
    }
	
	@Override
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    writeToNBT(tagCompound);
	    return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tagCompound);
	}
	@Override
	public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
            
            if(tagCompound.hasKey("energy")){
            	this.energy = tagCompound.getInteger("energy");
            }
            NBTTagList tagList = tagCompound.getTagList("Inventory");
            for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < inv.length) {
                            inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
}
				powered = tagCompound.getBoolean("POWER");
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
            tagCompound.setInteger("energy", this.energy);
    }
    
    
    @Override
    public void updateEntity(){
    	
    	if(!init && worldObj != null){
    		if(!worldObj.isRemote){
    			EnergyTileLoadEvent loadEvent = new EnergyTileLoadEvent(this);
    			MinecraftForge.EVENT_BUS.post(loadEvent);
    		}
    		init = true;
    	}
    	int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(!worldObj.isRemote) { //MAYBE A BIT LAGGY?
    	if(meta <= 3 && powered == true) {
    		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 2);
    	} else if(meta >= 4 && powered == false) {
    		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 2);
    	}
		}
		if(!(this.getStackInSlot(1) == null)) {

		}
    	sendPacket();
    	
    }
    @Override
    public void invalidate(){
    	
    	EnergyTileUnloadEvent unloadEvent = new EnergyTileUnloadEvent(this);
		MinecraftForge.EVENT_BUS.post(unloadEvent);
    }
    
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	@Override
	public int getMaxSafeInput() {
		return 32;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter,
			ForgeDirection direction) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public double demandedEnergyUnits() {
		return (int) (this.maxenergy - this.energy);
	}

	@Override
	public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {

		this.isUsingPower = true;
		if(this.energy >= this.maxenergy){
			sendPacket();
			return amount;
		}
		
		double openEnergy = this.maxenergy - this.energy;
		
		if(openEnergy >= amount){
			this.energy += amount;
			sendPacket();
			return 0;
		} else if (amount > openEnergy){
			this.energy = this.maxenergy;
			sendPacket();
			return amount - (int) openEnergy;
		}
		sendPacket();
		return 0;
	}
	public int getEnergy() {
		return this.energy;
	}
	public void sendPacket() {
		if(!worldObj.isRemote) {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
        	//System.out.println(this.energy + " ENERGYS SENDING");
                outputStream.writeInt(this.energy);
                outputStream.writeInt(this.xCoord);
                outputStream.writeInt(this.yCoord);
                outputStream.writeInt(this.zCoord);
        } catch (Exception ex) {
                ex.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "corepack";
        packet.data = bos.toByteArray();
        packet.length = bos.size();
    	PacketDispatcher.sendPacketToAllPlayers(packet); //Maybe change to players in radius?
		}
	}

	public int getEnergyScaled(int i) {
		return this.energy * i / maxenergy;
	}

}
