import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class ContactWorker {
	
	public static void display(List<Contact> contacts) {
		System.out.println("==================Contacts============================");
		for(Contact c:contacts) {
			System.out.println("******************************************");
			System.out.println("Contact id :- "+c.getContactID());
			System.out.println("Contact name :- "+c.getContactName());
			System.out.println("Contact email :- "+c.getContactEmail());
			System.out.println("Contact Number:- ");
			for(String s:c.getContactNumber()) {
				System.out.println(s+" ");
			}
			System.out.println();
		}
		System.out.println("******************************************");
		System.out.println("======================================================");
	}
	
	
	public static void main(String []args) {
		int choice, id;
		Contact c;

		List<Contact> contactlist=null;
		Set<Contact> contactFromDB=null;
		
		Scanner sc = new Scanner(System.in);
		contactlist = new ArrayList<Contact>();
		while(true) {
			System.out.println("----------MENU----------");
			System.out.println("1: Add contact object");
			System.out.println("2: Remove contact");
			System.out.println("3: Search contact by name");
			System.out.println("4: Search contact by numbers");
			System.out.println("5: Add contact number");
			System.out.println("6: Sort contact by names");
			System.out.println("7: Read contact from file");
			System.out.println("8: Serialize");
			System.out.println("9: Deserialize");
			System.out.println("10: Populate from db");
			System.out.println("11: Add list to existing contact list");
			System.out.println("12: Display all contacts");
			System.out.println("13: Exit");
			choice = sc.nextInt();
			switch(choice) {
				case 1: System.out.print("Enter ContactID: ");
						id = sc.nextInt();
						sc.nextLine();
						System.out.print("Enter contact name: ");
						String contactName = sc.nextLine();
						System.out.print("Enter email id: ");
						String email = sc.nextLine();
						System.out.print("Enter contact numbers in comma: ");
						
						String []nums = sc.nextLine().split(",");
						
						c = new Contact();
						c.setContactID(id);
						c.setContactName(contactName);
						c.setContactEmail(email);
						for(String number:nums) {
							c.setContactNumber(number);
						}
						
						ContactService.addContact(c, contactlist);
						
						System.out.print("Contact added \n");
						break;
				
				case 2: System.out.print("Enter contact id: ");
						id = sc.nextInt();
						c = new Contact();
						for(Contact each: contactlist)
							if(each.getContactID() == id) {
								c = each;
								break;
							}
						try {
							ContactService.removeContact(c, contactlist);
							System.out.println("Removed success !!");
						} catch (ContactNotFoundException e) {
							e.printStackTrace();
						}
						break;
						
				case 3: System.out.println("Enter name");
						try {
							sc.nextLine();
							ContactService.searchContactByName(sc.nextLine(), contactlist);
						} catch (ContactNotFoundException e) {
							
							e.printStackTrace();
						}
						break;
						
				case 4: System.out.print("Enter number: ");
						sc.nextLine();
						try {
							ContactService.searchContactByNumber(sc.nextLine(), contactlist);
						
						} catch (ContactNotFoundException e) {
							e.printStackTrace();
						}
						break;
						
				case 5: System.out.print("Enter contact id: ");
						id = sc.nextInt();
						System.out.print("Enter contact number");
						sc.nextLine();
						String number = sc.nextLine();
						ContactService.addContactNumber(id, number, contactlist);

						break;
						
				case 6: ContactService.sortContactsByName(contactlist);
						break;
				
				case 7:	ContactService.readContactsFromFile(contactlist,"Contact.txt");
						break;
				
				case 8: ContactService.serializeContactDetails(contactlist, "Contact.txt");
						break;
				
				case 9: 
					List<Contact> temp;
					temp = ContactService.deserializeContact("Contact.txt");
					display(temp);
					break;
				
				case 10: 
					contactFromDB = ContactService.populateContactFromDB();
						 break;
				
				case 11: 
					// Adds contacts from db to contacts list.
					ContactService.addContacts(contactlist, contactFromDB);
					break;
				
				case 12: display(contactlist);
						 break;
				
				case 13: sc.close();
						 System.exit(0);
						 break;
				
				default: System.out.print("Wrong choice !!");
			}
		}		
	}
}
