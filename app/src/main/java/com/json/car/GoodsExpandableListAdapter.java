package com.json.car;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GoodsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<GoodsBean> lists;
    private LayoutInflater inflater;
    private CustomDialog dialog;
    private OnSelectedAllListener listener;//回调接口
    private boolean isSelectedAll = false;

    public GoodsExpandableListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<GoodsBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return null != lists ? lists.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return null != lists ? lists.get(groupPosition).getGoods().size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return lists.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return lists.get(groupPosition).getGoods().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_group_listview, parent, false);
            holder = new GroupViewHolder();
            holder.tvShopName = (TextView) convertView.findViewById(R.id.tv_shopName);
            holder.ivGroupCheck = (ImageView) convertView.findViewById(R.id.iv_check_group);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        final GoodsBean bean = lists.get(groupPosition);
        holder.tvShopName.setText(bean.getShopName());
        selectedItem(bean.isGroupSelected(), holder.ivGroupCheck);

        holder.ivGroupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isGroupSelected = !bean.isGroupSelected();
                bean.setGroupSelected(isGroupSelected);//组的状态
                for (int i = 0; i < lists.get(groupPosition).getGoods().size(); i++) {
                    lists.get(groupPosition).getGoods().get(i).setChildSelected(isGroupSelected);//子的状态
                }
                //判断所有组是不是都选中了，都选中的话，通过接口告诉主界面的全选控件，并让其为选中状态的图片
                boolean isAllGroup = isAllGroupSelected(lists);
                if (listener != null) {
                    listener.isSelectedAll(isAllGroup);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_child_listview, parent, false);
            holder = new ChildViewHolder();
            holder.ivGoodsUrl = (ImageView) convertView.findViewById(R.id.iv_goods_url);
            holder.tvGoodsName = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.tvGoodsOriPrice = (TextView) convertView.findViewById(R.id.tv_goods_ori_price);
            holder.tvGoodsPrice = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.tvGoodsNum = (TextView) convertView.findViewById(R.id.tv_goods_num);
            holder.ivChildCheck = (ImageView) convertView.findViewById(R.id.iv_check_child);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final GoodsBean.GoodsDetailsBean bean = lists.get(groupPosition).getGoods().get(childPosition);
        holder.tvGoodsName.setText(bean.getGoodsName());
        holder.tvGoodsOriPrice.setText(bean.getGoodsOriPrice());
        holder.tvGoodsPrice.setText(bean.getGoodsPrice());
        holder.tvGoodsNum.setText(bean.getGoodsNum());
        //这里用本地的测试图片，正式开发则需要从网络取
        holder.ivGoodsUrl.setImageResource(bean.getGoodsUrl());

        selectedItem(bean.isChildSelected(), holder.ivChildCheck);

        holder.ivChildCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChildSelected = !bean.isChildSelected();
                bean.setChildSelected(isChildSelected);//子项的选中与未选中
                //还需要进一步处理子项状态导致的组的状态问题，如果某一组的子项都选中的话，那么所在的组也为选中状态
                boolean isSelectedGroup = isAllChildSelected(lists.get(groupPosition).getGoods());
                lists.get(groupPosition).setGroupSelected(isSelectedGroup);
                //因为子项状态会影响组的状态，判断所有组是不是都选中了，都选中的话，通过接口告诉主界面的全选控件，并让其为选中状态的图片
                boolean isAllGroup = isAllGroupSelected(lists);
                if (listener != null) {
                    listener.isSelectedAll(isAllGroup);
                }
                notifyDataSetChanged();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗 dialog
                dialog = new CustomDialog.Builder(context)
                        .setContent("你确定要删除该商品吗？")
                        .setLeftText("容朕三思")
                        .setRightText("朕意已决")
                        .setLeftOnclick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        })
                        .setRightOnClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class GroupViewHolder {
        TextView tvShopName;//店铺名称
        ImageView ivGroupCheck;//选择按钮
    }

    class ChildViewHolder {
        ImageView ivGoodsUrl;//商品图片url
        TextView tvGoodsName;//商品名称
        TextView tvGoodsOriPrice;//原价
        TextView tvGoodsPrice;//实际价格
        TextView tvGoodsNum;//购买数量
        ImageView ivChildCheck;//选择按钮
    }


    //选中状态的相关判断处理

    /**
     * 选中与未选中状态下对应的图片状态的切换
     *
     * @param isSelected 是否选中
     * @param iv         图片
     */
    private void selectedItem(boolean isSelected, ImageView iv) {
        if (isSelected) {
            iv.setImageResource(R.drawable.check_selected);
        } else {
            iv.setImageResource(R.drawable.check_default);
        }
    }

    /**
     * 所有组是不是都为选中状态，因为某一组选中的话，那么该组其下的所有子项也都选中了
     *
     * @param mLists
     * @return true：所有组都选中，相当于全选
     */
    private boolean isAllGroupSelected(List<GoodsBean> mLists) {
        for (int i = 0; i < mLists.size(); i++) {
            boolean isGroupSelected = mLists.get(i).isGroupSelected();
            if (!isGroupSelected) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有的子项是否都选中
     *
     * @param childLists
     * @return true：表示某一组内所有的子项都选中
     */
    private boolean isAllChildSelected(List<GoodsBean.GoodsDetailsBean> childLists) {
        for (int i = 0; i < childLists.size(); i++) {
            boolean isChildSelected = childLists.get(i).isChildSelected();
            if (!isChildSelected) {
                return false;
            }
        }
        return true;
    }

    /**
     * 全选
     *
     * @param lists
     * @param isSelectedAll
     * @param iv
     * @return
     */
    public boolean isSelectedAll(List<GoodsBean> lists, boolean isSelectedAll, ImageView iv) {
        isSelectedAll = !isSelectedAll;
        selectedItem(isSelectedAll, iv);
        for (int i = 0; i < lists.size(); i++) {
            lists.get(i).setGroupSelected(isSelectedAll);
            for (int j = 0; j < lists.get(i).getGoods().size(); j++) {
                lists.get(i).getGoods().get(j).setChildSelected(isSelectedAll);
            }
        }
        return isSelectedAll;
    }

    public interface OnSelectedAllListener {
        void isSelectedAll(boolean flag);
    }

    public void setOnSelectedAllListener(OnSelectedAllListener listener) {
        this.listener = listener;
    }


    public View.OnClickListener getAdapterOnClickListener() {
        return clickListener;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_check_all:  //主界面中全选按钮控件的id
                    isSelectedAll = isSelectedAll(lists, isSelectedAll, (ImageView) v);
                    notifyDataSetChanged();
                    break;
            }
        }
    };

}
