package com.json.car;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoodsExpandableListAdapter.OnSelectedAllListner {

    private ExpandableListView expandableListView;
    private GoodsExpandableListAdapter adapter;
    private List<GoodsBean> groupLists;//父类
    //private Map<String, List<GoodsBean.GoodsDetailsBean>> map;
    private ImageView ivCheckAll;//全选的图片按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        iniViews();
        //测试数据
        setDatas();
        //初始化adapter
        adapter = new GoodsExpandableListAdapter(this);
        expandableListView.setAdapter(adapter);

        adapter.refreshDatas(groupLists);
        //展开所有分组
        for (int i = 0; i < groupLists.size(); i++) {
            expandableListView.expandGroup(i);
        }

        //设置ExpandableListView的样式 去掉默认箭头 以及禁止原有的展开关闭功能
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        //设置adapter的监听事件
        adapter.setOnSelectedAllListner(this);

        //通过监听器关联Activity和Adapter的关系
        View.OnClickListener listener = adapter.getAdapterOnClickListener();
        if (listener != null) {
            ivCheckAll.setOnClickListener(listener);
        }
    }

    private void iniViews() {
        expandableListView = (ExpandableListView) findViewById(R.id.expand_listview);
        ivCheckAll = (ImageView) findViewById(R.id.iv_check_all);//全选
    }

    private void setDatas() {

        groupLists = new ArrayList<>();

        //child 类
        List<GoodsBean.GoodsDetailsBean> childBeanLists1 = new ArrayList<>();
        GoodsBean.GoodsDetailsBean childBean1 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 360", "¥ 260", "2");
        childBeanLists1.add(childBean1);
        //最外层实体类
        GoodsBean bean = new GoodsBean("a1", "京东店铺", childBeanLists1);
        groupLists.add(bean);

        List<GoodsBean.GoodsDetailsBean> childBeanLists2 = new ArrayList<>();
        GoodsBean.GoodsDetailsBean childBean2 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 200", "¥ 100", "3");
        GoodsBean.GoodsDetailsBean childBean22 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 150", "¥ 90", "6");
        childBeanLists2.add(childBean2);
        childBeanLists2.add(childBean22);
        //最外层实体类
        GoodsBean bean1 = new GoodsBean("a2", "淘宝店铺", childBeanLists2);
        groupLists.add(bean1);

        List<GoodsBean.GoodsDetailsBean> childBeanLists3 = new ArrayList<>();
        GoodsBean.GoodsDetailsBean childBean3 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 500", "¥ 300", "3");
        GoodsBean.GoodsDetailsBean childBean31 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 650", "¥ 500", "2");
        GoodsBean.GoodsDetailsBean childBean32 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 550", "¥ 40", "9");
        childBeanLists3.add(childBean3);
        childBeanLists3.add(childBean31);
        childBeanLists3.add(childBean32);
        //最外层实体类
        GoodsBean bean2 = new GoodsBean("a3", "天猫店铺", childBeanLists3);
        groupLists.add(bean2);

        List<GoodsBean.GoodsDetailsBean> childBeanLists4 = new ArrayList<>();
        GoodsBean.GoodsDetailsBean childBean4 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 1200", "¥ 900", "3");
        GoodsBean.GoodsDetailsBean childBean41 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 1600", "¥ 1000", "2");
        GoodsBean.GoodsDetailsBean childBean42 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 1500", "¥ 600", "9");
        GoodsBean.GoodsDetailsBean childBean43 = new GoodsBean.GoodsDetailsBean(R.drawable.product, "时尚背包 最新潮流 黑色", "¥ 888", "¥ 666", "5");
        childBeanLists4.add(childBean4);
        childBeanLists4.add(childBean41);
        childBeanLists4.add(childBean42);
        childBeanLists4.add(childBean43);
        //最外层实体类
        GoodsBean bean3 = new GoodsBean("a4", "苏宁易购店铺", childBeanLists4);
        groupLists.add(bean3);

    }

    @Override
    public void isSelectedAll(boolean flag) {
        if (flag) {
            // true  全选
            ivCheckAll.setImageResource(R.drawable.check_selected);
        } else {
            // false  取消全选
            ivCheckAll.setImageResource(R.drawable.check_default);
        }
    }
}
