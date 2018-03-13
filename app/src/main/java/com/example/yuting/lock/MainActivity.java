package com.example.yuting.lock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {
    protected final int UnRegistration = 0;
    protected final int LockScreen = 1;
    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;
    private Button btn_Lock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onPause() {
        UnReg_Lock(UnRegistration);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    private void init() {
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, AdminReceiver.class);
        btn_Lock = (Button) findViewById(R.id.button1);
        btn_Lock.setOnClickListener(lock);
        Registration();

    }

    private void Registration() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "程式的描述");
        startActivityForResult(intent, 0);
    }

    private void UnReg_Lock(int sltNum) {
        if (devicePolicyManager.isAdminActive(componentName)) {
            switch (sltNum) {
                case UnRegistration:
                    devicePolicyManager.removeActiveAdmin(componentName);
                    break;
                case LockScreen:
                    devicePolicyManager.lockNow();
                    break;
            }
        }
    }

    private Button.OnClickListener lock = new Button.OnClickListener() {
        public void onClick(View arg0) {
            UnReg_Lock(LockScreen);
        }
    };
}