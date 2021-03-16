import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Contact implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int contactID;
	private String contactName;
	private String emailAddress;
	private List<String> contactNumber = new ArrayList<>();
	
	public int getContactID() {
		return this.contactID;
	}
	
	public String getContactName() {
			return this.contactName;
	}
	
	public String getContactEmail() {
	return this.emailAddress;
	}
	
	public List<String> getContactNumber() {
		return this.contactNumber;
	}
	
	public void setContactID(int id) {
		this.contactID = id;
	}
	
	
	public void setContactName(String name) {
		this.contactName = name;
	}
	
	public void setContactEmail(String email) {
		this.emailAddress = email;
	}
	
	public void setContactNumber(String number) {
		this.contactNumber.add(number);
	}
	
	@Override
	public int hashCode() {
		return this.contactID;
	}
	
	@Override
	public boolean equals(Object c) {
		return (c instanceof Contact && ((Contact) c).getContactID()==this.contactID);
	}
}
