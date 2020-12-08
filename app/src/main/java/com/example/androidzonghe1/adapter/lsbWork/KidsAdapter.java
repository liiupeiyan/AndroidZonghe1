package com.example.androidzonghe1.adapter.lsbWork;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.androidzonghe1.ConfigUtil;
import com.example.androidzonghe1.R;
import com.example.androidzonghe1.entity.xtWork.Child;
import com.example.androidzonghe1.entity.xtWork.Contactor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KidsAdapter extends RecyclerView.Adapter<KidsAdapter.ViewHolder> implements View.OnClickListener{
    private int resLayout;
    private List<Child> data=new ArrayList<>();
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private Context context;
    private String sex;
    private String str;
    public KidsAdapter(Context context,List<Child> childs){
        this.context = context;
        data = childs;
    }
//    public KidsAdapter(Context context){
//        this.context = context;
//    }

    //添加
//    public void insertData(){
//        Child child = new Child();
//        this.data.add(child);
//        notifyDataSetChanged();
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
        sex = data.get(position).getSex();
        Log.e("hzzzzzzzz",data.toString());
        holder.etName.setText(data.get(position).getName());
        if(data.get(position).getSex()!=null){
            if(data.get(position).getSex().trim().equals("男")){
                holder.rbBoy.setChecked(true);
            }else {
                holder.rbGirl.setChecked(true);
            }

        }
        holder.etClasses.setText(data.get(position).getBanji());
        holder.btnSchool.setText(data.get(position).getSchool());
        //监听事件
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("KidsAdapter", "btnDelete onClick position:" + position);
                //网络流删除数据
                int id = data.get(position).getId();
                deleteChildById(ConfigUtil.Url+"deleteChildServlet?cId="+id);
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
                        sex = "男";
                        break;
                    case R.id.rb_girl:
                        holder.rbGirl.setChecked(true);
                        holder.rbBoy.setChecked(false);
                        sex="女";
                        break;
                }
            }
        });
        //修改孩子
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            URL url = new URL(ConfigUtil.xt+"UpdateMyChildServlet");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            OutputStream os = connection.getOutputStream();
                            JSONObject object = new JSONObject();
                            object.put("id",data.get(position).getId());
                            object.put("name",holder.etName.getText()+"");
                            object.put("banji",holder.etClasses.getText()+"");
                            object.put("sex",sex);
                            object.put("school",holder.btnSchool.getText()+"");
                            os.write(object.toString().getBytes());
                            InputStream is = connection.getInputStream();
                            int len = 0;
                            byte[] b = new byte[512];
                            if((len = is.read(b))!=-1){
                                str = new String(b,0,len);
                            }
                            Log.e("修改孩子结果",str);
                            os.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
            }
        });
        //添加孩子
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            URL url = new URL(ConfigUtil.xt+"AddMyChildServlet");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            OutputStream os = connection.getOutputStream();
                            JSONObject object = new JSONObject();
                            object.put("name",holder.etName.getText()+"");
                            object.put("banji",holder.etClasses.getText()+"");
                            object.put("sex",sex);
                            object.put("school","河北师范");
                            os.write(object.toString().getBytes());
                            InputStream is = connection.getInputStream();
                            int len = 0;
                            byte[] b = new byte[512];
                            if((len = is.read(b))!=-1){
                                str = new String(b,0,len);
                            }
                            Log.e("修改孩子结果",str);
                            os.close();
                            is.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
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
        private ImageView imgHeadPhoto;
        private EditText etName;
        private RadioGroup rgSex;
        private RadioButton rbBoy;
        private RadioButton rbGirl;
        private EditText etClasses;
        private Button btnSchool;
        private Button btnDelete;
        private Button btnAdd;
        private Button btnUpdate;
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
            btnAdd = itemView.findViewById(R.id.btn_add_child);
            btnUpdate = itemView.findViewById(R.id.btn_update_child);
        }
    }

    //删除一条数据
    public void deleteChildById(final String s){
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(s);
                    url.openStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
