package com.itwork.contact;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;


public class MainActivity extends AppCompatActivity {

    private SideBar sideBar;
    private TextView tvContent;
    private RecyclerView rlView;
    private List<ContactsBean> datas;
    public String[] names = {"宋江", "卢俊义", "吴用",
            "公孙胜", "关胜", "林冲", "秦明", "呼延灼", "花荣", "柴进", "李应", "朱仝", "鲁智深",
            "武松", "董平", "张清", "杨志", "徐宁", "索超", "戴宗", "刘唐", "李逵", "史进", "穆弘",
            "雷横", "李俊", "阮小二", "张横", "阮小五", " 张顺", "阮小七", "杨雄", "石秀", "解珍",
            " 解宝", "燕青", "朱武", "黄信", "孙立", "宣赞", "郝思文", "韩滔", "彭玘", "单廷珪",
            "魏定国", "萧让", "裴宣", "欧鹏", "邓飞", " 燕顺", "杨林", "凌振", "蒋敬", "吕方",
            "郭 盛", "安道全", "皇甫端", "王英", "扈三娘", "鲍旭", "樊瑞", "孔明", "孔亮", "项充",
            "李衮", "金大坚", "马麟", "童威", "童猛", "孟康", "侯健", "陈达", "杨春", "郑天寿",
            "陶宗旺", "宋清", "乐和", "龚旺", "丁得孙", "穆春", "曹正", "宋万", "杜迁", "薛永", "施恩",
            "周通", "李忠", "杜兴", "汤隆", "邹渊", "邹润", "朱富", "朱贵", "蔡福", "蔡庆", "李立",
            "李云", "焦挺", "石勇", "孙新", "顾大嫂", "张青", "孙二娘", " 王定六", "郁保四", "白胜",
            "时迁", "段景柱"};
    private ContactAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        sideBar = findViewById(R.id.side_bar);
        tvContent = findViewById(R.id.tv_content);
        rlView = findViewById(R.id.rl_view);
        sideBar.setOnItemSelectListener(new SideBar.OnItemSelectListener() {
            @Override
            public void setText(String content) {
                tvContent.setVisibility(View.VISIBLE);
                tvContent.setText(content);
                for(int i = 0 ; i < names.length ; i++) {
                    if ((datas.get(i).pinyin.charAt(0) + "").equals(content)) {
                        // getPositionForSection 根据分类列的索引号获得该序列的首个位置
                        int position = adapter.getPositionForSection(datas.get(i).pinyin.charAt(0));
                        if(position != -1){
                            //滑动到指定位置
                            manager.scrollToPositionWithOffset(position,0);
                        }
                    }
                }
            }

            @Override
            public void setVisibility() {
                tvContent.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        ContactsBean bean;
        for (int i = 0; i < names.length; i++) {
            bean = new ContactsBean(names[i]);
            datas.add(bean);
        }
        //对集合排序
        Collections.sort(datas);
        //条目间的间隔线,不设置不显示悬浮标题
        DividerDecoration divider = new DividerDecoration.Builder(MainActivity.this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.hui)
                .build();

        rlView.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rlView.setLayoutManager(manager);
        rlView.addItemDecoration(divider);
        adapter = new ContactAdapter(this, datas);
        //设置悬浮索引
        StickyHeaderDecoration decor = new StickyHeaderDecoration(adapter);
        rlView.setAdapter(adapter);
        rlView.addItemDecoration(decor, 1);
    }

}
