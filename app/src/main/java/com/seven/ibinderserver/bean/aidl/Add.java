package com.seven.ibinderserver.bean.aidl;

import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.IAdd;

/**
 * @author kuan
 * Created on 2019/1/9.
 * @description
 */
public class Add extends IAdd.Stub {

    public Add(){
        Log.e("Client 创建", "add");
    }

    @Override
    public void add() throws RemoteException {
        Log.e("Client Add", "add");
    }
}
