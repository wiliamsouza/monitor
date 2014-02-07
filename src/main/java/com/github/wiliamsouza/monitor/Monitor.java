/*
 * Copyright 2011 Ladislav Thon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.wiliamsouza.monitor;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;


public class Monitor {
    // you can call AndroidDebugBridge.init() and terminate() only once
    // createBridge() and disconnectBridge() can be called as many times as you want

    public void init() {
        AndroidDebugBridge.init(false);
    }

    public void finish() {
        AndroidDebugBridge.terminate();
    }

    public void usingDeviceChangeListener() throws Exception {
        AndroidDebugBridge.addDeviceChangeListener(new AndroidDebugBridge.IDeviceChangeListener() {
            // this gets invoked on another thread, but you probably shouldn't count on it
            public void deviceConnected(IDevice device) {
                System.out.println("Device connected " + device.getSerialNumber());
            }

            public void deviceDisconnected(IDevice device) {
                System.out.println("device disconnected " + device.getSerialNumber());
            }

            public void deviceChanged(IDevice device, int changeMask) {
                System.out.println("Device changed " + device.getSerialNumber());
            }
        });

        AndroidDebugBridge adb = AndroidDebugBridge.createBridge("adb", true);

        Thread.sleep(1000);
        if (!adb.isConnected()) {
            System.out.println("Couldn't connect to ADB server");
        }

        //AndroidDebugBridge.disconnectBridge();
    }

    public static void main(String[] args) throws Exception {
        Monitor monitor = new Monitor();

        monitor.init();

        System.out.println("Get information about devices asynchronously\n");
        monitor.usingDeviceChangeListener();

        //monitor.finish();
    }
}
