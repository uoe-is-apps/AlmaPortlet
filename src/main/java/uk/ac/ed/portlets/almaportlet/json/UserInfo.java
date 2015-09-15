package uk.ac.ed.portlets.almaportlet.json;

import uk.ac.ed.portlets.almaportlet.data.PatronID;

public class UserInfo {
	
	public static final String SESSION_ATTR = "uk.ac.ed.portlets.mylibraryinfo.user_info";

	private String uun;
	private String barcode;
	private String surname;
	
	private PatronID patronID;
	
	private boolean validated;
	
	public UserInfo() {
		
		this.validated = false;
	}
	
	/* 
	 * creates a user info 
	 * that requires validation (check for null barcode and surname)
	 */
	public UserInfo(String uun, String barcode, String surname){
		this.uun = uun;
		this.barcode = barcode;
		this.surname = surname;
		this.validated = false;
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

	public PatronID getPatronID() {
		return patronID;
	}

	public void setPatronID(PatronID patronID) {
		this.patronID = patronID;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

        public String getUun() {
            return uun;
        }

        public void setUun(String uun) {
            this.uun = uun;
        }

        
        
        @Override
        public String toString() {
            return "UserInfo{" + "barcode=" + barcode + ", surname=" + surname + ", patronID=" + patronID + ", validated=" + validated + '}';
        }

        
}
