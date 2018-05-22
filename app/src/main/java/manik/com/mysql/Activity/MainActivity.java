package manik.com.mysql.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import manik.com.mysql.DB.DBHandler;
import manik.com.mysql.R;

public class MainActivity extends AppCompatActivity {


    DBHandler mDBHandler;

    TextView tvReg,tvClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHandler=new DBHandler(this);
        if (mDBHandler.getClassDetail() == 0) {
            mDBHandler.insertClass("I");
            mDBHandler.insertClass("II");
        }

        tvReg=findViewById(R.id.dash_register);
        tvClass=findViewById(R.id.dash_class);

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        tvClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ClassDetailActivity.class));
            }
        });

    }
}
