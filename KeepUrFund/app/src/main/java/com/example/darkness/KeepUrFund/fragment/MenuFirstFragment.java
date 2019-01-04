package com.example.darkness.KeepUrFund.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.darkness.KeepUrFund.activity.BaseActivity;
import com.example.darkness.KeepUrFund.activity.BookNoteActivity;
import com.example.darkness.KeepUrFund.stickyheader.StickyHeaderGridLayoutManager;
import com.example.darkness.KeepUrFund.R;
import com.example.darkness.KeepUrFund.adapter.DetailAdapter;
import com.example.darkness.KeepUrFund.bean.DetailBean;
import com.example.darkness.KeepUrFund.utils.DateUtils;
import com.example.darkness.KeepUrFund.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 记账本--明细
 */
public class MenuFirstFragment extends BaseFragment {


    @BindView(R.id.data_year)
    TextView dataYear;
    @BindView(R.id.data_month)
    TextView dataMonth;
    @BindView(R.id.layout_data)
    LinearLayout layoutData;
    @BindView(R.id.cash_surplus)
    TextView cashSurplus;
    @BindView(R.id.cash_compared)
    TextView cashCompared;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.float_btn)
    FloatingActionButton floatBtn;
    Unbinder unbinder;


    private static final int SPAN_SIZE = 1;
    private StickyHeaderGridLayoutManager mLayoutManager;
    private static DetailAdapter adapter;
    private static List<DetailBean.DaylistBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_first;
    }
    @Override
    protected void initEventAndData() {

        dataYear.setText(DateUtils.getCurYear("yyyy 年"));
        dataMonth.setText(DateUtils.date2Str(new Date(), "MM"));
        String[] splitstr=dataYear.getText().toString().split("\\s+");
        BaseActivity.Qnydate=splitstr[0]+"年"+dataMonth.getText().toString()+"月";
        //改变加载显示的颜色
        swipe.setColorSchemeColors(getResources().getColor(R.color.text_red), getResources().getColor(R.color.text_red));
        //设置向下拉多少出现刷新
        swipe.setDistanceToTriggerSync(200);
        //设置刷新出现的位置
        swipe.setProgressViewEndTarget(false, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                DetailBean dbean=setTestData();
                cashSurplus.setText(dbean.getT_outcome());
                cashCompared.setText(dbean.getT_income());
            }
        });

        mLayoutManager = new StickyHeaderGridLayoutManager(SPAN_SIZE);
        mLayoutManager.setHeaderBottomOverlapMargin(5);

        // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
        // puts the removed item on the top of other views and it will be drawn above sticky header.
        // The only way to fix this, abandon remove animations :(
        rvList.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });
        rvList.setLayoutManager(mLayoutManager);
        adapter = new DetailAdapter(mContext, list);
        rvList.setAdapter(adapter);
        DetailBean dbean=setTestData();
        cashSurplus.setText(dbean.getT_outcome());
        cashCompared.setText(dbean.getT_income());
    }
    public static DetailBean setTestData() {
        DetailBean data = DataUtils.getTallyDetailBean();
        list = data.getDaylist();
        adapter.setmDatas(list);
        adapter.notifyAllSectionsDataSetChanged();//需调用此方法刷新
        return data;
    }
    @OnClick(R.id.float_btn)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(),BookNoteActivity.class);
        startActivity(intent);
    }
    @OnClick({R.id.layout_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_data:
                //时间选择器
                new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        dataYear.setText(DateUtils.date2Str(date, "yyyy 年"));
                        dataMonth.setText(DateUtils.date2Str(date, "MM"));
                        String[] splitstr=dataYear.getText().toString().split("\\s+");
                        BaseActivity.Qnydate=splitstr[0]+"年"+dataMonth.getText().toString()+"月";
                        DetailBean dbean=setTestData();
                        cashSurplus.setText(dbean.getT_outcome());
                        cashCompared.setText(dbean.getT_income());
                    }
                })
                        .setRangDate(null, Calendar.getInstance())
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .build()
                        .show();
                break;
        }
    }
}
