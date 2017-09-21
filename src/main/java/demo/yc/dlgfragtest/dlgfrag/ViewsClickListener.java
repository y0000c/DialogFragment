package demo.yc.dlgfragtest.dlgfrag;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: YC
 * @date: 2017/9/20 0020
 * @time: 16:47
 * @detail:
 */

public class ViewsClickListener implements Parcelable
{
    public ViewsClickListener()
    {
    }
    protected ViewsClickListener(Parcel in)
    {
    }

    public static final Creator<ViewsClickListener> CREATOR = new Creator<ViewsClickListener>()
    {
        @Override
        public ViewsClickListener createFromParcel(Parcel in)
        {
            return new ViewsClickListener(in);
        }

        @Override
        public ViewsClickListener[] newArray(int size)
        {
            return new ViewsClickListener[size];
        }
    };

    public void onClick(ViewHolder holder, BaseFrag frag)
    {

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
    }


}
