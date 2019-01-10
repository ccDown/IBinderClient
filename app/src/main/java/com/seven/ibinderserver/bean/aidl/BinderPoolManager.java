package com.seven.ibinderserver.bean.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.IBinderPool;

import java.util.concurrent.CountDownLatch;

public class BinderPoolManager {
    public static final int ADD = 0;
    public static final int SUB = 1;
 
    private Context mContext;
    private IBinderPool mBinderPool;
    private static volatile BinderPoolManager sInstance;
    private CountDownLatch mConnectBinderPoolCountDownLatch;

    public static BinderPoolManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new BinderPoolManager(context);
                }
            }
        }
        return sInstance;
    }

    private BinderPoolManager(Context context) {
        mContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    private synchronized void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent();
        service.setPackage("com.seven.ibinderserver");
        service.setAction("binderpool");
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);
        try {
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }
 
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
 
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("onServiceDisconnected","连接断开");
        }
 
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("onServiceConnected","连接成功");
            mBinderPool = BinderPool.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();
        }
    };
 
    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {    // 6
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };
 
}
