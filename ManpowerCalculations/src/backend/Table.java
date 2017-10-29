/*
 * This object class represents a 'table' object.  A table object is a representation of one weekly summary from the excel spreadsheet.
 */


package backend;

import java.util.ArrayList;

public class Table {

	private ArrayList<String[]> table;
	private String[] rates;
	private String[] employees;
	private String[] positions;
	private String[] job_ids;
	private ArrayList<String[]> job_hours;
	
	public Table(ArrayList<String[]> table){
		this.table = table;
		this.rates = setRates();
		this.employees = setEmployees();
		this.job_ids = setJobIds();
		this.job_hours = setJobHours();
	}
	
	/*
	 * private setters
	 */
	private String[] setRates(){
		String[] rate_column = new String[table.size()];
		for (int i = 0; i < rate_column.length; i++){
			rate_column[i] = table.get(i)[0];
		}
		return rate_column;
	}
	
	private String[] setEmployees(){
		String[] employee_column = new String[table.size()];
		for (int i = 0; i < employee_column.length; i++){
			employee_column[i] = table.get(i)[1];
		}
		return employee_column;
	}
	
	//stores the job ids for the table in an array.
	private String[] setJobIds(){
		String[] job_ids = new String[table.get(0).length-3];
		for (int i = 0; i < job_ids.length; i++){
			job_ids[i] = table.get(0)[i+3];
		}
		return job_ids;
	}
	
	//set the 2d array for the job ids and their corresponding hours.  All job ids and their hours are stored in a String array and said String arrays are stored in an arraylist,
	private ArrayList<String[]> setJobHours(){
		ArrayList<String[]> job_hours = new ArrayList<String[]>();
		for (int i = 0; i < job_ids.length; i++){
			String[] job_hours_column = new String[table.size()];
			for (int j = 0; j < job_hours_column.length; j++){
				job_hours_column[j] = table.get(j)[i+3];
			}
			job_hours.add(job_hours_column);
		}
		return job_hours;
	}

	
	/*
	 * getters
	 */
	
	public String[] getRates(){
		return rates;
	}
	

	public String[] getEmployees(){
		return employees;
	}
	
	
	//get employee name by search for a name
	public String getEmployee(String employee_name){
		for (int i = 0; i < employees.length; i++){
			if (employees[i].equals(employee_name)){
				return employees[i];
			}
		}
		return null;
	}
	
	//get employee name by index
	public String getEmployee(int employee_index){
		return employees[employee_index];
	}
	
	//get the index where an employee is stored in the array
	public int getEmployeeIndex(String employee_name){
		for (int i = 0; i < employees.length; i++){
			if (employees[i].equals(employee_name)){
				return i;
			}
		}
		return -1;
	}
	
	//get the array of job ids
	public String[] getJobIds(){
		return job_ids;
	}
	
	//get the index where the job id is stored in the job_ids array
	public int getJobIdIndex(String job_id){
		for (int i = 0; i < job_ids.length; i++){
			if (job_ids[i].equals(job_id)){
				return i;
			}
		}
		return -1;
	}

	//get all job id columns
	public ArrayList<String[]> getAllJobHoursColumns(){
		return job_hours;
	}
	
	
	//get a particular job id column by job id
	public String[] getJobHoursColumn(String job_id){
		for (int i = 0; i < job_hours.size(); i++){
			if (job_id.equals(job_hours.get(i)[0])){
				return job_hours.get(i);
			}
		}
		return null;
	}
	
	//get a particular job id column by index
	public String[] getJobHoursColumn(int job_index){
		return job_hours.get(job_index);
	}
	
	//search the column for a job id and get the value for the particular index
	public double getEmployeeHours(String job_id, int rowIndex){
		int jobIdIndex = getJobIdIndex(job_id);
		String[] job_hours_column = getJobHoursColumn(jobIdIndex);
		double hours = Double.parseDouble(job_hours_column[rowIndex]);	
		return hours;
	}
	
	public String getEmployeeHoursAsString(String job_id, int rowIndex){
		int jobIdIndex = getJobIdIndex(job_id);
		String[] job_hours_column = getJobHoursColumn(jobIdIndex);
		String hours = job_hours_column[rowIndex];	
		return hours;
	}
	
	
	//searches job id column to see what indicies have hours worked and returns the index array
	public ArrayList<Integer> getEmployeesOnJobIndex(String job_id){
		ArrayList<Integer> employee_indicies = new ArrayList<Integer>();
		int jobIdIndex = getJobIdIndex(job_id);
		String[] job_hours_column = getJobHoursColumn(jobIdIndex);
		for(int i = 1; i < job_hours_column.length; i++){
			boolean isHoursADouble = false;
			try{
				Double.parseDouble(job_hours_column[i]);
				isHoursADouble = true;
				Double.parseDouble(rates[i]);
				employee_indicies.add(i);
			} catch (NumberFormatException nfe){
				
				if (!job_hours_column[i].equals("") || isHoursADouble){
					employee_indicies.add(-i);
				}
			}
		}
		return employee_indicies;
	}
	
	//returns all aggregate rows that have worked in job id
	public ArrayList<AggregateRow> getAggregateRows(String job_id){
		ArrayList<AggregateRow> rowsOnJob = new ArrayList<AggregateRow>();
		ArrayList<Integer> indiciesOnJob = this.getEmployeesOnJobIndex(job_id);
		for (int i = 0; i < indiciesOnJob.size(); i++){
			if (!(indiciesOnJob.get(i) < 0)){
				rowsOnJob.add(createAggregateRow(job_id, indiciesOnJob.get(i)));
			}
		}
		return rowsOnJob;
	}
	
	public ArrayList<ErrorRow> getErrorRows(String job_id){
		ArrayList<ErrorRow> errorRows = new ArrayList<ErrorRow>();
		ArrayList<Integer> indiciesOnJob = this.getEmployeesOnJobIndex(job_id);
		for (int i = 0; i < indiciesOnJob.size(); i++){
			if (indiciesOnJob.get(i) < 0){
				errorRows.add(createErrorRow(job_id, -indiciesOnJob.get(i)));
			}
		}
		return errorRows;
	}
	

	
	/*
	 * functions
	 */
	
	//calculates the total hours performed by employee on a particular job id
	public double calculateJobHoursColumn(String job_id){
		String[] job_hours_column = getJobHoursColumn(job_id);
		double totalHours = 0;
		for (int i = 1; i < job_hours_column.length; i++){
			try {
				totalHours += Double.parseDouble(job_hours_column[i]);
			} catch (NumberFormatException nfe){
				//do nothing is String cannot be converted into double.
			}
		}
		
		return totalHours;
		
	}
	
	//check to see if the table contains a job id
	public boolean containsJobId(String job_id){
		for (int i = 0; i < job_ids.length; i++){
			if (job_ids[i].equals(job_id)){
				return true;
			}
		}
		return false;
	}
	
	public AggregateRow createAggregateRow(String job_id, int rowIndex){
		
		double rate = Double.parseDouble(rates[rowIndex]);
		String employee = employees[rowIndex];
		double hours= getEmployeeHours(job_id, rowIndex);
			
		AggregateRow createdRow = new AggregateRow(rate, employee, hours);
		return createdRow;
	}
	
	public ErrorRow createErrorRow(String job_id, int rowIndex){
		String rate = rates[rowIndex];
		String employee = employees[rowIndex];
		String hours = getEmployeeHoursAsString(job_id, rowIndex);
		
		ErrorRow errorRow  = new ErrorRow(employee, rate, hours);
		return errorRow;
		
	}
	
}



















