package com.example.androidzonghe1.adapter.lsbWork;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactorAdapter extends RecyclerView.Adapter<ContactorAdapter.ViewHolder> implements View.OnClickListener {

    List<String> data;
    RecyclerView recyclerView;
    OnItemClickListener onItemClickListener;
    Context context;

    public ContactorAdapter(Context context){
        this.context = context;
        data = new ArrayList<String>();
        data.add("one");
        data.add("two");
        data.add("three");
    }

    public ContactorAdapter(List<String> data, Context context){
        this.context = context;
        this.data = data;
    }

    public void insertData(){
        this.data.add(new String(""));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactor_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.etPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorAdapter", "btnDelete onClick position:" + position );
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.btnRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorAdapter", "etRelation onClick position:" + position);
                onItemClickListener.onItemClick(recyclerView, v, position, data.get(position));
            }
        });
        holder.etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("ContactorAdapter", "etPhone changed");
            }
        });
        holder.imgEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorAdapter", "imgEye onClick");
                if (holder.imgEye.getTag() == null || holder.imgEye.getTag().equals("show")){
                    holder.imgEye.setTag("hide");
                    holder.etPhone.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    holder.imgEye.setImageResource(R.drawable.eye_hide);
                } else {
                    holder.imgEye.setTag("show");
                    holder.etPhone.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    holder.imgEye.setImageResource(R.drawable.eye_show);
                }
            }
        });
        holder.btnSendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorAdapter", "btnSendInvite onClick");
                Pattern p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$");
                Matcher matcher = p.matcher(holder.etPhone.getText().toString().trim());
                if (matcher.matches()){

                } else {

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
        void onItemClick(RecyclerView parent, View view, int position, String data);
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
        ImageView imgPhoto;
        Button btnRelation;
        EditText etPhone;
        ImageView imgEye;
        TextView tvBind;
        Button btnSendInvite;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            btnRelation = itemView.findViewById(R.id.btn_relation);
            etPhone = itemView.findViewById(R.id.et_phone);
            imgEye = itemView.findViewById(R.id.img_eye);
            tvBind = itemView.findViewById(R.id.tv_bind);
            btnSendInvite = itemView.findViewById(R.id.btn_send_invite);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public void updateRelation(int position, String relation){

    }
}
