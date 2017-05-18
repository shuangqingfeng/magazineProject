package com.shuang.meiZhi.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.provider.SyncStateContract;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Tag;
import com.orhanobut.logger.Logger;
import com.shuang.meiZhi.Constants;
import com.shuang.meiZhi.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import retrofit2.Retrofit;

/**
 * @author feng
 * @Description: dialog 弹窗
 * @date 2017/5/17
 */
public class AlertDialogView implements View.OnClickListener {
    private static final int CAREMA = 1;

    private Context mContext;
    private boolean mCancelable;
    private int mThemeResId = R.style.dialogStyle;
    private Dialog mDialog;
    private TextView camera;
    private TextView photo;
    private TextView more;
    private ImageView close;
    private DialogChildAtViewClickListener mDialogChildAtViewClickListener;

    public AlertDialogView(Context context) {
        this(context, false);
    }

    public AlertDialogView(Context context, boolean cancelable) {
        this(context, cancelable, R.style.dialogStyle);
    }

    public AlertDialogView(Context context, boolean cancelable, @StyleRes int themeResId) {
        mContext = context;
        mCancelable = cancelable;
        mThemeResId = themeResId;
        RxBus.get().register(this);
    }

    public AlertDialogView builder() {
        mDialog = new Dialog(mContext, mThemeResId);
        mDialog.setCanceledOnTouchOutside(mCancelable);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_dialog, null);
        camera = (TextView) view.findViewById(R.id.iv_camera);
        photo = (TextView) view.findViewById(R.id.iv_photo);
        more = (TextView) view.findViewById(R.id.iv_more);
        close = (ImageView) view.findViewById(R.id.iv_close);
        camera.setOnClickListener(this);
        photo.setOnClickListener(this);
        more.setOnClickListener(this);
        close.setOnClickListener(this);
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        view.measure(0, 0);
        lp.width = (int) mContext.getResources().getDisplayMetrics().widthPixels;
        lp.height = view.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        window.setAttributes(lp);
        return this;
    }

    public void show() {
        mDialog.show();


    }

    public void dismiss() {
        mDialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_camera:
                mDialogChildAtViewClickListener.clickCarmera();
                break;
            case R.id.iv_photo:
                mDialogChildAtViewClickListener.clickPhoto();
                break;
            case R.id.iv_more:
                mDialogChildAtViewClickListener.clickMore();
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    public void setDialogChildAtViewClickListener(DialogChildAtViewClickListener dialogChildAtViewClickListener) {
        mDialogChildAtViewClickListener = dialogChildAtViewClickListener;
    }

    public interface DialogChildAtViewClickListener {
        void clickCarmera();

        void clickPhoto();

        void clickMore();
    }
}
