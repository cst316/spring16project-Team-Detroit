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
	private static String task;

	public TaskStatus(String task) {
		TaskStatus.task = task;
	}
	
	public static void setTask(String task) {
		TaskStatus.task = task;
	}
	
	public static String getTask() {
		return TaskStatus.task;
	}
	
	/**
	  Method:	complete
	  @param:	task completed
	  @return: 	N/A

	  Description: notifies observer of task completion
	*/
	public void complete(String task) {
		if(!task.equals(TaskStatus.task)) {
			setTask(task);
			setChanged();
			notifyObservers();
		}		
	}
}