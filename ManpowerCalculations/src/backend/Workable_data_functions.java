package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Workable_data_functions {

	public static ArrayList<String[]> read_file(String csvFile){
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<String[]> getRows = new ArrayList<String[]>();
		
		try{
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null){
				String[] row = line.split(cvsSplitBy, -1);
				getRows.add(row);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return getRows;
	}
	
	public static ArrayList<Table> createTables(ArrayList<String[]> raw_data){
		//go through rows to see which rows start with "rate" which is where a table will start
		ArrayList<Table> allTables = new ArrayList<Table>();
		for (int i = 0; i < raw_data.size(); i++){
			if (raw_data.get(i)[0].equals("rate")){
				ArrayList<String[]> temp_table = new ArrayList<String[]>();
				
				//add rows in a table until the 2nd entry in a row says "Total"
				while (!raw_data.get(i)[1].equals("Total")){
					temp_table.add(raw_data.get(i));
					i++;
				}
				
				//determine what index to trim the right side of the table down
				int last_column_index = trimTableIndex(temp_table);
				if (last_column_index != -1){
					ArrayList<String[]> trimmedTable = trimTable(temp_table, last_column_index);
					Table table = new Table(trimmedTable);
					allTables.add(table);
				} else {
					System.out.println("Incorrect format");
				}				
			}
		}
		
		return allTables;
	}
	
	private static int trimTableIndex(ArrayList<String[]> table){
		for (int i = 0; i < table.get(0).length; i++){
			if (table.get(0)[i].equals("Total")){
				return i;
			}
		}
		return -1;
	}
	
	private static ArrayList<String[]> trimTable(ArrayList<String[]> table, int trimIndex){
		ArrayList<String[]> trimmed_table = new ArrayList<String[]>();
		for (int i = 0; i < table.size(); i++){
			String[] trimmed_row = new String[trimIndex];
			for (int j = 0; j < trimmed_row.length; j++){
				trimmed_row[j] = table.get(i)[j];
			}
			trimmed_table.add(trimmed_row);
		}
		return trimmed_table;
	}
	
}










