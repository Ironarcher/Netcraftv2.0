package com.nitrogenegames.netcraft.gui;
 
import org.lwjgl.opengl.GL11;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
 
public class GuiModuleButton extends GuiButton {
	
	/*
	 * Stores the information for the tab button on the core GUI
	 * Also has the information of the tab itself
	 */
	
	//syntax for sheet is following order of buttons, going from top to bottom: normal, normal hovered, pressed, pressed+hovered
	private ResourceLocation texture1;
	private boolean pressed = false;
	public boolean modulePage = false;
	public boolean tabPage = false;
	private int mode = 0;
	public int page = 1;

	//for default size: 120 width and 20 height
    public GuiModuleButton(int id, int startx, int starty, String reslocation)
    {
        this(id, startx, starty, 120, 20, reslocation);
    }
 
    //use this
    public GuiModuleButton(int id, int xposition, int yposition, int width, int height, String reslocation, boolean pressed)
    {
        super(id, xposition, yposition, width, height, "");
        texture1 = new ResourceLocation(Netcraft.modid.toLowerCase(), reslocation);
        this.setPressed(pressed);
        }

    public GuiModuleButton(int id, int xposition, int yposition, int width, int height, String reslocation)
    {
    	this(id, xposition, yposition, width, height, reslocation, false);
    }
    /*public ItemStack getModule() {
    	
    }*/
    
    @Override
    public void drawButton(Minecraft mc, int mx, int my)
    {
    	if(this.drawButton && this.tabPage && this.modulePage){
	        mc.renderEngine.bindTexture(texture1);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        if(!pressed){
	        	drawTexturedModalRect(this.xPosition, this.yPosition, 0, mode*5, this.width, this.height);
	        	//case 1: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 5, this.width, this.height);
	        	//case 2: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 10, this.width, this.height);
	        	//case 3: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 15, this.width, this.height);
	        	
	        } else if(pressed){
	        	drawTexturedModalRect(this.xPosition, this.yPosition, 12, mode*5, this.width, this.height);
	        	//case 1: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 5, this.width, this.height);
	        	//case 2: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 10, this.width, this.height);
	        	//case 3: drawTexturedModalRect(this.xPosition, this.yPosition, 0, 15, this.width, this.height);
	        }
    	}
    }
   
    //0: Red Mode - Module not running
    //1: Orange button - Module attempting run, but not sufficient power
    //2: Yellow button - Module slot open
    //3: Green button - Module running successfully
    
    public int getMode(){
    	return mode;
    }
    
    public void setMode(int s){
    	mode = s;
    }
    
    public void togglePressed(){
    	pressed = !pressed;
    }
    
    public void setPressed(boolean s){
    	pressed = s;
    }
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        return this.enabled && this.tabPage && this.modulePage && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
    }
}