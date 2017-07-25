package com.small.small.goal.my.guess.elevenchoosefive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.elevenchoosefive.entity.ChooseOvalEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Combine;
import com.small.small.goal.utils.EditTextHeightUtil;
import com.small.small.goal.utils.GameUtils;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.HuiFuDialog2;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/27 17:38
 * 修改：
 * 描述： 选完 下注多少的界面
 **/
public class ChooseAddMoneyActivity extends BaseActivity {


    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.rl_iAdd)
    RelativeLayout rlIAdd;
    @InjectView(R.id.rl_jAdd)
    RelativeLayout rlJAdd;
    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.tv_myDou)
    TextView tvMyDou;
    @InjectView(R.id.tv_zhui_hint)
    TextView tvZhuiHint;
    @InjectView(R.id.img_zhuiDelete)
    ImageView imgZhuiDelete;
    @InjectView(R.id.etv_zhuiNums)
    EditText etvZhuiNums;
    @InjectView(R.id.img_zhuiAdd)
    ImageView imgZhuiAdd;
    @InjectView(R.id.tv_qi_hint)
    TextView tvQiHint;
    @InjectView(R.id.tv_tou_hint)
    TextView tvTouHint;
    @InjectView(R.id.img_touDelete)
    ImageView imgTouDelete;
    @InjectView(R.id.etv_touNums)
    EditText etvTouNums;
    @InjectView(R.id.img_touAdd)
    ImageView imgTouAdd;
    @InjectView(R.id.tv_bei_hint)
    TextView tvBeiHint;
    @InjectView(R.id.tv_go)
    TextView tvGo;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    private List<Map<Integer, List<ChooseOvalEntity>>> data;
    private MyAdapter myAdapter;
    private int zhuNums;
    private HuiFuDialog2 dialog;
    private int min;
    public static final int MAX = 11;
    private String[] types;
    private int[] codes;            //彩票的代码
    //    private int intent_flag;
//    private int maxRed;
//    private int maxBlue;

    EditTextHeightUtil editTextHeightUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_addmoney);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
//        EditTextHeightUtil.assistActivity(this);
        editTextHeightUtil = new EditTextHeightUtil(this, 1);
    }

    @Override
    public void initData() {
        super.initData();
        rightImageInclude.setVisibility(View.GONE);
        data = CacheUtil.getInstance().getElevenChooseFive();
        min = getIntent().getIntExtra(KeyCode.INTENT_MIN, 1);
        nameTextInclude.setText("11选5");

        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        dialog = new HuiFuDialog2(this, "", new HuiFuDialog2.onTvOKClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(ChooseAddMoneyActivity.this, ChooseMainActivity.class));
                finish();

            }
        });

        rlIAdd.setOnClickListener(this);
        rlJAdd.setOnClickListener(this);

        imgZhuiDelete.setOnClickListener(this);
        imgZhuiAdd.setOnClickListener(this);
        imgTouDelete.setOnClickListener(this);
        imgTouAdd.setOnClickListener(this);
        tvGo.setOnClickListener(this);


        etvTouNums.addTextChangedListener(new MyTextWatch(etvTouNums.getId()));
        etvZhuiNums.addTextChangedListener(new MyTextWatch(etvZhuiNums.getId()));


        types = new String[]{"", "前一", "任二", "任三", "任四", "任五", "任六", "任七", "任八", "前二直选", "前二组选", "前三直选", "前三组选"};
        codes = new int[]{0, 31, 22, 23, 24, 25, 26, 27, 28, 32, 302, 33, 303};
        setZhu();
        setTvGo();
    }

    private void setZhu() {
        zhuNums = 0;

        for (Map<Integer, List<ChooseOvalEntity>> one : data) {

            Set<Integer> integers = one.keySet();
            for (Integer i : integers) {
                List<ChooseOvalEntity> chooseOvalEntities = one.get(i);
                if (i == 9) {
                    int nums = 0;
                    for (ChooseOvalEntity entity : chooseOvalEntities) {
                        if (!entity.getContent().equals(","))
                            nums++;
                    }
                    zhuNums += Combine.getNumber(2, nums);
                } else if (i == 11) {
                    int nums = 0;
                    for (ChooseOvalEntity entity : chooseOvalEntities) {
                        if (!entity.getContent().equals(","))
                            nums++;
                    }
                    zhuNums += Combine.getNumber(3, nums);
                } else if (i == 10) {
                    zhuNums += Combine.getNumber(2, chooseOvalEntities.size());
                } else if (i == 12) {
                    zhuNums += Combine.getNumber(3, chooseOvalEntities.size());
                } else {
                    zhuNums += Combine.getNumber(i, chooseOvalEntities.size());
                }
            }

        }
        tvMyDou.setText("我的金豆： 800  " + "您选择了" + zhuNums + "注");


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_iAdd:
                startActivity(new Intent(this, ChooseMainActivity.class));
                finish();
                break;
            case R.id.rl_jAdd:
                int[] ints = randomJ(1, MAX, min);


                List<ChooseOvalEntity> addData = new ArrayList<>();

                for (int i = 0; i < ints.length; i++) {
                    int red = ints[i];
                    if (min == 9 || min == 11) {
                        if (i == ints.length - 1)
                            addData.add(new ChooseOvalEntity(red >= 10 ? red + "" : "0" + red + "", false, 0));
                        else {
                            addData.add(new ChooseOvalEntity(red >= 10 ? red + "" : "0" + red + "", false, 0));
                            addData.add(new ChooseOvalEntity(",", false, 0));
                        }
                    } else
                        addData.add(new ChooseOvalEntity(red >= 10 ? red + "" : "0" + red, false, 0));
                }

                Map<Integer, List<ChooseOvalEntity>> map = new HashMap<>();
                map.put(min, addData);
                data.add(map);
                myAdapter.notifyDataSetChanged();
                setZhu();
                setTvGo();
                break;

            case R.id.img_zhuiDelete:
                if (etvZhuiNums.getText().toString().equals("1")) return;

                etvZhuiNums.setText((Integer.valueOf(etvZhuiNums.getText().toString()) - 1) + "");
                etvZhuiNums.setSelection(etvZhuiNums.getText().length());
                break;
            case R.id.img_zhuiAdd:
                etvZhuiNums.setText((Integer.valueOf(etvZhuiNums.getText().toString()) + 1) + "");
                etvZhuiNums.setSelection(etvZhuiNums.getText().length());
                break;
            case R.id.img_touDelete:
                if (etvTouNums.getText().toString().equals("1")) return;
                etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) - 1) + "");
                etvTouNums.setSelection(etvTouNums.getText().length());
                break;
            case R.id.img_touAdd:
                etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) + 1) + "");
                etvTouNums.setSelection(etvTouNums.getText().length());
                break;
            case R.id.tv_go:
                //          dialog.show();
                postTouZhu();
                break;
        }
    }

    /**
     * 彩票投注
     * create  wjz
     **/
    private void postTouZhu() {

        String context = "";
        for (Map<Integer, List<ChooseOvalEntity>> one : data) {
            Set<Integer> keys = one.keySet();

            for (Integer i : keys) {
                if (!one.get(i).get(0).getContent().equals(",")) {
                    context += codes[i] + ":";
                    List<ChooseOvalEntity> list = one.get(i);
                    for (int y = 0; y < list.size(); y++) {
                        if (list.get(y).getContent().equals(",")) {
                            context += "+";
                        } else if (y != list.size() - 1) {
                            if (list.get(y + 1).getContent().equals(",")) {
                                context += list.get(y).getContent();
                            } else {
                                context += list.get(y).getContent() + ",";
                            }

                        } else {
                            context += list.get(y).getContent() + ";";
                        }

                    }
                }
            }
        }

        Integer tou = Integer.valueOf(etvTouNums.getText().toString());
        Integer zhui = Integer.valueOf(etvZhuiNums.getText().toString());
        RequestParams requestParams = new RequestParams(Url.Url + "/wager");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("content", context);
        requestParams.addBodyParameter("number", "1");
        requestParams.addBodyParameter("multifold", tou + "");  //倍数
        requestParams.addBodyParameter("bean", (tou * zhui * zhuNums * 20) + "");          //金豆
        requestParams.addBodyParameter("linkId", "");
        requestParams.addBodyParameter("type", "4");
        requestParams.addBodyParameter("name", "11选5");
        requestParams.addBodyParameter("expect", "");
        requestParams.addBodyParameter("code", "sd11x5");
        requestParams.addBodyParameter("rule", "");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                boolean b = GameUtils.CallResultOK(result);
                Utils.showToast(ChooseAddMoneyActivity.this, GameUtils.getMsg(result));
                if (b) {
                    CacheUtil.getInstance().closeElevenChooseFive();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 生成随机数
     * create  wjz
     **/
    public static int[] randomJ(int min, int max, int nums) {

        switch (nums) {
            case 9:
            case 10:
                nums = 2;
                break;
            case 11:
            case 12:
                nums = 3;
                break;
        }


        int[] intRet = new int[nums];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        while (count < intRet.length) {
            intRd = min + (int) (Math.random() * max);
            for (int i = 0; i < count; i++) {
                if (intRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                intRet[count] = intRd;
                count++;
            }
        }
        return intRet;
    }

    class MyTextWatch implements TextWatcher {

        private final int id;

        public MyTextWatch(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (id) {
                case R.id.etv_touNums:
                    setTvGo();
                    break;
                case R.id.etv_zhuiNums:
                    setTvGo();
                    break;
            }
        }
    }

    private void setTvGo() {
        if ("".equals(etvTouNums.getText().toString())) return;
        if ("".equals(etvZhuiNums.getText().toString())) return;

        Integer tou = Integer.valueOf(etvTouNums.getText().toString());
        Integer zhui = Integer.valueOf(etvZhuiNums.getText().toString());

        tvGo.setText("立即投注  " + (tou * zhui * zhuNums * 20) + "金豆");

    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(ChooseAddMoneyActivity.this).inflate(R.layout.item_choose_addmoney, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Map<Integer, List<ChooseOvalEntity>> map = data.get(position);
            List<ChooseOvalEntity> oneData = new ArrayList<>();
            for (Map.Entry<Integer, List<ChooseOvalEntity>> eny : map.entrySet()) {

                vh.tvType.setText(types[eny.getKey()]);
                oneData = map.get(eny.getKey());
            }

            if (oneData.size() > 0) {


                String str = "";
                for (int i = 0; i < oneData.size(); i++) {
                    ChooseOvalEntity entity = oneData.get(i);
                    str += i == oneData.size() - 1 ? entity.getContent() : entity.getContent() + " ";
                }

                vh.tvRed.setText(str);
            }

            vh.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                    setZhu();
                    setTvGo();
                }
            });

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.tv_type)
            TextView tvType;
            @InjectView(R.id.tv_red)
            TextView tvRed;
            @InjectView(R.id.img_delete)
            ImageView imgDelete;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

}
