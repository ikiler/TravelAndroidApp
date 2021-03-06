package com.ikiler.travel.Base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ikiler.travel.Model.CallBack;
import com.ikiler.travel.Model.bean.User;
import com.tencent.mmkv.MMKV;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private MMKV mmkv;
    protected ProgressDialog dialog;

    protected final int CODE_FROM_BASEACTIVITY = -2;
    public final static int TAKE_PHOTO = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mmkv = MMKV.defaultMMKV();
    }
    public void setTitleBarTrans() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);

        }
    }
    public User getUser() {
        User user = new User();
        String name = mmkv.decodeString("name", "");
        String pwd = mmkv.decodeString("pwd", "");
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd))
            return null;
        user.setPwd(pwd);
        user.setName(name);
        return user;
    }

    /**
     * Toast提示
     *
     * @param text
     */
    public void showToast(String text) {
        Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    public void showDialog(String msg, final CallBack callBack){
        AlertDialog alertDialog = new AlertDialog.Builder(BaseActivity.this)
                .setMessage(msg)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.calBack(true,CODE_FROM_BASEACTIVITY);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.calBack(false,CODE_FROM_BASEACTIVITY);
                    }
                }).create();
        alertDialog.show();
    }


    public void showNetProgress(){
        if (dialog == null){
            dialog = new ProgressDialog(BaseActivity.this);
            dialog.setMessage("正在请求中......");
            dialog.setCancelable(false);
        }
        if (dialog.isShowing()){
            dialog.cancel();
        }
        dialog.show();
    }
    public void cancelNetDialog(){
        if (dialog!=null && dialog.isShowing()){
            dialog.cancel();
        }
    }
    public MMKV getMmkv() {
        return mmkv;
    }

}
