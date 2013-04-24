package il.ac.huji.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoTask {
	public String _task;
	public String _date;

	public ToDoTask(String task, Date date) {
		this._task = task;
		this._date = "No due date";
		if (date != null)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dateString = sdf.format(date);
			this._date = dateString;			
		}
	}
}
