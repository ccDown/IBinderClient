package com.seven.ibinderclient;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.seven.ibinderserver.IAdd;
import com.seven.ibinderserver.ISub;
import com.seven.ibinderserver.bean.aidl.BinderPoolManager;

/**
 * @author kuan
 * Created on 2019/1/9.
 * @description
 */
public class BinderPoolActivity extends AppCompatActivity {

    private Button btnConnect;
    private Button btnUnConnect;
    private BinderPoolManager binderPoolManager;
    private IAdd add;
    private ISub sub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binderpool);
        btnConnect = findViewById(R.id.btn_connect);
        btnUnConnect =findViewById(R.id.btn_unconnect);

        btnUnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add.add();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                try {
                    sub.sub();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        binderPoolManager = BinderPoolManager.getInstance(BinderPoolActivity.this);
                        IBinder addBinder = binderPoolManager.queryBinder(BinderPoolManager.ADD);
                        add = IAdd.Stub.asInterface(addBinder);

                        IBinder subBinder = binderPoolManager.queryBinder(BinderPoolManager.SUB);
                        sub = ISub.Stub.asInterface(subBinder);


                    }
                }).start();

            }
        });

    }
}
