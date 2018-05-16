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
import java.io.EOFException;
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
	public static String fileName = "frontBackLung\\000020.dcm";
	public static void main(String[] args) throws IOException {
		activateLicense();

		BufferedImage img = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\" + fileName));
		BufferedImage img2 = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\" + fileName));
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
		//segmentation3D();

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
	
	public static void segmentation3D() throws IOException{
		File folder = new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\frontBackLung");
		File[] listOfFiles = folder.listFiles();
		String[] temp = new String[listOfFiles.length];
		int index = 0;
		for(File file : listOfFiles){
			if(file.isFile()){
				temp[index] = file.getName();
				index++;
			}
		}
		
		
		for(int i = 0; i < temp.length; i++){
		String filter = "WatershedSegmentation1.exe";
		String input = "frontBackLung\\" + temp[i];
		String output = "frontBackLung2\\" + temp[i];
		String argument1 = "0";
		String argument2 = "0";
		String argument3 = ".08";
		String argument4 = ".27";
		String argument5 = "0";
		System.out.println(filter + " " + input + " " + output + " " + argument1 + " " + argument2 + " " + argument3 + " " + argument4 + " " + argument5);
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", filter, input, output, argument1, argument2, argument3, argument4, argument5);
		builder = builder.directory(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release"));
		builder.redirectErrorStream(true);
		Process p = builder.start();
		}
		
	}
	
	public static void cmdInput() throws IOException{
		String filter = "WatershedSegmentation1.exe";
		String input = fileName;
		String output = "output6.dcm";
		String argument1 = Double.toString( (slider[0].getValue()));
		String argument2 = Double.toString( (slider[1].getValue()) );
		String argument3 = Double.toString( 3 * (slider[2].getValue()) / (double)1000 );
		String argument4 = Double.toString( (slider[3].getValue()) / (double)100 );
		String argument5 = Integer.toString((slider[4].getValue()));
		System.out.println(argument1 + " " + argument2 + " " + argument3 + " " + argument4 + " " + argument5);
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", filter, input, output, argument1, argument2, argument3, argument4, argument5);
		builder = builder.directory(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release"));
		builder.redirectErrorStream(true);
		Process p = builder.start();
        try {
			sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        try{
            BufferedImage img2 = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\output6.dcm"));
            ImageIcon icon2 = new ImageIcon(img2);
            label2.setIcon(icon2);
        } catch (EOFException e){
        	e.printStackTrace();
        	System.out.println("EOFException");
        } catch (NullPointerException e){
        	e.printStackTrace();
        	System.out.println("NullPointerException");
        } catch (IndexOutOfBoundsException e){
        	e.printStackTrace();
        	System.out.println("IndexOutOfBoundsException");
        }
	}
	
	private static void activateLicense(){
		Plugin.setLicenseKey("A9TECXJCF57D40UHRVZSQBC");
	}

}
