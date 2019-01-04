package com.example.darkness.KeepUrFund.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.darkness.KeepUrFund.R;
import com.example.darkness.KeepUrFund.adapter.MyPagerAdapter;
import com.example.darkness.KeepUrFund.adapter.BookNoteAdapter;
import com.example.darkness.KeepUrFund.bean.DataBean;
import com.example.darkness.KeepUrFund.bean.NoteBean;
import com.example.darkness.KeepUrFund.fragment.MenuFirstFragment;
import com.example.darkness.KeepUrFund.utils.DateUtils;
import com.example.darkness.KeepUrFund.utils.DataUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 记账本 --- 记一笔
 */
public class BookNoteActivity extends BaseActivity {
    @BindView(R.id.tb_note_income)
    TextView incomeTv;
    @BindView(R.id.tb_note_outcome)
    TextView outcomeTv;
    @BindView(R.id.tb_note_remark)
    ImageView remarkTv;
    @BindView(R.id.tb_note_money)
    TextView moneyTv;
    @BindView(R.id.tb_note_date)
    TextView dateTv;
    @BindView(R.id.tb_note_cash)
    TextView cashTv;
    @BindView(R.id.viewpager_item)
    ViewPager viewpagerItem;
    @BindView(R.id.layout_icon)
    LinearLayout layoutIcon;
    @BindView(R.id.tb_note_paytype)
    TextView paytypeTv;
    public String comments = "";

    public String type = "支出";//1支出2收入
    //计算器
    private boolean isDot;
    private String num = "0";
    private String dotNum = ".00";
    private final int MAX_NUM = 9999999;
    private final int DOT_NUM = 2;
    private int count = 0;
    //选择器
    private OptionsPickerView pvCustomOptions;
    private List<String> cardItem;
    //viewpager数据
    private int page;
    private boolean isTotalPage;
    private int sortPage = -1;
    private List<NoteBean.SortlistBean> mDatas;
    private List<NoteBean.SortlistBean> tempList;
    //记录上一次点击后的imageview
    public NoteBean.SortlistBean lastBean;
    public ImageView lastImg;


    @Override
    protected int getLayout() {
        return R.layout.activity_tallybook_note;
    }

    @Override
    protected void initEventAndData() {

        dateTv.setText(DateUtils.getCurDateStr("yyyy-MM-dd"));
        setTitleStatus();

    }


    /**
     * 设置状态
     */
    private void setTitleStatus() {

        NoteBean noteBean = null;
        if (type.equals("支出")) {
            outcomeTv.setSelected(true);
            incomeTv.setSelected(false);
            noteBean = DataUtils.getTallyNoteBeanOut();
        }
        if (type.equals("收入")) {
            incomeTv.setSelected(true);
            outcomeTv.setSelected(false);
            noteBean = DataUtils.getTallyNoteBeanIn();
        }

        mDatas = noteBean.getSortlist();

        lastBean = mDatas.get(0);
        lastImg = new ImageView(this);

        cardItem = new ArrayList<>();
        for (int i = 0; i < noteBean.getPayinfo().size(); i++) {
            cardItem.add(noteBean.getPayinfo().get(i).getPay_name());
        }

        initViewPager();
    }


    private void initViewPager() {
        LayoutInflater inflater = this.getLayoutInflater();// 获得一个视图管理器LayoutInflater
        viewList = new ArrayList<>();// 创建一个View的集合对象
        if (mDatas.size() % 10 == 0)
            isTotalPage = true;
        else
            isTotalPage=false;
        page = (int) Math.ceil(mDatas.size() * 1.0 / 10);
        for (int i = 0; i < page; i++) {
            tempList = new ArrayList<>();
            View view = inflater.inflate(R.layout.pager_item_tb_type, null);
            RecyclerView recycle = (RecyclerView) view.findViewById(R.id.pager_type_recycle);
            //若不为最后一页或为最后一页且item填满该页的情况
            if (i != page - 1 || (i == page - 1 && isTotalPage)) {
                for (int j = 0; j < 10; j++) {
                        tempList.add(mDatas.get(i * 10 + j));
                }
            } else {
                for (int j = 0; j < mDatas.size() % 10; j++) {
                        tempList.add(mDatas.get(i * 10 + j));
                }
            }

            BookNoteAdapter mAdapter = new BookNoteAdapter(this, tempList);

            //修改
            mAdapter.setOnBookNoteClickListener(new BookNoteAdapter.OnBookNoteClickListener() {

                @Override
                public void OnClick(int index) {
                    //获取真实index
                    index = index + viewpagerItem.getCurrentItem() * 10;
                    lastBean = mDatas.get(index);
                    paytypeTv.setText(lastBean.getSort_name());
                }

                @Override
                public void OnLongClick(int index) {

                }
            });

            GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
            recycle.setLayoutManager(layoutManager);
            recycle.setAdapter(mAdapter);
            viewList.add(view);
            //修改！：默认选择第一个分类
            paytypeTv.setText(lastBean.getSort_name());
        }

        viewpagerItem.setAdapter(new MyPagerAdapter(viewList));
        viewpagerItem.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewpagerItem.setOffscreenPageLimit(1);//预加载数据页
        viewpagerItem.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    for (int i = 0; i < viewList.size(); i++) {
                        icons[i].setImageResource(R.drawable.icon_banner_point2);
                    }
                    icons[position].setImageResource(R.drawable.icon_banner_point1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initIcon();
    }

    private List<View> viewList;
    private ImageView[] icons;

    private void initIcon() {
        icons = new ImageView[viewList.size()];
        layoutIcon.removeAllViews();
        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageView(this);
            icons[i].setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            icons[i].setImageResource(R.drawable.icon_banner_point2);
            if (viewpagerItem.getCurrentItem() == i) {
                icons[i].setImageResource(R.drawable.icon_banner_point1);
            }
            icons[i].setPadding(5, 0, 5, 0);
            icons[i].setAdjustViewBounds(true);
            layoutIcon.addView(icons[i]);
        }
        if (sortPage != -1)
            viewpagerItem.setCurrentItem(sortPage);
    }
    @OnClick({R.id.tb_note_income, R.id.tb_note_outcome, R.id.tb_note_cash, R.id.tb_note_date,
            R.id.tb_note_remark, R.id.tb_calc_num_done, R.id.tb_calc_num_del, R.id.tb_calc_num_1,
            R.id.tb_calc_num_2, R.id.tb_calc_num_3, R.id.tb_calc_num_4, R.id.tb_calc_num_5,
            R.id.tb_calc_num_6, R.id.tb_calc_num_7, R.id.tb_calc_num_8, R.id.tb_calc_num_9,
            R.id.tb_calc_num_0, R.id.tb_calc_num_dot, R.id.tb_note_clear, R.id.back_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.tb_note_income://收入
                type = "收入";
                setTitleStatus();
                break;
            case R.id.tb_note_outcome://支出
                type = "支出";
                setTitleStatus();
                break;
            case R.id.tb_note_cash://现金
                pvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        cashTv.setText(cardItem.get(options1));
                    }
                })
                        .build();
                pvCustomOptions.setPicker(cardItem);
                pvCustomOptions.show();
                break;
            case R.id.tb_note_date://日期
                final TimePickerView startTimePicker = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        dateTv.setText(sdf.format(date));
                    }
                })
                        .setRange(2017, 2050)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build();
                startTimePicker.show();
                break;
            case R.id.tb_note_remark://备注
                showContentDialog();
                Toast.makeText(mContext, "点击备注", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tb_calc_num_done://确定
                String[] datas = dateTv.getText().toString().split("-");
                String nydatas = datas[0] + "年" + datas[1] + "月";
                String today = datas[2] + "日";
                //String money,String moneytype,String paytype,String payway,String comments,String nydate,String date
                DataBean data = new DataBean(moneyTv.getText().toString(),
                        type,
                        paytypeTv.getText().toString(),
                        cashTv.getText().toString(),
                        comments,
                        nydatas,
                        today
                );
                BaseActivity.dbServiceUtils.save(data);
                Toast.makeText(mContext, nydatas, Toast.LENGTH_SHORT).show();
                finish();
                MenuFirstFragment m=new MenuFirstFragment();
                m.setTestData();
                break;
            case R.id.tb_calc_num_1:
                calcMoney(1);
                break;
            case R.id.tb_calc_num_2:
                calcMoney(2);
                break;
            case R.id.tb_calc_num_3:
                calcMoney(3);
                break;
            case R.id.tb_calc_num_4:
                calcMoney(4);
                break;
            case R.id.tb_calc_num_5:
                calcMoney(5);
                break;
            case R.id.tb_calc_num_6:
                calcMoney(6);
                break;
            case R.id.tb_calc_num_7:
                calcMoney(7);
                break;
            case R.id.tb_calc_num_8:
                calcMoney(8);
                break;
            case R.id.tb_calc_num_9:
                calcMoney(9);
                break;
            case R.id.tb_calc_num_0:
                calcMoney(0);
                break;
            case R.id.tb_calc_num_dot:
                if (dotNum.equals(".00")) {
                    isDot = true;
                    dotNum = ".";
                }
                moneyTv.setText(num + dotNum);
                break;
            case R.id.tb_note_clear://清空
                num = "0";
                count = 0;
                dotNum = ".00";
                isDot = false;
                moneyTv.setText("0.00");
                break;
            case R.id.tb_calc_num_del://删除
                if (isDot) {
                    if (count > 0) {
                        dotNum = dotNum.substring(0, dotNum.length() - 1);
                        count--;
                    }
                    if (count == 0) {
                        isDot = false;
                        dotNum = ".00";
                    }
                    moneyTv.setText(num + dotNum);
                } else {
                    if (num.length() > 0)
                        num = num.substring(0, num.length() - 1);
                    if (num.length() == 0)
                        num = "0";
                    moneyTv.setText(num + dotNum);
                }
                break;
        }
    }


    private void calcMoney(int money) {
        if (num.equals("0") && money == 0)
            return;
        if (isDot) {
            if (count < DOT_NUM) {
                count++;
                dotNum += money;
                moneyTv.setText(num + dotNum);
            }
        } else if (Integer.parseInt(num) < MAX_NUM) {
            if (num.equals("0"))
                num = "";
            num += money;
            moneyTv.setText(num + dotNum);
        }
    }


    /**
     * 显示备注内容输入框
     */
    public void showContentDialog() {
        final EditText editText = new EditText(BookNoteActivity.this);

        //弹出输入框
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("备注")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = editText.getText().toString();
                        if (input.equals("")) {
                            Toast.makeText(getApplicationContext(), "内容不能为空！" + input,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            comments = input;
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                //调用系统输入法
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }
}
