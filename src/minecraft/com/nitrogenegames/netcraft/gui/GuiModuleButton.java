package com.nitrogenegames.netcraft.gui;
 
import org.lwjgl.opengl.GL11;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
 
public class GuiModuleButton extends GuiButton {
	
	/*
	 * Stores the information for the tab button on the core GUI
	 * Also has the information of the tab itself
	 */
	
	//syntax for sheet is following order of buttons, going from top to bottom: normal, normal hovered, pressed, pressed+hovered
	private ResourceLocation texture1;
	private boolean pressed = false;

 
	//for default size: 120 width and 20 height
    public GuiModuleButton(int id, int startx, int starty, String text, String reslocation)
    {
        this(id, startx, starty, 120, 20, text, reslocation);
    }
 
    //use this
    public GuiModuleButton(int id, int xposition, int yposition, int width, int height, String text, String reslocation, boolean pressed)
    {
        super(id, xposition, yposition, width, height, text);
        texture1 = new ResourceLocation(Netcraft.modid.toLowerCase(), reslocation);
        this.setPressed(pressed);
        }

    private static String charFit(String s){
    	if(s.length() > 9 ){
    		return s.substring(0,7) + "..";
    	}
    	return s;
    }
    public GuiModuleButton(int id, int xposition, int yposition, int width, int height, String text, String reslocation)
    {
    	this(id, xposition, yposition, width, height, text, reslocation, false);
    }
    protected int getHoverState(boolean flag)
    {
        byte byte0 = 1;
        if (!enabled)
        {
                byte0 = 0;
                }
        else if (flag)
        {
                byte0 = 2;
        }
        return byte0;
        }
    
    @Override
    public void drawButton(Minecraft mc, int mx, int my)
    {
        FontRenderer fontrenderer = mc.fontRenderer;
        mc.renderEngine.bindTexture(texture1);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean flag = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;  //Flag, tells if your mouse is hovering the button
        if(!pressed){
	        if (flag)
	        { // Hover Action
	        	drawTexturedModalRect(this.xPosition, this.yPosition, 0, 15, this.width, this.height);
	        }
	        else { // Normal
	        	drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.width, this.height);
	        }
        } else{
        	if (flag)
	        { // Hover Action
        		drawTexturedModalRect(this.xPosition, this.yPosition, 0, height*2, this.width, this.height); 
	        }
	        else { // Normal
	        	drawTexturedModalRect(this.xPosition, this.yPosition, 0, height*3, this.width, this.height);
	        }
        }
        drawCenteredString(fontrenderer, charFit(displayString), xPosition + width / 2, yPosition + (height - 8) / 2, 0xFFCCCCCC);
    }
   
    public void togglePressed(){
    	pressed = !pressed;
    }
    
    public void setPressed(boolean s){
    	pressed = s;
    }
}