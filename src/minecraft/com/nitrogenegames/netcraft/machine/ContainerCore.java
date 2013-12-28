package com.nitrogenegames.netcraft.machine;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.nitrogenegames.netcraft.Netcraft;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ContainerCore extends Container {

        public TileEntityCore tileEntity;
        public int energy;
        public ContainerCore (InventoryPlayer inventoryPlayer, TileEntityCore te){
                tileEntity = te;
                energy = te.energy;
                //the Slot constructor takes the IInventory and the slot number in that it binds to
                //and the x-y coordinates it resides on-screen
                addSlotToContainer(new SlotModuleCore(tileEntity, 0, 20, 13));

                //commonly used vanilla code that adds the player's inventory
                bindPlayerInventory(inventoryPlayer);
        }

        @Override
        public boolean canInteractWith(EntityPlayer player) {
                return tileEntity.isUseableByPlayer(player);
        }


        protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
                for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                                8 + (12) + j * 18, 84 + i * 18));
                        }
                }

                for (int i = 0; i < 9; i++) {
                        addSlotToContainer(new Slot(inventoryPlayer, i, 8 + (12) + i * 18, 142));
                }
        }
        /*@SideOnly(Side.CLIENT)
        public void updateProgressBar(int par1, int par2)
        {
        	this.energy = par2;
        } */
        @SideOnly(Side.SERVER)
        public void detectAndSendChanges()
        {
        	super.detectAndSendChanges();
            for (int i = 0; i < this.crafters.size(); ++i)
            {
            	//ICrafting icrafting = (ICrafting)this.crafters.get(i);
                //icrafting.sendProgressBarUpdate(this, 1, this.energy);
        		/*if(!worldObj.isRemote) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
                DataOutputStream outputStream = new DataOutputStream(bos);
                try {
                        outputStream.writeInt(this.energy);
                        outputStream.writeInt(this.tileEntity.xCoord);
                        outputStream.writeInt(this.tileEntity.yCoord);
                        outputStream.writeInt(this.tileEntity.zCoord);
                } catch (Exception ex) {
                        ex.printStackTrace();
                }

                Packet250CustomPayload packet = new Packet250CustomPayload();
                packet.channel = "corepack";
                packet.data = bos.toByteArray();
                packet.length = bos.size();
                PacketDispatcher.sendPacketToPlayer(packet, (Player) crafters.get(i));
        		} */
            }
        	//this.energy = this.tileEntity.energy;
        	
        }
        public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
        {
            ItemStack itemstack = null;
            Slot slot = (Slot)this.inventorySlots.get(par2);

            if (slot != null && slot.getHasStack())
            {
                ItemStack itemstack1 = slot.getStack();
                itemstack = itemstack1.copy();

                if (par2 == 0)
                {
                    if (!this.mergeItemStack(itemstack1, 1, 37, true))
                    {
                        return null;
                    }
                }
                else
                {
                    if (((Slot)this.inventorySlots.get(0)).getHasStack() || !Netcraft.isModule(itemstack))
                    {
                        return null;
                    }

                    if (itemstack1.hasTagCompound() && itemstack1.stackSize == 1)
                    {
                        ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
                        itemstack1.stackSize = 0;
                    }
                    else if (itemstack1.stackSize >= 1)
                    {
                        ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.itemID, 1, itemstack1.getItemDamage()));
                        --itemstack1.stackSize;
                    }
                }

                if (itemstack1.stackSize == 0)
                {
                    slot.putStack((ItemStack)null);
                }
                else
                {
                    slot.onSlotChanged();
                }

                if (itemstack1.stackSize == itemstack.stackSize)
                {
                    return null;
                }

                slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
            }

            return itemstack;
        }
}