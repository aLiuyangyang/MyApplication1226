package com.example.myjob;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myjob.activity.MyViewPageradapter;
import com.example.myjob.bean.ShopBean;
import com.example.myjob.presenter.Presenterlmpl;
import com.example.myjob.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.recker.flybanner.FlyBanner;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements IView, View.OnClickListener {
    private Presenterlmpl presenterlmpl;
    private String SHOPPATH="http://www.zhaoapi.cn/product/getProductDetail";
    @BindView(R.id.main2_name)
    TextView main2name;
    @BindView(R.id.main2_price)
    TextView main2price;
    @BindView(R.id.main2_but)
    Button main2_but;
    @BindView(R.id.main2_simple)
    SimpleDraweeView main2_simple;
    @BindView(R.id.fly)
    ViewPager viewPager;
    private MyViewPageradapter viewPageradapter;
    private List<String> list;
    private String img;
    private int y=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(y%list.size());
            y++;
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        presenterlmpl=new Presenterlmpl(this);
        Intent intent1=getIntent();
        viewPageradapter=new MyViewPageradapter(this);
        viewPager.setAdapter(viewPageradapter);
        int pid = intent1.getIntExtra("pid", 1);
        Map<String,String> mar=new HashMap<>();
        mar.put("pid",pid+"");
        presenterlmpl.setRquestData(SHOPPATH,mar,ShopBean.class);
        main2_but.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.main2_but:
              Toast.makeText(this,"加入成功",Toast.LENGTH_SHORT).show();
              UMShareAPI umShareAPI = UMShareAPI.get(Main2Activity.this);
              umShareAPI.getPlatformInfo(Main2Activity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                  @Override
                  public void onStart(SHARE_MEDIA share_media) {

                  }

                  @Override
                  public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                      Log.i("aa",map+"");
                      //获取头像
                      img = map.get("iconurl");
                      Uri uri = Uri.parse(img);
                      main2_simple.setImageURI(uri);

                  }

                  @Override
                  public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                  }

                  @Override
                  public void onCancel(SHARE_MEDIA share_media, int i) {

                  }
              });
              break;
              default:break;
      }
    }

    @Override
    public void setData(Object data) {
         ShopBean bean= (ShopBean) data;
         list=new ArrayList<>();
         main2name.setText(bean.getData().getTitle());
         main2price.setText(bean.getData().getPrice()+"");
         //轮播图
        String images = bean.getData().getImages();
        String[] split = images.split("\\|");
        List<String> strings = Arrays.asList(split);
        Log.i("aa",strings.size()+"");
        for (String s:strings){
         this.list.add(s);
        }
        viewPageradapter.setList(this.list);
        handler.sendEmptyMessageDelayed(0,2000);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
