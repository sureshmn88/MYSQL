package manik.com.mysql.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import manik.com.mysql.Pojo.ClassInfo;
import manik.com.mysql.Pojo.Student;
import manik.com.mysql.R;

/**
 * Created by manikkam on 19/5/18.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Student> mList;
    OnClickListener onClickListener;

    public interface OnClickListener {
        void onLayoutClick(int position);
    }

    public StudentAdapter(Context mContext, ArrayList<Student> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setList(ArrayList<Student> mList) {
        this.mList = mList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_student_details, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Student item=mList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvFatherName.setText("Father Name : " + item.getFatherName());
        holder.tvDOB.setText("Status : " +item.getClassStatus());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvFatherName,tvDOB;
        ImageButton ibRemove;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.item_name);
            tvFatherName=itemView.findViewById(R.id.item_fname);
            tvDOB=itemView.findViewById(R.id.item_dob);
            ibRemove=itemView.findViewById(R.id.item_remove);

            ibRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListener!=null)
                        onClickListener.onLayoutClick(getAdapterPosition());
                }
            });


        }
    }
}

