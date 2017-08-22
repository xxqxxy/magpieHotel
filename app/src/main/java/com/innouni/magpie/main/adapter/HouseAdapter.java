package com.innouni.magpie.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.innouni.magpie.main.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xxq on 2017/8/22.
 */

public class HouseAdapter extends BaseAdapter {

    Context context ;
    List<HashMap<String , Object>> list;
   View. OnClickListener onClickListener;
    LayoutInflater inflater;
    public HouseAdapter(Context mContext ,  List<HashMap<String , Object>> mList ,  View. OnClickListener mOnClickListener){
        this.context = mContext;
        this.list= mList;
        this.onClickListener = mOnClickListener;
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.home_house_item, null);
            holder.ll_house =(LinearLayout) convertView.findViewById(R.id.ll_house);
            holder.iv_hmap =(ImageView) convertView.findViewById(R.id.iv_hmap);
            holder.iv_c_state =(ImageView) convertView.findViewById(R.id.iv_c_state);
            holder.tv_price =(TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_address = (TextView)convertView.findViewById(R.id.tv_address);
            holder.tv_title =(TextView)convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_price.setText("￥"+list.get(position).get("price")+"");
        holder.tv_title.setText(list.get(position).get("hs_title")+"");
        holder.tv_address.setText(list.get(position).get("hs_address")+"");
        Glide.with(context).load(list.get(position).get("hs_fist_img")+"").placeholder(R.mipmap.icon_home_top_lunbo).into(holder.iv_hmap);
        // 1 ：收藏  /  2：未收藏
        if((list.get(position).get("c_state")+"") .equals("1")){
            holder.iv_c_state.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_house_collection));
        }else{
            holder.iv_c_state.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_house_collection_normal));
        }

        //设置点击事件
        holder.ll_house.setTag(position+"");
        holder.ll_house.setOnClickListener(onClickListener);

        holder.iv_c_state.setTag(position+"");
        holder.iv_c_state.setOnClickListener(onClickListener);


        return convertView;
    }

    class ViewHolder{

        LinearLayout ll_house;
        ImageView iv_hmap,iv_c_state;
        TextView tv_price,tv_title,tv_address;
    }


}
