package uk.ac.ed.portlets.almaportlet.json;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ed.portlets.almaportlet.data.Fine;
import uk.ac.ed.portlets.almaportlet.data.LoanItem;
import uk.ac.ed.portlets.almaportlet.data.RequestItem;

public class PatronAccountSummary {
	
	private String voyagerUrl;

	private String barcode;
	private String surname;
	
	private int totalFines = 0;
	private List<Fine> fines;
	
	private int totalLoans = 0;
	private List<LoanItem> loanItems;
	
	private int totalRequests = 0;
	private List<RequestItem> requestItems = new ArrayList<RequestItem>();

	/**
	 * @return the requestItems
	 */
	public List<RequestItem> getRequestItems() {
		return requestItems;
	}

	public void setRequestItems(List<RequestItem> requestItems) {
		this.requestItems = requestItems;
	}



	public String getVoyagerUrl() {
		return voyagerUrl;
	}
	public void setVoyagerUrl(String voyagerUrl) {
		this.voyagerUrl = voyagerUrl;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getTotalFines() {
		return totalFines;
	}
	public void setTotalFines(int totalFines) {
		this.totalFines = totalFines;
	}
	public List<Fine> getFines() {
		return fines;
	}
	public void setFines(List<Fine> fines) {
		this.fines = fines;
	}
	public int getTotalLoans() {
		return totalLoans;
	}
	public void setTotalLoans(int totalLoans) {
		this.totalLoans = totalLoans;
	}
	public List<LoanItem> getLoanItems() {
		return loanItems;
	}
	public void setLoanItems(List<LoanItem> loanItems) {
		this.loanItems = loanItems;
	}
	public int getTotalRequests() {
		return totalRequests;
	}
	public void setTotalRequests(int totalRequests) {
		this.totalRequests = totalRequests;
	}

}
