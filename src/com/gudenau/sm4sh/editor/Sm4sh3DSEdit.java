package com.gudenau.sm4sh.editor;

public class Sm4sh3DSEdit {
	private static LoadingScreen screen;
	
	public static void main(String[] args) throws Exception{
		screen = new LoadingScreen("Loading...");
		
		new MainFrame(screen);
	}
}
