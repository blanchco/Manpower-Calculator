package backend;

public class AggregateRow {
	private double rate;
	private String employee;
	private double hours;
	private double wage;
	
	public AggregateRow(double rate, String employee, double hours){
		this.rate = rate;
		this.employee = employee;
		this.hours = hours;
		this.wage = this.rate * this.hours;
	}
	
	public double getRate(){
		return rate;
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
	
	public boolean isValidated(AggregateRow ar){
		if (ar.getRate() == -1 || ar.getHours() == -1){
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
