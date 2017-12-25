package com.example.tjf.mycoolweather.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjf.mycoolweather.MainActivity;
import com.example.tjf.mycoolweather.R;
import com.example.tjf.mycoolweather.WeatherActivity;
import com.example.tjf.mycoolweather.db.City;
import com.example.tjf.mycoolweather.db.County;
import com.example.tjf.mycoolweather.db.Province;
import com.example.tjf.mycoolweather.util.HttpUtil;
import com.example.tjf.mycoolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-12-23 0023.
 */
public class Choose_AreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog mProgressDialog;

    //fragment中的使用
    Unbinder unbinder;
    @BindView(R.id.title_text)
    TextView titile;
    @BindView(R.id.back_button)
    Button bt_back;
    @BindView(R.id.list_view)
    ListView mlistView;
    List<String> datalist = new ArrayList<>();
    ArrayAdapter<String> mArrayAdapter;
    //省市县集合数据
    List<Province> provinceList;
    List<City> cityList;
    List<County> countyList;

    //选中的省市县
    Province selecteprovince;
    City selectecity;
    County selectecounty;

    //选中的级别
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choose_area, container, false);
        unbinder = ButterKnife.bind(this, view);
        mArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, datalist);
        mlistView.setAdapter(mArrayAdapter);
        return view;
    }

    /*  @OnItemClick(R.id.list_view)
      void OnItemClick(int position) {
          if (currentLevel == LEVEL_PROVINCE) {
              selecteprovince = provinceList.get(position);
              queryCities();
          } else if (currentLevel == LEVEL_CITY) {
              selectecity = cityList.get(position);
              queryCounties();
          }
      }*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvinces();
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selecteprovince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectecity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    String weatherId = countyList.get(position).getWeatherId();
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });

    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryProvinces() {
        titile.setText("中国");
        bt_back.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            datalist.clear();
            for (Province p : provinceList) {
                datalist.add(p.getProviceName());
            }
            mArrayAdapter.notifyDataSetChanged();
            mlistView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }

    }


    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCities() {
        titile.setText(selecteprovince.getProviceName());
        bt_back.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selecteprovince.getId())).find(City.class);

        if (cityList.size() > 0) {
            datalist.clear();
            for (City c : cityList) {
                datalist.add(c.getCityName());
            }

            mArrayAdapter.notifyDataSetChanged();
            mlistView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selecteprovince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }

    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询。
     */
    private void queryCounties() {
        titile.setText(selectecity.getCityName());
        bt_back.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectecity.getId())).find(County.class);
        if (countyList.size() > 0) {
            datalist.clear();
            for (County c : countyList) {
                datalist.add(c.getCountyName());
            }
            mArrayAdapter.notifyDataSetChanged();
            mlistView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selecteprovince.getProvinceCode();
            int cityCode = selectecity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }

    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据。
     */

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                boolean isresult = false;
                if ("province".equals(type)) {
                    isresult = Utility.handleProvinceResponse(result);
                } else if ("city".equals(type)) {
                    isresult = Utility.handleCityResponse(result, selecteprovince.getId());
                } else if ("county".equals(type)) {
                    isresult = Utility.handleCountyResponse(result, selectecity.getId());
                }
                if (isresult) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });

                }
            }
        });

    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("正在加载中。。。");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解绑
        unbinder.unbind();
    }
}
