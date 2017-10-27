package layouts;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import backend.AggregateRow;
import backend.Summary;
import backend.Table;
import backend.Workable_data_functions;

public class MainFrame extends JFrame {

	private Search_Job_Id_Panel id_panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		setTitle("Manpower Calculator");	
		id_panel = new Search_Job_Id_Panel();
		setLayout(new BorderLayout(0,0));
		setContentPane(id_panel);
		pack();	
		
		
	
	}
}
