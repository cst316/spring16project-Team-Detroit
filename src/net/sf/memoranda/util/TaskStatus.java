/* 
  File:		TaskStatus.java
  Author:	Ryan Schultz	
  Date:		2/26/2016

  Description: Task object to be observed
*/

package net.sf.memoranda.util;

import java.util.Observable;

/**
Class:	TaskStatus

Description:  Task object to be observed
*/
public class TaskStatus extends Observable {
	private String task;

	public TaskStatus(String task) {
		this.task = task;
	}
	
	public void setTask(String t) {
		task = t;
	}
	
	public String getTask() {
		return task;
	}
	
	/**
	  Method:	complete
	  @param:	task completed
	  @return: 	N/A

	  Description: notifies observer of task completion
	*/
	public void updatePercentage(int percent) {
		if(percent == 100) {
			setTask(task);
			setChanged();
			notifyObservers();
		}		
	}
}