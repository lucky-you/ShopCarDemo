package com.json.car;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 *  自定义dialog  builder模式
 */

public class CustomDialog extends Dialog{


    public CustomDialog(Context context) {
        super(context, R.style.myDialog);
    }

    public static class Builder{
        private Context context;
        private String content;//内容
        private String leftCancle;//左边取消按钮显示的文字
        private String rightSure;//右边确定按钮显示的文字

        //控件
        private TextView tvContent;
        private Button btnLeft;
        private Button btnRight;
        //设置监听事件
        private View.OnClickListener leftListener;
        private View.OnClickListener rightListener;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public Builder setLeftText(String leftText){
            this.leftCancle = leftText;
            return this;
        }

        public Builder setRightText(String rightText){
            this.rightSure = rightText;
            return this;
        }

        public Builder setLeftOnclick(View.OnClickListener leftListener){
            this.leftListener = leftListener;
            return this;
        }

        public Builder setRightOnClick(View.OnClickListener rightListener){
            this.rightListener = rightListener;
            return this;
        }

        public CustomDialog create(){
            CustomDialog dialog = new CustomDialog(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            btnLeft = (Button) view.findViewById(R.id.btnLeft);
            btnRight = (Button) view.findViewById(R.id.btnRight);

            if(!TextUtils.isEmpty(content)){
                tvContent.setText(content);
            }
            if(!TextUtils.isEmpty(leftCancle)){
                btnLeft.setText(leftCancle);
            }
            if(!TextUtils.isEmpty(rightSure)){
                btnRight.setText(rightSure);
            }

            if(leftListener != null){
                btnLeft.setOnClickListener(leftListener);
            }
            if(rightListener != null){
                btnRight.setOnClickListener(rightListener);
            }

            //根据屏幕宽来设置dialog的宽高
            Window window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams params = window.getAttributes();
            //获取手机屏幕宽度
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();

            params.width = (int) (metrics.widthPixels * 0.8);
            window.setAttributes(params);

            window.setGravity(Gravity.CENTER);
            //将布局加载到dialog上
            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(true);

            return dialog;
        }

    }
}
