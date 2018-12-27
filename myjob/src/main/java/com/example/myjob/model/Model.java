package com.example.myjob.model;



import com.example.myjob.callback.MyCallback;

import java.util.Map;

public interface Model {
    void setRquestData(String path, Map<String, String> mar, Class clazz, MyCallback myCallback);
}
