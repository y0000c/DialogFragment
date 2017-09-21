package demo.yc.dlgfragtest.dlgfrag;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import demo.yc.dlgfragtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFrag extends DialogFragment
{


    private static final String BOTTOM = "show_bottom";
    private static final String CANCEL = "out_cancel";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";
    private static final String AMOUNT = "amount";
    private static final String WIDTH = "width";

    protected boolean showBottom;//是否底部显示
    protected float amount = 0.5f;//是否点击外部取消
    protected boolean outCancel = true;//是否点击外部取消
    protected boolean isWidthMatch = false;//是否宽度充满屏幕
    @StyleRes
    private int animStyle = 0;
    @LayoutRes
    protected int layoutId;

    protected DialogInterface.OnCancelListener cancelListener;


    public BaseFrag()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle save)
    {
        super.onCreate(save);
        // 设置window相关配置
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.MyDialog);
        layoutId = getLayoutId();
        if(save != null)
        {
            showBottom = save.getBoolean(BOTTOM);
            outCancel = save.getBoolean(CANCEL);
            animStyle = save.getInt(ANIM);
            layoutId = save.getInt(LAYOUT);
            amount = save.getFloat(AMOUNT);
            isWidthMatch = save.getBoolean(WIDTH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if(layoutId != 0)
        {
            View view = inflater.inflate(layoutId, container, false);
            bindView(ViewHolder.create(view),this);
            return view;
        }
        else
            throw new IllegalArgumentException("layout can be null");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        initParams();

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BOTTOM,showBottom);
        outState.putBoolean(CANCEL,outCancel);
        outState.putInt(ANIM,animStyle);
        outState.putInt(LAYOUT,layoutId);
        outState.putBoolean(WIDTH,isWidthMatch);
    }

    protected abstract int getLayoutId();

    private  void initParams()
    {
        Window window = getDialog().getWindow();
        if(window != null)
        {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams lp = window.getAttributes();
            if(showBottom)
            {
                lp.gravity = Gravity.BOTTOM;
                if(animStyle == 0)
                    animStyle = R.style.Dialog_bottom_anim;
            }
            lp.dimAmount = amount;
            if(animStyle == 0)
            {
                animStyle = R.style.Dialog_default_anim;
            }
            if(isWidthMatch)
            {
               window.setLayout(WindowManager.LayoutParams.MATCH_PARENT
                       ,WindowManager.LayoutParams.WRAP_CONTENT);
              //  lp.width = getWidth();
            }
            window.setWindowAnimations(animStyle);
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }


    protected abstract void bindView(ViewHolder holder,BaseFrag frag);

    public BaseFrag isShowBottom(boolean isShowBottom)
    {
        this.showBottom = isShowBottom;
        return this;
    }

    public BaseFrag isOutCancel(boolean isOutCancel)
    {
        this.outCancel = isOutCancel;
        return this;
    }

    public BaseFrag show(FragmentManager manager) {
      super.show(manager, System.currentTimeMillis()+"");
        return this;
    }

    public BaseFrag setAmount(float amount)
    {
        this.amount = amount;
        return this;
    }

    public BaseFrag setAnimStyle(@StyleRes int anim)
    {
        this.animStyle = anim;
        return this;
    }

    public BaseFrag isWidthMatch(boolean isMatch)
    {
        this.isWidthMatch = isMatch;
        return this;
    }

}
