package com.example.mygreendao;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mygreendao.bean.UserBean;
import com.example.mygreendao.gen.DaoMaster;
import com.example.mygreendao.gen.DaoSession;
import com.example.mygreendao.gen.UserBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button my_add;
    private Button my_delete;
    private Button my_update;
    private Button my_select;
    private UserBeanDao userBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDbHelp();

    }

    private void initView() {
        my_add = (Button) findViewById(R.id.my_add);
        my_delete = (Button) findViewById(R.id.my_delete);
        my_update = (Button) findViewById(R.id.my_update);
        my_select = (Button) findViewById(R.id.my_select);

        my_add.setOnClickListener(this);
        my_delete.setOnClickListener(this);
        my_update.setOnClickListener(this);
        my_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_add:
                  myadd();
                break;
            case R.id.my_delete:
                      del();
                break;
            case R.id.my_update:
                 updata();
                break;
            case R.id.my_select:
                select();
                break;
        }
    }

    public void myadd(){
        UserBean bean=new UserBean(1,"yy");
        userBeanDao.insert(bean);
        //Toast.makeText(this,userBeanDao+"",Toast.LENGTH_SHORT).show();
    }
    public void updata(){
        UserBean bean=new UserBean(1,"洋洋啊");
        userBeanDao.insertOrReplace(bean);
       // Toast.makeText(this,,Toast.LENGTH_SHORT).show();
    }
   public void del(){
        userBeanDao.deleteByKey(Long.valueOf(1));

   }
   public void select(){
        String ss="";
       QueryBuilder<UserBean> userBeanQueryBuilder = userBeanDao.queryBuilder();
       List<UserBean> list = userBeanQueryBuilder.where(UserBeanDao.Properties.Id.eq(1)).list();
       for (UserBean userBean:list){
           ss+=userBean.getId()+userBean.getName();
       }
       Toast.makeText(this,ss,Toast.LENGTH_SHORT).show();

   }
    private void initDbHelp() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "recluse-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        userBeanDao = daoSession.getUserBeanDao();
    }
}
