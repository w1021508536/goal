package com.pi.small.goal.my.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.weight.VirtualKeyboardView;


/**
 * 作者 wjz
 * 说明
 * 创建时间 2016/7/1
 * 公司名称 百迅科技
 * 描述
 */
public class KeyBoardDialog extends AlertDialog {
    private final Context context;
    private VirtualKeyboardView vkbUpdataPass;
    private LinearLayout llPass;

    private GridView gridView;
    private TextView[] tvList;
    private ImageView[] imgList;
    private int currentIndex = -1;    //用于记录当前输入密码格位置
    private OnKeyBoardLinener linener;
    //String title;


    public KeyBoardDialog(Context context) {
        super(context);
        this.context = context;
    }

    public interface OnKeyBoardLinener {
        void onKey(String key);
    }

    public void setOnKeyBoardLinener(OnKeyBoardLinener linener) {
        this.linener = linener;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_keyboard);

        initDialog();


//        lianxi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + phones));
//                context.startActivity(intent);
//            }
//        });
    }

    private void initDialog() {
        Window window = this.getWindow();
        //    window.setWindowAnimations(R.style.dialog_anim2);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(params);

        vkbUpdataPass = (VirtualKeyboardView) window.findViewById(R.id.vkb_updataPass);
        llPass = (LinearLayout) window.findViewById(R.id.ll_pass);
        LinearLayout ll_key = (LinearLayout) window.findViewById(R.id.ll_key);


        int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - Utils.dip2px(context, context.getResources().getDimension(R.dimen.top_left));

        float v = width / 6.0f;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_key.getLayoutParams();
        layoutParams.height = (int) v;
        ll_key.setLayoutParams(layoutParams);

        gridView = vkbUpdataPass.getGridView();
        measureListViewHeight(gridView);

        tvList = new TextView[6];
        imgList = new ImageView[6];
        tvList[0] = (TextView) findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) findViewById(R.id.img_pass6);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 11 && position != 9) {    //点击0~9按钮
                    if (currentIndex >= -1 && currentIndex < 5) {      //判断输入位置————要小心数组越界

                        ++currentIndex;
                        tvList[currentIndex].setText(vkbUpdataPass.getValueList().get(position).get("name"));

                        tvList[currentIndex].setVisibility(View.INVISIBLE);
                        imgList[currentIndex].setVisibility(View.VISIBLE);
                    }
                } else {
                    if (position == 11) {      //点击退格键
                        if (currentIndex - 1 >= -1) {      //判断是否删除完毕————要小心数组越界

                            tvList[currentIndex].setText("");

                            tvList[currentIndex].setVisibility(View.VISIBLE);
                            imgList[currentIndex].setVisibility(View.INVISIBLE);

                            currentIndex--;
                        }
                    }
                }
            }
        });
        setOnFinishInput();
    }

    //设置监听方法，在第6位输入完成后触发
    public void setOnFinishInput() {


        tvList[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 1) {

                    String strPassword = "";     //每次触发都要先将strPassword置空，再重新获取，避免由于输入删除再输入造成混乱

                    for (int i = 0; i < 6; i++) {
                        strPassword += tvList[i].getText().toString().trim();
                    }
                    System.out.println("strPassword :" + strPassword);
                    //           verifyPass(strPassword);
                    if (linener != null) {
                        linener.onKey(strPassword);
                    }
                }
            }
        });
    }

    public void measureListViewHeight(final AbsListView listView) {
        final ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        listView.post(new Runnable() {
            @Override
            public void run() {
                int totalHeight = 0;
                int count = listAdapter.getCount();
                //TODO 这里可以去获取每一列最高的一个
                View listItem = listAdapter.getView(0, null, listView);

                listItem.measure(0, 0);
                if (listView instanceof GridView) {
                    int columns = ((GridView) listView).getNumColumns();
                    int rows = count % columns != 0 ? 1 : 0;
                    rows += count / columns;
                    totalHeight += listItem.getMeasuredHeight() * rows;
                } else if (listView instanceof ListView) {
                    for (int i = 0; i < count; i++) {
                        listItem = listAdapter.getView(i, null, listView);
                        listItem.measure(0, 0);
                        totalHeight += listItem.getMeasuredHeight() + ((ListView) listView).getDividerHeight() * (listAdapter.getCount() - 1);
                    }
                }
                handler.sendEmptyMessage(totalHeight);
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            llPass.setPadding(0, 0, 0, msg.what / 2);

            return false;
        }
    });
}
