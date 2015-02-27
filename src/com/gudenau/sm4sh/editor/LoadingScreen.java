package com.gudenau.sm4sh.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.gudenau.sm4sh.editor.util.FrameUtils;

public class LoadingScreen {
	private JFrame frame;
	
	public LoadingScreen(String message) throws Exception {
		frame = new JFrame("Sm4sh 3DS Save Editor");
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));		
		
		frame.add(new BallPanel());
		frame.setSize(256, 256);
		FrameUtils.center(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(FrameUtils.icon);
		frame.setVisible(true);
	}

	public void close() {
		frame.dispose();
		frame = null;
	}
	
	private static class BallPanel extends JPanel{
		private static final long serialVersionUID = -3423798023524173568L;
		private short color = 0;
		
		private int redXor   = 0;
		private int redOff   = 0;
		private int greenXor = 0;
		private int greenOff = 0;
		private int blueXor  = 0;
		private int blueOff  = 0;
		
		private BufferedImage frame;
		
		public BallPanel(){
			setBackground(new Color(0, 0, 0, 0));
			java.util.Random rand = new java.util.Random(System.currentTimeMillis());
			
			try{
				frame = ImageIO.read(LoadingScreen.class.getResourceAsStream("/res/images/SmashBallFrame.png"));
			}catch(Throwable t){}
			
			redXor =   rand.nextInt() & 0xFF;
			redOff =   rand.nextInt() & 0xFF;
			greenXor = rand.nextInt() & 0xFF;
			greenOff = rand.nextInt() & 0xFF;
			blueXor =  rand.nextInt() & 0xFF;
			blueOff =  rand.nextInt() & 0xFF;
			
			Thread t = new Thread(){				
				public void run(){
					try{
						Thread.sleep(10);
					}catch(Throwable t){}
					
					while(BallPanel.this.isVisible()){
						color++;
						BallPanel.this.repaint();
						
						try{
							Thread.sleep(10);
						}catch(Throwable t){}
					}
				}
			};
			
			t.setDaemon(true);
			t.start();
		}
		
		@Override
		public void paint(Graphics g){
			super.paint(g);
			
			g.setColor(new Color(
					((color + redOff) ^ redXor) & 0xFF,
					((color + greenOff) ^ greenXor) & 0xFF,
					((color + blueOff) ^ blueXor) & 0xFF
					));
			g.fillOval(0, 0, getWidth(), getHeight());
			
			g.drawImage(frame, 0, 0, null);
		}
	}
}
