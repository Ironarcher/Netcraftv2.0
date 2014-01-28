package com.nitrogenegames.netcraft.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.ContainerCore;
import com.nitrogenegames.netcraft.machine.SlotCore;
import com.nitrogenegames.netcraft.machine.TileEntityCore;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class GuiCore extends GuiContainer {
	
		TileEntityCore tel;
		ArrayList<GuiTabButton> tabs;
		private int selected = 0;
		
		//public boolean tabbed = false;
		//int x,y;
		
        public GuiCore (InventoryPlayer inventoryPlayer, TileEntityCore tileEntity) {
                //the container is instanciated and passed to the superclass for handling
                super(new ContainerCore(inventoryPlayer, tileEntity));
                tel = tileEntity;
                
                this.xSize = 252;
                this.ySize = 166;
        }
        public static int getX() {
            return 112;
        }
        public static int getY() {
            return 44;
        }
        @Override
        protected void drawGuiContainerForegroundLayer(int param1, int param2) {

                //draw text and stuff here
                //the parameters for drawString are: string, x, y, color
        		//tel = (TileEntityCore) tel.worldObj.getBlockTileEntity(tel.xCoord, tel.yCoord, tel.zCoord);
        	ContainerCore parentContainer = (ContainerCore) super.inventorySlots;
        	//System.out.println(tel.energy);
        	//System.out.println(parentContainer.tileEntity.energy);

                fontRenderer.drawString("Net Core", 66 + 25, 6, 4210752);
                //202, 252, middle is 227
                int ewidth = fontRenderer.getStringWidth("EU:");
                fontRenderer.drawString("EU:", 227-(ewidth/2) + 25, 120, 4210752);
                int nwidth = fontRenderer.getStringWidth(tel.energy + "");
                fontRenderer.drawString(tel.energy + "", 227-(nwidth/2) + 25, 135, 4210752);
                ItemStack par1ItemStack = tel.getStackInSlot(0);
                if(par1ItemStack != null) {
        		if( par1ItemStack.stackTagCompound == null )
                    par1ItemStack.setTagCompound( new NBTTagCompound( ) );

                NBTTagCompound tagCompound = par1ItemStack.getTagCompound();	
                NBTTagList tagList = tagCompound.getTagList("Marked");
                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
                    fontRenderer.drawString(tag.getString("MarkedThing"), 90, (i * 10) + 30, 4210752);
                }
                }
                //draws "Inventory" or your regional equivalent
                fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8 + (12) + 25,  ySize - 96 + 2, 4210752);
                
                if(selected == 0){
                	fontRenderer.drawString("Module Running: " + tel.moduleSelected, 66+25, 74, 4210752);
                	
                } else if(selected == 1){
                	
                } else if(selected == 2){
                	
                }
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
        {
        	updateTabs();
        	final ResourceLocation texture = new ResourceLocation(Netcraft.modid.toLowerCase(), "/textures/gui/coreGuiNew.png");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(texture);
            int x = (this.width - this.xSize + 50) / 2;
            int y = (this.height - this.ySize) / 2;
            //this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            //par1 = actual x, par2 = actual y, par3 = (u) x on texture file, par4 = (v) y on texture file, par5 = width, par6 = height
            drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
            
            int k = this.tel.getEnergyScaled(78);
            /*
            218 is width from gui border left
            20 is length from gui border top, with 78 being the height of the energy bar (subtracting k to make it grow from the bottom)
            (0,166) is the location of the texturefile energy bar, top left
            16 is width of texture, k is height (displays from bottom - up)
            */
            drawTexturedModalRect(x + 218, y + 20 + 78 - k, 0, 166 + 78 - k, 16, k);
            //-            drawTexturedModalRect(guiLeft + 218, guiTop + 20 + 78 - k, 0, 166 + 78 - k, 16, k);
            if(selected == 0){
            	
            } else if(selected == 1){
            	
            } else if(selected == 2){
            	
            }
        }
        
        public void actionPerformed(GuiButton button)
        {
        	for(int i = 0; i < tabs.size(); i++){
        		tabs.get(i).setPressed(false);
        		if(tabs.get(i).id == button.id) {
                	tabs.get(i).togglePressed();
        		}
        	}

        	this.selected = button.id;
        	tel.setPressed(button.id);
        	
        }
        
        public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel){
    		Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0,1);
            tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
            tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1,0);
            tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
            tessellator.draw();
    	}
        @Override
        public void initGui()
        {
            super.initGui();
            //this.mc.thePlayer.openContainer = this.inventorySlots;
            //this.guiLeft = (this.width - this.xSize) / 2;
            //this.guiTop = (this.height - this.ySize) / 2;
            initTabs();
            updateTabs();

        }
        public void updateTabs() {
        	this.selected = tel.getTabPage();
        	for(int i = 0; i < tabs.size(); i++){
        		tabs.get(i).setPressed(false);
        		if(tabs.get(i).id == this.selected) {
                	tabs.get(i).togglePressed();
                	((ContainerCore) super.inventorySlots).selected = tabs.get(i).id;
        		}
        	}
        	((ContainerCore)this.inventorySlots).updateTab();
        }
        private void initTabs(){
        	tabs = new ArrayList<GuiTabButton>();
        	createTab(tabs.size(), "Modules", 0, false);
        	createTab(tabs.size(), "Nodes", 1, false);
        	createTab(tabs.size(), "Power", 2, false);
        	//createTab(tabs.size(), "Tesla Field Module", false); --NO NO NO
        }
        
        public void createTab(int placement, String text, int id, boolean press){
                	int x = (this.width - this.xSize + 50) / 2;
             int y = (this.height - this.ySize) / 2;
        	GuiTabButton temp = new GuiTabButton(id, x - 52, y + placement*16, 50, 15, text, "/textures/gui/tabButtonBlueBig.png");
        	this.buttonList.add(temp);
        	tabs.add(temp);
        } 
        /* WIP
        @Override
        protected void drawSlotInventory(Slot par1Slot)
        {
        	boolean flag = false;
            if(par1Slot instanceof SlotCore) {
            	if(((SlotCore) par1Slot).hidden == true) {
            		flag = true;
            	}
            }
            if(!flag) {
            	super.drawSlotInventory(par1Slot);
            }
        }
        public Slot getSlotAtPosition(int par1, int par2)
        {
            for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k)
            {
                Slot slot = (Slot)this.inventorySlots.inventorySlots.get(k);

                if (this.publicMouseOverSlot(slot, par1, par2))
                {
                	if(slot instanceof SlotCore) {
                		if(!(((SlotCore) slot).hidden == true)) {
                            return slot;
                		}
                	} else {
                    return slot;
                	}
                }
            }

            return null;
        }
        @Override
        protected void mouseClicked(int par1, int par2, int par3)
        {
        	try {
				Method m = this.getClass().getSuperclass().getDeclaredMethod("mouseClicked");
				try {
					m.invoke(this, par1, par2, par3);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
        }
        
        public boolean publicMouseOverSlot(Slot par1Slot, int par2, int par3) {
            return this.isPointInRegion(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
        }
        protected void mouseClickMove(int par1, int par2, int par3, long par4)
        {
        	try {
				Method m = this.getClass().getSuperclass().getDeclaredMethod("mouseClickMove");
				try {
					m.invoke(this, par1, par2, par3, par4);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
        }
        protected void mouseMovedOrUp(int par1, int par2, int par3)
        {
        	try {
				Method m = this.getClass().getSuperclass().getDeclaredMethod("mouseMovedOrUp");
				try {
					m.invoke(this, par1, par2, par3);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
        }*/


}