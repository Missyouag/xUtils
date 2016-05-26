package com.sc.utils.SweetAlert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.R;

/**
 * Created by Missyouag on 2015/9/6.
 */
public class SweetAlertToast {
    public static SweetAlertToast sweetAlertToast;
    private final View v;
    private final TextView t;
    private Toast result;
    private long time;
    private CharSequence oldMsg;

    private SweetAlertToast(Context context) {
        result = new Toast(context);
        result.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflate = LayoutInflater.from(context);
        v = inflate.inflate(R.layout.alert_toast, null);
        t = (TextView) v.findViewById(R.id.title_text);
//        v.setAnimation(OptAnimationLoader.loadAnimation(context, R.anim.modal_in));
        result.setView(v);
    }

    public static Toast makeText(Context context, CharSequence text) {
        //连弾..
        if (sweetAlertToast == null) {
            sweetAlertToast = new SweetAlertToast(context);
        }
        sweetAlertToast.SetText(text);
        return sweetAlertToast.result;
    }

    public static void cancel() {
        if (sweetAlertToast != null && sweetAlertToast.result != null) {
            sweetAlertToast.result.cancel();
        }
    }

    private void SetText(CharSequence text) {
        if (text != null && !text.equals(oldMsg)) {
            if (System.currentTimeMillis() - sweetAlertToast.time < 500) {
                text = new StringBuffer(oldMsg).append('\n').append(text);
            }
            oldMsg = text;
            t.setText(text);
            time = System.currentTimeMillis();
        }
    }

}
