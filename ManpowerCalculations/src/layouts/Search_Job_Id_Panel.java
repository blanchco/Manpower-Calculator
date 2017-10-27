package layouts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import backend.AggregateRow;
import backend.Summary;
import backend.Table;
import backend.Workable_data_functions;

import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Search_Job_Id_Panel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -819504695147674004L;
	private JFileChooser file_chooser;
	
	private JTextField fileField;
	private JButton addFileButton;
	
	private ButtonGroup searchOptionGroup;
	private JRadioButton onlyJobIdButton;
	private JRadioButton weekRangeButton;
	
	private JLabel jobIdLabel;
	private JTextField jobIdTextField;
	
	private JLabel weekRangeLabel;
	private JPanel weekInputPanel;
	private JTextField lowWeek;
	private JTextField highWeek;
	
	private JSeparator sep1;
	private JSeparator sep2;
	private JSeparator sep3;
	
	private JPanel statusPanel;
	private JLabel resultsLabel;
	private JLabel statusLabel;
	
	private JScrollPane tableScrollPane;
	private JTable displayTable;
	private DefaultTableModel model;
	private DefaultTableCellRenderer cellRenderer;
	
	private JButton searchButton;
	
	private ArrayList<String[]> raw_data;
	private ArrayList<Table> raw_tables;
	private Summary summary;
	private ArrayList<AggregateRow> foundRows;
	
	/**
	 * Create the panel.
	 */
	public Search_Job_Id_Panel() {
		setPreferredSize(new Dimension(1080,720));
		
		setLayout(new GridBagLayout());
		setLayoutConstraints();
	}
	
	//setting our gridbag layout constraints
	private void setLayoutConstraints(){
		GridBagConstraints gc = new GridBagConstraints();
		
		fileField = new JTextField(SwingConstants.HORIZONTAL);
		fileField.setEditable(false);
		
		addFileButton = new JButton("Choose File...");
		addFileButton.addActionListener(this);
	
		model = new DefaultTableModel();
		model.addColumn("Employee");
		model.addColumn("Total Hours");
		model.addColumn("Total Wage");
		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.RIGHT);
		displayTable = new JTable(model);
		displayTable.setFillsViewportHeight(true);
		displayTable.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		displayTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		tableScrollPane = new JScrollPane(displayTable);
		
		
		searchOptionGroup = new ButtonGroup();
		onlyJobIdButton = new JRadioButton("Search Only by Job Number");
		onlyJobIdButton.setSelected(true);
		onlyJobIdButton.addActionListener(this);
		weekRangeButton = new JRadioButton("Search With Week Range");
		weekRangeButton.addActionListener(this);
		searchOptionGroup.add(onlyJobIdButton);
		searchOptionGroup.add(weekRangeButton);
		
		jobIdLabel = new JLabel("Job #: ");
		jobIdTextField = new JTextField(6);
		jobIdTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		weekRangeLabel = new JLabel("Enter Week Range: ");
		weekInputPanel = setWeekInputPanel(weekInputPanel);
		
		
		sep1 = new JSeparator(SwingConstants.HORIZONTAL);
		sep2 = new JSeparator(SwingConstants.HORIZONTAL);
		sep3 = new JSeparator(SwingConstants.HORIZONTAL);
		
		statusPanel = setStatusPanel(statusPanel);
		
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0.2;
		add(fileField, gc);
		
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0.2;
		add(addFileButton, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		gc.weighty = 0;
		add(onlyJobIdButton, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		add(weekRangeButton, gc);
		
		
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 1;
		add(jobIdLabel, gc);
		
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		gc.gridy = 1;
		//gc.weightx = 0.1;
		add(jobIdTextField, gc);
		
		gc.anchor = GridBagConstraints.EAST;
		gc.gridx = 1;
		gc.gridy = 2;
		gc.weightx = 0;
		add(weekRangeLabel, gc);
		
		gc.anchor = GridBagConstraints.WEST;
		gc.gridx = 2;
		gc.gridy = 2;
		add(weekInputPanel, gc);
		
		gc.anchor = GridBagConstraints.WEST;
		gc.fill = GridBagConstraints.VERTICAL;
		gc.gridx = 3;
		gc.gridy = 1;
		gc.gridheight = 2;
		gc.weightx = 0.7;
		add(searchButton, gc); 
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 4;
		gc.gridx = 0;
		gc.gridy = 3;
		add(sep1, gc);
		
		//gc.weightx = 1;
		gc.weighty = 0.8;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridwidth = 4;
		gc.gridx = 0;
		gc.gridy = 4;	
		add(tableScrollPane, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weighty = 0;
		gc.gridwidth = 4;
		gc.gridx = 0;
		gc.gridy = 5;
		add(sep2, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 4;
		gc.gridx = 0;
		gc.gridy = 6;
		add(statusPanel, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 4;
		gc.gridx = 0;
		gc.gridy = 7;
		add(sep3, gc);
		
		
	}
	
	private JPanel setStatusPanel(JPanel statusPanel){
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		resultsLabel = new JLabel("3 results found.");
		statusLabel = new JLabel("Seaching...");
		statusPanel.add(resultsLabel, BorderLayout.LINE_START);
		statusPanel.add(statusLabel, BorderLayout.LINE_END);
		return statusPanel;
	}
	
	private JPanel setWeekInputPanel(JPanel weekInputPanel){
		weekInputPanel = new JPanel();
		weekInputPanel.setLayout(new BorderLayout());
		JLabel toLabel = new JLabel(" to ");
		lowWeek = new JTextField(2);
		highWeek = new JTextField(2);
		lowWeek.setEditable(false);
		highWeek.setEditable(false);
		lowWeek.setHorizontalAlignment(SwingConstants.RIGHT);
		highWeek.setHorizontalAlignment(SwingConstants.RIGHT);
		weekInputPanel.add(lowWeek, BorderLayout.LINE_START);
		weekInputPanel.add(toLabel, BorderLayout.CENTER);
		weekInputPanel.add(highWeek, BorderLayout.LINE_END);
		return weekInputPanel;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addFileButton){
			file_chooser = new JFileChooser();
			file_chooser.setCurrentDirectory(new File("C:/"));
			file_chooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Value File (.csv)", "csv"));
			file_chooser.showOpenDialog(null);
			File file = file_chooser.getSelectedFile();
			
			if (file != null){
				String filename = file.getAbsolutePath();
				fileField.setText(filename);
				raw_data = Workable_data_functions.read_file(fileField.getText());
				raw_tables = Workable_data_functions.createTables(raw_data);
				summary = new Summary(raw_tables);
			}
			
		} else if (e.getSource() == searchButton){
			if(!fileField.getText().equals("")){
				if (onlyJobIdButton.isSelected()){
					model.setRowCount(0);
					String job_id = jobIdTextField.getText();
					foundRows = summary.combineAllAggregateRows(job_id);
					if (foundRows != null)
						for (int i = 0; i < foundRows.size(); i++){
							model.addRow(new Object[]{
									foundRows.get(i).getEmployee(),
									foundRows.get(i).getHours(),
									foundRows.get(i).getWage()
									});
					} else {
						JOptionPane.showMessageDialog(null, "Job id: " + jobIdTextField.getText() + " not found.");
					}
				} else if (weekRangeButton.isSelected()){
					String job_id = jobIdTextField.getText();
					try {
						int lowDate = Integer.parseInt(lowWeek.getText());
						int highDate = Integer.parseInt(highWeek.getText());
						if (highDate >= lowDate){
							if (lowDate < 1){
								JOptionPane.showMessageDialog(null, "First week too low, lowest value allowed is 1.");
							} else if (highDate > summary.getSize()){
								JOptionPane.showMessageDialog(null, "Second week number too high, there are only " + summary.getSize() + " weeks in the selected file.");
							} else {
								model.setRowCount(0);
								foundRows = summary.combineAllAggregateRows(job_id, lowDate, highDate);
								if (foundRows != null){
									for (int i = 0; i < foundRows.size(); i++){
										model.addRow(new Object[]{
												foundRows.get(i).getEmployee(),
												foundRows.get(i).getHours(),
												foundRows.get(i).getWage()
												});
									}
								} else {
									JOptionPane.showMessageDialog(null, "Job id: " + jobIdTextField.getText() + " not found between weeks "
																	+ lowWeek.getText() + " and " + highWeek.getText() + ".");
								}
								
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "First week number needs to be equal or lower than the second week number.");
						}
						
					} catch (NumberFormatException nfe){
						JOptionPane.showMessageDialog(null, "Invalid Input.");
					}		
				}
			} else {
				JOptionPane.showMessageDialog(null, "No File Found.");
			}
		} else if (e.getSource() == weekRangeButton){
			lowWeek.setEditable(true);
			highWeek.setEditable(true);
			
		} else if (e.getSource() == onlyJobIdButton){
			lowWeek.setEditable(false);
			highWeek.setEditable(false);
			lowWeek.setText("");
			highWeek.setText("");
			
		}
		
	}
}
