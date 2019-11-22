//package ca.mimic.usagestatistics.Activity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.widget.Button;
//
//import com.github.omadahealth.lollipin.lib.managers.AppLockActivity;
//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.takwolf.android.lock9.Lock9View;
//
//import ca.mimic.usagestatistics.Activity.Settings;
//import ca.mimic.usagestatistics.Utils.SharedPreference;
//
//public class PasswordSelectLock extends AppLockActivity {
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
////        setContentView(R.layout.activity_password_select_lock);
//        sharedPreference = new SharedPreference();
////        forgetPassword = (Button) findViewById(R.id.forgetPassword);
////        lock9View = (Lock9View) findViewById(R.id.lock_9_view);
////        check = false;
////        lock9View.setCallBack(new Lock9View.CallBack() {
////            @Override
////            public void onFinish(String password) {
////                if (password.matches(sharedPreference.getPassword(context))) {
////                    finish();
////                } else {
////                    Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng. Thử lại", Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//
//    }
//
//    //    @Override
////    public int getContentView() {
////        return R.layout.activity_pin;
////    }
//
//    @Override
//    public int getPinLength() {
//        return 4;
//    }
//
//    @Override
//    public void showForgotDialog() {
//
//    }
//
//    @Override
//    public void onPinFailure(int attempts) {
//
//    }
//
//    @Override
//    public void onPinSuccess(int attempts) {
//        Intent i = new Intent(this, Settings.class);
//        startActivity(i);
//        finish();
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        sharedPreference.savePasswordApp(this,true);
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

package ca.mimic.usagestatistics.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.takwolf.android.lock9.Lock9View;

import java.util.List;

import ca.mimic.usagestatistics.R;
import ca.mimic.usagestatistics.Utils.AppLockConstants;
import ca.mimic.usagestatistics.Utils.SharedPreference;

public class PasswordSelectLockAgain extends AppCompatActivity {
    SharedPreference sharedPreference;
    Context context;
    Button forgetPassword;
    TextView tvDrawPassword;

    PatternLockView patternLockViewAgain;
    String password;
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
        setContentView(R.layout.activity_password_select_lock_again);
        sharedPreference = new SharedPreference();
        forgetPassword = (Button) findViewById(R.id.forgetPassword);
        patternLockViewAgain = findViewById(R.id.patternViewAgain);
        tvDrawPassword = findViewById(R.id.tvDrawPassword);
        getSupportActionBar().hide();

        if(getIntent()!=null){
            password = getIntent().getStringExtra(AppLockConstants.EXTRA_PASSWORD_APP);
        }


        check = false;
        patternLockViewAgain.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                password = PatternLockUtils.patternToString(patternLockViewAgain, pattern);
                if(PatternLockUtils.patternToString(patternLockViewAgain, pattern).equals(password)){
                    finish();
                }
//                patternLockViewAgain.setVisibility(View.VISIBLE);
//                tvDrawPassword.setText(R.string.draw_password_again);
//                //                sharedPreference.savePasswordApp(PasswordSelectLock.this, password);

            }

            @Override
            public void onCleared() {

            }
        });
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