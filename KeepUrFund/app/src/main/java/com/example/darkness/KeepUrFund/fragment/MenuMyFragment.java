package com.example.darkness.KeepUrFund.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.darkness.KeepUrFund.R;
import com.example.darkness.KeepUrFund.activity.BaseActivity;
import com.example.darkness.KeepUrFund.adapter.AccountAdapter;
import com.example.darkness.KeepUrFund.bean.AccountBean;
import com.example.darkness.KeepUrFund.utils.DateUtils;
import com.example.darkness.KeepUrFund.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 记账本--我的账户
 */
public class MenuMyFragment extends BaseFragment {


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


    private static AccountAdapter adapter;
    private static List<AccountBean.ListBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_my;
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
                AccountBean accountBean = setTestData();
                cashSurplus.setText(accountBean.getTotal_out());
                cashCompared.setText(accountBean.getTotal_in());
            }
        });

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AccountAdapter(getActivity(), list);
        adapter.setmListener(new AccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        rvList.setAdapter(adapter);
        //test data---------------
        AccountBean accountBean = setTestData();
        cashSurplus.setText(accountBean.getTotal_out());
        cashCompared.setText(accountBean.getTotal_in());
    }


    public static AccountBean setTestData() {
        AccountBean data = DataUtils.getTallyAccountBean();
        list = data.getList();
        adapter.setmDatas(list);
        adapter.notifyDataSetChanged();//需调用此方法刷新
        return data;
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
                        AccountBean accountBean = setTestData();
                        cashSurplus.setText(accountBean.getTotal_out());
                        cashCompared.setText(accountBean.getTotal_in());
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
