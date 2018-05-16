package BioImaging;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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

public class Registration {

	public static JFrame frame;
	public static JLabel label;
	public static JLabel label2;
	public static JLabel label3;
	public static JComboBox box;
	public static JComboBox box2;
	public static JButton button;
	public static String fileName = "frontBackLung\\000000.dcm";
	public static void main(String[] args) throws IOException {
		activateLicense();

		
		BufferedImage img = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\" + fileName));
		BufferedImage img2 = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\" + fileName));
		BufferedImage img3 = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\" + fileName));
		ImageIcon icon = new ImageIcon(img);
		ImageIcon icon2 = new ImageIcon(img2);
		ImageIcon icon3 = new ImageIcon(img3);
		
		frame = new JFrame();
		frame.setSize(1600, 600);
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		label.setIcon(icon);
		label2.setIcon(icon2);
		label3.setIcon(icon3);
		frame.add(label);
		frame.add(label2);
		frame.add(label3);
		

		allFileNameToBox();
		buttonActions();
		frame.setVisible(true);
	}
	
	private static void imageRegistration() throws IOException{
		String filter = "ImageRegistration1.exe";
		String inputNotMoving = "frontBackLung\\" + box.getSelectedItem().toString();
		String inputMoving = "frontBackLung\\" + box2.getSelectedItem().toString();
		String output = "output6.dcm";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", filter, inputNotMoving, inputMoving, output);
		builder = builder.directory(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release"));
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String ss = null;
		while ((ss = r.readLine()) != null) {
            System.out.println(ss);
        }
        BufferedImage temp = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\output6.dcm"));
        label3.setIcon(new ImageIcon(temp));
	}
	
	private static void buttonActions(){
		button = new JButton("Apply Registration");
		frame.add(button);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					imageRegistration();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	private static void allFileNameToBox(){
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
		box = new JComboBox(temp);
		box2 = new JComboBox(temp);
		
		box.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				BufferedImage t = null;
				try {
					t = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\frontBackLung\\" + box.getSelectedItem().toString()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				label.setIcon(new ImageIcon(t));
			}
		});
		box2.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent event){
				BufferedImage t = null;
				try {
					t = ImageIO.read(new File("C:\\InsightToolkit-4.13.0\\build\\bin\\Release\\frontBackLung\\" + box2.getSelectedItem().toString()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				label2.setIcon(new ImageIcon(t));
			}
		});
		frame.add(box);
		frame.add(box2);
	}
	
	private static void activateLicense(){
		Plugin.setLicenseKey("A9TECXJCF57D40UHRVZSQBC");
	}

}
