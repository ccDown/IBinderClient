package com.seven.ibinderclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seven.ibinderserver.IButtonControlAIDL;
import com.seven.ibinderserver.bean.ButtonInfoEntry;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    IButtonControlAIDL mIButtonControlAIDL;
    Button btnCommit;
    Button btnStop;
    EditText etName;
    EditText etBack;
    boolean isBind;

    //死亡代理 当连接断开之后打印日志
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("deathRecipient", "连接断开");
            mIButtonControlAIDL.asBinder().unlinkToDeath(this, 0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCommit = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        etName = findViewById(R.id.et_name);
        etBack = findViewById(R.id.et_back);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setPackage("com.seven.ibinderserver");
                intent.setAction("AIDL.buttonserver");
                if (isBind) {
                    ButtonInfoEntry buttonInfoEntry;
                    try {
                        List<ButtonInfoEntry> buttonInfoEntries = mIButtonControlAIDL.getButtonInfoList();
                        if (buttonInfoEntries.size() > 0) {
                            buttonInfoEntry = mIButtonControlAIDL.getButtonInfoList().get(buttonInfoEntries.size() - 1);
                            Log.e("buttonInfoEntry===", buttonInfoEntry.toString());
                            etName.setText(buttonInfoEntry.getButtonName());
                            etBack.setText(String.valueOf(buttonInfoEntry.getButtonBackground()));

//                            //判断是否存活
//                            mIButtonControlAIDL.asBinder().isBinderAlive();
                        } else {
                            Log.e("buttonInfoEntry===", "没有数据");
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                } else {
                    isBind = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isBind) {
                    unbindService(mServiceConnection);
                    isBind = false;
                }
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIButtonControlAIDL = IButtonControlAIDL.Stub.asInterface(service);
            Log.e("连接  ComponentName=====", name.getClassName());

            try {
                mIButtonControlAIDL.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIButtonControlAIDL = null;
            isBind = false;
            Log.e("断开  ComponentName=====", name.getClassName());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
