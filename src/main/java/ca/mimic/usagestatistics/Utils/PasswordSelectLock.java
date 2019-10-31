package ca.mimic.usagestatistics.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.omadahealth.lollipin.lib.managers.AppLockActivity;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.takwolf.android.lock9.Lock9View;

import ca.mimic.usagestatistics.R;
import ca.mimic.usagestatistics.Settings;

public class PasswordSelectLock extends AppLockActivity {
    Lock9View lock9View;
    SharedPreference sharedPreference;
    Context context;
    Button forgetPassword;
    public static boolean check = true;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
//        setContentView(R.layout.activity_password_select_lock);
//        sharedPreference = new SharedPreference();
//        forgetPassword = (Button) findViewById(R.id.forgetPassword);
//        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
//        check = false;
//        lock9View.setCallBack(new Lock9View.CallBack() {
//            @Override
//            public void onFinish(String password) {
//                if (password.matches(sharedPreference.getPassword(context))) {
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng. Thử lại", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    //    @Override
//    public int getContentView() {
//        return R.layout.activity_pin;
//    }

    @Override
    public int getPinLength() {
        return 4;
    }

    @Override
    public void showForgotDialog() {

    }

    @Override
    public void onPinFailure(int attempts) {

    }

    @Override
    public void onPinSuccess(int attempts) {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setCheck(true);
    }

    @Override
    protected void onStart() {
        GoogleAnalytics.getInstance(context).reportActivityStart(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        GoogleAnalytics.getInstance(context).reportActivityStop(this);
        super.onStop();
    }
}

//public class PasswordSelectLock extends AppCompatActivity {
//    Lock9View lock9View;
//    SharedPreference sharedPreference;
//    Context context;
//    Button forgetPassword;
//    public static boolean check = true;
//
//    public boolean isCheck() {
//        return check;
//    }
//
//    public void setCheck(boolean check) {
//        this.check = check;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        context = getApplicationContext();
//        setContentView(R.layout.activity_password_select_lock);
//        sharedPreference = new SharedPreference();
//        forgetPassword = (Button) findViewById(R.id.forgetPassword);
//        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
//        check = false;
//        lock9View.setCallBack(new Lock9View.CallBack() {
//            @Override
//            public void onFinish(String password) {
//                if (password.matches(sharedPreference.getPassword(context))) {
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng. Thử lại", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        setCheck(true);
//    }
//
//    @Override
//    protected void onStart() {
//        GoogleAnalytics.getInstance(context).reportActivityStart(this);
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        GoogleAnalytics.getInstance(context).reportActivityStop(this);
//        super.onStop();
//    }
//}
