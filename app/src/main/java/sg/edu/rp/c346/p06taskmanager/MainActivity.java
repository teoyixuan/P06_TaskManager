package sg.edu.rp.c346.p06taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    Button btnAddTask;
    ArrayAdapter aa;
    ArrayList<Task> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = findViewById(R.id.btnAddTask);
        lv = findViewById(R.id.lv);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivityForResult(i, 9);
            }
        });

        DBHelper db = new DBHelper(MainActivity.this);
        al = db.getAllData();
        db.close();
        aa = new arrayAdapter(MainActivity.this, R.layout.row, al);
        lv.setAdapter(aa);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper db = new DBHelper(MainActivity.this);
            al.clear();
            al.addAll(db.getAllData());
            db.close();
            aa = new arrayAdapter(MainActivity.this, R.layout.row, al);
            lv.setAdapter(aa);
        }
    }
}
