
import java.util.Scanner;
import java.io.*;
import java.util.*;



class ParsingParticipant {
//read file put file name and create a new file with the right information
	 String fileRead;
	 PrintWriter outputFile;
	 String[] items;
	 ArrayList<Participant> participants;
	 int ParticipantNumber;
	 
	 int timeStored; //temp variable
	 
	 void initializeParticipants () {
		 participants = new ArrayList<Participant> (); 
		 ParticipantNumber = -1;
	 }
	 
	 /*void getFileName () {
		 Scanner scanner = new Scanner (System.in);
		 System.out.println("Enter name of the file:");
		 fileRead = scanner.nextLine();
		 System.out.println("Name of the file is "+ fileRead);
		 scanner.close(); 
	 }*/
	 
	 void readInMultiParticipantData ()
	 {
		//Scanner scanner;
		//do {
                  //  int x = 0;
                            
                    for (int i = 101; i<192; i++)
                    {                    
	//		 scanner = new Scanner (System.in);
	//		 System.out.println("Enter name of the file:");
			 fileRead = Integer.toString(i);//scanner.nextLine();
			 System.out.println("Name of the file is "+ fileRead);
			readInRawVPALData();
			
		} //while (!fileRead.isEmpty());
		//scanner.close();
	 }
	 
	 void readInRawVPALData() {
		 
		 ParticipantNumber ++;
		 Participant p = new Participant();
		 participants.add(p);
		 // open the file for reading
		 try {
			 BufferedReader br = new BufferedReader (new FileReader("data/"+fileRead));
			 Boolean flag = false;
			 String Line = br.readLine();
			 
			 String spliting = Line.subSequence(Line.indexOf("'")+1 ,Line.indexOf(("'"), Line.indexOf("'")+2)).toString();
			 
                      //  String spliting = fileRead.subSequence(0, fileRead.indexOf(".")).toString();
                        
			 participants.get(ParticipantNumber).ID = Integer.parseInt(spliting);
			 String currentPosition = null, newPosition;
			 Line = br.readLine();
			 outputFile = new PrintWriter(fileRead+"_Output.csv", "UTF-8");
			 String lastAction = "";
			 String lastObject="";
			 String action="";
			 while (Line != null)
			 {
				 items = Line.split(",");
				 if (items[0].length() >4) {
					 switch (items [0].substring(0, 4))
					 {
					 case "Posi": 
						 newPosition = items[0].substring(9, items[0].length());
						 if ( (currentPosition == null) || (!currentPosition.equals(newPosition)) ) {
							 currentPosition = newPosition;
							 outputFile.println("ChangedPosition, " + currentPosition);
							 flag = true;
						 }
						 break;
					 case "Ques": 
						 readWriteQuest();
						 break;
					 case "Stat": break;
					 case "Obje":
						 if (items[0].startsWith("ObjectOnActivate")) action = "activate";
						 if (!lastAction.equals(action) || !lastObject.equals(items[1])) {
							 outputFile.println("Activate, " + items[1]);
							 participants.get(ParticipantNumber).AddObjectAction(action,items[1], "", "");
							 lastAction = action;
							 lastObject = items[1];
						 }
						 break;
					 case "Dial":
						 outputFile.println("Dialogue, "+ items[4]+ ", " + items[5]);
						 participants.get(ParticipantNumber).dialogueUtteranceNum++;
						 break;
					 case "Play":
						 if (items[0].startsWith("Player health")) {
							 participants.get(ParticipantNumber).AddObjectAction("takeHit", "", "", "");
							 outputFile.println("takeHit");
						 }
						 else if (items[0].startsWith("Player killed")) {
							 participants.get(ParticipantNumber).AddObjectAction("Kills", items[3], "", "");
							 outputFile.println("Kills, "+ items[3]);
						 }
						 else if (items[0].startsWith("Player looted dead")) {
							 participants.get(ParticipantNumber).AddObjectAction("Looted", items[3], "", "");
							 outputFile.println("Looted Dead, "+ items[3]);
						 }
						 else if (items[0].startsWith("PlayerLooted")) {
							 participants.get(ParticipantNumber).AddObjectAction("Looted", items[1], "", "");
							 if (!lastAction.equals("LootedItem") ) {
								 outputFile.println("Looted Item, "+ items[1]);
								 lastAction = "LootedItem";
							 }
						 }
						 else if (items[0].startsWith("PlayerJumped")) {}
						 else if (items[0].startsWith("Player attacked")) {
							 participants.get(ParticipantNumber).AddObjectAction("Attack", items[3], "", "");
							 outputFile.println("Attack, "+ items[3]);
						 }
						 else if (items[0].startsWith("Player shooting a dead")) {}
						 else if (items[0].startsWith("PlayerDropItem")) {}
						 else if (items[0].startsWith("PlayerEquiped")) {
							 if (!lastAction.equals("Armed") || !lastObject.equals(items[1])) {
								 participants.get(ParticipantNumber).AddObjectAction("Armed", items[1], "", "");
								 outputFile.println("Armed, "+ items[1]);
								 lastAction = "Armed";
								 lastObject = items[1];
							 }
						 }
						 else if (items[0].startsWith("PlayerShot")) {
							 participants.get(ParticipantNumber).AddObjectAction("Shot", items[1], "", "");
							 outputFile.println("Shot, "+ items[1]);
						 }
                                                 else if (items[0].startsWith("Player killed")) {
							 participants.get(ParticipantNumber).AddObjectAction("Player Killed", items[3], "", "");
							 outputFile.println("Player killed,"+ items[3]);
						 }
                                                 else if (items[0].startsWith("PlayerEquipped")) {
							 participants.get(ParticipantNumber).AddObjectAction("PlayerEquipped", items[1], "", "");
							 outputFile.println("PlayerEquipped, "+ items[1]);
						 }
						 break;
					 case "Craf": 
						 break;
					 case "Crea": 
						 break;
					 case "Atta":
						 if (items[4]!=null) {
							 if (items[4].startsWith("Quest")) 
								 participants.get(ParticipantNumber).AddQAction("unknown", "attack_"+ items[3]);
							 else participants.get(ParticipantNumber).AddObjectAction("Attack", items[3], "", items[4]);
						 }
						 outputFile.println("Attack, "+ items[3]+", "+ items[4]);
						 break;
					
					 case "Inte":
						 if (items[0].startsWith("InteractionContain")) {
							 action = "InteractObject";
						     if (!lastAction.equals(action) || !lastObject.equals(items[3])) {
							     outputFile.println(action+ ", " + items[3]+ ", "+ items[1]);
						   	     participants.get(ParticipantNumber).AddObjectAction(action,items[3], items[1], "");
							     lastAction = action;
							     lastObject = items[3];
						     }
						 }
						 else if (items[0].startsWith("InteractionDoor")) {
							 action = "InteractObject";
						     if (!lastAction.equals(action) || !lastObject.equals("Door")) {
							     outputFile.println(action+ ", Door, " + items[1]);
						   	     participants.get(ParticipantNumber).AddObjectAction(action, "Door", items[1], "");
							     lastAction = action;
							     lastObject = "Door";
						     }
						 }
						 else if (items[0].startsWith("InteractionNPC")) {
							 action = "InteractNPC";
						     if (!lastAction.equals(action) || !lastObject.equals(items[3])) {
							     outputFile.println(action+ ", "+ items[3] +", " + items[1]);
						   	     participants.get(ParticipantNumber).AddObjectAction(action, items[3], items[1], "");
							     lastAction = action;
							     lastObject = items[3];
						     }
						 }
						 break;
						 
					 default: outputFile.println(Line);
					 }
				 }
				 flag = false; 
				 Line = br.readLine();
				 if (Line.isEmpty()) Line = br.readLine();
				  
			 }
			 
			 outputFile.close();
			 br.close();
		 }
		 catch (FileNotFoundException fnfe) { System.out.println("file not found");}
	     catch (IOException ioe) { ioe.printStackTrace(); }
	// All data types: 
	   // Quest: put in file, quest, name of quest, action (started, completed), time step
	   // Dialogue: put in file: dialog, number, person, utterance, time step
	   // Need to loop through this Position: put in file: position, last number, time step begin, time step end
	   // ObjectOnActivate: object, time step
	   // InteractionContainer: container, container name, place, time step
	   // InteractionDoor: door, place, time step
	   // Creature: creature name, time step
	   // Attacked: attacked, creature name, quest related, time step
	   // Player Health: Player health change
	   // Player Killed: player killed, by who, time step
	   // Player looted dead: player looted dead, who, time step
	   // InteractionNPC: interactedwithNPC, who
	   // PlayerLootedItem: loot, item name, time step
		 //PrintParticipantData ();
		 
	 }
	 
	 void readWriteQuest () {
		 
		 if (items[4].startsWith(" Started")) {
			 outputFile.print("QuestAction, ");
			 participants.get(ParticipantNumber).numQuestsStarted++;
			 QuestInfo q = new QuestInfo(items[3], timeStored);
			 participants.get(ParticipantNumber).quests.add(q);
			 
			 outputFile.print(items[3]+ ", "+ "Start");
			 outputFile.println("");
		 }
		 else if (items[4].startsWith(" Completed")) {
			 outputFile.print("QuestAction, ");
			 //participants.get(ParticipantNumber).numQuestsCompleted++;
			 outputFile.print(items[3]+ ", "+ "Complete"); 
			 int index = 0;
			 participants.get(ParticipantNumber).addTimeCompleted(items[3], timeStored);
			 outputFile.println("");
		 }
		 else {
			 items[4] = items[4].trim();
			 try {
				 timeStored = Integer.parseInt(items[4]);
			 } 
			 catch (NumberFormatException e) {
				participants.get(ParticipantNumber).AddQAction(items[3], items[4]); 
			 }
			 //participants.get(ParticipantNumber)
			// System.out.println("storing time" + timeStored);
		 }
		
	 }
	 
	 
	 void PrintParticipantData () {
		 participants.get(0).PrintParticipantData();
	 }
	 
	 void PrintParticipantDataToFile () {
	 try {
		 PrintWriter outputFile = new PrintWriter("ParticipantQuestData.csv", "UTF-8");
		 
		 for (Participant p: participants) 
			 p.PrintParticipantDataToFile(outputFile);
		 outputFile.close();
	     }
		  catch (IOException ioe) { ioe.printStackTrace(); }
	  
	  
	 }
	 
	 void readnwriteQuestData () {
		 
	 }
	 
	 
	
	 // main of the program
	 public static void main (String [] args )
	 { 
		 ParsingParticipant p = new ParsingParticipant();
		 p.initializeParticipants();
		 //p.readInRawVPALData();
		 p.readInMultiParticipantData();
		 p.PrintParticipantDataToFile();
	
	 }

}

