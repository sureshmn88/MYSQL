package manik.com.mysql.Activity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import manik.com.mysql.DB.DBHandler;
import manik.com.mysql.Pojo.ClassInfo;
import manik.com.mysql.R;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etFName,etDOB,etAddress;
    Spinner spnClass;
    Button btnSubmit;

    ArrayList<Integer> mIDList;
    ArrayList<String> mClassList;

    DBHandler mDBHandler;

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validate()) {
                    mDBHandler.insertStudent(etName.getText().toString().trim(),
                            etFName.getText().toString().trim(),
                            etDOB.getText().toString().trim(),
                            etAddress.getText().toString().trim(),
                            mClassList.get(spnClass.getSelectedItemPosition()),
                            mDBHandler.getStatus(mClassList.get(spnClass.getSelectedItemPosition())));

                    Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void initViews() {

        mDBHandler=new DBHandler(this);
        etName=findViewById(R.id.reg_name);
        etFName=findViewById(R.id.reg_fname);
        etDOB=findViewById(R.id.reg_dob);
        etAddress=findViewById(R.id.reg_address);
        spnClass=findViewById(R.id.reg_class);
        btnSubmit=findViewById(R.id.reg_submit);

        Cursor cursor = mDBHandler.getClassDetails();

        mIDList=new ArrayList<>();
        mClassList=new ArrayList<>();

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {

                    mIDList.add(cursor.getInt(0));
                    mClassList.add(cursor.getString(1));

                } while (cursor.moveToNext());
            }

        }

        ArrayAdapter<String> mAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mClassList);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnClass.setAdapter(mAdapter);

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

    }

    boolean validate() {

        boolean failFlag=false;

        if (etName.getText().toString().trim().isEmpty()) {
            etName.setError("Enter Name");
            failFlag=true;
        }

        if (etFName.getText().toString().trim().isEmpty()) {
            etFName.setError("Enter Father Name");
            failFlag=true;
        }

        if (etDOB.getText().toString().trim().isEmpty()) {
            etDOB.setError("Enter Date of Birth");
            failFlag=true;
        }

        if (etAddress.getText().toString().trim().isEmpty()) {
            etAddress.setError("Enter Address");
            failFlag=true;
        }

        return failFlag;

    }

    void showDateDialog() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        etDOB.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
}
