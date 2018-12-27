package com.example.myjob.presenter;

import com.example.myjob.callback.MyCallback;
import com.example.myjob.model.Modellmpl;
import com.example.myjob.view.IView;

import java.util.Map;

public class Presenterlmpl implements Presenter{
    private IView iView;
    private Modellmpl modellmpl;

    public Presenterlmpl(IView iView) {
        this.iView = iView;
        modellmpl=new Modellmpl();
    }
    @Override
    public void setRquestData(String path, Map<String, String> mar, Class clazz) {
        modellmpl.setRquestData(path, mar, clazz, new MyCallback() {
            @Override
            public void setData(Object data) {
                   iView.setData(data);
            }
        });
    }
    public void setDel(){
        if (iView!=null){
            iView=null;
        }
        if (modellmpl!=null){
            modellmpl=null;
        }
    }
}
