/*
 * This object class represents the entire summary sheet for the inputed csv file.  The object consists of all table objects to be stored in a single summary object.
 */
package backend;

import java.util.ArrayList;

public class Summary {
	
	private ArrayList<Table> allTables;
	
	//constructor
	public Summary(ArrayList<Table> allTables){
		this.allTables = allTables;
	}
	
	public Table getTable(int index){
		return allTables.get(index);
	}
	
	public int getSize(){
		return allTables.size();
	}
	
	//returns the index array of all tables that contain a particular job id
	public ArrayList<Integer> getContainsJobIdIndexList(String job_id){
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i = 0; i < allTables.size(); i++){
			if(allTables.get(i).containsJobId(job_id)){
				indexList.add(i);
			}
		}
		return indexList;
	}
	
	//returns whether the job id exists in our input file
	public boolean doesJobIdExist(ArrayList<Integer> tablesWithJobId){
		if (tablesWithJobId.size() > 0){
			return true;
		}
		return false;
	}
	
	//returns the index array of all tables that contain a particular job id, searches over a range of weeks
	public ArrayList<Integer> getContainsJobIdIndexList(String job_id, int lowDate, int highDate){
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for (int i = lowDate-1; i < highDate; i++){
			if (allTables.get(i).containsJobId(job_id)){
				indexList.add(i);
			}
		}
		return indexList;
	}
	

	//get the rows the match the job id from all tables and create an array of these row arrays
	public ArrayList<ArrayList<AggregateRow>> getMatchingAggregateRows(String job_id){
		ArrayList<ArrayList<AggregateRow>> matching_rows_from_all_tables = new ArrayList<ArrayList<AggregateRow>>();
		ArrayList<Integer> tableIndexArray = getContainsJobIdIndexList(job_id);
		for (int i = 0; i < tableIndexArray.size(); i++){
			matching_rows_from_all_tables.add(this.getTable(tableIndexArray.get(i)).getAggregateRows(job_id));
		}
		return matching_rows_from_all_tables;
	}
	
	public ArrayList<ArrayList<AggregateRow>> getMatchingAggregateRows(String job_id, int lowDate, int highDate){
		ArrayList<ArrayList<AggregateRow>> matching_rows_from_all_tables = new ArrayList<ArrayList<AggregateRow>>();
		ArrayList<Integer> tableIndexArray = this.getContainsJobIdIndexList(job_id, lowDate, highDate);
		for (int i = 0; i < tableIndexArray.size(); i++){
			matching_rows_from_all_tables.add(this.getTable(tableIndexArray.get(i)).getAggregateRows(job_id));
		}
		return matching_rows_from_all_tables;
	}
	
	//get the index of the employee in the agg row array when compared to the agg row checkRow
	public int indexOfEmployeeInAggregateRowArray(ArrayList<AggregateRow> ar, AggregateRow checkRow){
		for (int i = 0; i < ar.size(); i++){
			if (ar.get(i).hasSameEmployee(checkRow)){
				return i;
			}
		}
		return -1;
	}
	
	/*public boolean isRowInAggregateRowArray(ArrayList<AggregateRow> ar, AggregateRow checkRow){
		String[] employees = getEmployeesFromAggregateRowArray(ar);
		for (int i = 0;)
		return false;
	}*/
	
	//combine 2 aggregate row arrays into 1, combines employees with the same names
	public ArrayList<AggregateRow> compareAndCombineAggregateRowArrays(ArrayList<AggregateRow> ar1, ArrayList<AggregateRow> ar2){
	
		for (int j = 0; j < ar2.size(); j++){
			int index = indexOfEmployeeInAggregateRowArray(ar1, ar2.get(j));
			if(index == -1){
				ar1.add(ar2.get(j));
			} else {
				ar1.get(index).combineAggregateRows(ar2.get(j));
			}
			
		}
		return ar1;
	}
	
	//combine all aggregate rows into 1.
	public ArrayList<AggregateRow> combineAllAggregateRows(String job_id){
		ArrayList<ArrayList<AggregateRow>> matched_rows = getMatchingAggregateRows(job_id);
		if (matched_rows.size() <= 0){
			return null;
		} else {
			ArrayList<AggregateRow> combinedRows = matched_rows.get(0); //the first index in the match_rows will be our base for comparison.
			for (int i = 1; i < matched_rows.size(); i++){
				combinedRows = compareAndCombineAggregateRowArrays(combinedRows, matched_rows.get(i));
			}
			return combinedRows;
		}
		
	}
	
	public ArrayList<AggregateRow> combineAllAggregateRows(String job_id, int lowDate, int highDate){
		ArrayList<ArrayList<AggregateRow>> matched_rows = getMatchingAggregateRows(job_id, lowDate, highDate);
		if (matched_rows.size() <= 0){
			return null;
		} else {
			ArrayList<AggregateRow> combinedRows = matched_rows.get(0); //the first index in the match_rows will be our base for comparison.
			for (int i = 1; i < matched_rows.size(); i++){
				combinedRows = compareAndCombineAggregateRowArrays(combinedRows, matched_rows.get(i));
			}
			return combinedRows;
		}
	}
	
}
