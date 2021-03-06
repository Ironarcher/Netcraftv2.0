package com.nitrogenegames.netcraft.block;

import java.util.ArrayList;
import java.util.Random;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityCore;
import com.nitrogenegames.netcraft.net.INet;
import com.nitrogenegames.netcraft.net.INetBlock;



import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockNodeConnection extends Block implements INetBlock {
	private int[] coords;
	private Icon icon;
	private boolean state = false;
	private Icon icon2;
	private int x;
	private int y;
	private int z;
	public BlockNodeConnection(int par1, String texture) {
	super(par1, Material.rock);
		setCreativeTab(Netcraft.netcrafttab); //place in creative tabs
		setHardness(4f);
 		setResistance(10f);
 		setLightValue(0f);
 		setStepSound(Block.soundMetalFootstep);
		//this.setBlockBounds(0.32F, 0.32F, 0.32F, 0.68F, 0.68F, 0.68F);
	}
	/*
	public int getRenderBlockPass()
	{
	    return 0;
	}
	
	
	public boolean isOpaqueCube()
	{
	    return false;
	}
	
	
	public boolean renderAsNormalBlock()
	{
	    return false;
	}
	*/
	//drops when broken with pickaxe
	public int idDropped(int par1, Random par2Random, int par3)
		{
		return Netcraft.connectionnode.blockID;
		}
	public int quantityDropped(Random random)
		{
		return 1;
		}
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		state = Netcraft.isConectedToCore(world, this.x, this.y, this.z);
		if(state == false) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		} else {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	public void updateConnection(World par1World, int par2, int par3, int par4) {
		if(!par1World.isRemote) {
		state = Netcraft.isConectedToCore(par1World, par2, par3, par4);
		if(state == false) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		} else {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
		ArrayList p = Netcraft.getConnectedObjects(par1World, par2, par3, par4);
		for(int i = 0; i < p.size(); i++) {
			int[] coords = (int[]) p.get(i);
			if(Netcraft.isConectedToCore(par1World, par2, par3, par4)) {
				Block.blocksList[this.blockID].onNeighborBlockChange(par1World, par2, par3, par4, 451);
			} else {
				Block.blocksList[this.blockID].onNeighborBlockChange(par1World, par2, par3, par4, 451);
			} 
			System.out.println("Updating: " + coords[0] + ", " + coords[1] + ", " + coords[2]);
		} 
		
		}
		/*
		for(int c = -1; c <= 1; c++) {
			System.out.println("Updating....");
			if(par1World.getBlockId(par2 + c, par3, par4)  == Netcraft.connectionnode.blockID){
				par1World.notifyBlockOfNeighborChange(par2 + c, par3, par4, Netcraft.connectionnode.blockID);
			} 
			if(par1World.getBlockId(par2, par3 + c, par4)  == Netcraft.connectionnode.blockID){
				par1World.notifyBlockOfNeighborChange(par2, par3 + c, par4, Netcraft.connectionnode.blockID);
			} 
			if(par1World.getBlockId(par2, par3, par4 + c)  == Netcraft.connectionnode.blockID){
				par1World.notifyBlockOfNeighborChange(par2, par3, par4 + c, Netcraft.connectionnode.blockID);
			}  //				par1World.notifyBlockOfNeighborChange(par2, par3, par4, Netcraft.connectionnode.blockID);
}
*/
	}
	 @Override
	 public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	 {
			if(par1World.isRemote) {
			if(Netcraft.isConectedToCore(par1World, par2, par3, par4)){

				TileEntityCore tileEntity = (TileEntityCore) par1World.getBlockTileEntity(par2, par3, par4);
			   	  //try {
							tileEntity.update();
				}
					//} catch (Exception e) {
						
					//}
				}
			updateConnection(par1World, par2, par3, par4);
	 }

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		//try{
		if(par1World.isRemote) {
		if(Netcraft.isConectedToCore(par1World, par2, par3, par4)){
      	  TileEntityCore tileEntity = (TileEntityCore) par1World.getBlockTileEntity(Netcraft.getCoreCoordinates(par1World, par2, par3, par4)[0], Netcraft.getCoreCoordinates(par1World, par2, par3, par4)[1], Netcraft.getCoreCoordinates(par1World, par2, par3, par4)[2]);
		 	tileEntity.update();
		}
		}
		//} catch (Exception e) {
			
		//}
		if(!(par5 == 451)) {
		updateConnection(par1World, par2, par3, par4);
		} else {
			updateConnectionWithoutNotify(par1World, par2, par3, par4);
		}
	}
	public void updateConnectionWithoutNotify(World par1World, int par2, int par3, int par4) {
		try {
		if(Netcraft.isConectedToCore(par1World, par2, par3, par4)){
	      	  TileEntityCore tileEntity = (TileEntityCore) par1World.getBlockTileEntity(Netcraft.getCoreCoordinates(par1World, par2, par3, par4)[0], Netcraft.getCoreCoordinates(par1World, par2, par3, par4)[1], Netcraft.getCoreCoordinates(par1World, par2, par3, par4)[2]);
			 	tileEntity.update();
			}
		
	
		} catch (Exception e) {
			
		}
		if(!par1World.isRemote) {
		state = Netcraft.isConectedToCore(par1World, par2, par3, par4);
		if(state == false) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		} else {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
		}
		ArrayList p = Netcraft.getConnectedObjects(par1World, par2, par3, par4);
		for(int i = 0; i < p.size(); i++) {
			int[] coords = (int[]) p.get(i);
			if(Netcraft.isConectedToCore(par1World, par2, par3, par4)) {
				//Block.blocksList[this.blockID].onNeighborBlockChange(par1World, par2, par3, par4, 452);
			} else {
				//Block.blocksList[this.blockID].onNeighborBlockChange(par1World, par2, par3, par4, 452);
			} 
			//Block.blocksList[this.blockID].onNeighborBlockChange(par1World, par2, par3, par4, 0);
			System.out.println("Updating: " + coords[0] + ", " + coords[1] + ", " + coords[2]);
		} 
	}
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
		{
		if(par2 == 4) {
			return icon2;
		} else {
			return icon;
		}
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
	
		this.icon = par1IconRegister.registerIcon(Netcraft.modid + ":" + (this.getUnlocalizedName() + "_off"));
	
		this.icon2 = par1IconRegister.registerIcon(Netcraft.modid + ":" + (this.getUnlocalizedName() + "_on"));    
		
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


	
	
	

}