package com.nitrogenegames.netcraft.block;

import java.util.List;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityProjector;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemBlockProjector extends Item {
	//Netcraft.EnumProjector type;
	public int blockID;
     public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par2List, boolean par4)
     {
		Netcraft.InstantiateStackNBT(par1ItemStack);      
		 par2List.add("Range: "+ par1ItemStack.getTagCompound().getInteger("range"));

    }
     public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
			Netcraft.InstantiateStackNBT(par1ItemStack);
     }
	public ItemBlockProjector(int par1) {
		super(par1);
		this.maxStackSize = 1;
		this.blockID = Netcraft.projector.blockID;
	}
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + this.getDamage(itemstack);
	}
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	//ITEMBLOCK CODE
    /**
     * Returns the blockID for this Item
     */

    public int getBlockID()
    {
        return this.blockID;
    }
    

    @SideOnly(Side.CLIENT)

    /**
     * Returns 0 for /terrain.png, 1 for /gui/items.png
     */
    public int getSpriteNumber()
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {

    	if(par1 == 0) {
           return Netcraft.projector.getBlockTextureFromSide(1);
    	} else     	if(par1 == 1) {
            return Netcraft.projector.getBlockTextureFromSide(1);
    	} else {
            return Netcraft.projector.getBlockTextureFromSide(1);
    	}
    }
    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        int i1 = par3World.getBlockId(par4, par5, par6);
        //System.out.println(Block.blocksList[this.blockID] == null);
        if (i1 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1)
        {
            par7 = 1;
        }
        else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID
                && (Block.blocksList[i1] == null || !Block.blocksList[i1].isBlockReplaceable(par3World, par4, par5, par6)))
        {
            if (par7 == 0)
            {
                --par5;
            }

            if (par7 == 1)
            {
                ++par5;
            }

            if (par7 == 2)
            {
                --par6;
            }

            if (par7 == 3)
            {
                ++par6;
            }

            if (par7 == 4)
            {
                --par4;
            }

            if (par7 == 5)
            {
                ++par4;
            }
        }

        if (par1ItemStack.stackSize == 0)
        {
            return false;
        }
        else if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
        {
            return false;
        }
        else if (par5 == 255 && Block.blocksList[this.blockID].blockMaterial.isSolid())
        {
            return false;
        }
        else if (par3World.canPlaceEntityOnSide(this.blockID, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack))
        {
            Block block = Block.blocksList[this.blockID];
            int j1 = this.getMetadata(par1ItemStack.getItemDamage());

            if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, j1))
            {
                par3World.playSoundEffect((double)((float)par4 + 0.5F), (double)((float)par5 + 0.5F), (double)((float)par6 + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                --par1ItemStack.stackSize;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the given ItemBlock can be placed on the given side of the given block position.
     */
    public boolean canPlaceItemBlockOnSide(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer, ItemStack par7ItemStack)
    {
        int i1 = par1World.getBlockId(par2, par3, par4);

        if (i1 == Block.snow.blockID)
        {
            par5 = 1;
        }
        else if (i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID
                && (Block.blocksList[i1] == null || !Block.blocksList[i1].isBlockReplaceable(par1World, par2, par3, par4)))
        {
            if (par5 == 0)
            {
                --par3;
            }

            if (par5 == 1)
            {
                ++par3;
            }

            if (par5 == 2)
            {
                --par4;
            }

            if (par5 == 3)
            {
                ++par4;
            }

            if (par5 == 4)
            {
                --par2;
            }

            if (par5 == 5)
            {
                ++par2;
            }
        }

        return par1World.canPlaceEntityOnSide(this.getBlockID(), par2, par3, par4, false, par5, (Entity)null, par7ItemStack);
    }


    @SideOnly(Side.CLIENT)

    /**
     * gets the CreativeTab this item is displayed on
     */
    public CreativeTabs getCreativeTab()
    {
        return Netcraft.netcrafttab;
    }



    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
    		for (int ix = 0; ix < 2; ix++) {
    			subItems.add(new ItemStack(this, 1, ix));
    		}
    }
    

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {

    }

    /**
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     *
     * @param stack The item stack that was used to place the block. This can be changed inside the method.
     * @param player The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param side The side the player (or machine) right-clicked on.
     */
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
       if (!world.setBlock(x, y, z, this.blockID, metadata, 3))
       {
           return false;
       }

       if (world.getBlockId(x, y, z) == this.blockID)
       {
           Block.blocksList[this.blockID].onBlockPlacedBy(world, x, y, z, player, stack);
           Block.blocksList[this.blockID].onPostBlockPlaced(world, x, y, z, metadata);
    	   //world.setBlock(x, y, z, this.blockID, 0, 2);
           //TileEntity tileentity = Block.blocksList[this.blockID].createTileEntity(world, 0);
           //world.setBlockTileEntity(x, y, z, tileentity);
           TileEntity tileentity = world.getBlockTileEntity(x, y, z);
           Netcraft.InstantiateStackNBT(stack);
           ((TileEntityProjector) tileentity).range = stack.stackTagCompound.getInteger("range");
           //TODO SET RANGE
       }

       return true;
    }

}
