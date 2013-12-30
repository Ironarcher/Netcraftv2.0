package com.nitrogenegames.netcraft;

import java.util.ArrayList;

import com.nitrogenegames.netcraft.block.BlockCore;
import com.nitrogenegames.netcraft.block.BlockMatrixCube;
import com.nitrogenegames.netcraft.block.BlockNetworkFabricator;
import com.nitrogenegames.netcraft.block.BlockNodeBase;
import com.nitrogenegames.netcraft.block.BlockNodeCondition;
import com.nitrogenegames.netcraft.block.BlockNodeConnection;
import com.nitrogenegames.netcraft.block.BlockNodeInput;
import com.nitrogenegames.netcraft.gui.GuiHandler;
import com.nitrogenegames.netcraft.item.ItemCoil;
import com.nitrogenegames.netcraft.item.ItemCrafting;
import com.nitrogenegames.netcraft.item.ItemModuleBase;
import com.nitrogenegames.netcraft.item.ItemModules;
import com.nitrogenegames.netcraft.item.ItemUpgrade;
import com.nitrogenegames.netcraft.machine.TileEntityCore;
import com.nitrogenegames.netcraft.machine.TileEntityNetworkFabricator;
import com.nitrogenegames.netcraft.misc.PacketHandler;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Netcraft.modid, name = "Netcraft", version = "Reloaded 0.0.1")
@NetworkMod(channels = { "corepack", "coretab" }, packetHandler = PacketHandler.class)
public class Netcraft {
	public static final String modid = "netcraft";
	
	@Instance("netcraft")
	public static Netcraft instance = new Netcraft();
	
	public static final int GuiIDNetworkFabricator = 1;
	
	//basic crafting for starting	
	public static Block blockNetworkFabricatorActive;
	public static Block blockNetworkFabricatorIdle;
	public static Item glassfibrecoil;
	public static Item highvoltagecoil;
	public static Block core;
	public static Item netdatachip;
	public static Block matrixcube;
	public static Item communicator;
	public static Item centraldatachip;
	public static Block node;
	public static Item connector;
	public static Item superdatachip;
	public static Item forcefieldemitters;
	public static Item nuclearalloy; //texture

	//modules
	public static Item design;
	public static Item redmodule;
	public static Item deathmodule;
	public static Item powermodule;
	public static Item regenmodule;
	public static Item resistmodule;
	public static Item weathermodule; //req
	public static Item timemodule; //req
	public static Item tpmodule; 
	public static Item atpmodule; 

	//nodes
	public static Block connectionnode;
	public static Block conditionalnode;
	public static Block inputnode; //req, 1.6

	//upgrades
	public static Item baseupgrade;
	public static Item euupgrade;
	public static Item storageupgrade;
	public static Item rangeupgrade;
	public static Item capacitorupgrade;
	public static Item coreupgrade;
	//misc

	public static CreativeTabs netcrafttab = new NetcraftTab(CreativeTabs.getNextID(), "Netcraft");

	public static boolean isModule(ItemStack itemstack) {
		Item par1item = itemstack.getItem();
		if (par1item == Netcraft.redmodule || par1item == Netcraft.timemodule|| par1item == Netcraft.weathermodule|| par1item == Netcraft.resistmodule|| par1item == Netcraft.regenmodule|| par1item == Netcraft.deathmodule || par1item == Netcraft.powermodule || par1item == Netcraft.tpmodule || par1item == Netcraft.atpmodule) {
			return true;
		} else {
			return false;
		}
			
	}
	public static boolean isNodeConnectedToCore(World world, int x, int y, int z, ArrayList p) {
		boolean isC = false;
		boolean isCC = false;
		for(int c = -1; c <= 1; c++) {
			if(!(c==0)) {
					if(world.getBlockId(x + c, y, z)  == connectionnode.blockID) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
						if(!isC) {
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x + c)) && (coords[1] == (y)) && (coords[2] == (z)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x + c) + "," + (y) + "," + (z));
						isC = isNodeConnectedToCore(world, x + c, y, z, p);
						}
						}
					} 
					if(world.getBlockId(x, y + c, z)  == connectionnode.blockID) {
						//System.out.println("" + (x) + "," + (y + c) + "," + (z));
						if(!isC) {
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {
							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y + c)) && (coords[2] == (z)) )) {
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
							p.add((x) + "," + (y + c) + "," + (z));
						isC = isNodeConnectedToCore(world, x, y + c, z, p);
						}
						}
					}
					if(world.getBlockId(x, y, z + c)  == connectionnode.blockID) {
						//System.out.println("" + (x) + "," + (y) + "," + (z + c));
						if(!isC) {
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {
							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y)) && (coords[2] == (z + c)) )) {
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
							p.add((x) + "," + (y) + "," + (z + c));
						isC = isNodeConnectedToCore(world, x, y, z + c, p);
						}
						}
					}
					if(world.getBlockId(x + c, y, z)  == core.blockID) {
						isC = true;
					} 
					if(world.getBlockId(x, y + c, z)  == core.blockID) {
						isC = true;
					} 
					if(world.getBlockId(x, y, z + c)  == core.blockID) {
						isC = true;
					}	
			}
		}
		return isC;
	}
	public TileEntityCore getClosestCoreTileEntity (World world, int x, int y, int z) {
		int[] coords = getCoreCoordinates(world, x, y, z);
		return (TileEntityCore) world.getBlockTileEntity(coords[0], coords[1], coords[2]);
	}
	public static int[] getCoreCoordinates(World world, int x, int y, int z) {
		boolean isC = false;
		int[] coords = new int[3];
		for(int c = -1; c <= 1; c++) {
					if(world.getBlockId(x + c, y, z)  == connectionnode.blockID){
						if(!isC) {
						ArrayList l = new ArrayList();
						l.add("" + (x + c) + "," + (y) + "," + (z));
						isC = isNodeConnectedToCore(world, x + c, y, z, l);
						if(isC) {
							coords = getCoreCoordinatesFromNode(world, x + c, y, z, l);
						}
						}
					}
					if(world.getBlockId(x, y + c, z)  == connectionnode.blockID){
						if(!isC) {
						ArrayList l = new ArrayList();
						l.add("" + (x) + "," + (y + c) + "," + (z));
						isC = isNodeConnectedToCore(world, x, y + c, z, l);
						if(isC) {
							coords = getCoreCoordinatesFromNode(world, x, y + c, z, l);
						}
						}
					} 
					if(world.getBlockId(x, y, z + c)  == connectionnode.blockID){
						if(!isC) {
						ArrayList l = new ArrayList();
						l.add("" + (x) + "," + (y) + "," + (z + c));
						isC = isNodeConnectedToCore(world, x, y, z + c, l);
						if(isC) {
							coords = getCoreCoordinatesFromNode(world, x, y, z + c, l);
						}
						}
					} 
					if(world.getBlockId(x + c, y, z)  == core.blockID) {
						isC = true;
						coords[0] = x+c;
						coords[1] = y;
						coords[2] = z;
					} 
					if(world.getBlockId(x, y + c, z)  == core.blockID) {
						isC = true;
						coords[0] = x;
						coords[1] = y+c;
						coords[2] = z;
					} 
					if(world.getBlockId(x, y, z + c)  == core.blockID) {
						isC = true;
						coords[0] = x;
						coords[1] = y;
						coords[2] = z+c;
					}	
		}
		if(isC) {
			return coords;
		} else {
			return null;
		}

	}
	private static ArrayList convertToArray(ArrayList p) {
		ArrayList e = new ArrayList();
		for(int i = 0; i < p.size(); i++) {
			int[] coords = decompileNBT((String) p.get(i));
			e.add(coords);
		}
		return e;
	}
	public static ArrayList getConnectedObjects(World world, int x, int y, int z) {
		ArrayList p = new ArrayList();
		for(int c = -1; c <= 1; c++) {
			if(!(c==0)) {
				if(isNetConductable(world.getBlockId(x + c, y, z))) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x + c)) && (coords[1] == (y)) && (coords[2] == (z)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x + c) + "," + (y) + "," + (z));
							p = getConnectedObjectsFromNode(world, x + c, y, z, p);
						}

				} else 	if(isNetObject(world.getBlockId(x + c, y, z))) {
					boolean repeat = false;
				for(int i = 0; i < p.size(); i++) {

					int[] coords = decompileNBT((String) p.get(i));
					if(((coords[0] == (x+c)) && (coords[1] == (y)) && (coords[2] == (z)) )) {
						
						repeat = true;
					} 
				}
				if(!(repeat == true)) {
				p.add((x+ c) + "," + (y) + "," + (z));
				}
			} 
				if(isNetConductable(world.getBlockId(x, y + c, z))) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y + c)) && (coords[2] == (z)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x) + "," + (y + c) + "," + (z));
							p = getConnectedObjectsFromNode(world, x, y + c, z, p);
						}

				} else 	if(isNetObject(world.getBlockId(x, y + c, z))) {
					boolean repeat = false;
				for(int i = 0; i < p.size(); i++) {

					int[] coords = decompileNBT((String) p.get(i));
					if(((coords[0] == (x)) && (coords[1] == (y + c)) && (coords[2] == (z)) )) {
						
						repeat = true;
					} 
				}
				if(!(repeat == true)) {
				p.add((x) + "," + (y + c) + "," + (z));
				}
			} 
				if(isNetConductable(world.getBlockId(x, y, z + c))) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y)) && (coords[2] == (z + c)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x) + "," + (y) + "," + (z + c));
							p = getConnectedObjectsFromNode(world, x, y, z + c, p);
						}

				} else 	if(isNetObject(world.getBlockId(x, y, z + c))) {
						boolean repeat = false;
					for(int i = 0; i < p.size(); i++) {
	
						int[] coords = decompileNBT((String) p.get(i));
						if(((coords[0] == (x)) && (coords[1] == (y)) && (coords[2] == (z + c)) )) {
							
							repeat = true;
						} 
					}
					if(!(repeat == true)) {
					p.add((x) + "," + (y) + "," + (z + c));
					}
				} 

			}
		

		}
		return convertToArray(p);
	}
	private static ArrayList getConnectedObjectsFromNode(World world, int x, int y, int z, ArrayList p) {
		
		for(int c = -1; c <= 1; c++) {
			if(!(c==0)) {
				if(isNetConductable(world.getBlockId(x + c, y, z))) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x + c)) && (coords[1] == (y)) && (coords[2] == (z)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x + c) + "," + (y) + "," + (z));
							p = getConnectedObjectsFromNode(world, x + c, y, z, p);
						}

				} else 	if(isNetObject(world.getBlockId(x + c, y, z))) {
					boolean repeat = false;
				for(int i = 0; i < p.size(); i++) {

					int[] coords = decompileNBT((String) p.get(i));
					if(((coords[0] == (x+c)) && (coords[1] == (y)) && (coords[2] == (z)) )) {
						
						repeat = true;
					} 
				}
				if(!(repeat == true)) {
				p.add((x+ c) + "," + (y) + "," + (z));
				}
			} 
				if(isNetConductable(world.getBlockId(x, y + c, z))) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y + c)) && (coords[2] == (z)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x) + "," + (y + c) + "," + (z));
							p = getConnectedObjectsFromNode(world, x, y + c, z, p);
						}

				} else 	if(isNetObject(world.getBlockId(x, y + c, z))) {
					boolean repeat = false;
				for(int i = 0; i < p.size(); i++) {

					int[] coords = decompileNBT((String) p.get(i));
					if(((coords[0] == (x)) && (coords[1] == (y + c)) && (coords[2] == (z)) )) {
						
						repeat = true;
					} 
				}
				if(!(repeat == true)) {
				p.add((x) + "," + (y + c) + "," + (z));
				}
			} 
				if(isNetConductable(world.getBlockId(x, y, z + c))) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y)) && (coords[2] == (z + c)) )) {
								
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
						p.add((x) + "," + (y) + "," + (z + c));
							p = getConnectedObjectsFromNode(world, x, y, z + c, p);
						}

				} else 	if(isNetObject(world.getBlockId(x, y, z + c))) {
						boolean repeat = false;
					for(int i = 0; i < p.size(); i++) {
	
						int[] coords = decompileNBT((String) p.get(i));
						if(((coords[0] == (x)) && (coords[1] == (y)) && (coords[2] == (z + c)) )) {
							
							repeat = true;
						} 
					}
					if(!(repeat == true)) {
					p.add((x) + "," + (y) + "," + (z + c));
					}
				} 

			}
		

		}
		return p;
	}
	private static int[] getCoreCoordinatesFromNode(World world, int x, int y,
			int z, ArrayList p) {
		boolean isC = false;
		int[] scoords = new int[3];
		for(int c = -1; c <= 1; c++) {
			if(!(c==0)) {
					if(world.getBlockId(x + c, y, z)  == connectionnode.blockID) {
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
						if(!isC) {
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {

							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x + c)) && (coords[1] == (y)) && (coords[2] == (z)) )) {
								
								repeat = true;
							} 
						}

						if(!(repeat == true)) {
						p.add((x + c) + "," + (y) + "," + (z));
						isC = isNodeConnectedToCore(world, x + c, y, z, p);
						if(isC) {
							scoords = getCoreCoordinatesFromNode(world, x + c, y, z, p);
						}
						}
						}
					} 
					if(world.getBlockId(x, y + c, z)  == connectionnode.blockID) {
						//System.out.println("" + (x) + "," + (y + c) + "," + (z));
						if(!isC) {
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {
							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y + c)) && (coords[2] == (z)) )) {
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
							p.add((x) + "," + (y + c) + "," + (z));
						isC = isNodeConnectedToCore(world, x, y + c, z, p);
						if(isC) {
							scoords = getCoreCoordinatesFromNode(world, x , y + c, z, p);
						}
						}
						}
					}
					if(world.getBlockId(x, y, z + c)  == connectionnode.blockID) {

						//System.out.println("" + (x) + "," + (y) + "," + (z + c));
						if(!isC) {
							boolean repeat = false;
						for(int i = 0; i < p.size(); i++) {
							int[] coords = decompileNBT((String) p.get(i));
							if(((coords[0] == (x)) && (coords[1] == (y)) && (coords[2] == (z + c)) )) {
								repeat = true;
							} 
						}
						if(!(repeat == true)) {
							p.add((x) + "," + (y) + "," + (z + c));
						isC = isNodeConnectedToCore(world, x, y, z + c, p);
						System.out.println(isC);
						if(isC) {
							scoords = getCoreCoordinatesFromNode(world, x, y, z + c, p);
						}
						}
						}
					}
					if(world.getBlockId(x + c, y, z)  == core.blockID) {
						isC = true;
						scoords[0] = x + c;
						scoords[1] = y;
						scoords[2] = z;
					} 
					if(world.getBlockId(x, y + c, z)  == core.blockID) {
						isC = true;
						scoords[0] = x;
						scoords[1] = y + c;
						scoords[2] = z;
					} 
					if(world.getBlockId(x, y, z + c)  == core.blockID) {
						isC = true;
						scoords[0] = x;
						scoords[1] = y;
						scoords[2] = z + c;
					}	
			}
		}
		if(isC) {
			return scoords;
		} else {
			return null;
		}

	}
	public static boolean isConectedToCore(World world, int x, int y, int z) {
		boolean isC = false;
		for(int c = -1; c <= 1; c++) {
					if(world.getBlockId(x + c, y, z)  == connectionnode.blockID){
						//System.out.println("" + (x + c) + "," + (y) + "," + (z));
						if(!isC) {
						ArrayList l = new ArrayList();
						l.add((x + c) + "," + (y) + "," + (z));
						isC = isNodeConnectedToCore(world, x + c, y, z, l);
						}
					} 
					if(world.getBlockId(x, y + c, z)  == connectionnode.blockID){
						//System.out.println("" + (x) + "," + (y + c) + "," + (z));
						if(!isC) {
						ArrayList l = new ArrayList();
						l.add((x) + "," + (y + c) + "," + (z));
						isC = isNodeConnectedToCore(world, x, y + c, z, l);
						}
					} 
					if(world.getBlockId(x, y, z + c)  == connectionnode.blockID){
						//System.out.println("" + (x) + "," + (y) + "," + (z + c));
						if(!isC) {
						ArrayList l = new ArrayList();
						l.add((x) + "," + (y) + "," + (z + c));
						isC = isNodeConnectedToCore(world, x, y, z + c, l);
						}
					} 
					if(world.getBlockId(x + c, y, z)  == core.blockID) {
						isC = true;
					} 
					if(world.getBlockId(x, y + c, z)  == core.blockID) {
						isC = true;
					} 
					if(world.getBlockId(x, y, z + c)  == core.blockID) {
						isC = true;
					}	
		}
		return isC;
	}
	public static boolean isSelectiveModule(ItemStack itemstack) {
		if(isModule(itemstack)) {
			Item par1item = itemstack.getItem();
		
			if (par1item == Netcraft.tpmodule || par1item == Netcraft.atpmodule) {
				return true;
			}
		}
		return false;
	}
	public static boolean isMarkableModule(ItemStack itemstack) {
		if(isModule(itemstack)) {
			Item par1item = itemstack.getItem();
		
			if (par1item == Netcraft.redmodule || par1item == Netcraft.powermodule) {
				return true;
			}
		}
		return false;
	}
	public static boolean isRangedModule(ItemStack itemstack) {
		if(isModule(itemstack)) {
			Item par1item = itemstack.getItem();
		
			if (par1item == Netcraft.deathmodule || par1item == Netcraft.regenmodule || par1item == Netcraft.resistmodule) {
				return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e){
		
		//declarations
		blockNetworkFabricatorActive = new BlockNetworkFabricator(3850, true).setUnlocalizedName("networkfabricatoractive").setLightValue(0.8f);
		blockNetworkFabricatorIdle = new BlockNetworkFabricator(3851, false).setUnlocalizedName("networkfabricatoridle").setCreativeTab(netcrafttab);
		glassfibrecoil = new ItemCoil(3813).setUnlocalizedName("glassfibrecoil");
		highvoltagecoil = new ItemCoil(3814).setUnlocalizedName("highvoltagecoil");
		netdatachip = new ItemCrafting(3816, false).setUnlocalizedName("netdatachip");
		core= new BlockCore(3817, "core").setUnlocalizedName("core");
		matrixcube= new BlockMatrixCube(3815, "matrixcube").setUnlocalizedName("matrixcube");
		communicator = new ItemCrafting(3819, false).setUnlocalizedName("communicator");
		centraldatachip = new ItemCrafting(3820, false).setUnlocalizedName("centraldatachip");
		connector = new ItemCrafting(3826, false).setUnlocalizedName("connector");;
		design = new ItemModuleBase(3834).setUnlocalizedName("design");
		redmodule = new ItemModules(3831).setUnlocalizedName("redmodule");
		deathmodule = new ItemModules(3832).setUnlocalizedName("deathmodule");
		powermodule = new ItemModules(3833).setUnlocalizedName("powermodule");
		node= new BlockNodeBase(3821, "node").setUnlocalizedName("node");
		connectionnode= new BlockNodeConnection(3823, "connectionnode").setUnlocalizedName("connectionnode");
		conditionalnode= new BlockNodeCondition(3825, "conditionalnode").setUnlocalizedName("conditionalnode");
		regenmodule = new ItemModules(3835).setUnlocalizedName("regenmodule");
		resistmodule = new ItemModules(3836).setUnlocalizedName("resistmodule");
		weathermodule = new ItemModules(3837).setUnlocalizedName("weathermodule");
		timemodule = new ItemModules(3838).setUnlocalizedName("timemodule");
		inputnode= new BlockNodeInput(3839, "inputnode").setUnlocalizedName("inputnode");
		tpmodule = new ItemModules(3840).setUnlocalizedName("tpmodule");
		atpmodule = new ItemModules(3841).setUnlocalizedName("atpmodule");
		euupgrade = new ItemUpgrade(3827).setUnlocalizedName("euupgrade");
		storageupgrade = new ItemUpgrade(3828).setUnlocalizedName("storageupgrade");
		rangeupgrade = new ItemUpgrade(3829).setUnlocalizedName("rangeupgrade");
		capacitorupgrade = new ItemUpgrade(3830).setUnlocalizedName("capacitorupgrade");
		coreupgrade = new ItemUpgrade(3842).setUnlocalizedName("coreupgrade");
		baseupgrade = new ItemCrafting(3843, false).setUnlocalizedName("baseupgrade");
		superdatachip = new ItemCrafting(3844, true).setUnlocalizedName("superdatachip");
		forcefieldemitters = new ItemCrafting(3845, false).setUnlocalizedName("forcefieldemitters");
		nuclearalloy = new ItemCrafting(3846, true).setUnlocalizedName("nuclearalloy");
	
		register();
		
		//gui
		GameRegistry.registerTileEntity(TileEntityNetworkFabricator.class, "tilEntityNetworkFabricator");
		LanguageRegistry.instance().addStringLocalization("container.networkFabricator", "Network Fabricator");
		GameRegistry.registerTileEntity(TileEntityCore.class, "tileEntityCore");
	    NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
	}
	
	
	
	public void registerBlock(Block block, String name){
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
		LanguageRegistry.addName(block, name);
	}
	
	public void registerItem(Item item, String name){
		GameRegistry.registerItem(item, item.getUnlocalizedName());
		LanguageRegistry.addName(item, name);
	}
	
	public void register(){
		registerBlock(blockNetworkFabricatorActive, "Network Fabricator Active");
		registerBlock(blockNetworkFabricatorIdle, "Network Fabricator");
		registerBlock(matrixcube, "Matrix Cube");
		registerBlock(core, "Core");
		registerBlock(node, "Node");
		registerBlock(connectionnode, "Connection Node");
		registerBlock(conditionalnode, "Conditional Node");
		registerBlock(inputnode, "Input Node");
		registerItem(glassfibrecoil, "Glass Fibre Coil");
		registerItem(highvoltagecoil, "High Voltage Coil");
		registerItem(netdatachip, "Net Datachip");
		registerItem(communicator, "Communicator");
		registerItem(centraldatachip, "Central Datachip");
		registerItem(connector, "Connector");
		registerItem(design, "Design");
		registerItem(redmodule, "Redstone Emmission Module");
		registerItem(deathmodule, "Tesla Field Module");
		registerItem(powermodule, "Power Emmission Module");
		registerItem(regenmodule, "Regeneration Module");
		registerItem(resistmodule,"Resistance Module");
		registerItem(weathermodule, "Weather Module");
		registerItem(timemodule, "Celestial Manipulation Module");
		registerItem(tpmodule, "Basic Teleportation Module");
		registerItem(atpmodule, "Advanced Teleportation Module");
		registerItem(baseupgrade, "Upgrade Template");
		registerItem(euupgrade, "Energy Expansion Upgrade");
		registerItem(storageupgrade, "Module Stroage Upgrade");
		registerItem(rangeupgrade, "Range Upgrade");
		registerItem(capacitorupgrade, "Quantum Capacitor Upgrade");
		registerItem(coreupgrade, "Basic Upgrade");
		registerItem(superdatachip, "Supernatural Datachip");
		registerItem(forcefieldemitters, "Force Field Emitter");
		registerItem(nuclearalloy, "Nuclear Alloy");
		//register block through game registry
		/*
		GameRegistry.registerBlock(matrixcube, "matrixcube");
		GameRegistry.registerBlock(core, "core");
		GameRegistry.registerBlock(node, "node");
		GameRegistry.registerBlock(connectionnode, "connection_node");
		GameRegistry.registerBlock(conditionalnode, "conditional_node");
		GameRegistry.registerBlock(inputnode, "input_node");\
		//add block to language registry
		LanguageRegistry.addName(glassfibrecoil, "Glass Fibre Coil");
		LanguageRegistry.addName(highvoltagecoil, "High Voltage Coil");
		LanguageRegistry.addName(netdatachip, "Net Datachip");
		LanguageRegistry.addName(matrixcube, "Matrix Cube");
		LanguageRegistry.addName(core, "Core");
		LanguageRegistry.addName(communicator, "Communicator");
		LanguageRegistry.addName(centraldatachip, "Central Datachip");
		LanguageRegistry.addName(connector, "Connector");
		LanguageRegistry.addName(design, "Design");
		LanguageRegistry.addName(redmodule, "Redstone Emission Module");
		LanguageRegistry.addName(deathmodule, "Tesla Field Module");
		LanguageRegistry.addName(powermodule, "Power Emission Module");
		LanguageRegistry.addName(node, "Node");
		LanguageRegistry.addName(connectionnode, "Connection Node");
		LanguageRegistry.addName(conditionalnode, "Conditional Node");
		LanguageRegistry.addName(inputnode, "Input Node");
		LanguageRegistry.addName(regenmodule, "Regenerative Module");
		LanguageRegistry.addName(resistmodule, "Resistive Module");
		LanguageRegistry.addName(weathermodule, "Weather Module");
		LanguageRegistry.addName(timemodule, "Celestial Manipulation Module");
		LanguageRegistry.addName(tpmodule, "Basic Teleportation Module");
		LanguageRegistry.addName(atpmodule, "Advanced Teleportation Module");
		LanguageRegistry.addName(baseupgrade, "Upgrade Template");
		LanguageRegistry.addName(euupgrade, "Energy Expansion Upgrade");
		LanguageRegistry.addName(storageupgrade, "Module Storage Upgrade");
		LanguageRegistry.addName(rangeupgrade, "Range Upgrade");
		LanguageRegistry.addName(capacitorupgrade, "Quantum Capacitor Upgrade");
		LanguageRegistry.addName(coreupgrade, "Basic Upgrade");
		LanguageRegistry.addName(superdatachip, "Supernatural Datachip");
		LanguageRegistry.addName(forcefieldemitters, "Force Field Emitters");
		LanguageRegistry.addName(nuclearalloy, "Nuclear Alloy");
		*/
	}
	
	public static int[] decompileNBT(String s)
	{
		int[] coords = new int[3];
		int length = s.length();
		int stop1 = 0;
		int stop2 = 0;
		String toadd = "";
		for (int i = 0; i < length; i++)
		{
			if (s.substring(i,  i + 1).equals(","))
			{
				coords[0] = Integer.parseInt(s.substring(0, i));
				stop1 = i;
				break;
			}		
		}
		
		for (int i = stop1 + 1; i < length; i++)
		{
			if (s.substring(i,  i + 1).equals(","))
			{
				coords[1] = Integer.parseInt(s.substring(stop1 + 1, i));
				stop2 = i;
				break;
			}			
		}
		
		for (int i = stop2 + 1; i < length; i++)
		{
			toadd = toadd + s.substring(i,  i + 1);			
		}
		coords[2] = Integer.parseInt(toadd);
		return coords;

	}
	public static boolean isNetConductable(int bid) {
		if(bid == core.blockID || bid == connectionnode.blockID) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isNetObject(int bid) {
		if(bid == core.blockID || bid == connectionnode.blockID || bid == conditionalnode.blockID) {
			return true;
		} else {
			return false;
		}
	}

}
