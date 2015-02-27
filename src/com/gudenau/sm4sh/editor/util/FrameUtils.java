package com.gudenau.sm4sh.editor.util;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class FrameUtils {

	public static final Image icon;
	
	static {
		Image icon_ = null;
		
		try {
			icon_ = ImageIO.read(FrameUtils.class.getResource("/res/images/icon.png"));
		} catch (IOException e) {}
		
		icon = icon_;
	}

	public static void center(JFrame frame) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dim.width - frame.getWidth()) / 2, (dim.height - frame.getHeight()) / 2);
	}

}
