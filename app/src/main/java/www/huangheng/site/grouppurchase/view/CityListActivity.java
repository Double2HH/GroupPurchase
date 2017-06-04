package www.huangheng.site.grouppurchase.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import www.huangheng.site.grouppurchase.R;
import www.huangheng.site.grouppurchase.entity.ConstantPool;
import www.huangheng.site.grouppurchase.other.citylist.adapter.CityGridViewAdapter;
import www.huangheng.site.grouppurchase.other.citylist.adapter.SortAdapter;
import www.huangheng.site.grouppurchase.other.citylist.bean.CityModel;
import www.huangheng.site.grouppurchase.other.citylist.bean.RegionInfo;
import www.huangheng.site.grouppurchase.other.citylist.db.RegionFunction;
import www.huangheng.site.grouppurchase.other.citylist.utils.ClearEditText;
import www.huangheng.site.grouppurchase.other.citylist.utils.KeyBoard;
import www.huangheng.site.grouppurchase.other.citylist.utils.PinyinComparator;
import www.huangheng.site.grouppurchase.other.citylist.widget.SideBar;

/**
 * 城市列表
 */
public class CityListActivity extends AppCompatActivity {

    private Intent mIntent;

    private List<RegionInfo> countyList;    //用于存放县一级的城市名
    private List<RegionInfo> cityList;  //用于存放城市名（二级城市，省下直接下辖单位）
    private List<RegionInfo> provinceList;  //用于存放省级地名（江苏、北京、山东等）

    private List<String> countyName;
    private List<String> cityName;
    private List<String> provinceName;

    private ListView sortListView;
    private SortAdapter adapter;
    private SideBar sideBar;

    private ClearEditText clearEditText;
    private TextView dialog;

    private List<RegionInfo> hotCity;//热门城市列表
    private GridView gridView;
    private CityGridViewAdapter gridViewAdapter;

    private List<CityModel> SourceDateList;//用于存放排序后的二级城市，最主要的功能

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);
        ButterKnife.bind(this);

        mIntent = getIntent();

        initData();
        initViews();
        setListener();
    }

    private void initData() {

        //获取所有的省级城市
        provinceList = RegionFunction.getProvincesOrCity(1);
        //获取数据库中的所有二级城市信息
        cityList = RegionFunction.getProvincesOrCity(2);
        //获取数据库中所有的三级城市信息
        countyList = RegionFunction.getProvincesOrCity(3);

        provinceName = new ArrayList<>();
        for (RegionInfo info : provinceList) {
            provinceName.add(info.getName().trim());
        }
        //四个直辖市，港、澳、台特殊处理
        provinceName.remove("北京");
        provinceName.remove("天津");
        provinceName.remove("上海");
        provinceName.remove("重庆");
        provinceName.remove("香港");
        provinceName.remove("澳门");
        provinceName.remove("台湾");

        cityName = new ArrayList<>();
        for (RegionInfo info : cityList) {
            cityName.add(info.getName().trim());
        }

        cityName.add("北京");
        cityName.add("天津");
        cityName.add("上海");
        cityName.add("重庆");
        cityName.add("香港");
        cityName.add("澳门");
        cityName.add("台湾");

        countyName = new ArrayList<>();
        for (RegionInfo info : countyList) {
            countyName.add(info.getName().trim());
        }

        //热门城市的数据初始化
        hotCity = new ArrayList<>();
        //手动设置热城市
        hotCity.add(new RegionInfo(2, 1, "北京", "B"));
        hotCity.add(new RegionInfo(25, 1, "上海", "S"));
        hotCity.add(new RegionInfo(76, 6, "广州", "G"));
        hotCity.add(new RegionInfo(77, 6, "深圳", "S"));
        hotCity.add(new RegionInfo(343, 1, "天津", "T"));

        pinyinComparator = new PinyinComparator();
        // 根据a-z进行排序源数据
        SourceDateList = filledData(cityList);
        Collections.sort(SourceDateList, pinyinComparator);

    }

    private void initViews() {
        View view = View.inflate(this, R.layout.citylist_head, null);

        TextView city = (TextView) findViewById(R.id.citylist_titlebar_currentcity);
        city.setText("当前城市-" + mIntent.getStringExtra(ConstantPool.CITYLIST_CITY));

        gridView = (GridView) view.findViewById(R.id.citylist_hotcity_gridview);
        gridViewAdapter = new CityGridViewAdapter(this, hotCity);
        gridView.setAdapter(gridViewAdapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.addHeaderView(view);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);

        clearEditText = (ClearEditText) findViewById(R.id.filter_edit);

    }

    private void setListener() {
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                KeyBoard.closeSoftKeyboard(CityListActivity.this);
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                String cityName = ((CityModel) adapter.getItem(position - 1)).getName();
                if (cityName != null && cityName.length() > 0) {
                    KeyBoard.closeSoftKeyboard(CityListActivity.this);
                    Intent intent = new Intent();
                    intent.putExtra(ConstantPool.CITYLIST_CITY, cityName);
                    setResult(ConstantPool.CITYLIST_RESULT_CODE, intent);
                    finish();
                }
            }
        });

        //根据输入框输入值的改变来过滤搜索
        clearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String cityName = hotCity.get(position).getName();
                if (cityName != null && cityName.length() > 0) {
                    KeyBoard.closeSoftKeyboard(CityListActivity.this);
                    Intent intent = new Intent();
                    intent.putExtra(ConstantPool.CITYLIST_CITY, cityName);
                    setResult(ConstantPool.CITYLIST_RESULT_CODE, intent);
                    finish();
                }
            }
        });
    }

    /**
     * 为ListView填充数据
     */
    private List<CityModel> filledData(List<RegionInfo> data) {

        List<CityModel> mSortList = new ArrayList<>();

        for (int i = 0, len = data.size(); i < len; i++) {
            CityModel cityModel = new CityModel();
            String name = data.get(i).getName();
            String firstName = data.get(i).getFirstName();
            cityModel.setName(name);
            cityModel.setFirstName(firstName);
            mSortList.add(cityModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     */
    private void filterData(String keyword) {
        List<CityModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(keyword)) {
            filterDateList = SourceDateList;
        } else {
            if (provinceName.contains(keyword)) {
                filterDateList.clear();
                //匹配省级城市的名单
                for (int i = 0; i < provinceList.size(); i++) {
                    String name = provinceList.get(i).getName();
                    int id = provinceList.get(i).getId();
                    if (name.startsWith(keyword)) {
                        filterDateList.addAll(filledData(RegionFunction.getProvencesOrCityOnParent(id)));
                    }
                }

            } else if (cityName.contains(keyword)) {
                filterDateList.clear();
                //匹配二级城市菜单
                for (int i = 0; i < cityList.size(); i++) {
                    String name = cityList.get(i).getName();
                    int id = cityList.get(i).getId();
                    if (name.equals(keyword)) {
                        filterDateList.addAll(filledData(RegionFunction.getProvencesOrCityOnParent(id)));
                    }
                }
            } else if (!cityName.contains(keyword) && !provinceName.contains(keyword)) {
                filterDateList.clear();
                //模糊匹配二级城市
                for (CityModel cityModel : SourceDateList) {
                    String name = cityModel.getName();
                    String firstName = cityModel.getFirstName().toLowerCase();
                    if (name.indexOf(keyword.toString()) != -1 || firstName.startsWith(keyword.toString())) {
                        filterDateList.add(cityModel);
                    }
                }
                if (filterDateList.size() == 0) {
                    //二级城市匹配失败，匹配县级城市（三级城市）
                    for (int i = 0; i < countyList.size(); i++) {
                        String name = countyList.get(i).getName();
                        String firstName = countyList.get(i).getFirstName();
                        if (name.startsWith(keyword)) {
                            CityModel model = new CityModel();
                            model.setName(name);
                            model.setFirstName(firstName);
                            filterDateList.add(model);
                        }
                    }
                    if (filterDateList.size() == 0) {
                        //数据库中无法找到对应的信息，提示错误信息
                        Toast.makeText(CityListActivity.this, "您输入的关键字有误，请重新输入！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        KeyBoard.closeSoftKeyboard(CityListActivity.this);
    }

    @OnClick(R.id.citylist_titlebar_back)
    public void onViewClicked() {
        finish();
    }
}
