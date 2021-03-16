import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import DBUtil.Connect;

@SuppressWarnings("serial")
class ContactNotFoundException extends Exception{
	ContactNotFoundException(String msg){
		super(msg);
	}
}

public class ContactService {
	static void addContact(Contact contact,List<Contact> contacts) {
		contacts.add(contact);
	}
	
	static void removeContact(Contact contact,List<Contact> contacts) throws ContactNotFoundException{
		contacts
		.stream()
		.filter(p->p
				.getContactID()!=contact.getContactID()
				);
	}
	
	static void searchContactByName(String name,List<Contact> contact)throws ContactNotFoundException {
		if(contact
				.stream()
				.anyMatch(p->p
						.getContactName()
						.equals(name)
						)
			) 
		{
			System.out.println("Contact Found");
		}
		else {
			throw new ContactNotFoundException("No contact found");
		}
	}
	
	static void searchContactByNumber(String number,List<Contact> contact)throws ContactNotFoundException{
		if(contact
			.stream()
			.anyMatch(p->p
					.getContactNumber()
					.stream()
					.anyMatch(q->q.equals(number))
					)				
		) 
		{
			System.out.println("Contact Found");
		}
		else {
			throw new ContactNotFoundException("No Contact Found");
		}
	}
	
	static void addContactNumber(int contactID,String contactNo,List<Contact> contacts) {
		boolean flag = true;
		for(Contact c:contacts) {
			if(c.getContactID()==contactID) {
				c.setContactNumber(contactNo);
				flag = false;
			}
		}
		if(flag) System.out.println("Contact Not Found");
		else System.out.println("Added Succesfully");
	}
	
	
	static void sortContactsByName(List<Contact> contacts) {
		Collections.sort(contacts, (p1,p2)-> p1.getContactName().compareTo(p2.getContactName()));
		for(Contact c:contacts) {
			System.out.println("Contact id "+c.getContactID());
			System.out.println("Contact name "+c.getContactName());
			System.out.println("Contact email "+c.getContactEmail());
			System.out.println("Contact Number:- ");
			for(String s:c.getContactNumber()) {
				System.out.print(s+" ");
			}
		}
	}
	
	static void readContactsFromFile(List<Contact> contacts,String filename) {
		try(Scanner sc = new Scanner(new File(filename))){
			while(sc.hasNext()) {
				String temp[] = sc.nextLine().split(",");
				Contact con = new Contact();
				int id = Integer.parseInt(temp[0]);
				con.setContactID(id);
				con.setContactName(temp[1]);
				con.setContactEmail(temp[2]);
				for(int i=3;i<temp.length;i++) {
					con.setContactNumber(temp[i]);
				}
				contacts.add(con);
			}
		}
		catch(Exception e) {
			System.out.println("File not found "+e.getMessage());
		}
	}
	
	static void serializeContactDetails(List<Contact> contacts,String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(contacts);
			out.close();
			fos.close();
		}
		catch(Exception e) {
			System.out.println("Cannot Serialize "+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	static List<Contact> deserializeContact(String filename){
		List<Contact> con = null;
		try {
		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream ois  = new ObjectInputStream(fis);
		
		con = (List<Contact>) ois.readObject();
		ois.close();
		fis.close();
		}
		catch(Exception e) {
			System.out.println("Cannot deserialize "+e.getMessage());
		}
		return con;
	}
	
	static Set<Contact> populateContactFromDB(){
		Set<Contact> contacts = new HashSet<>();
		Connection con = Connect.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from contact_tbl");
			while(rs.next()) {
				Contact c = new Contact();
				c.setContactID(rs.getInt(1));
				c.setContactName(rs.getString(2));
				c.setContactEmail(rs.getString(3));
				for(String number:rs.getString(4).split(",")) {
					c.setContactNumber(number);
				}
				contacts.add(c);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return contacts;
	}
	
	static boolean addContacts(List<Contact> existingContact , Set<Contact> newContacts) {
		try {
		newContacts
		.forEach(p->existingContact.add(p));
		return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
