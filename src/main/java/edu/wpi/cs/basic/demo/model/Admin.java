package edu.wpi.cs.basic.demo.model;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.wpi.cs.basic.demo.CreateChoiceHandler;

public class Admin  { //Eren


	String createReport(Collection<Choice> choices) {
		Iterator<Choice> iterator = choices.iterator();
		String reportOutput = "";
		while (iterator.hasNext()) {
			Choice temp = iterator.next();
			reportOutput += "Choice ID: " + temp.uniqueID + "\n";
			reportOutput += "\t";
			if (temp.isComplete()) {
				reportOutput += "Uncomplete";
			} else {
				reportOutput += "Complete";
				reportOutput += "\n\tDay Of Completion: " + temp.dateOfCompletion.toString() + "\n";
			}
		}
		return reportOutput;
	}
	

}
