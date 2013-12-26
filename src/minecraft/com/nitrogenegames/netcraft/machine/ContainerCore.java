package com.nitrogenegames.netcraft.machine;

import com.nitrogenegames.netcraft.Netcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCore extends Container {

        protected TileEntityCore tileEntity;

        public ContainerCore (InventoryPlayer inventoryPlayer, TileEntityCore te){
                tileEntity = te;
                
                //the Slot constructor takes the IInventory and the slot number in that it binds to
                //and the x-y coordinates it resides on-screen
                addSlotToContainer(new SlotModuleCore(tileEntity, 0, 10, 10));

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
                                                8 + j * 18, 84 + i * 18));
                        }
                }

                for (int i = 0; i < 9; i++) {
                        addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
                }
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