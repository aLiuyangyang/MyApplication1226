package com.example.myjob.model;

import com.example.myjob.callback.MyCallback;
import com.example.myjob.util.ICallback;
import com.example.myjob.util.Okhttputils;

import java.util.Map;

public class Modellmpl implements Model{

    @Override
    public void setRquestData(String path, final Map<String, String> mar, Class clazz, final MyCallback myCallback) {
        Okhttputils.getmInstance().postEnQueue(path, mar, clazz, new ICallback() {
            @Override
            public void setSuccess(Object obj) {
                myCallback.setData(obj);
            }

            @Override
            public void setfiee(Exception ex) {
                 myCallback.setData(ex);
            }
        });
    }
}
