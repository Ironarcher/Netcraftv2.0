package com.nitrogenegames.netcraft.item;

import java.util.*;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.misc.DamageSourceTesla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;


public class ItemModules extends Item {
	public static EntityPlayer p;
	private Icon iconIndex;
	public int[] projector;
	public Netcraft.EnumProjector ptype;
	public ItemModules(int par1) {
		super(par1); //Returns super constructor: par1 is ID
		setCreativeTab(Netcraft.netcrafttab); //Tells the game what creative mode tab it goes in
		this.setMaxStackSize(1);
		
		
	}
	public boolean tpChecker(int x, int y, int z, ItemStack par1ItemStack, World world) {
		if((!(world.getBlockId(x, y, z) == Netcraft.core.blockID)) && (par1ItemStack.getItem().itemID == Netcraft.tpmodule.itemID)) {
			for(int a = -2;a <=2;a++){
				for(int b = -2;b <=2;b++){
					for(int c = -2; c <=2; c++ ) {
						if(world.getBlockId(x+a, y+b, z+c) == Netcraft.core.blockID){
							return true;
						}
					}
				}
			}
		} else {
			return true;
		}
		return false;
	}

    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
    	if(par1ItemStack.getItem() == Netcraft.deathmodule) {
    		par3List.add("Damages any mob, creature,");
    		par3List.add("or player (changable by player)");
    		par3List.add("in a radius up to the cores range.");
    	} else if(par1ItemStack.getItem() == Netcraft.regenmodule) {
    		par3List.add("Applies a Regeneration II");
    		par3List.add("effect to players in a");
    		par3List.add("radius up to the cores range");
    	} else if(par1ItemStack.getItem() == Netcraft.resistmodule) {
    		par3List.add("Applies a Resistance II");
    		par3List.add("effect to players in a");
    		par3List.add("radius up to the cores range");
    	} else if(par1ItemStack.getItem() == Netcraft.weathermodule) {
    		par3List.add("Toggles the weather");
    		par3List.add("of the Overworld");
    	} else if(par1ItemStack.getItem() == Netcraft.timemodule) {
    		par3List.add("Allows the user to");
    		par3List.add("manipulate the time of day,");
    		par3List.add("or set a permanant time of day");
    	} else if(par1ItemStack.getItem() == Netcraft.powermodule) {
    		par3List.add("Allows the user to");
    		par3List.add("wirelessly supply");
    		par3List.add("power to marked coordinates");
    	} else if(par1ItemStack.getItem() == Netcraft.redmodule) {
    		par3List.add("Allows the user to");
    		par3List.add("wirelessly emit a");
    		par3List.add("redstone signal to");
    		par3List.add("marked coordinates");
    	}
		/*if( !(par1ItemStack.stackTagCompound == null )) {
		NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
		NBTTagList list = itemcomp.getTagList("Marked");
		if(!(list.tagCount() == 0)) {
		par3List.add("Marked Coordinates:");
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			String mark = tag.getString("MarkedThing");
			int[] coords = netcraft.decompileNBT(mark);
			par3List.add(coords[0] + ", " + coords[1] + ", " + coords[2]);
		}
		}
		} */
    }
    
	/*public void NBTLocalSetup(ItemStack par1ItemStack) {

		if( par1ItemStack.stackTagCompound == null )
            par1ItemStack.setTagCompound( new NBTTagCompound( ) );
        NBTTagCompound tagCompound = par1ItemStack.getTagCompound();	
        NBTTagList tagList = tagCompound.getTagList("Marked");
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            marked.add(tag.getString("MarkedThing"));
        } 
	}
	public void NBTGlobalSetup(ItemStack par1ItemStack) {
		if( par1ItemStack.stackTagCompound == null )
            par1ItemStack.setTagCompound( new NBTTagCompound( ) );
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < marked.size(); i++) {
        	NBTTagCompound tag = new NBTTagCompound();
        	tag.setString("MarkedThing", marked.get(i));
        	itemList.appendTag(tag);
        }
        par1ItemStack.getTagCompound().setTag("Marked", itemList);
	}
	*/
	

	public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {

	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(!world.isRemote) {
			if (!player.isSneaking()) {
				p = player;
				player.openGui(Netcraft.instance, 0, world, 0, 0, 0);
				//}
				
			}
		}
		return itemStack;
	}
	public static boolean canUseProjector(ItemModules m, int e) {
		if(m.getUnlocalizedName().equals("item.weathermodule")) {
			return e == 2;
		} else if((m.getUnlocalizedName().equals("item.regenmodule"))||(m.getUnlocalizedName().equals("item.resistmodule"))||(m.getUnlocalizedName().equals("item.deathmodule")||m.getUnlocalizedName().equals("item.tpmodule")) || (m.getUnlocalizedName().equals("item.atpmodule"))) {
			return ((e == 1) || (e == 0));
		} else {
			return false;
		}
	}
	public boolean activate(World world, ItemStack itemStack, int x, int y, int z) {
		if(world.getBlockId(x, y, z) != Netcraft.projector.blockID) {
			return false;
		}
		if(!canUseProjector(this, world.getBlockPowerInput(x, y, z))) {
			return false;
		}
		if (this.getUnlocalizedName().equals("item.weathermodule")) {
			MinecraftServer.getServer().worldServers[0].toggleRain();
			return true;
			//MinecraftServer.getServer().worldServers[0].getWorldInfo().setThundering(true);
		} else if (this.getUnlocalizedName().equals("item.regenmodule")) {
		    NBTTagCompound tagCompound = itemStack.getTagCompound();	
		    int range = tagCompound.getInteger("Range");
			List entities = new ArrayList<EntityPlayer>();
			entities = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range));
			for (int i = 0; i < entities.size(); i++) 
			{
				EntityPlayer mob = (EntityPlayer) entities.get(i);
				mob.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 60, 1));
			}
			return true;
		} else if (this.getUnlocalizedName().equals("item.resistmodule")) {
		    NBTTagCompound tagCompound = itemStack.getTagCompound();	
		    int range = tagCompound.getInteger("Range");
			List entities = new ArrayList<EntityLiving>();
			entities = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range));
			for (int i = 0; i < entities.size(); i++) 
			{
				EntityPlayer mob = (EntityPlayer) entities.get(i);
				mob.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 60, 1));
			}
			return true;
		} else if (this.getUnlocalizedName().equals("item.deathmodule")) {
		    NBTTagCompound tagCompound = itemStack.getTagCompound();	
		    int trange = tagCompound.getInteger("Range");
		    if(trange == 0) {
		    	trange = 20; //PUT DEFAULT RANGE HERE
		    }
		    int type = tagCompound.getInteger("Mode");
		    if(type == 0) {
		    	type = 3; //PUT DEFAULT MODE HERE
		    }
			List entities = new ArrayList<EntityLiving>();
			Class mode;
			if (type == 1) {
				mode = EntityMob.class;
			} else if (type == 2) {
				mode = EntityCreature.class;
			} else {
				mode = EntityLivingBase.class;
			}



			entities = world.getEntitiesWithinAABB(mode, AxisAlignedBB.getBoundingBox(x - trange, y - trange, z - trange, x + trange, y + trange, z + trange));

				
			for (int i = 0; i < entities.size(); i++) 
			{
				if(i < entities.size()) {
					DamageSourceTesla ds = new DamageSourceTesla("net");

					EntityLivingBase mob = (EntityLivingBase) entities.get(i);
					mob.attackEntityFrom(ds, 4);
				}
				
				
			}
			return true;
		} else if ((this.getUnlocalizedName().equals("item.tpmodule")) || (this.getUnlocalizedName().equals("item.atpmodule"))) {
			int trange = 10;
			List entities = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x - trange, y - trange, z - trange, x + trange, y + trange, z + trange));
			for (int i = 0; i < entities.size(); i++) 
			{

				if( itemStack.stackTagCompound == null )
		            itemStack.setTagCompound( new NBTTagCompound( ) );
		        NBTTagCompound tagCompound = itemStack.getTagCompound();	
		        NBTTagList tagList = tagCompound.getTagList("Marked");
		        for (int l = 0; l < tagList.tagCount(); l++) {
		            if(l == tagCompound.getInteger("Selected")) {
		                NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(l);
		                String s = tag.getString("MarkedThing");
		                int[] coords = Netcraft.decompileNBT(s);
		                if(tpChecker(coords[0], coords[1], coords[2], itemStack, world) || (this.getUnlocalizedName().equals("item.atpmodule"))) {
							EntityPlayer mob = (EntityPlayer) entities.get(i);
							mob.setPositionAndUpdate(coords[0] + .5, coords[1] + 1, coords[2] + .5);
		                } else {
							EntityPlayer mob = (EntityPlayer) entities.get(i);
							//mob.setPositionAndUpdate(coords[0] + .5, coords[1] + 1, coords[2] + .5);
		                	mob.addChatMessage("A nearby core attempted to teleport you to an invalid coordinate! (No core nearby)");
		                }
		            }
		            
		        } 


				
				
			}
			return true;
		}
		return false;
	}
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer thePlayer, World par2World, int x, int y, int z, int par7, float xFloat, float yFloat, float zFloat)
	{
		
		if(!par2World.isRemote && (Netcraft.isMarkableModule(par1ItemStack) || Netcraft.isSelectiveModule(par1ItemStack))) {
			if( par1ItemStack.stackTagCompound == null ) {
	            par1ItemStack.setTagCompound( new NBTTagCompound( ) );
			}
		thePlayer.swingItem();

		thePlayer.setItemInUse(par1ItemStack, 0);
			
		if (thePlayer.isSneaking()) {
			thePlayer.swingItem();
			/*if((par2World.getBlockId(x, y, z) == Netcraft.projectorBeam.blockID)) {
				if(this.canUseProjector(this, Netcraft.EnumProjector.BEAM)) {
					NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
					itemcomp.setString("projector", x + "," + y + "," + z);
					return false;
	
				}
			} else 	if((par2World.getBlockId(x, y, z) == Netcraft.projectorRadial.blockID)) {
				if(this.canUseProjector(this, Netcraft.EnumProjector.CIRCULAR)) {
					NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
					itemcomp.setString("projector", x + "," + y + "," + z);
					return false;
				}
			} else 	if((par2World.getBlockId(x, y, z) == Netcraft.projectorSatelite.blockID)) {
				if(this.canUseProjector(this, Netcraft.EnumProjector.SATELITE)) {
					NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
					itemcomp.setString("projector", x + "," + y + "," + z);
					return false;
				}
			} */
			if (!isMarked(x, y, z, par1ItemStack)) {
				if(!tpChecker(x, y, z, par1ItemStack, par2World)) {
					thePlayer.addChatMessage("Block must be within a 3 by 3 radiues of a core to be markable!");
				} else {
				thePlayer.addChatMessage("Block marked at " + x + ", " + y + ", " + z + "!");
				boolean s = (Netcraft.isConectedToCore(par2World, x, y, z));
				if (s == true) {
					int[] coords = Netcraft.getCoreCoordinates(par2World, x, y, z);
					if (coords != null ) {
					thePlayer.addChatMessage("Block is connected to core at " + coords[0] + " " + coords[1] + " " + coords[2] + "!");
				
					}
				}
					

				String coords = x + "," + y + "," + z;

				NBTTagCompound comp = new NBTTagCompound();

				comp.setString("MarkedThing", coords);
				NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
				NBTTagList list = itemcomp.getTagList("Marked");
				list.appendTag(comp);
		        itemcomp.setTag("Marked", list);

		       
				}
			} else {
				thePlayer.addChatMessage("Block unmarked at " + x + ", " + y + ", " + z + "!");
				String coords = x + "," + y + "," + z;
				NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
				NBTTagList tagList = itemcomp.getTagList("Marked");
				//thePlayer.addChatMessage("Block aleady marked!");

		        for (int i = 0; i < tagList.tagCount(); i++) {
		            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
		            if (tag.getString("MarkedThing").equals(coords)) {
		            	tagList.removeTag(i);
		            }
		        } 
		        itemcomp.setTag("Marked", tagList);
		        

			}
			return false;
		} else {
//Open gui
			 
		return false;
		}
		} else {
			if (thePlayer.isSneaking()) {
				thePlayer.swingItem();
			}
			thePlayer.setItemInUse(par1ItemStack, 0);
			return false;
		}

		
			
	}
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) {

	}
	public boolean isMarked(int x, int y, int z, ItemStack par1ItemStack) {
		/*if( par1ItemStack.stackTagCompound == null )
            par1ItemStack.setTagCompound( new NBTTagCompound( ) );
            */
		NBTTagCompound itemcomp = par1ItemStack.getTagCompound();
		NBTTagList list = itemcomp.getTagList("Marked");
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			String mark = tag.getString("MarkedThing");
			if (mark.equals(x + "," + y + "," + z)) {
				return true;
			}
		}
		return false;
	}
	
	public int getEuUsed() {
		//this.getUnlocalizedName().equals("item.tpmodule") YADAYADA LATER
		/*if(this.getUnlocalizedName().equals("item.tpmodule")) {
			return 10;
		}*/ //piece of cake
		switch(this.getUnlocalizedName()){
		case "item.deathmodule": return 20;
		case "item.regenmodule": return 80;
		case "item.resistmodule": return 120;
		}
		return 20;
	}
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Netcraft.modid + ":" + this.getUnlocalizedName());
    }

}


