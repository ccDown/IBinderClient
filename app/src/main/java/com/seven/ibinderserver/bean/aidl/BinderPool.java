package com.seven.ibinderserver.bean.aidl;

import android.os.IBinder;
import android.os.RemoteException;

import com.seven.ibinderserver.IBinderPool;

import static com.seven.ibinderserver.bean.aidl.BinderPoolManager.ADD;
import static com.seven.ibinderserver.bean.aidl.BinderPoolManager.SUB;

public class BinderPool extends IBinderPool.Stub {

    public BinderPool(){

    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case ADD: {
                binder = new Add();
                break;
            }
            case SUB: {
                binder = new Sub();
                break;
            }
            default:
                break;
        }

        return binder;
    }
}
