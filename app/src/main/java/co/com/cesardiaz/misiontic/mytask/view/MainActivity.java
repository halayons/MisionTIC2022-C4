package co.com.cesardiaz.misiontic.mytask.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import co.com.cesardiaz.misiontic.mytask.R;
import co.com.cesardiaz.misiontic.mytask.mvp.MainMVP;
import co.com.cesardiaz.misiontic.mytask.presenter.MainPresenter;
import co.com.cesardiaz.misiontic.mytask.view.adapter.TaskAdapter;
import co.com.cesardiaz.misiontic.mytask.view.dto.TaskItem;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    private TextInputLayout tilNewTask;
    private TextInputEditText etNewTask;
    private RecyclerView rvTasks;

    private TaskAdapter taskAdapter;
    
    private MainMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(MainActivity.this);

        initUI();
        presenter.loadTasks();
    }

    private void initUI() {
        tilNewTask = findViewById(R.id.til_new_task);
        tilNewTask.setEndIconOnClickListener(v -> presenter.addNewTask());

        etNewTask = findViewById(R.id.et_new_task);

        taskAdapter = new TaskAdapter();
        taskAdapter.setListener(item -> presenter.taskItemClicked(item));
        rvTasks = findViewById(R.id.rv_tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvTasks.setAdapter(taskAdapter);
    }

    @Override
    public void showTaskList(List<TaskItem> items) {
        taskAdapter.setData(items);
    }

    @Override
    public String getTaskDescription() {
        return etNewTask.getText().toString();
    }

    @Override
    public void addTaskToList(TaskItem task) {

        taskAdapter.addItem(task);
    }

    @Override
    public void updateTask(TaskItem task) {
        taskAdapter.updateTask(task);
    }



    @Override
    public void showConfirmDialog(String message, TaskItem task) {
         CharSequence[] choices = {"Task Done", "Delete Task"};
         new AlertDialog.Builder(this)
                 .setIcon(android.R.drawable.ic_dialog_alert)
                 .setTitle("Task selected")
                 //.setMessage(message)
                 .setSingleChoiceItems(choices, 0,new DialogInterface.OnClickListener() {

                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         ListView lv = ((AlertDialog)dialogInterface).getListView();
                         lv.setTag(new Integer(i));

                     }
                 })
                 .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                         ListView lv = ((AlertDialog)dialog).getListView();
                         Integer selected= (Integer)lv.getTag();
                         if (selected != null) {
                             presenter.deleteTask(task);

                         } else if (selected == null) {

                             presenter.updateTask(task);
                         }
                     }
                 })

                 .setNegativeButton("Cancelar",null)
                 .show();

    }
    @Override
    public void showConfirmDialog(String message) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Task selected")
                .setMessage(message)
                .setNegativeButton("Aceptar",null)
                .show();
    }

    @Override
    public void deleteTask(TaskItem task) {
        taskAdapter.removeTask(task);
    }
}





