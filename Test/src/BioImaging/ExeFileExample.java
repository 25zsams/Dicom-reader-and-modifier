package BioImaging;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import fr.apteryx.imageio.dicom.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.w3c.dom.events.*;

public class ExeFileExample extends Thread{
	public static JSlider[] slider = new JSlider[5];
	public static boolean coolDown = true;

	public static JFrame frame;
	public static JLabel label;
	public static JLabel label2;
	public static Thread thread;
	public static void main(String[] args) throws IOException {
		activateLicense();

		BufferedImage img = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\000000.dcm"));
		BufferedImage img2 = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\000000.dcm"));
		ImageIcon icon = new ImageIcon(img);
		ImageIcon icon2 = new ImageIcon(img2);
		
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(1200, 700);
		label = new JLabel();
		label2 = new JLabel();
		label.setIcon(icon);
		label2.setIcon(icon2);
		frame.add(label);
		frame.add(label2);

		setSlider();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}
	
	public static void setSlider(){
		for(int i = 0; i < slider.length; i++){
			slider[i] = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
			slider[i].setMajorTickSpacing(20);
			slider[i].setPaintTicks(true);
			slider[i].setLabelTable(slider[i].createStandardLabels(20));
			slider[i].setPaintTicks(true);
			slider[i].setPaintLabels(true);
			frame.add(slider[i], BorderLayout.SOUTH);
			slider[i].addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent c){
					try {
						if(coolDown == true){
							coolDown = false;
							long startTime = System.currentTimeMillis();
							cmdInput();
						    coolDown = true;
							long endTime   = System.currentTimeMillis();
							long totalTime = endTime - startTime;
							System.out.println(totalTime);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				
			});
			
		}
		

	}
	
	public static void cmdInput() throws IOException{
		String filter = "BinaryThresholdImageFilter.exe";
		String input = "000000.dcm";
		String output = "output6.dcm";
		String argument1 = Integer.toString((slider[0].getValue() * 512) / 100);
		String argument2 = Integer.toString((slider[1].getValue() * 512) / 100);
		String argument3 = Integer.toString((slider[2].getValue()));
		String argument4 = Integer.toString((slider[3].getValue()));
		System.out.println(argument1 + " " + argument2 + " " + argument3 + " " + argument4);
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", filter, input, output, argument1, argument2, argument3, argument4);
		builder = builder.directory(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release"));
		builder.redirectErrorStream(true);
		Process p = builder.start();
        try {
			sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        BufferedImage img2 = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\output6.dcm"));
        ImageIcon icon2 = new ImageIcon(img2);
        label2.setIcon(icon2);
	}
	
	private static void activateLicense(){
		Plugin.setLicenseKey("A9TECXJCF57D40UHRVZSQBC");
	}

}
