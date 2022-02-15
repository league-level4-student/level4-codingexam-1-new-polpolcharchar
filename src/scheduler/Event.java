package scheduler;

public class Event {

	private int hour;
	private int minutes;
	
	private String activity;
	
	Event(int h, int m, String a){
		hour = h;
		minutes = m;
		activity = a;
	}
	
	public void printActivity() {
		System.out.println(hour + ":" + (minutes < 10 ? "0" + minutes : minutes) + "---> " + activity);
	}
	
	public void printActivityWIndex(int i) {
		System.out.println(i + ") " + hour + ":" + (minutes < 10 ? "0" + minutes : minutes) + "---> " + activity);
	}
	
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minutes;
	}
	
	public int getTotalTime() {
		return hour * 60 + minutes;
	}
	
}
