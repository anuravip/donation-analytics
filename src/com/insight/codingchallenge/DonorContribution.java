package com.insight.codingchallenge;

public class DonorContribution {

	private int year;
	private float amount;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public DonorContribution(int year,float amount){
		this.year=year;
		this.amount=amount;
	}
	
}
