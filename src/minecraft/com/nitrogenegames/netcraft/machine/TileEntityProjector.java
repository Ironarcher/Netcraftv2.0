package com.nitrogenegames.netcraft.machine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.gui.GuiCore;
import com.nitrogenegames.netcraft.net.INet;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
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

public class TileEntityProjector extends TileEntity implements ISidedInventory, INet {

	public int range = 20;
	public int currentrange = 20;
	public int pid = 0;
	//private BasicSink electricSlicer = new BasicSink(this, 32, 3);
	private ItemStack[] inv = new ItemStack[2];
	
	@Override
    public int getSizeInventory() {
            return inv.length;
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
            return "ironarcher.tileentityprojector";
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
            if(tagCompound.hasKey("range")) {
            	this.range = tagCompound.getInteger("range");
            }
            if(tagCompound.hasKey("crange")) {
            	this.currentrange = tagCompound.getInteger("crange");
            }
            if(tagCompound.hasKey("pid")) {
            	this.pid = tagCompound.getInteger("pid");
            } 
            NBTTagList tagList = tagCompound.getTagList("Inventory");
            for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    byte slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < inv.length) {
                            inv[slot] = ItemStack.loadItemStackFromNBT(tag);
                    }
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

            tagCompound.setInteger("range", range);

            tagCompound.setInteger("crange", currentrange);

            tagCompound.setInteger("pid", pid);

    }
    
    
    @Override
    public void updateEntity(){
    	
    }
    @Override
    public void invalidate(){
    	
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
	public ArrayList getConnected() {
		return Netcraft.getConnectedObjects(worldObj, this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public int[] getCore() {
		return Netcraft.getCoreCoordinates(worldObj, this.xCoord, this.yCoord, this.zCoord);
	}



}
