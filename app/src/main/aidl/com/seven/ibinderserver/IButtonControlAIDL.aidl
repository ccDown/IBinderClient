// IButtonControlAIDL.aidl
package com.seven.ibinderserver;

// Declare any non-default types here with import statements
import com.seven.ibinderserver.bean.ButtonInfoEntry;

interface IButtonControlAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */


    List<ButtonInfoEntry> getButtonInfoList();

    void addButtonInfoList(in ButtonInfoEntry buttonInfo);
}
