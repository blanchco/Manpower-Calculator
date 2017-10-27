package backend;

public class AggregateRow {
	private String employee;
	private double hours;
	private double wage;
	
	public AggregateRow(double rate, String employee, double hours){
		this.employee = employee;
		if (hours != -1){
			this.hours = hours;
		} else {
			
		}
		
		if (rate != -1){
			this.wage = rate * this.hours;
		}
	}
	
	public String getEmployee(){
		return employee;
	}
	
	public double getHours(){
		return hours;
	}
	
	public double getWage(){
		return wage;
	}
	
	public void addHours(double hours){
		this.hours += hours;
	}
	
	public void addWage(double wage){
		this.wage += wage;
	}
	
	public boolean hasSameEmployee(AggregateRow ar){
		if (!ar.getEmployee().equals(this.employee)){
			return false;
		} else {
			return true;
		}
	}
	
	public AggregateRow combineAggregateRows(AggregateRow ar){
		if (this.hasSameEmployee(ar)){
			this.addHours(ar.getHours());
			this.addWage(ar.getWage());
		}
		return this;
	}
}
