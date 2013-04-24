package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.content.Intent;

public class TodoListManagerActivity extends Activity {

    private TodoListAdapter adapter;
    private ArrayList<ToDoTask> todoList;
    private ListView todoListView;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        todoList = new ArrayList<ToDoTask>();
        todoListView = (ListView)findViewById(R.id.lstTodoItems);
        adapter = new TodoListAdapter(this, todoList);
        todoListView.setAdapter(adapter);
        registerForContextMenu(todoListView);
    }
    
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.ctxmenu, menu);		
		
		AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		ToDoTask task = (ToDoTask) todoListView.getItemAtPosition(info.position);
		menu.setHeaderTitle(task._task);
		if (!task._task.startsWith("Call "))
			{
		 	menu.removeItem(R.id.menuItemCall);
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int selectedItemIndex = info.position;
		switch (item.getItemId()){
		case R.id.menuItemDelete:
			adapter.remove(adapter.getItem(selectedItemIndex));
			break;
		case R.id.menuItemCall:
			ToDoTask task = adapter.getItem(selectedItemIndex);
			Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+task._task.substring(5))); 
			startActivity(dial); 
		}
		return true;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	    	case R.id.menuItemAdd:
    		Intent intent = new Intent(this, AddNewTodoItemActivity.class); 
    		startActivityForResult(intent, 42); 
    		break;
    	}
	    return true;
    }
    	
    protected void onActivityResult(int reqCode, int resCode, Intent data) { 
    	if (resCode == RESULT_CANCELED)
    	{
    		return;
    	}
    	switch (reqCode) { 
    	case 42:
    		Date date = null;
    		if (data.getExtras().containsKey("dueDate") )
    		{
        		date = (Date) data.getSerializableExtra("dueDate");    			
    		}
    		String task = data.getStringExtra("task");
     		adapter.add(new ToDoTask(task, date));
     	break;
    	}
    }

}
	