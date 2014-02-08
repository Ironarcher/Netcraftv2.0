package com.nitrogenegames.netcraft.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
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
import org.lwjgl.opengl.GL12;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.block.BlockNodeConnection;
import com.nitrogenegames.netcraft.machine.ContainerCore;
import com.nitrogenegames.netcraft.machine.SlotNetcraft;
import com.nitrogenegames.netcraft.machine.TileEntityCore;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class GuiCore extends GuiContainer {
	
		TileEntityCore tel;
		ArrayList<GuiTabButton> tabs;
		ArrayList<GuiModuleButton> moduleButtons;
		private int selected = 0;
		private int modulepage = 1;
		public EntityPlayer player;
		private boolean moduleSelected = false;
		
		//public boolean tabbed = false;
		//int x,y;
		
        public GuiCore (InventoryPlayer inventoryPlayer, TileEntityCore tileEntity, EntityPlayer p) {
                //the container is instanciated and passed to the superclass for handling
                super(new ContainerCore(inventoryPlayer, tileEntity));
                tel = tileEntity;
                this.player = p;
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
                fontRenderer.drawString("Net Core", 100 - (fontRenderer.getStringWidth("Net Core")/2) + 25, 6, 4210752);
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
                	//MODULES
                	fontRenderer.drawString("Module Running: " + tel.moduleSelected, 66+25, 74, 4210752);
                	
                } else if(selected == 1){

                	//this.drawItemStack(new ItemStack(Netcraft.connectionnode, 1), 50+20, 20, j + "");
                } else if(selected == 2){
                	//POWER
                	
                	//Speedometer labels
                	int center1 = 158 - (fontRenderer.getStringWidth("Energy Use")/2) + 25;
                	int center2 = 41 - (fontRenderer.getStringWidth("Energy Gain")/2) + 25;
                	fontRenderer.drawString("Energy Use", center1, 16, 4210752);
                	fontRenderer.drawString("Energy Gain", center2, 16, 4210752);
                	
                	//Speedometer counts
                	fontRenderer.drawString(tel.getEnergyGainPerTick() + "", 25 + 19 + 25, 26 + 20, 4210752);
                	//GET ENERGY USE FROM MODULES
                	fontRenderer.drawString("0", 25 + 136 + 25, 26 + 20, 4210752);
                	
                	//Energy type status
                	fontRenderer.drawString("Energy Type:", 100 - (fontRenderer.getStringWidth("Energy Type:")/2) + 25, 45, 4210752);
                	fontRenderer.drawString("EU/t", 100 - (fontRenderer.getStringWidth("EU/t")/2) + 25, 54, 4210752);
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
            //drawTexturedModalRect(guiLeft + 218, guiTop + 20 + 78 - k, 0, 166 + 78 - k, 16, k);
            if(selected == 0){
            	//MODULES
            	
            	//Main box of module storage
            	drawTexturedModalRect(x+19, y+16, 80, 185, 90, 18);
            	drawTexturedModalRect(x+19, y+44, 80, 185, 90, 18);
            } else if(selected == 1){
              	int j = this.tel.connectors.size();
            	
                this.drawItemStack(new ItemStack(Netcraft.connectionnode, 1), x+20, y+20, j + ""); 
            } else if(selected == 2){
            	//POWER
            	
            	//speedometers
            	drawTexturedModalRect(x+19, y+26, 17, 185, 45, 45);
            	drawTexturedModalRect(x+136, y+26, 17, 185, 45, 45);
            	drawTexturedModalRect(x+91, y+23, 80, 185, 18, 18);
            	
            	//speedometer animation
            	double l = tel.getEnergyGainPerTick()*35/tel.getMaxSafeInput();
            	System.out.println(l);
            	if(l>tel.getMaxSafeInput()){
            		l = 35;
            	}
            	drawTexturedModalRect(x+19+8, (int)(y+26+5+35-l), 62, (int)(185+35-l), 17, (int)(l));
            	drawTexturedModalRect(x+136+8, (int)(y+26+5+35-l), 62, (int)(185+35-l), 17, (int)(l));
            }
        }
        
        /*public void changeButtonModule(boolean s){
        	//Custom buttons here
        	if(modulepage == 1){
        		for(int i = 0+3; i < 10+3; i++){
        			((GuiModuleButton)this.buttonList.get(i)).drawButton = s;
        		}
        	} else if (modulepage == 2){
        		for(int i = 10+3; i < 20+3; i++){
        			((GuiModuleButton)this.buttonList.get(i)).drawButton = s;
        		}	
        	}
        	
        	//Normal buttons here
        } */
        
        public void drawItem(ItemStack s, int x, int y) {
        	drawItemStack(s, x, y, "");
        }
        public void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str)
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            this.zLevel = 50.0F;
            itemRenderer.zLevel = 50.0F;
            FontRenderer font = null;
            if (par1ItemStack != null) font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
            if (font == null) font = fontRenderer;
            //GL11.glEnable(GL11.GL_DEPTH_TEST);
            itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3);
            itemRenderer.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), par1ItemStack, par2, par3, par4Str);
            this.zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
        }
        
        public void actionPerformed(GuiButton button)
        {
        	if(button instanceof GuiTabButton) {
        	for(int i = 0; i < tabs.size(); i++){
        		tabs.get(i).setPressed(false);
        		if(tabs.get(i).id == button.id) {
                	tabs.get(i).togglePressed();
        		}
        	}

        	this.selected = button.id;
        	tel.setPressed(button.id);
        	} else if(button instanceof GuiModuleButton) {
        		if(this.inventorySlots.getSlot(button.id - 3).getHasStack()) {
        			player.openGui(Netcraft.instance, 0, this.tel.worldObj, this.tel.xCoord, this.tel.yCoord, this.tel.zCoord);
        		}
        		this.moduleSelected = true;
        	}
        	
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
            
            int x = (this.width - this.xSize) / 2;
            int y = (this.height - this.ySize) / 2;
            GuiButton temp1 = new GuiButton(24, x + 143, y + 23, "Toggle");
            temp1.drawButton = false;
            GuiButton temp2 = new GuiButton(25, x + 143, y + 47, "Open GUI");
            temp2.drawButton = false;
            this.buttonList.add(temp1);
            this.buttonList.add(temp2);
        
            initTabs();
            initModuleButton();
            updateTabs();
            initButtons();

        }
        public void initButtons() {
        	
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
        	for(int i = 0; i < moduleButtons.size(); i++){
	        	if(this.selected == 0) {
	        		moduleButtons.get(i).tabPage = true;
	        	} else {
	        		moduleButtons.get(i).tabPage = false;	
	        	}
	        	if(moduleButtons.get(i).page == this.modulepage) {
	        		moduleButtons.get(i).modulePage = true;
	        	} else {
	        		moduleButtons.get(i).modulePage = false;   		
	        	}
        	}
        	
        	if(this.moduleSelected){
        		((GuiModuleButton)(this.buttonList.get(24))).drawButton = true;
        		((GuiModuleButton)(this.buttonList.get(25))).drawButton = true;
        	} else{
        		((GuiModuleButton)(this.buttonList.get(24))).drawButton = false;
        		((GuiModuleButton)(this.buttonList.get(25))).drawButton = false;
        	}
        	
        }
        private void initTabs(){
        	tabs = new ArrayList<GuiTabButton>();
        	createTab(tabs.size(), "Modules", 0, false);
        	createTab(tabs.size(), "Nodes", 1, false);
        	createTab(tabs.size(), "Power", 2, false);
        	//createTab(tabs.size(), "Tesla Field Module", false); --NO NO NO
        }
        
        private void initModuleButton(){
        	moduleButtons = new ArrayList<GuiModuleButton>();
        	int x = (this.width - this.xSize + 50) / 2;
            int y = (this.height - this.ySize) / 2;
        	for(int i = 0; i < 90; i+=18){
        		createModuleButton(i/18+3, i+22+x, 37+y, 1);
        	}
        	for(int i = 0; i < 90; i+=18){
        		createModuleButton(i/18+3+5, i+22+x, 65+y, 1);
        	}
        	for(int i = 0; i < 90; i+=18){
        		createModuleButton(i/18+3+10, i+22+x, 37+y, 2);
        	}
        	for(int i = 0; i < 90; i+=18){
        		createModuleButton(i/18+3+15, i+22+x, 65+y, 2);
        	}
        }
        
        public void createTab(int placement, String text, int id, boolean press){
                	int x = (this.width - this.xSize + 50) / 2;
             int y = (this.height - this.ySize) / 2;
        	GuiTabButton temp = new GuiTabButton(id, x - 52, y + placement*16, 50, 15, text, "/textures/gui/tabButtonBlueBig.png");
        	this.buttonList.add(temp);
        	tabs.add(temp);
        } 
        
        public void createModuleButton(int id, int x, int y, int visible){
        	GuiModuleButton temp = new GuiModuleButton(id, x, y, 12, 5, "/textures/gui/modulebuttonfinal.png");
        	this.buttonList.add(temp);
        	moduleButtons.add(temp);
        	temp.page = visible;
        	if(this.modulepage==temp.page) {
        		temp.tabPage = true;
        	} else {
        		temp.tabPage = false;
        	}
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