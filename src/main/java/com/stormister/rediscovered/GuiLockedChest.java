//	  Copyright 2012-2014 Matthew Karcz
//
//	  This file is part of The Rediscovered Mod.
//
//    The Rediscovered Mod is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    The Rediscovered Mod is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with The Rediscovered Mod.  If not, see <http://www.gnu.org/licenses/>.




package com.stormister.rediscovered;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiLockedChest extends GuiContainer implements GuiYesNoCallback
{
	public final ResourceLocation texture = new ResourceLocation(mod_Rediscovered.modid.toLowerCase(), "textures/gui/LockedChest.png");
	private final int PROMPT_REPLY_ACTION = 0;
	private URI displayedURI = null;
	
	public GuiLockedChest(EntityPlayer player){
		super(new ContainerLockedChest(player));
		this.xSize = 248;
		this.ySize = 166;
	}
	
    public void initGui()
    {
        this.buttonList.clear();
        byte b0 = -16;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 107, this.height / 6 + 130, 98, 20, I18n.format("Not Now", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 10, this.height / 6 + 130, 98, 20, I18n.format("Go to store", new Object[0])));
    }
    
    protected void actionPerformed(GuiButton p_146284_1_)
    {
        switch (p_146284_1_.id)
        {
            case 0:
            	this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;
            case 1:
            	URI uri = URI.create("https://minecraft.net/store");
				if (uri != null) {
					// Rude not to ask
					if (Minecraft.getMinecraft().gameSettings.chatLinksPrompt) {
						this.displayedURI = uri;
						this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.displayedURI.toString(), PROMPT_REPLY_ACTION, false));
					} else {
						openURI(uri);
					}
				}
            	break;
        }
    }
    
//    @Override
//	public void componentMouseDown(BaseComponent component, int offsetX, int offsetY, int button) {
//		if (component.getName().equals("btnDonate")) {
//			if (((GuiComponentTextButton)component).isButtonEnabled()) {
//				URI uri = URI.create(getContainer().getOwner().getDonateUrl());
//				if (uri != null) {
//					// Rude not to ask
//					if (Minecraft.getMinecraft().gameSettings.chatLinksPrompt) {
//						this.displayedURI = uri;
//						this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, this.displayedURI.toString(), PROMPT_REPLY_ACTION, false));
//					} else {
//						openURI(uri);
//					}
//				}
//			}
//		}
//	}

	private void openURI(URI uri) {
		try {
			Desktop.getDesktop().browse(uri);
		} catch (IOException e) {}
	}

	@Override
	public void confirmClicked(boolean result, int action) {
		if (action == PROMPT_REPLY_ACTION && result) {
			openURI(this.displayedURI);
			this.displayedURI = null;
		}
		this.mc.displayGuiScreen(this);
	}
    
    @Override
    public boolean doesGuiPauseGame()
    {
    	return false;
    }

	@Override
	public void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(this.width / 2 - (this.xSize/2), this.height / 6, 0, 0, xSize, ySize);
	}
}
