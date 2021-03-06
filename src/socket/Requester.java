package socket;

import controllers.LoginController;
import controllers.WindowController;
import models.Attendant;
import models.Calendar;
import models.Event;
import models.Notification;
import models.Person;
import models.Room;
import models.UserGroup;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Requester {

	Socket con;

	/**
	 * Requester oppretter en connection med servereren, og etter det kan man
	 * kjøre "metoder" mot serveren. IP TIL SERVER MÅ SETTES HER!
	 */
	public Requester() {
		String host = "localhost";
		/** Define a port */
		int port = 25025;

		try {
			/** Obtain an address object of the server */
			InetAddress address = InetAddress.getByName(host);
			/** Establish a socket connection */
			con = new Socket(address, port);
			con.setSoTimeout(5000);
		} catch (EOFException e) {	
		} catch (ConnectException e) {
			WindowController.warning("Can't connect to server.");
			LoginController.getLoginController().setStatus(
					"Can't connect to server.");
		} catch (IOException e) {
			System.out.println("No internet connection.");
			LoginController.getLoginController().setStatus(
					"No internet connection.");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// USER GROUPS
	/**
	 * Tar inn en person og returnerer alle userGroups som personen er medlem i.
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<UserGroup> getUserGroups(Person p) {
		Command cmd = new Command("getUserGroups-person");
		ArrayList<UserGroup> userGroups = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			userGroups = (ArrayList<UserGroup>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return userGroups;
	}

	/**
	 * Tar inn en liste med userGroups og returnerer personer som er medlem i
	 * disse.
	 * 
	 * @param userGroups
	 * @return
	 */
	public ArrayList<Person> getPersons(ArrayList<UserGroup> userGroups) {
		ArrayList<Person> persons = null;
		Command cmd = new Command("getPersons-usergroup");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(userGroups);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			persons = (ArrayList<Person>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return persons;
	}

	/**
	 * Tar inn en liste med kalendere og returnerer alle userGroups som hører
	 * til kalenderene.
	 * 
	 * @param cal
	 * @return
	 */
	public ArrayList<UserGroup> getUserGroups(ArrayList<Calendar> cal) {
		Command cmd = new Command("getUserGroups-calendar");
		ArrayList<UserGroup> userGroups = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			userGroups = (ArrayList<UserGroup>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return userGroups;
	}

	/**
	 * Tar in en liste med userGroups og sletter de fra databasen.
	 * 
	 * @param userGroups
	 */
	public void deleteUserGroups(ArrayList<UserGroup> userGroups) {
		Command cmd = new Command("deleteUserGroups-usergroups");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(userGroups);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn en userGroup og oppretter den i databasen.
	 * 
	 * @param ug
	 */
	public UserGroup createUserGroup(UserGroup ug) {
		Command cmd = new Command("createUserGroup-usergroup");
		UserGroup userGroup = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(ug);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			userGroup = (UserGroup) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return userGroup;
	}

	/**
	 * Tar inn en userGroup, og oppdaterer userGroup i databasen.
	 * 
	 * @param ug
	 */
	public void addUsers(UserGroup ug) {
		Command cmd = new Command("addUsers-usergroup");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(ug);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Henter ut alle private userGroups fra databasen.
	 * 
	 * @return
	 */
	public ArrayList<UserGroup> getPrivateUserGroups() {
		Command cmd = new Command("getPrivateUserGroups");
		ArrayList<UserGroup> userGroups = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			userGroups = (ArrayList<UserGroup>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return userGroups;
	}

	/**
	 * Henter den personlige userGroupen for en Person.
	 * 
	 * @param p
	 * @return
	 */
	public UserGroup getPersonalUserGroup(Person p) {
		Command cmd = new Command("getPersonalUserGroup-person");
		UserGroup userGroup = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			userGroup = (UserGroup) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return userGroup;
	}

	/**
	 * Henter saltet til et brukernavn
	 * 
	 * @param p
	 * @return
	 */
	public Person getSalt(Person p) {
		Command cmd = new Command("getSalt-person");
		Person salt = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			salt = (Person) o;
		} catch (EOFException e) {
			LoginController.getLoginController().setStatus("Incorrect username or password");
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return salt;
	}

	// CALENDAR
	/**
	 * Tar inn en usergroup og henter alle kalendere usergroupen tilhører.
	 * 
	 * @param ug
	 * @return
	 */
	public ArrayList<Calendar> getCalendars(UserGroup ug) {
		Command cmd = new Command("getCalendars-usergroup");
		ArrayList<Calendar> calendars = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(ug);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			calendars = (ArrayList<Calendar>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return calendars;
	}

	public ArrayList<Calendar> getCalendars(Person p) {
		Command cmd = new Command("getCalendars-person");
		ArrayList<Calendar> calendars = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			calendars = (ArrayList<Calendar>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return calendars;
	}
	
	public ArrayList<UserGroup> getUserGroupsInCalendar(Calendar cal) {
		Command cmd = new Command("getUserGroupsInCalendar-calendar");
		ArrayList<UserGroup> userGroups = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			userGroups = (ArrayList<UserGroup>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return userGroups;
	}
	
	/**
	 * Tar inn en usergroup og henter alle kalendere usergroupen tilhører.
	 * 
	 * @param
	 * @return
	 */
	public ArrayList<Calendar> getAllCalendars() {
		Command cmd = new Command("getAllCalendars");
		ArrayList<Calendar> calendars = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			calendars = (ArrayList<Calendar>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return calendars;
	}

	/**
	 * Tar inn kalender og userGroup, oppdaterer kalender med de nye
	 * usergroupene og returnerer kalender.
	 * 
	 * @param cal
	 * @param ug
	 * @return
	 */
	public void addUserGroup(Calendar cal, UserGroup ug) {
		Command cmd = new Command("addUserGroup-calendar");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			oos.writeObject(ug);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn kalender og UserGroup og fjerner usergroup fra kalender
	 * 
	 * @param cal
	 * @param ug
	 */
	public void removeUserGroup(Calendar cal, UserGroup ug) {
		Command cmd = new Command("removeUserGroup-calendar");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			oos.writeObject(ug);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn et kalenderobjekt, oppretter kalenderen i databasen, og
	 * returnerer kalender med ny id.
	 * 
	 * @param cal
	 * @return
	 */
	public Calendar createCalendar(Calendar cal) {
		Command cmd = new Command("createCalendar-calendar");
		Calendar calendar = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			calendar = (Calendar) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return calendar;
	}

	/**
	 * Tar inn et kalenderobjekt og fjerner kalenderen fra databasen.
	 * 
	 * @param cal
	 */
	public void deleteCalendar(Calendar cal) {
		Command cmd = new Command("deleteCalendar-calendar");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	// EVENTS
	/**
	 * Tar inn en arraylist med kalendere og returnerer alle Events som hører
	 * til kalenderene
	 * 
	 * @param cal
	 * @return
	 */
	public ArrayList<Event> getEvents(ArrayList<Calendar> cal, UserGroup ug) {
		Command cmd = new Command("getEvents-calendars-usergroup");
		ArrayList<Event> events = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			oos.writeObject(ug);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			events = (ArrayList<Event>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return events;
	}

	public ArrayList<Event> getEvents(Calendar cal) {
		Command cmd = new Command("getEvents-calendar");
		ArrayList<Event> events = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(cal);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			events = (ArrayList<Event>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return events;
	}

	/**
	 * Denne må returnere et Event og oppdatere eventet. Tar inn et event og et
	 * rom, oppretter eventet, booker rom og returnerer eventet.
	 * 
	 * @param event
	 */
	public Event createEvent(Event event) {
		Command cmd = new Command("createEvent-event");
		Event ev = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			ev = (Event) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return ev;
	}
	
	public Event createGroupEvent(Event event) {
		Command cmd = new Command("createGroupEvent-event");
		Event ev = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			ev = (Event) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return ev;
	}

	/**
	 * Oppdaterer event.
	 * 
	 * @param event
	 */
	public void editEvent(Event event, UserGroup sender) {
		Command cmd = new Command("editEvent-event-usergroup");
		Event ev = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			oos.writeObject(sender);
		} catch (EOFException e) {
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	/**
	 * Tar inn et event og sletter eventet fra databasen.
	 * 
	 * @param event
	 */
	public void deleteEvent(Event event) {
		Command cmd = new Command("deleteEvent-event");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	// NOTIFICATION
	/**
	 * Denne metoden tar inn en person og returnerer alle notifications for
	 * personen.
	 * 
	 * @param ug
	 * @return
	 */
	public ArrayList<Notification> getNotifications(UserGroup ug) {
		Command cmd = new Command("getNotifications-person");
		ArrayList<Notification> notification = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(ug);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			notification = (ArrayList<Notification>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return notification;
	}

	/**
	 * Tar inn en notification og oppretter notification i databasen.
	 * 
	 * @param n
	 * @return
	 */
	public void setNotification(Notification n) {
		Command cmd = new Command("setNotification-notification");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(n);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn en notification og en person og oppdaterer notification til
	 * "read" i databasen.
	 * 
	 * @param n
	 * @param ug
	 */
	public void setRead(Notification n, UserGroup ug) {
		Command cmd = new Command("setRead-notification-person");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(n);
			oos.writeObject(ug);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	// PERSON
	/**
	 * Tar inn en person og returnerer "hele" personen med id, navn etc. (nok å
	 * ta inn brukernavn og passord i personen)
	 * 
	 * @param p
	 * @return
	 */
	public Person getPerson(Person p) {
		Command cmd = new Command("getPerson-person");
		Person person = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			person = (Person) o;
		} catch (EOFException e) {
			LoginController.getLoginController().setStatus("Incorrect username or password.");
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return person;
	}

	/**
	 * Tar inn en person, og oppretter personen i databasen.
	 * 
	 * @param p
	 * @return
	 */
	public void createPerson(Person p) {
		Command cmd = new Command("createPerson-person");
		Person person = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn en person og sletter personen fra databasen
	 * 
	 * @param p
	 */
	public void deletePerson(Person p) {
		Command cmd = new Command("deletePerson-person");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 *
	 * LogIn må returnere en person og sette navn etc.
	 * 
	 * @param p
	 */
	public Person authenticate(Person p) {
		Command cmd = new Command("authenticate-username-pass");
		Person person = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(p);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			person = (Person) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return person;
	}

	// ROOM
	/**
	 * Returnerer en liste med alle rom.
	 * 
	 * @return
	 */
	public ArrayList<Room> getRooms() {
		Command cmd = new Command("getRooms");
		ArrayList<Room> rooms = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			rooms = (ArrayList<Room>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return rooms;
	}

	/**
	 * Tar inn et event og returnerer tilgjengelige rom for eventet.
	 * 
	 * @param event
	 * @return
	 */
	public ArrayList<Room> getAvailableRooms(Event event) {
		Command cmd = new Command("getAvailableRooms-event");
		ArrayList<Room> rooms = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			InputStream is = con.getInputStream();
			try {
				ObjectInputStream os = new ObjectInputStream(is);
				Object o = os.readObject();
				rooms = (ArrayList<Room>) o;
			} catch (EOFException e) {
				System.out.println("No rooms found.");
			}
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return rooms;
	}

	/**
	 * Tar inn et event og et rom og oppretter rombooking. Om et event
	 * eksisterer med rombooking fra før vil denne booking bli slettet, og en ny
	 * opprettet.
	 * 
	 * @param event
	 * @param room
	 */
	public void bookRoom(Event event, Room room) {
		Command cmd = new Command("bookRoom-event-room");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			oos.writeObject(room);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn et event og room. Oppdaterer location til gitt event
	 * 
	 * @param event
	 * @param room
	 */
	public void updateLocation(Event event, Room room) {
		Command cmd = new Command("updateLocation-event-room");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			oos.writeObject(room);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Tar inn et event og returnerer eventuell roombooking for eventet.
	 * 
	 * @param event
	 * @return room
	 */
	public Room getEventRoom(Event event) {
		Command cmd = new Command("getEventRoom-event");
		Room room = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			InputStream is = con.getInputStream();
			try {
				ObjectInputStream os = new ObjectInputStream(is);
				Object o = os.readObject();
				room = (Room) o;
			} catch (EOFException e) {
				System.out.println("No room yet.");
			}
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return room;
	}

	// Attends
	/**
	 * Updates person to mark if he is attending or not in the database.
	 * 
	 * @param event
	 * @param attendant
	 */
	public void updateAttends(Event event, Attendant attendant) {
		Command cmd = new Command("updateAttends-event-attendant");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			oos.writeObject(attendant);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Inserts an attendant to Attends
	 * 
	 * @param event
	 * @param userGroup
	 */
	public void setAttends(Event event, ArrayList<UserGroup> userGroup) {
		Command cmd = new Command("setAttends-event-attendants");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			oos.writeObject(userGroup);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Henter alle inviterte deltakere til et event.
	 * 
	 * @param event
	 * @return
	 */
	public ArrayList<Attendant> getAttendants(Event event) {
		Command cmd = new Command("getAttendants-event");
		ArrayList<Attendant> attendants = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			attendants = (ArrayList<Attendant>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return attendants;
	}
	
	public ArrayList<Person> getAllPersons() {
		Command cmd = new Command("getAllPersons");
		ArrayList<Person> persons = null;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			InputStream is = con.getInputStream();
			ObjectInputStream os = new ObjectInputStream(is);
			Object o = os.readObject();
			persons = (ArrayList<Person>) o;
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
		return persons;
	}
	
	public boolean editUserGroup(UserGroup ug) {
		Command cmd = new Command("editUserGroup-usergroup");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(ug);
		} catch (EOFException e) {
			return false;
		} catch (IOException e) {
			System.out.println(e);
			return false;
		} catch (ClassCastException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public void updateAttendsPersonalEvent(Event event, Attendant attendant) {
		Command cmd = new Command("updateAttendsPersonalEvent-event-attendant");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					con.getOutputStream());
			oos.writeObject(cmd);
			oos.writeObject(event);
			oos.writeObject(attendant);
		} catch (EOFException e) {
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassCastException e) {
			System.out.println(e);
		}
	}

	/**
	 * Metode for å lukke connection med server.
	 * 
	 * @return boolean
	 */
	public boolean closeConnection() {
		try {
			con.close();
			return true;
		} catch (IOException e) {
			System.out.println(e);
			return false;
		}
	}
}