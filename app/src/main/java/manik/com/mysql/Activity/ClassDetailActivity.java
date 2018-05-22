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

import java.util.ArrayList;

import manik.com.mysql.Adapter.ClassAdapter;
import manik.com.mysql.DB.DBHandler;
import manik.com.mysql.Pojo.ClassInfo;
import manik.com.mysql.R;

public class ClassDetailActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ClassAdapter mClassAdapter;
    ArrayList<ClassInfo> mUserList;

    DBHandler mDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        getSupportActionBar().setTitle("Class Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDBHandler=new DBHandler(this);
        mRecyclerView = findViewById(R.id.class_recyclerview);

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

        mUserList = new ArrayList<>();
        Cursor cursor = mDBHandler.getClassDetails();

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {

                    ClassInfo item = new ClassInfo();
                    item.setId(cursor.getInt(0));
                    item.setClassName(cursor.getString(1));
                    mUserList.add(item);

                } while (cursor.moveToNext());
            }

        }

        if (mUserList.size() > 0) {

            mRecyclerView.setVisibility(View.VISIBLE);

            mClassAdapter = new ClassAdapter(this, mUserList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mClassAdapter);
            mClassAdapter.notifyDataSetChanged();

            mClassAdapter.setOnClickListener(new ClassAdapter.OnClickListener() {
                @Override
                public void onLayoutClick(int position) {
                    Intent intent=new Intent(ClassDetailActivity.this,StudentInfoActivity.class);
                    intent.putExtra("classId",mUserList.get(position).getClassName());
                    startActivity(intent);
                }
            });
        }

    }
}
