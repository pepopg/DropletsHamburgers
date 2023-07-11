package com.droplet.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher 
{
	public static void main (String[] arg) 
	{
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		config.setForegroundFPS(60);
		config.setWindowedMode(800, 600);
		config.useVsync(true);
		config.setTitle("Droplets & Hamburgers");
		
		new Lwjgl3Application(new DropletsHamburgers(), config);
	}
}