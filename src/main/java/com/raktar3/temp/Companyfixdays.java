package com.raktar3.temp;

import java.util.List;

import com.raktar3.entities.Company;
import com.raktar3.entities.Machine;

public class Companyfixdays {

	
	Company company;
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<String> getFixdays() {
		return fixdays;
	}

	public void setFixdays(List<String> fixdays) {
		this.fixdays = fixdays;
	}

	List<String> fixdays;
	
	boolean vangepe=false;

	public boolean isVangepe() {
		return vangepe;
	}

	public void setVangepe(boolean vangepe) {
		this.vangepe = vangepe;
	}
	
	
}
