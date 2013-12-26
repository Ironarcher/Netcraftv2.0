package com.nitrogenegames.netcraft.block;

import java.util.Random;

import com.nitrogenegames.netcraft.Netcraft;

import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockNodeBase extends Block {



public BlockNodeBase(int par1, String texture) {
	super(par1, Material.rock);
		setCreativeTab(Netcraft.netcrafttab); //place in creative tabs
		setHardness(4f);
		setResistance(10f);
		setLightValue(0f);
		setStepSound(Block.soundMetalFootstep);
	}

	//drops when broken with pickaxe
	public int idDropped(int par1, Random par2Random, int par3)
		{
		return Netcraft.node.blockID;
		}
	
	public int quantityDropped(Random random)
		{
		return 1;
		}
	//texure the block (Not sure if it's required)
	//public String getTextureFile(){
	//return "/textures/blocks/TEXTURE_NAME.png";
	//}
	
	
	
	

}