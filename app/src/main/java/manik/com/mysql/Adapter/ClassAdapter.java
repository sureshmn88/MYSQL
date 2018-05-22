package manik.com.mysql.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import manik.com.mysql.Pojo.ClassInfo;
import manik.com.mysql.R;

/**
 * Created by manikkam on 19/5/18.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<ClassInfo> mList;
    OnClickListener onClickListener;

    public interface OnClickListener {
        void onLayoutClick(int position);
    }

    public ClassAdapter(Context mContext, ArrayList<ClassInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setList(ArrayList<ClassInfo> mList) {
        this.mList = mList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_class_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.textView.setText(mList.get(position).getClassName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.item_class);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListener!=null)
                        onClickListener.onLayoutClick(getAdapterPosition());
                }
            });


        }
    }
}
