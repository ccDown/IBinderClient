package com.seven.ibinderclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.seven.ibinderserver.IButtonControlAIDL;
import com.seven.ibinderserver.bean.ButtonInfoEntry;

import java.util.List;

/**
 * @author kuan
 * Created on 2018/7/31.
 * @description
 */
public class ButtonService extends Service {

    List<ButtonInfoEntry> mButtonInfoEntryList;

    IBinder mIBinder = new IButtonControlAIDL.Stub(){

        @Override
        public List<ButtonInfoEntry> getButtonInfoList() throws RemoteException {
            return mButtonInfoEntryList;
        }

        @Override
        public void addButtonInfoList(ButtonInfoEntry buttonInfo) throws RemoteException {
            mButtonInfoEntryList.add(buttonInfo);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }
}
