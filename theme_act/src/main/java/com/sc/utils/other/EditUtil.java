package com.sc.utils.other;

import android.view.View;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/1/26.
 */
public class EditUtil {

    /**
     * 设置输入法显示
     * @param edit
     * @return
     */
    public static EditText setEdit(EditText edit) {
        edit.setVisibility(View.VISIBLE);
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        edit.findFocus();
        return edit;
    }

}
