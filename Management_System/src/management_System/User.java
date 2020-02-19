package management_System;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

class User{
 String username;
 String password;
 String phoneno;
public User(String username, String password, String phoneno) 
{
	super();
	this.username = username;
	this.password = password;
	this.phoneno = phoneno;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhoneno() {
	return phoneno;
}
public void setPhoneno(String phoneno) {
	this.phoneno = phoneno;
}
@Override
public String toString() {
	return "User [username=" + username + ", password=" + password + ", phoneno=" + phoneno + "]";
}

public User() {
	try (Scanner sc = new Scanner(System.in)) {
		String userName;
		String passWord;
		String phoneno;
		ArrayList<User> al=new ArrayList<User>();
		while(1>0) {
			System.out.println("Choose what you want to do: ");
			int choice;
			System.out.println("Enter 1 for sign up: ");		
			System.out.println("Enter 2 for login : ");
			System.out.println("enter 0 for exit: ");
			choice =sc.nextInt();
		switch(choice) {
		case 1:
		System.out.println("Enter username: ");
		userName =sc.next();
		System.out.println("Enter Password: ");
		passWord=sc.next();
		System.out.println("Enter Phone No.: ");
		phoneno=sc.next();
		User c1=new User(userName,passWord,phoneno);	
		al.add(c1);
		Iterator<User> i=al.iterator();
		while(i.hasNext()) {
			User c=i.next();
			System.out.println(c);	
		}
		break;
		case 2:
			System.out.println("Enter username: ");
			userName =sc.next();
			System.out.println("Enter Password: ");
			passWord=sc.next();
		    int flag=0;
			Iterator<User> i2=al.iterator();
			while(i2.hasNext()) 
			{
				User c=i2.next();
				if(userName.equals(c.username) && passWord.equals(c.password)) {
					System.out.println("welcome "+userName+"\n book ur flight?");
					flag=1;
					Database prodDB = new Database();
					prodDB.bootstrap();
					
					 //Initialize console1
					boolean always = true;
					String passengerName = null;
					int flightNumber = 0;
					int seatNumber = 0;
					LocalDate departureDate = LocalDate.now();
					//reading input from screen;
					BufferedReader screenInput = new BufferedReader(new InputStreamReader(System.in));
					
					while(always)
					{
						// ask for passenger name and add
						System.out.println("Enter Passenger: ");
						
						try {
							passengerName = screenInput.readLine();
						} catch (IOException e) {
							System.out.println("Sorry, i dont understand.");
						}
						// checking whether an existing passenger or new one
						boolean result = prodDB.addPassenger(passengerName);
						
						// welcoming existing passenger or new one

						if(result)
						{
							System.out.println("Welcome back: " +  passengerName);
						}
						else{
							System.out.println("Welcome: " +  passengerName);
						}
						// show flights and ask for flights
						System.out.println("\nHere are available flights: ");
						for(Flight item: prodDB.getFlight()){
							System.out.println(item);
						}
						System.out.println("Enter the flight no to book");
						try{
						flightNumber = Integer.parseInt(screenInput.readLine());
						} catch (IOException e) {
							System.out.println("Please enter flight no");
					}
						catch (NumberFormatException e) {
							System.out.println("That was not a number");
						}
						
						// Show available seats and asks
						
						System.out.println("\nAssuming you are flying today,");
						System.out.println("Here are available seats on that flight: ");
						ArrayList<Seat> openSeats = prodDB.getOpenSeats(departureDate, flightNumber);
						for(Seat item: openSeats){
							System.out.print(item.getSeatNo() + " ");
						}
						System.out.println("\nEnter the seat you want: ");
						try{
							seatNumber = Integer.parseInt(screenInput.readLine());
							
						}
						catch (IOException e) {
							System.out.println("Please enter a seat no");
					}
						String ticketInfo = prodDB.addTicket(departureDate, passengerName, flightNumber, seatNumber);
						System.out.println("\nReservations Successful!! Here are your ticket details");
						System.out.println(ticketInfo + "\n");
					}
					break;
				}	
				else 
					{
					flag=0;
					}
			}
			if(flag==0)
			{
				System.out.println("invalid user ");
			}
			break;
			
		case 0: System.exit(0);
		}
		}
	}
	
}
}
