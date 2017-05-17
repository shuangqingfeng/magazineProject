package com.shuang.meiZhi.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shuang.meiZhi.R;

import butterknife.BindView;
import retrofit2.Retrofit;

/**
 * @author feng
 * @Description: dialog 弹窗
 * @date 2017/5/17
 */
public class AlertDialogView implements View.OnClickListener {
    private Context mContext;
    private boolean mCancelable;
    private int mThemeResId;
    private Dialog mDialog;
    private ImageView camera;
    private ImageView photo;
    private ImageView more;
    private ImageView close;

    public AlertDialogView(Context context) {
        this(context, false);
    }

    public AlertDialogView(Context context, boolean cancelable) {
        this(context, cancelable, 0);
    }

    public AlertDialogView(Context context, boolean cancelable, @StyleRes int themeResId) {
        mContext = context;
        mCancelable = cancelable;
        mThemeResId = themeResId;

    }

    public AlertDialogView builder() {
        mDialog = new Dialog(mContext, mThemeResId);
        mDialog.setCanceledOnTouchOutside(mCancelable);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_dialog, null);
        camera = (ImageView) view.findViewById(R.id.iv_camera);
        photo = (ImageView) view.findViewById(R.id.iv_photo);
        more = (ImageView) view.findViewById(R.id.iv_more);
        close = (ImageView) view.findViewById(R.id.iv_close);
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
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

                break;
            case R.id.iv_photo:

                break;
            case R.id.iv_more:

                break;
            case R.id.iv_close:

                break;
        }
    }
}
