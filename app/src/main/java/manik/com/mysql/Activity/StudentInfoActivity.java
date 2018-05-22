package manik.com.mysql.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import manik.com.mysql.Adapter.ClassAdapter;
import manik.com.mysql.Adapter.StudentAdapter;
import manik.com.mysql.DB.DBHandler;
import manik.com.mysql.Pojo.ClassInfo;
import manik.com.mysql.Pojo.Student;
import manik.com.mysql.R;

public class StudentInfoActivity extends AppCompatActivity {

    String classId="";

    RecyclerView mRecyclerView;
    StudentAdapter mAdapter;
    ArrayList<Student> mList;

    DBHandler mDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

        getSupportActionBar().setTitle("Student Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        if (intent != null) {
            if(intent.hasExtra("classId"))
                classId=intent.getStringExtra("classId");
        }

        mDBHandler=new DBHandler(this);
        mRecyclerView=findViewById(R.id.stu_recylerview);
        getDetails();
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

    void getDetails() {

        getUpdateList();

        if (mList.size() > 0) {

            mRecyclerView.setVisibility(View.VISIBLE);

            mAdapter = new StudentAdapter(this, mList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            mAdapter.setOnClickListener(new StudentAdapter.OnClickListener() {
                @Override
                public void onLayoutClick(int position) {
                    removeStudent(position);
                }
            });
        }

    }

    void getUpdateList() {

        mList = new ArrayList<>();
        Cursor cursor = mDBHandler.getStudentDetails(classId);

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {

                    Student item = new Student();
                    item.setId(cursor.getInt(0));
                    item.setName(cursor.getString(1));
                    item.setFatherName(cursor.getString(2));
                    item.setDob(cursor.getString(3));
                    item.setAddress(cursor.getString(4));
                    item.setClassName(cursor.getString(5));
                    item.setClassStatus(cursor.getString(6));
                    mList.add(item);

                } while (cursor.moveToNext());
            }

        }

    }

    void removeStudent(int position) {

        Student item=mList.get(position);
        boolean status=false;
        int updateId=-1;

        if (item.getClassStatus().equals("Active")) {
            status=true;
        }

        mDBHandler.removeStudent(item.getId());

        if (status) {

            Cursor cursor = mDBHandler.getUpdateStudentDetails(classId);

            if (cursor != null) {
                if (cursor.getCount()>0 && cursor.moveToFirst()) {
                    updateId=cursor.getInt(0);
                }
            }
            if(updateId>-1)
                mDBHandler.updateStudent(updateId);
        }

        Toast.makeText(this,"Student Removed",Toast.LENGTH_SHORT).show();

        if (mAdapter != null) {
            getUpdateList();
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        }

    }
}
