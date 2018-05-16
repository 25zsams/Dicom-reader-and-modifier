package BioImaging;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

public class SlidingBarSample extends Thread{

	public static int n = 0;
	public static boolean k = true;
	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0 , 100, 0);
		//slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setLabelTable(slider.createStandardLabels(20));
		slider.setPaintLabels(true);
		
		
		
		slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(k){
					k = false;
					System.out.println(n++);
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					k = true;
				} else {
					System.out.println("nothing happened");
				}
				
			}
		});
		
		frame.add(slider);
		frame.setVisible(true);
		frame.setSize(700, 500);
		
	}
}
