package com.nitrogenegames.netcraft.block;

//import ic2.api.energy.tile.IEnergyTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.item.ItemModules;
import com.nitrogenegames.netcraft.machine.TileEntityCore;
import com.nitrogenegames.netcraft.net.INet;
import com.nitrogenegames.netcraft.net.INetBlock;
import com.nitrogenegames.netcraft.net.NetEntity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;

public class BlockCore extends BlockContainer implements INetBlock {

	 public BlockCore (int id, String texture) {
         super(id, Material.rock);
         setCreativeTab(Netcraft.netcrafttab);
 }

	 
	 public void activatemodules(World world, int x, int y, int z) {
         TileEntityCore tileEntity = (TileEntityCore) world.getBlockTileEntity(x, y, z);
         for (int i = 0; i < 1; i++) {
            ItemStack stack = tileEntity.getStackInSlot(i);
            if(!(stack == null)) {
        	if (!(stack.getItem() == null)) {
        	 if(Netcraft.isModule(stack)) {
        		 ItemModules module = (ItemModules) stack.getItem();
        		 if(tileEntity.energy >= module.getEuUsed()) {
        			 tileEntity.energy -= module.getEuUsed();
        		 module.activate(world, stack, x, y, z);
        		 
        		 } else {
        			 //NOT ENOUGH POWER
        		 }
        	 }
        	 }
         	}
            
         }
	 }
	 /*public ItemStack[] pickupstack(World world, int x, int y, int z) {
		 ItemStack[] stacks = new ItemStack[1];
         TileEntityCore tileEntity = (TileEntityCore) world.getBlockTileEntity(x, y, z);
         for (int i = 0; i < 1; i++) {
        	 ItemStack stack = tileEntity.getStackInSlot(i);
        	 stacks[i] = stack;
         }
         return stacks;
	 } */
	 @Override
	 public void onBlockAdded(World par1World, int par2, int par3, int par4)
	 {
			this.getEntity(par1World, par2, par3, par4).update();
	          if (!par1World.isRemote)
	          {
	        	  TileEntityCore tileEntity = (TileEntityCore) par1World.getBlockTileEntity(par2, par3, par4);
	        	  	tileEntity.net = new NetEntity(par1World);
	        	  	tileEntity.net.objects = Netcraft.getConnectedObjects(par1World, par2, par3, par4);
	                  
	                  boolean powered = tileEntity.powered;
	                  if (powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
	                  {
	                          par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
	                  }
	                  else if (!powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
	                  {
	                          tileEntity.powered = true;
	                          activatemodules(par1World, par2, par3, par4);
	                  }
	          }
	 }
	 @Override
	 public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	 {
			this.getEntity(par1World, par2, par3, par4).update();
	          if (!par1World.isRemote)
	          {
	        	  TileEntityCore tileEntity = (TileEntityCore) par1World.getBlockTileEntity(par2, par3, par4);
	        	  	
                  
                  boolean powered = tileEntity.powered;
	                  if (powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
	                  {
	                          par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
	                  }
	                  else if (!powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
	                  {
	                          tileEntity.powered = true;
	                          activatemodules(par1World, par2, par3, par4);
	                  }
	          }
	 }

	 @Override
	 public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	 {
	          if (!par1World.isRemote)
	          {
	        	  TileEntityCore tileEntity = (TileEntityCore) par1World.getBlockTileEntity(par2, par3, par4);
                  
                  boolean powered = tileEntity.powered;
	                  if (powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
	                          tileEntity.powered = false;
	          }
	          
	 }
	 @SideOnly(Side.CLIENT)
     private Icon[] icons;

     @SideOnly(Side.CLIENT)
     @Override
     public void registerIcons(IconRegister par1IconRegister)
     {
           icons = new Icon[5];
           icons[0] = par1IconRegister.registerIcon(Netcraft.modid + ":core_bottom");
           icons[1] = par1IconRegister.registerIcon(Netcraft.modid + ":core_top");
           icons[2] = par1IconRegister.registerIcon(Netcraft.modid + ":core_front");
           icons[3] = par1IconRegister.registerIcon(Netcraft.modid + ":core_front_on");
           icons[4] = par1IconRegister.registerIcon(Netcraft.modid + ":core_back");
     }
     public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
     {
 		this.getEntity(par1World, par2, par3, par4).update();
     //int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
    //par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
     
    	 int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
         par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
     }
     
     @SideOnly(Side.CLIENT)
     @Override
     public Icon getIcon(int par1, int par2)
     {
                         switch(par1)
                         {
                         case 1:

                        	 return icons[1];
                         case 2:
                        	 if(par2 == 2) {
                                 return icons[2];
                            	 } else if(par2 == 6) {
                                     return icons[3];
                               } else  {
                                 return icons[4]; 
                            	 }
                         case 3:
                        	 if(par2 == 0) {
                             return icons[2];
                        	 } else if(par2 == 4) {
                                 return icons[3];
                           } else  {
                             return icons[4]; 
                        	 }
                        		 
                         case 4:
                        	 if(par2 == 1) {
                                 return icons[2];
                        	 } else if(par2 == 5) {
                                 return icons[3];
                           } else  {
                                 return icons[4]; 
                            	 }
                         case 5:
                        	 if(par2 == 3) {
                                 return icons[2];
                        	 } else if(par2 == 7) {
                                 return icons[3];
                           } else  {
                                 return icons[4]; 
                            	 }
                         case 0:
                        	 return icons[0];
                         default:
                             return icons[4];
                         }

     }
     /*@SideOnly(Side.CLIENT)
     public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
     {
           for(int i = 0; i < 2; i++)
           {
                  par3List.add(new ItemStack(par1, 1, i));
           }
     }
     */

        @Override
        public boolean onBlockActivated(World world, int x, int y, int z,
                        EntityPlayer player, int idk, float what, float these, float are) {
                TileEntityCore tileEntity = (TileEntityCore) world.getBlockTileEntity(x, y, z);

                if (tileEntity == null || player.isSneaking()) {
                        return false;
                }
        //code to open gui explained later
                
        player.openGui(Netcraft.instance, 0, world, x, y, z);
                return true;
        }

        @Override
        public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
                dropItems(world, x, y, z);
                super.breakBlock(world, x, y, z, par5, par6);
        }

        private void dropItems(World world, int x, int y, int z){
                Random rand = new Random();

                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if (!(tileEntity instanceof IInventory)) {
                        return;
                }
                IInventory inventory = (IInventory) tileEntity;

                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                        ItemStack item = inventory.getStackInSlot(i);

                        if (item != null && item.stackSize > 0) {
                                float rx = rand.nextFloat() * 0.8F + 0.1F;
                                float ry = rand.nextFloat() * 0.8F + 0.1F;
                                float rz = rand.nextFloat() * 0.8F + 0.1F;

                                EntityItem entityItem = new EntityItem(world,
                                                x + rx, y + ry, z + rz,
                                                new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                                if (item.hasTagCompound()) {
                                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                                }

                                float factor = 0.05F;
                                entityItem.motionX = rand.nextGaussian() * factor;
                                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                                entityItem.motionZ = rand.nextGaussian() * factor;
                                world.spawnEntityInWorld(entityItem);
                                item.stackSize = 0;
                        }
                }
        }
        @Override
        public TileEntity createNewTileEntity(World mainworld) {
            return new TileEntityCore();
    }


    	@Override
    	public ArrayList getConnected(World par1, int par2, int par3, int par4) {
    		// TODO Auto-generated method stub
    		return Netcraft.getConnectedObjects(par1, par2, par3, par4);
    	}
    	@Override
    	public int[] getCore(World par1, int par2, int par3, int par4) {
    		// TODO Auto-generated method stub
    		return Netcraft.getCoreCoordinates(par1, par2, par3, par4);
    	}
    	@Override
    	public NetEntity getEntity(World par1, int par2, int par3, int par4) {
    		// TODO Auto-generated method stub
    		return ((TileEntityCore) par1.getBlockTileEntity(getCore(par1, par2, par3, par4)[0], getCore(par1, par2, par3, par4)[1] ,  getCore(par1, par2, par3, par4)[2])).getEntity();
    	}

        

}