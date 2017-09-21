package demo.yc.dlgfragtest.dlgfrag;

import android.util.SparseArray;
import android.view.View;

/**
 * @author: YC
 * @date: 2017/9/20 0020
 * @time: 16:18
 * @detail:
 */

public class ViewHolder
{
    private View convertView;
    private SparseArray<View> itemViews;

    private ViewHolder(View view)
    {
        convertView = view;
        itemViews = new SparseArray<>();
    }

    public static ViewHolder create(View view)
    {
        return new ViewHolder(view);
    }

    public <T extends View> T getItemView(int itemId)
    {
        View view  = itemViews.get(itemId);
        if(view == null)
        {
            view = convertView.findViewById(itemId);
            itemViews.put(itemId,view);
        }
        return (T)view;
    }


}
