class QuestInfo {
	String name;
	int timeStarted;
	int timeCompleted;
	
	QuestInfo (String name, int time) {
		this.name = name;
		timeStarted = time;
		timeCompleted = -1;
	}
	
	int TimeElapsed () { 
		if (timeCompleted != -1) return timeCompleted - timeStarted; 
		else return -1;
		}
	
	
}