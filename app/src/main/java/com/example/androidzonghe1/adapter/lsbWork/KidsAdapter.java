package com.example.androidzonghe1.adapter.lsbWork;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.xtWork.Child;

import java.util.ArrayList;
import java.util.List;

public class KidsAdapter extends RecyclerView.Adapter<KidsAdapter.ViewHolder> implements View.OnClickListener{
    int resLayout;
    List<Child> data;
    RecyclerView recyclerView;
    OnItemClickListener onItemClickListener;
    Context context;
    public KidsAdapter(Context context,List<Child> childs){
        this.context = context;
        data = childs;
    }
//    public KidsAdapter(List<Child> data, Context context){
//        this.context = context;
//        this.data = data;
//    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kid_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //给控件赋值
        holder.etName.setText(data.get(position).getName());
        if(data.get(position).getSex().equals("男")){
            holder.rbBoy.setChecked(true);
        }else {
            holder.rbGirl.setChecked(true);
        }
        holder.etClasses.setText(data.get(position).getBanji());
        holder.btnSchool.setText(data.get(position).getSchool());
        //监听事件
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KidsAdapter", "btnDelete onClick position:" + position);
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btnSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(recyclerView, v, position, data.get(position));
            }
        });
        holder.rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_boy:
                        holder.rbBoy.setChecked(true);
                        holder.rbGirl.setChecked(false);
                        break;
                    case R.id.rb_girl:
                        holder.rbGirl.setChecked(true);
                        holder.rbBoy.setChecked(false);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        if (onItemClickListener != null){
            onItemClickListener.onItemClick(recyclerView, v, position, data.get(position));
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(RecyclerView parent, View view, int position, Child data);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHeadPhoto;
        EditText etName;
        RadioGroup rgSex;
        RadioButton rbBoy;
        RadioButton rbGirl;
        EditText etClasses;
        Button btnSchool;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHeadPhoto = itemView.findViewById(R.id.img_head_photo);
            etName = itemView.findViewById(R.id.et_name);
            rgSex = itemView.findViewById(R.id.rg_sex);
            rbBoy = itemView.findViewById(R.id.rb_boy);
            rbGirl = itemView.findViewById(R.id.rb_girl);
            etClasses = itemView.findViewById(R.id.et_classes);
            btnSchool = itemView.findViewById(R.id.btn_school);
            btnDelete = itemView.findViewById(R.id.btn_delete);

        }
    }
}
