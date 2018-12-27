package com.example.myjob.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myjob.R;
import com.example.myjob.bean.Bean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLinearAdapter extends RecyclerView.Adapter<MyLinearAdapter.ViewHolder> {
    private List<Bean.DataBean> list;
    private Context context;

    public MyLinearAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<Bean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyLinearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLinearAdapter.ViewHolder viewHolder, final int i) {
         viewHolder.name.setText(list.get(i).getTitle());
         viewHolder.price.setText(list.get(i).getPrice()+"");
         String images = list.get(i).getImages();
         String[] split = images.split("\\|");
         Uri uri = Uri.parse(split[0]);
         viewHolder.imageView.setImageURI(uri);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.myCilck(list.get(i).getPid());
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setAnimatorandDel(v,i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
      return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
         SimpleDraweeView imageView;
        @BindView(R.id.text_name)
         TextView name;
        @BindView(R.id.text_price)
        TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           ButterKnife.bind(this,itemView);
        }

    }
    //删除加属性动画
    public void setAnimatorandDel(final View view, final int position){
        final float x = view.getX();
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", 0, 500);
        translationX.setDuration(2000);
        translationX.start();
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                list.remove(position);
                notifyDataSetChanged();
                view.setX(x);
                if (itemCilck!=null){
                    itemCilck.Cilck(view,position);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private ItemCilck itemCilck;

    public void setItemCilck(ItemCilck itemCilck) {
        this.itemCilck = itemCilck;
    }

    public interface ItemCilck{
        void Cilck(View view,int position);
    }
    private Click click;

    public void setClick(Click click) {
        this.click = click;
    }

    public interface Click{
        void myCilck(int pid);
    }
}
