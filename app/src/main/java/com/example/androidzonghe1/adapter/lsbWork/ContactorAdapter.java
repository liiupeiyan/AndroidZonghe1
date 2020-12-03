package com.example.androidzonghe1.adapter.lsbWork;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.xtWork.Contactor;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactorAdapter extends RecyclerView.Adapter<ContactorAdapter.ViewHolder> implements View.OnClickListener {

    private List<Contactor> data;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public ContactorAdapter(List<Contactor> data, Context context){
        this.context = context;
        this.data = data;
    }
    //添加
    public void insertData(){
        this.data.add(new Contactor());
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
        //给控件赋值
        holder.btnRelation.setText(data.get(position).getRelat());
        holder.etPhone.setText(data.get(position).getPhone());
        //点击删除按钮
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ContactorAdapter", "btnDelete onClick position:" + position );
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        JSONObject jsonObject=new JSONObject();
                        try {
                            Log.e("id",data.get(position).getId()+"");
                            jsonObject.put("contactor_id",data.get(position).getId());
                            URL url=new URL("http://192.168.10.1:8080/Dingdongg/DeleteContactorServlet");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            //设置http请求方式，get、post、put、...(默认get请求)
                            connection.setRequestMethod("POST");//设置请求方式
                            OutputStream outputStream=connection.getOutputStream();
                            outputStream.write(jsonObject.toString().getBytes());
                            InputStream inputStream=connection.getInputStream();
                            byte[] bytes = new byte[256];
                            int len=-1;
                            StringBuffer buffer=new StringBuffer();
                            while((len=inputStream.read(bytes))!=-1) {
                                buffer.append(new String(bytes,0,len));
                            }
                            String arr=buffer.toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
                try {
                    thread.join();
                    data.remove(position);
                    notifyDataSetChanged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


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
        //点击修改按钮
        holder.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String relation=holder.btnRelation.getText()+"";
                        String phone=holder.etPhone.getText()+"";
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("relat",relation);
                            jsonObject.put("contactor_phone",phone);
                            jsonObject.put("id",data.get(position).getId());
                            URL url=new URL("http://192.168.10.1:8080/Dingdongg/UpdateContactorServlet");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            //设置http请求方式，get、post、put、...(默认get请求)
                            connection.setRequestMethod("POST");//设置请求方式
                            OutputStream outputStream=connection.getOutputStream();
                            outputStream.write(jsonObject.toString().getBytes());
                            InputStream inputStream=connection.getInputStream();
                            byte[] bytes = new byte[256];
                            int len=-1;
                            StringBuffer buffer=new StringBuffer();
                            while((len=inputStream.read(bytes))!=-1) {
                                buffer.append(new String(bytes,0,len));
                            }
                            String arr=buffer.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                };
                thread.start();
                try {
                    thread.join();
                    notifyDataSetChanged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //点击保存按钮,可以添加数据
        holder.hold.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String relation=holder.btnRelation.getText()+"";
                        String phone=holder.etPhone.getText()+"";
                        Log.e("phone",phone);
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("relat",relation);
                            jsonObject.put("contactor_phone",phone);
                            URL url=new URL("http://192.168.10.1:8080/Dingdongg/AddContactorServlet");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            //设置http请求方式，get、post、put、...(默认get请求)
                            connection.setRequestMethod("POST");//设置请求方式
                            OutputStream outputStream=connection.getOutputStream();
                            outputStream.write(jsonObject.toString().getBytes());
                            InputStream inputStream=connection.getInputStream();
                            byte[] bytes = new byte[256];
                            int len=-1;
                            StringBuffer buffer=new StringBuffer();
                            while((len=inputStream.read(bytes))!=-1) {
                                buffer.append(new String(bytes,0,len));
                            }
                            String arr=buffer.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                };
                thread.start();
                try {
                    thread.join();
                    notifyDataSetChanged();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        void onItemClick(RecyclerView parent, View view, int position, Contactor data);
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
        private ImageView imgPhoto;
        private Button btnRelation;
        private EditText etPhone;
        private ImageView imgEye;
        private Button hold;
        private Button change;
        private Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //获取控件引用
            imgPhoto = itemView.findViewById(R.id.img_photo);
            change=itemView.findViewById(R.id.change);
            btnRelation = itemView.findViewById(R.id.btn_relation);
            etPhone = itemView.findViewById(R.id.et_phone);
            imgEye = itemView.findViewById(R.id.img_eye);
            hold=itemView.findViewById(R.id.hold);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    public void updateRelation(int position, String relation){

    }
}
