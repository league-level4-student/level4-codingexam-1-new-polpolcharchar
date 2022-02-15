package scheduler;

import java.util.Scanner;

/*
 * Objective: Create a weekly scheduling application.
 * 
 * You may create any additional enums, classes, methods or variables needed
 * to accomplish the requirements below:
 * 
 * - You should use an array filled with enums for the days of the week and each
 *   enum should contain a LinkedList of events that includes a time and what is 
 *   happening at the event.
 * 
 * - The user should be able to to interact with your application through the
 *   console and have the option to add events, view events or remove events by
 *   day.
 *   
 * - Each day's events should be sorted by chronological order.
 *  
 * - If the user tries to add an event on the same day and time as another event
 *   throw a SchedulingConflictException(created by you) that tells the user
 *   they tried to double book a time slot.
 *   
 * - Make sure any enums or classes you create have properly encapsulated member
 *   variables.
 */
public class Scheduler {

	static Scanner s = new Scanner(System.in);
	
    public static void main(String[] args) {
    	System.out.println("All times in military time(Hours from 0 - 23)");
    	
    	days dayList[] = days.values();
    	
    	while(true) {
    		int choice = -1;
    		do {
    			System.out.println();
    			System.out.println("Add Event(0)\nRemove Event(1)\nView One Day's Events(2)\nView All Events(3)");
    			choice = s.nextInt();
    		}while(choice < 0 || choice > 3);
    		
    		if(choice == 0) {
    			//add event
    			
    			int dayChoice = -1;
    			int eventHour = -1;
    			int eventMinutes = -1;
    			String eventName = "";
    			
    			System.out.println("Which day would you like to add to?(Monday{0}, Tuesday{1}, Wednesday{2}, Thursday{3}, Friday{4}, Saturday{5}, Sunday{6})");
    			do {
    				dayChoice = s.nextInt();
    			}while(dayChoice < 0 || dayChoice > 6);
    			System.out.println(dayList[dayChoice].name() + ":");

    			System.out.println("Enter the hour of this activity(0 - 23):");
    			do {
    				eventHour = s.nextInt();
    			}while(eventHour < 0 || eventHour > 23);
    			
    			System.out.println("Enter the minute of this activity(0 - 59):");
    			do {
    				eventMinutes = s.nextInt();
    			}while(eventMinutes < 0 || eventMinutes > 59);
    			
    			System.out.println("Enter the name of the activity:");
    			s.nextLine();
    			eventName = s.nextLine();
    			
    			try {
					dayList[dayChoice].addEvent(eventHour, eventMinutes, eventName);
				} catch (SchedulingConflictException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					e.doubleBookAlert();
				}	
    		}else if(choice == 1) {
    			//remove event
    			System.out.println("Which day would you like to remove?(Monday{0}, Tuesday{1}, Wednesday{2}, Thursday{3}, Friday{4}, Saturday{5}, Sunday{6})");
    			int dayChoice = -1;
    			do {
    				dayChoice = s.nextInt();
    			}while(dayChoice < 0 || dayChoice > 6);
    			System.out.println(dayList[dayChoice].name() + ":");

    			dayList[dayChoice].removeEvent();
    		}else if(choice == 2) {
    			//view events
    			System.out.println("Which day would you like to view?(Monday{0}, Tuesday{1}, Wednesday{2}, Thursday{3}, Friday{4}, Saturday{5}, Sunday{6})");
    			int dayChoice = -1;
    			do {
    				dayChoice = s.nextInt();
    			}while(dayChoice < 0 || dayChoice > 6);
    			System.out.println(dayList[dayChoice].name() + ":");
    			dayList[dayChoice].printEvents();
    			
    		}else if(choice == 3) {
    			for(int i = 0; i < dayList.length; i++) {
    				System.out.println(dayList[i].name() + ":");
    				dayList[i].printEvents();
    			}
    		}
    	}
    }
}
enum days{
	Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday;
	
	private LinkedList<Event> list;

	private days() {
		list = new LinkedList<>();
	}
	
	public void printEvents() {
		Node<Event> currentNode = list.getHead();
		while(currentNode != null) {
			currentNode.getValue().printActivity();
			currentNode = currentNode.getNext();
		}
	}
	
	public void addEvent(int h, int m, String a) throws SchedulingConflictException {
		
		if(list.getHead() == null) {
			list.add(new Event(h, m, a));
		}else {
			Node<Event> currentNode = list.getHead();
			
			while(currentNode.getValue().getTotalTime() < h * 60 + m) {
				currentNode = currentNode.getNext();
				if(currentNode == null) {
					break;
				}
			}
			
			if(currentNode == null) {
				list.add(new Event(h, m, a));
			}else if(currentNode.getValue().getHour() == h && currentNode.getValue().getMinute() == m) {
				throw new SchedulingConflictException();
			}else {
				Node<Event> newNode = new Node<>(new Event(h, m, a));
				if(list.getHead() == currentNode) {
					currentNode.setPrev(newNode);
					newNode.setNext(currentNode);
					list.setHead(newNode);
				}else {
					
					newNode.setNext(currentNode);
					newNode.setPrev(currentNode.getPrev());
					
					currentNode.getPrev().setNext(newNode);
					currentNode.setPrev(newNode);	
				}	
			}	
		}
	}
	
	public void removeEvent() {
		Scanner s = new Scanner(System.in);
		Node<Event> currentNode = list.getHead();
		int currentIndex = 0;
		while(currentNode != null) {
			currentNode.getValue().printActivityWIndex(currentIndex);
			currentIndex++;
			currentNode = currentNode.getNext();
		}
		
		
		int removeIndex = -1;
		System.out.println("Enter an index:");
		do {
			
			removeIndex = s.nextInt();
		}while(removeIndex < 0 || removeIndex > currentIndex - 1);
		
		
		currentNode = list.getHead();
		for(int i = 0; i < removeIndex; i++) {
			currentNode = currentNode.getNext();
		}
		if(currentNode == list.getHead() && currentNode.getNext() == null) {
			list.setHead(null);
			list.setTail(null);
		}
		else if(currentNode == list.getHead()) {
			list.setHead(currentNode.getNext());
			currentNode.getNext().setPrev(null);
		}else if(currentNode == list.getTail()){
			list.setTail(currentNode.getPrev());
			currentNode.getPrev().setNext(null);
		}else {
			currentNode.getPrev().setNext(currentNode.getNext());
			currentNode.getNext().setPrev(currentNode.getPrev());
		}
		currentNode = null;	
	}
}
class SchedulingConflictException extends Exception {
	
	public void doubleBookAlert() {
		System.out.println("This time slot is already occupied!");
	}
}