package it.unibz.pomodroid.factories;

import it.unibz.pomodroid.persistency.Activity;
import it.unibz.pomodroid.persistency.DBHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import android.util.Log;
import it.unibz.pomodroid.exceptions.PomodroidException;


/**
 * 
 * @author Thomas Schievenin
 *
 * A class that saves all information about tickets it into the database.
 * Each ticket taken from TRAC is checked. If some information are empty, the
 * class provides some auto-generated values.
 *
 */
public class ActivityFactory {
	
	/**
	 * @param id trac identifier 
	 * @param deadLine date within the work has be done
	 * @param attributes list containing all information about one ticket
	 * @param dbHelper reference to the database
	 */
	public int produce (Vector<HashMap<String, Object>> tickets, DBHelper dbHelper) throws PomodroidException {
		String auto = "AUTOGENERATED VALUE for ";
		int ticketsNumber = 0;
		for (HashMap<String, Object> ticket : tickets) {
			ticketsNumber++;
			if (isEmpty(ticket.get("summary").toString())) ticket.put("summary", auto + "summary.");
			if (isEmpty(ticket.get("description").toString())) ticket.put("description", auto + "description.");

			Activity activity = new Activity(0, 
					new Date(), 
					(Date)ticket.get("deadLine"), 
					ticket.get("summary").toString(), 
					ticket.get("description").toString(), 
					"trac", 
					(Integer)ticket.get("ticketId"), 
					ticket.get("priority").toString(),
					ticket.get("reporter").toString(), 
					ticket.get("type").toString());

			activity.save(dbHelper);
		}

		return ticketsNumber;
	}
	
	private boolean isEmpty (String string) throws PomodroidException{
		return (string.equals("")||string.equals(null));
	}
	
}
