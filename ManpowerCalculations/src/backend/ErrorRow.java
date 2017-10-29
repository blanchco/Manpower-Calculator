//this object class identifies what row have invalid input which cannot be used in the program.
package backend;

import java.util.ArrayList;

public class ErrorRow {
	private String employee;
	private String rate;
	private String hours;
	private int tableNumber;
	
	public ErrorRow(String employee, String rate, String hours){
		this.employee = employee;
		this.rate = rate;
		this.hours = hours;
		
	}
	
	
	public String getEmployee(){
		return employee;
	}
	
	public String getRate(){
		return rate;
	}
	
	public String getHours(){
		return hours;
	}
	
	public boolean isRateCorrect(){
		try {
			Double.parseDouble(rate);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	public boolean isHoursCorrect(){
		try {
			Double.parseDouble(hours);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
	public void setTableNumber(int tableNumber){
		this.tableNumber = tableNumber;
	}
	
	public int getTableNumber(){
		return tableNumber;
	}
	
	
	public String getErrorMessage(){
		String errorMessage;
		if (!isRateCorrect() && !isHoursCorrect()){
			if (rate.equals("")){
				errorMessage = "Week " + getTableNumber() + ": There may be a error with employee: '" + getEmployee() + "' with no rate and hours value: '" + getHours() + "'\n";
			} else {
				errorMessage = "Week " + getTableNumber() + ": There may be a error with employee: '" + getEmployee() + "' with rate value: '" + getRate() + "' and hours value: '" + getHours() + "'\n";
			}
			return errorMessage;
		} else if (!isRateCorrect()){
			if (rate.equals("")){
				errorMessage = "Week " + getTableNumber() + ": There may be a error with employee: '" + getEmployee() + "' with no rate\n";
			} else {
				errorMessage = "Week " + getTableNumber() + ": There may be a error with employee: '" + getEmployee() + "' with rate value: '" + getRate() + "'\n";
			}
			return errorMessage;
		} else {
			errorMessage = "Week " + getTableNumber() + ": There may be a error with employee: '" + getEmployee() + "' with hours value: '" + getHours() + "'\n";
			return errorMessage;
		}
		
	}
}
