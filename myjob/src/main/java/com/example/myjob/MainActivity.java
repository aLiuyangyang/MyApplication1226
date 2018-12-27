package com.example.myjob;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.example.myjob.activity.MyLinearAdapter;
import com.example.myjob.bean.Bean;
import com.example.myjob.bean.UserBean;
import com.example.myjob.gen.DaoMaster;
import com.example.myjob.gen.DaoSession;
import com.example.myjob.gen.UserBeanDao;
import com.example.myjob.presenter.Presenterlmpl;
import com.example.myjob.util.Okhttputils;
import com.example.myjob.view.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IView {
    private String ShopUrl = "http://www.zhaoapi.cn/product/searchProducts";
    private Presenterlmpl presenterlmpl;
    private MyLinearAdapter adapter;
    @BindView(R.id.my_recyview)
    RecyclerView my_recyview;
    private UserBeanDao userBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenterlmpl = new Presenterlmpl(this);
        //线性
        GridLayoutManager manager=new GridLayoutManager(this,2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        my_recyview.setLayoutManager(manager);
        //实例化
        adapter=new MyLinearAdapter(this);
        my_recyview.setAdapter(adapter);
        initDbHelp();
        setinit();
        adapter.setClick(new MyLinearAdapter.Click() {
            @Override
            public void myCilck(int pid) {
                Intent intent1=new Intent(MainActivity.this,Main2Activity.class);
                intent1.putExtra("pid",pid);
                startActivity(intent1);
            }
        });
    }
  public void setinit(){
      boolean b = Okhttputils.hasNetWork(this);
      if (b){
          Map<String,String> mar=new HashMap<>();
          mar.put("keywords","笔记本");
          mar.put("page",1+"");
          presenterlmpl.setRquestData(ShopUrl,mar,Bean.class);
      }else {
         List<Bean.DataBean> list=new ArrayList<>();
         List<UserBean> userBeans=userBeanDao.queryBuilder().list();
          for (int i = 0; i < userBeans.size(); i++) {
              Bean.DataBean bean=new Bean.DataBean();
              bean.setPid((int) userBeans.get(i).getPid());
              bean.setImages(userBeans.get(i).getImages());
              bean.setPrice(userBeans.get(i).getPrice());
              bean.setTitle(userBeans.get(i).getTitle());
              list.add(bean);
          }
          adapter.setList(list);
      }

  }
    @Override
    public void setData(Object data) {
     if (data instanceof Bean){
         Bean bean = (Bean) data;
         List<Bean.DataBean> data1 = bean.getData();
         adapter.setList(bean.getData());
         for (int i = 0; i < data1.size(); i++) {
             UserBean userBean=new UserBean();
             userBean.setImages(data1.get(i).getImages());
             userBean.setPrice(data1.get(i).getPrice());
             userBean.setTitle(data1.get(i).getTitle());
             userBean.setPid(data1.get(i).getPid());
             userBeanDao.insertOrReplace(userBean);
         }
     }

    }
    private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "recluse-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        userBeanDao = daoSession.getUserBeanDao();
    }

}
