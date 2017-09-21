package demo.yc.dlgfragtest.dlgfrag;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

/**
 * @author: YC
 * @date: 2017/9/20 0020
 * @time: 16:30
 * @detail:
 */

public class MyDialog extends BaseFrag
{
    private static final String LISTENER = "listener";

    @LayoutRes
    private int layoutId;
    ViewsClickListener listener;

    public static MyDialog init()
    {
        return new MyDialog();
    }

    @Override
    protected int getLayoutId()
    {
        return layoutId;
    }

    @Override
    protected void bindView(ViewHolder holder, BaseFrag frag)
    {
        if(listener != null)
        {
            listener.onClick(holder,frag);
        }

    }

    public MyDialog setLayoutId(@LayoutRes int layoutId)
    {
        this.layoutId = layoutId;
        return this;
    }


    public MyDialog setClickListener(ViewsClickListener listener)
    {
        this.listener = listener;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle save)
    {
        super.onCreate(save);
        if(save != null)
            listener = save.getParcelable(LISTENER);

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LISTENER,listener);

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        listener = null;
    }
}
