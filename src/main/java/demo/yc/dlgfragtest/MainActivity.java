package demo.yc.dlgfragtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import demo.yc.dlgfragtest.dlgfrag.BaseFrag;
import demo.yc.dlgfragtest.dlgfrag.MyDialog;
import demo.yc.dlgfragtest.dlgfrag.ViewHolder;
import demo.yc.dlgfragtest.dlgfrag.ViewsClickListener;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyOrientationListener listener = new MyOrientationListener(this);
        listener.enable();
    }

    public void click1(View view)
    {
        MyDialog.init().setLayoutId(R.layout.frag_normal_layout)
                .setClickListener(new ViewsClickListener()
                {
                    @Override
                    public void onClick(ViewHolder holder, final BaseFrag frag)
                    {
                        holder.getItemView(R.id.normal_cancel).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Toast.makeText(MainActivity.this,"点击了取消",Toast.LENGTH_SHORT).show();
                                frag.dismiss();
                            }
                        });
                        holder.getItemView(R.id.normal_ok).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Toast.makeText(MainActivity.this,"点击了ok",Toast.LENGTH_SHORT).show();
                                frag.dismiss();
                            }
                        });
                    }
                })
                .setAmount(0.2f)
                .isOutCancel(true)
                .isShowBottom(false)
                .show(getSupportFragmentManager());
    }

    public void redClick(View view)
    {
        MyDialog.init()
                .setLayoutId(R.layout.frag_red_layout)
                .setClickListener(new ViewsClickListener(){
                    @Override
                    public void onClick(ViewHolder holder, final BaseFrag frag)
                    {
                        holder.getItemView(R.id.red_close).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                frag.dismiss();
                            }
                        });
                        ImageView image =  holder.getItemView(R.id.red_src);
                        Glide.with(frag.getContext()).applyDefaultRequestOptions(new RequestOptions().priority(Priority.LOW)).load(R.drawable.red_packet_bg).into(image);
                        holder.getItemView(R.id.red_open).setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                Toast.makeText(MainActivity.this,"真遗憾，是个空的",Toast.LENGTH_LONG).show();
                                frag.dismiss();
                            }
                        });
                    }
                })
                .setAmount(0.6f)
                .show(getSupportFragmentManager());
    }

    public void loadClick(View view)
    {
        Toast.makeText(MainActivity.this,"1o秒后自动取消加载"
                ,Toast.LENGTH_SHORT).show();
        final MyDialog dialog = MyDialog.init();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(MainActivity.this,"自动取消加载",Toast.LENGTH_SHORT).show();
                    dialog.dismissAllowingStateLoss();
            }
        };

        dialog.setLayoutId(R.layout.frag_loading_layout)
                .isOutCancel(true)
                .show(getSupportFragmentManager());
        handler.postDelayed(runnable,1000*10);

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                dialog.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        Toast.makeText(MainActivity.this,"手动取消加载"
                                ,Toast.LENGTH_SHORT).show();
                        handler.removeCallbacks(runnable);
                    }
                });
            }
        });

    }

    public void bottomListClick(View view)
    {
        MyDialog.init().setLayoutId(R.layout.frag_bottom_list_layout)
                .setClickListener(new ViewsClickListener(){
                    @Override
                    public void onClick(ViewHolder holder, BaseFrag frag)
                    {
                        ListView listView = holder.getItemView(R.id.bottom_list);
                        initListView(listView,frag);
                    }
                })
                .isShowBottom(true)
                .isWidthMatch(true)
                .show(getSupportFragmentManager());
    }

    // 可以做listView任意的事情
    // 设置这种属性，adapter
    private void initListView(ListView listView, final BaseFrag frag)
    {
        String[] datas = getResources().getStringArray(R.array.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MainActivity.this,
                        parent.getAdapter().getItem(position).toString()
                        ,Toast.LENGTH_SHORT).show();
                frag.dismiss();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        int orientation = newConfig.orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Log.w("screen","配置方法被调用了"+"---》现在是竖屏");

        }else
        {
            Log.w("screen","配置方法被调用了"+"---》现在是横屏");
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.w("screen","onPause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.w("screen","onResume");
    }

    class MyOrientationListener extends OrientationEventListener
    {
        public MyOrientationListener(Context context)
        {
            super(context);
        }

        @Override
        public void onOrientationChanged(int rotation)
        {
            Log.w("screen","rotation = "+rotation);
            // 设置为竖屏
            if (((rotation >= 0) && (rotation <= 45)) || (rotation >= 315)) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            // 设置为横屏
            if(((rotation >= 225) && (rotation <= 315))) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            //设置为横屏（逆向）
            if(((rotation >= 45) && (rotation <= 135))) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        }
    }

}



