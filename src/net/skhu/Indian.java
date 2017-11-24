package net.skhu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.FlowLayout;
import java.awt.Label;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import GUI.AiGUI;
import GUI.MainFrame;
import GUI.UserGUI;

import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.List;
import java.awt.TextArea;
import java.awt.Panel;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Button;

public class Indian{

	private JFrame frame;
	
	
	private Game game = new Game();
	
	private Button button;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Indian window = new Indian();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Indian() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MainFrame frame = new MainFrame(game);
		frame.setBounds(100, 100, 500, 500);
		
		JPanel panel = frame.mainView();
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
	

	
	
}
