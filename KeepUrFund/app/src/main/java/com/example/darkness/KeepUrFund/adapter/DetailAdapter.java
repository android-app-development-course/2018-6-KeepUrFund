package com.example.darkness.KeepUrFund.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.darkness.KeepUrFund.activity.BaseActivity;
import com.example.darkness.KeepUrFund.bean.DataBean;
import com.example.darkness.KeepUrFund.fragment.MenuMyFragment;
import com.example.darkness.KeepUrFund.stickyheader.StickyHeaderGridAdapter;
import com.example.darkness.KeepUrFund.R;
import com.example.darkness.KeepUrFund.bean.DetailBean;
import com.example.darkness.KeepUrFund.view.SwipeMenuView;

import java.util.List;

/**
 * 悬浮头部项
 * 可侧滑删除
 */

public class DetailAdapter extends StickyHeaderGridAdapter {

    private Context mContext;

    private List<DetailBean.DaylistBean> mDatas;

    public void setmDatas(List<DetailBean.DaylistBean> mDatas) {
        this.mDatas = mDatas;
    }

    public DetailAdapter(Context context, List<DetailBean.DaylistBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }


    @Override
    public int getSectionCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return (mDatas == null || mDatas.get(section).getList() == null) ? 0 : mDatas.get(section).getList().size();
    }

    //映射标头
    @Override
    public StickyHeaderGridAdapter.HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    //映射item布局
    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_item, parent, false);
        return new MyItemViewHolder(view);
    }

    //标头设置
    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder) viewHolder;
        holder.header_date.setText(mDatas.get(section).getTime());
        holder.header_money.setText(mDatas.get(section).getMoney());
    }

    //item设置
    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder) viewHolder;
        final String label = mDatas.get(section).getList().get(position).getTypename();
        holder.item_title.setText(label);
        //load()内加载资源int resource = R.drawable.image;
        Glide.with(mContext).load(BaseActivity.picMap.get(mDatas.get(section).getList().get(position).getImg())).into(holder.item_img);
        holder.item_money.setText(mDatas.get(section).getList().get(position).getAffect_money());
        holder.mSwipeMenuView.setSwipeEnable(true);
        holder.item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                //先从数据库中删除
                BaseActivity.dbServiceUtils.delete(Integer.valueOf(mDatas.get(section).getList().get(offset).getId()));
                //再从view中删除
                mDatas.get(section).getList().remove(offset);
                notifySectionItemRemoved(section, offset);
                //Toast.makeText(holder.item_delete.getContext(), "删除--" + label, Toast.LENGTH_SHORT).show();
            }
        });
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击明细中的项目触发的事件
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).setTitle("备注")
                        .setPositiveButton("朕知道了", null)
                        .show();
                final Window window = alertDialog.getWindow();
                window.setContentView(R.layout.dialog_a_bill);
                TextView tv_title = (TextView) window.findViewById(R.id.dialog_bill_tv_title);
                TextView tv_content = (TextView) window.findViewById(R.id.dialog_bill_tv_content);
                TextView tv_btn = (TextView) window.findViewById(R.id.dialog_bill_btn);

                DetailBean.DaylistBean.ListBean listbean=mDatas.get(section).getList().get(offset);

                //iv_bill.setImageDrawable(ImageUtils.getDrawable(listbean.getSortImg()));
                DataBean data= BaseActivity.dbServiceUtils.search(Integer.valueOf(listbean.getId()));
                tv_content.setText("备注信息：" + data.getdComments());
                tv_title.setText("因" + data.getdPaytype()+ "消费" + Math.abs(Double.valueOf(data.getdMoney()))+ "元");
                if (data.getdMoneytype().equals("收入"))
                    tv_title.setText("因" + data.getdPaytype()+ "收入" + Math.abs(Double.valueOf(data.getdMoney())) + "元");
                tv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                Toast.makeText(holder.item_layout.getContext(), "点击--" + label, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class MyHeaderViewHolder extends HeaderViewHolder {
        TextView header_date;
        TextView header_money;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            header_date = (TextView) itemView.findViewById(R.id.header_date);
            header_money = (TextView) itemView.findViewById(R.id.header_money);
        }
    }

    public static class MyItemViewHolder extends ItemViewHolder {
        TextView item_title;
        TextView item_money;
        Button item_delete;
        ImageView item_img;
        RelativeLayout item_layout;
        SwipeMenuView mSwipeMenuView;

        MyItemViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            item_money = (TextView) itemView.findViewById(R.id.item_money);
            item_delete = (Button) itemView.findViewById(R.id.item_delete);
            item_img = (ImageView) itemView.findViewById(R.id.item_img);
            item_layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            mSwipeMenuView = (SwipeMenuView) itemView.findViewById(R.id.swipe_menu);
        }
    }
}
