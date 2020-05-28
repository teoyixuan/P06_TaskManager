package sg.edu.rp.c346.p06taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText etName, etDescription, etRemind;
    Button btnAdd, btnCancel;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etRemind = findViewById(R.id.etRemind);
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().length() == 0 || etDescription.getText().length() == 0 || etRemind.getText().length() == 0){
                    Toast.makeText(AddActivity.this, "Information not complete",
                            Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper dbh = new DBHelper(AddActivity.this);
                    long inserted_id = dbh.insertData(etName.getText().toString(), etDescription.getText().toString());
                    dbh.close();

                    int second = Integer.parseInt(etRemind.getText().toString());

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, second);

                    Intent intent = new Intent(AddActivity.this,ScheduledNotificationReceiver.class);
                    intent.putExtra("name", etName.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
