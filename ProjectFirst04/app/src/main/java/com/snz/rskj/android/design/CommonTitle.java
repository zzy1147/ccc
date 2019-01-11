package com.snz.rskj.android.design;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snz.rskj.android.R;


/**
 * Created by power on 2018/9/26.
 */

public class CommonTitle extends RelativeLayout {

    private final String mTitle;
    private final boolean mCanBack;
    private final int mMoreImg,mBackImage;
    private final String mMoreText;
    public ImageView mMoreImage;
    public TextView mMoreText1;
    public TextView mTvTitle;
    public ImageView mBackBtn;
    public ImageView mReghtBtn;
    private RelativeLayout mTitleRl;
    private final int mBackColor;

    public CommonTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonTitle, 0, 0);
        try {
            mTitle = ta.getString(R.styleable.CommonTitle_titleText);
            mCanBack = ta.getBoolean(R.styleable.CommonTitle_canBack, false);
            mMoreImg = ta.getResourceId(R.styleable.CommonTitle_moreImg, 0);
            mBackImage = ta.getResourceId(R.styleable.CommonTitle_backImage, 0);
            mMoreText = ta.getString(R.styleable.CommonTitle_moreText);
            mBackColor = ta.getColor(R.styleable.CommonTitle_backColor, ContextCompat.getColor(context, R.color.default_color));
            setUpView(context);
        } finally {
            ta.recycle();
        }
    }

    public void setmBackBtn(ImageView mBackBtn) {
        this.mBackBtn = mBackBtn;
    }


    public void setTitleContent(Context content, String string){
        setUpView(content);
        mTvTitle.setTextColor(Color.BLUE);
        mTvTitle.setText("nohaodfasfadf");
    }

    public void setmMoreImage(int img){
        mMoreImage.setImageResource(img);
    }

    public  void setmReghtBtn(int img){
        mReghtBtn.setImageResource(img);

    }

    public void setUpView(Context context){
        mTitleRl = findViewById(R.id.title_rl);
        mTitleRl.setBackgroundColor(mBackColor);
        mTvTitle = (TextView) findViewById(R.id.title);
        mTvTitle.setText(mTitle);
        mBackBtn = findViewById(R.id.back_image);
        mBackBtn.setImageResource(mBackImage);
        mBackBtn.setVisibility(mCanBack ? VISIBLE : INVISIBLE);
        if (mCanBack){
            mBackBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).finish();
                }
            });
        }
        mMoreImage = findViewById(R.id.img_more);
        mReghtBtn = findViewById(R.id.title_reght_img);

        mMoreImage.setImageResource(mMoreImg);
        mMoreText1 = findViewById(R.id.txt_more);
        mMoreText1.setText(mMoreText);
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }
}
