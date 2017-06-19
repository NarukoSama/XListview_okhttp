package com.bwie.day0618_oldweekexam_ab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.bwie.day0618_oldweekexam_ab.xlistview.XListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TwoActivity extends AppCompatActivity implements  XListView.IXListViewListener{

    private XListView xlv;

    int page = 1;

    List<Bean.ResultBean.DataBean> list = new ArrayList<>();

    private IAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        xlv = (XListView) findViewById(R.id.xlv);



        xlv.setPullRefreshEnable(true);

        xlv.setPullLoadEnable(true);

        xlv.setXListViewListener(this);

        getData(true);

        xlv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

//                (5):长按 item 弹出dialog,里面两个按钮一个确定按钮,一个取消按钮,点击确定删除本地数据,同时刷新界面(20分)


                AlertDialog.Builder builder = new AlertDialog.Builder(TwoActivity.this);

                builder.setMessage("确定删除？");

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        list.remove(position-1);


                        iAdapter.notifyDataSetChanged();

                    }
                });

                builder.show();


                return true;
            }
        });


    }

    public void setAdapter(final boolean flag){


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (flag){

                    iAdapter = new IAdapter(TwoActivity.this,list);

                    xlv.setAdapter(iAdapter);

                }else{

                    iAdapter.notifyDataSetChanged();

                }
            }
        });


    }

    public void getData(final boolean flag){

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("http://japi.juhe.cn/joke/content/list.from?key=94fbc7ec2262160140d71e1418322f34%20&page="+page+"&pagesize=10&sort=asc&time=1418745237").build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String result = response.body().string();

                System.out.println("result = " + result);

                Bean bean = JSON.parseObject(result, Bean.class);

                list.addAll(bean.getResult().getData());

                setAdapter(flag);

            }
        });

    }

    @Override
    public void onRefresh() {

        list.clear();

        getData(true);

        xlv.setRefreshTime("刚刚");

        xlv.stopRefresh();

    }

    @Override
    public void onLoadMore() {

        page++;

        getData(false);

        xlv.stopLoadMore();

    }
}
