package com.ysl.recyclerviewdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysl.recyclerviewdecoration.model.City;
import com.ysl.recyclerviewdecoration.utils.CityUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.city_list)
    RecyclerView mRecyclerView;
    private List<City> mCityList;

    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void initView() {
        //模拟数据
        mCityList.addAll(CityUtil.getCityList());
        //设置recyclerView模式
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //// TODO: 2017/6/29 builder

        mAdapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.mTextView.setText(mCityList.get(position).getName());
            }

            @Override
            public int getItemCount() {
                return mCityList.size();
            }
        };
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv)
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
