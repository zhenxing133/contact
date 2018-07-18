package com.itwork.contact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by yuan.zhen.xing on 2018-07-17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder>
                            implements StickyHeaderAdapter<ContactAdapter.HeadViewHolder>{
    private LayoutInflater inflater ;
    private Context mContext ;
    private List<ContactsBean> names;
    // \u0000 为字节的null
    private char currentC = '\u0000';
    private int index = 0;
    public ContactAdapter(Context context, List<ContactsBean> datas){
        this.mContext = context ;
        inflater = LayoutInflater.from(mContext);
        this.names = datas ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_contact.setText(names.get(position).name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    @Override
    public long getHeaderId(int position) {
        //这里处理是否显示悬浮标题，主要判断当前position与之前position是否一致
        //如果return值为1，那么悬浮标题永远为1，悬浮标题数量为当前return值动态返回
        char c = names.get(position).pinyin.charAt(0);
        //判断是否为空
        if(c == '\u0000'){
            currentC = c;
            return index;
        }else{
            if(currentC == c){
                return index;
            }else{
                currentC = c;
                index ++ ;
                return index;
            }
        }
    }

    @Override
    public ContactAdapter.HeadViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.letters_layout, parent, false);
        return new HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(ContactAdapter.HeadViewHolder viewholder, int position) {
        String letter = names.get(position).pinyin.charAt(0) + "";
        viewholder.tv_letter.setText(letter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_contact;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_contact = itemView.findViewById(R.id.tv_contact);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{
        TextView tv_letter ;
        public HeadViewHolder(View itemView) {
            super(itemView);
            tv_letter = itemView.findViewById(R.id.tv_letter);
        }
    }
    /**
     * 获得指定首字母的位置
     * @param ch
     * @return
     */
    public int getPositionForSection(char ch){

        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = names.get(i).pinyin.charAt(0);
            if (firstChar == ch) {
                return i;
            }
        }
        return -1;

    }

}
