package co.com.cesardiaz.misiontic.mytask.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.com.cesardiaz.misiontic.mytask.LoginActivity;
import co.com.cesardiaz.misiontic.mytask.model.MainInteractor;
import co.com.cesardiaz.misiontic.mytask.mvp.MainMVP;
import co.com.cesardiaz.misiontic.mytask.view.MainActivity;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskItem;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskState;

public class MainPresenter implements MainMVP.Presenter {
    
    private final MainMVP.View view;
    private final MainMVP.Model model;
    
    public MainPresenter(MainMVP.View view){
        this.view = view;
        this.model = new MainInteractor();
    }
    
    @Override
    public void loadTasks() {
        List<TaskItem> items = model.getTasks();

        view.showTaskList(items);
    }

    @Override
    public void addNewTask() {
        String description = view.getTaskDescription();
        String date = SimpleDateFormat.getDateInstance().format(new Date());
        if(TextUtils.isEmpty(description)){
            view.showConfirmDialog("Field Required");
        }else{
                TaskItem task = new TaskItem(description,date);
                model.saveTask(task);
                view.addTaskToList(task);
            }
        }



    @Override
    public void taskItemClicked(TaskItem task) {
        String message = task.getState() == TaskState.PENDING
                ? "Would you like to mark as DONE this task?"
                : "Would you like to mark as PENDING this task?";
        view.showConfirmDialog(message,task);

    }

    @Override
    public void updateTask(TaskItem task) {
        task.setState(task.getState() == TaskState.PENDING ? TaskState.DONE : TaskState.PENDING);

        model.updateTask(task);
        view.updateTask(task);

    }

    @Override
    public void deleteTask(TaskItem task) {
        model.deleteTask(task);
        view.deleteTask(task);

    }
}
