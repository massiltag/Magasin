package operations;
import java.util.ArrayList;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaction implements Serializable {
	private static final long serialVersionUID = 7178234810945956906L;
	int id;
	private LocalDateTime date;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	ArrayList<LigneCom> lignes;
	
	public Transaction(ArrayList<LigneCom> arr) {
		this.date = LocalDateTime.now();
		this.lignes = arr;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	public LocalDateTime getDate(){
		return this.date;
	}
	
	
	public String getDateString(){
		return formatter.format(this.date);
	}
	
	public int getID() {
		return this.id;
	}
}
