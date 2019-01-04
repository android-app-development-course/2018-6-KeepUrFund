package com.example.darkness.KeepUrFund.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.darkness.KeepUrFund.activity.BaseActivity;
import com.example.darkness.KeepUrFund.activity.BookNoteActivity;
import com.example.darkness.KeepUrFund.R;
import com.example.darkness.KeepUrFund.bean.NoteBean;

import java.util.List;


public class BookNoteAdapter extends RecyclerView.Adapter<BookNoteAdapter.ViewHolder>{

    //修改过的
    private OnBookNoteClickListener onBookNoteClickListener;
    public void setOnBookNoteClickListener(OnBookNoteClickListener listener) {
        if (onBookNoteClickListener == null)
            this.onBookNoteClickListener = listener;
    }

    private BookNoteActivity mContext;
    private LayoutInflater mInflater;
    private List<NoteBean.SortlistBean> mDatas;

    public void setmDatas(List<NoteBean.SortlistBean> mDatas) {
        this.mDatas = mDatas;
    }

    public BookNoteAdapter(BookNoteActivity context, List<NoteBean.SortlistBean> datas){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this. mDatas = datas;

    }


    @Override
    public int getItemCount() {
        return (mDatas== null) ? 0 : mDatas.size();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tb_type, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(mDatas.get(position).getSort_name());
        if (mContext.lastBean.equals(mDatas.get(position))) {
            mDatas.get(position).setSelected(true);
            mContext.lastImg = holder.img;
        }
        String imgUrl = mDatas.get(position).getSort_img();
        Glide.with(mContext).load(BaseActivity.picMap.get(imgUrl)).into(holder.img);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView title;
        private ImageView img;

        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.item_tb_type_tv);
            img = (ImageView) view.findViewById(R.id.item_tb_type_img);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (mDatas.get(getAdapterPosition()).getSort_id().equals("0")){//添加按钮
                Toast.makeText(mContext, "点击添加", Toast.LENGTH_SHORT).show();
            }else if (!mContext.lastBean.equals(mDatas.get(getAdapterPosition()))){
                mDatas.get(getAdapterPosition()).setSelected(true);
                String imgUrl = mDatas.get(getAdapterPosition()).getSort_img();
                //load函数内改为map中的图片
                Glide.with(mContext).load(BaseActivity.picMap.get(imgUrl)).into(img);
                mContext.lastBean.setSelected(false);
                //Glide.with(mContext).load(BaseActivity.picMap.get(imgUrl)).into(mContext.lastImg);
                mContext.lastImg = img;
                mContext.lastBean = mDatas.get(getAdapterPosition());

                onBookNoteClickListener.OnClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            //根据自己需求进行判断
            if (Integer.parseInt(mDatas.get(getAdapterPosition()).getUid()) > 0 ){
                Toast.makeText(mContext, "长按编辑", Toast.LENGTH_SHORT).show();
            }

            onBookNoteClickListener.OnLongClick(getAdapterPosition());
            return false;
        }
    }
    /**
     * 自定义分类选择接口
     */
    public interface OnBookNoteClickListener {
        void OnClick(int index);
        void OnLongClick(int index);
    }

}
