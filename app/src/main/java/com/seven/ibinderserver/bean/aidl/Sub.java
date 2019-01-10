package com.seven.ibinderserver.bean.aidl;

import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.ISub;

/**
 * @author kuan
 * Created on 2019/1/9.
 * @description
 */
public class Sub extends ISub.Stub {
    public Sub(){
        Log.e("Client 创建", "Sub");

    }
    @Override
    public void sub() throws RemoteException {
        Log.e("Client 创建", "sub");
    }
}
