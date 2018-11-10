import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.Action;

class Participant {
	 int ID;
	 int numPositions;
	 int numQuestsCompleted=0;
	 int numQuestsStarted=0;
	 int numKills=0;
	 ArrayList<QuestInfo> quests;
	 ArrayList<QActions> QActionList;
	 ArrayList<ObjectAction> objectActionList;
	 int dialogueUtteranceNum = 0;
	 int takeHits =0;
	 int kills = 0;
	 int loots = 0;
	 int numShots = 0;
	 int selfDefense =0;
	 int unmotivatedAttacks = 0;
	 int friendlyNPCAttacks=0;
         int Playerkilled=0;
	 int PlayerEquipped=0;
	 Participant ()
	 {
		 ID = -1;
		 quests = new ArrayList<QuestInfo>();
		 QActionList = new ArrayList<QActions>();
		 objectActionList = new ArrayList<ObjectAction>();
		
	 }
	 
	 void addTimeCompleted (String name, int time) {
		 for (QuestInfo q: quests) {
			 if (q.name.equals(name)) q.timeCompleted = time;
		 }
			 
	 }
	 
	 void getNumCompletedQuests () 
	 {
		 for (QuestInfo q: quests) {
			 if (q.timeCompleted != -1) numQuestsCompleted++;
		 }
	 }
	 
	 void AddQAction (String quest, String action)
	 {
		 QActions qa = new QActions (quest, action);
		 QActionList.add(qa);
	 }
	 
	 void AddObjectAction (String actionname, String objectname, String place, String motivation)
	 {
		 ObjectAction action = new ObjectAction(actionname, objectname, place, motivation);
		 objectActionList.add(action);
		 if (actionname.equals("Kills")) kills++;
		 if (actionname.equals("takeHit")) takeHits++;
		 if (actionname.equals("Looted")) loots++;
		 if (actionname.equals("Shot")) numShots++;
		 if (motivation.startsWith("SelfDefense")) selfDefense++;
		 if (motivation.startsWith("UnMotivated")) unmotivatedAttacks ++;
		 if (motivation.startsWith("Friendly NPC")) friendlyNPCAttacks ++;
                 if (actionname.startsWith("Player killed")) Playerkilled ++;
                 if (actionname.startsWith("PlayerEquipped")) PlayerEquipped ++;
                 
 	 }
	 
	 void PrintParticipantData () {
		 getNumCompletedQuests();
		 System.out.println(" DialogueNumber, "+ dialogueUtteranceNum);
		 System.out.println(" ID , Quest Started, Quest Completed ");
		 System.out.println(ID + ", " + 
				 numQuestsStarted + "," +
				 numQuestsCompleted);
		 
		 System.out.println("Number of Quests Started:" + quests.size());
		 
		 for (QuestInfo quest: quests) {
			 if (quest != null) 
				 System.out.println("Quests: "+ quest.name+ "," +
						 quest.TimeElapsed());
		 }
		 
		 System.out.println("Number of Actions Related to Quests:" + QActionList.size());
		 
		 for (QActions actions: QActionList) {
			 System.out.println("Action: " + actions.action + " Quest "+ actions.quest);
		 }
		 
		 System.out.println("Number of Object Interactions:" + objectActionList.size());
		 for (ObjectAction actions: objectActionList) {
			 System.out.println("Action: " + actions.actionName + " Quest "+ actions.objectName);
		 }
	 }
	 
	 void PrintParticipantDataToFile (PrintWriter outputFile) {
		 
		 getNumCompletedQuests();
//		 outputFile.println(" ID , Quest Started, Quest Completed, DialogueNumber, TakeHits, Killed, Loots, Shots, SelfDefenseAttacks, "
//		 		+ "UnMotivatedAttacks, FriendlyNPCAttacks");
		 outputFile.println(ID + ", " + 
				 quests.size() + "," +
				 numQuestsCompleted + "," +
				 dialogueUtteranceNum + "," +
				 takeHits + "," +
				 kills + "," +
				 loots + "," +
				 numShots + "," +
				 selfDefense + "," +
				 unmotivatedAttacks + "," +
				 friendlyNPCAttacks + "," +
                                 Playerkilled + "," +
                                 PlayerEquipped
                         );
		 
//		 outputFile.println("Quest , Time To Complete");
//			
//		 for (QuestInfo quest: quests) {
//			 if (quest != null) 
//				 outputFile.println(quest.name+ "," +
//						 quest.TimeElapsed());
//		 }
//		 
//		 outputFile.println("Number of Actions Related to Quests:" + QActionList.size());
//		 QActionList = TrimListQActions ();
//		 objectActionList = TrimListActions ();
//		 outputFile.println("Action , Quest, NumberOfTimes");
//		 
//		 for (QActions actions: QActionList) {
//			 if (actions.numbTimes == -1 ) actions.numbTimes = 1;
//			 outputFile.println(actions.action + " , "+ actions.quest + ", " + actions.numbTimes);
//		 }
//		 outputFile.println("Number of Object Interactions:" + objectActionList.size());
//		 outputFile.println("Action , Object, Times, Place, Motivation");
//		 for (ObjectAction actions: objectActionList) {
//			 outputFile.println(actions.actionName +", " + actions.objectName+ ", " + actions.numbTimes+ ", "+ actions.place +", "+ actions.attack_motivation);
//		 }
	 }
	 
	 ArrayList<QActions> TrimListQActions () {
		 ArrayList<QActions> trimmedActions = new ArrayList<QActions> ();
		 boolean found;
		 for (QActions actions: QActionList) {
			 found = false;
			 for (QActions qa: trimmedActions) {
				 if (actions.quest.equals(qa.quest) && actions.action.equals(qa.action)) {
					 found = true;
					 qa.numbTimes++;
			     }
			 }
		     if (!found) trimmedActions.add(actions);
		 }
		return trimmedActions;
	 }
	 
	 ArrayList<ObjectAction> TrimListActions () {
		 ArrayList<ObjectAction> trimmedActions = new ArrayList<ObjectAction> ();
		 boolean found;
		 for (ObjectAction actions: objectActionList) {
			 found = false;
			 for (ObjectAction qa: trimmedActions) {
				 if (actions.actionName.equals(qa.actionName) && actions.objectName.equals(qa.objectName) && 
						 actions.place.equals(qa.place) && actions.attack_motivation.equals(qa.attack_motivation)) {
					 found = true;
					 qa.numbTimes++;
			     }
			 }
		     if (!found) trimmedActions.add(actions);
		 }
		return trimmedActions;
	 }
	 
}
