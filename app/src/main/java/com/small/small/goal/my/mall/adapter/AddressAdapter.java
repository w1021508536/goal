package com.small.small.goal.my.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.mall.activity.AddAddressAcivity;
import com.small.small.goal.my.mall.entity.Address;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 13:18
 * 修改：
 * 描述：地址列表的适配器
 **/
public class AddressAdapter extends BaseAdapter {

    private final Context context;
    private List<Address> data;

    public AddressAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Address> data) {
        this.data = data;
        notifyDataSetChanged();
    }

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final Address addressEntity = data.get(position);
        vh.tvName.setText(addressEntity.getConsignee());
        vh.tvAddress.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getDistrict() + addressEntity.getAddress());
        vh.tvTelphone.setText(addressEntity.getMobile());
        vh.cb.setChecked(addressEntity.getIsDefault() == 1 ? true : false);
        vh.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addressEntity.getIsDefault() != 1) {
                    RequestParams requestParams = new RequestParams(Url.Url + Url.Address);
                    requestParams.addHeader("token", Utils.GetToken(context));
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addBodyParameter("addressId", String.valueOf(addressEntity.getId()));
                    requestParams.addBodyParameter("isDefault", "1");
                    XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                if (new JSONObject(result).getString("code").equals("0")) {
                                    for (int i = 0; i < data.size(); i++) {
                                        if (i != position) {
                                            data.get(i).setIsDefault(0);
                                        } else {
                                            data.get(i).setIsDefault(1);
                                        }
                                    }
                                    notifyDataSetChanged();
                                } else {
                                    Utils.showToast(context, new JSONObject(result).getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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


            }
        });
        vh.tvSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddAddressAcivity.class);
                intent.putExtra(KeyCode.INTENT_ADDRESSENTITY, addressEntity);
                intent.putExtra(KeyCode.INTENT_POSITION, position);
                context.startActivity(intent);
            }
        });
        vh.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onAddressAdapterClickListener != null) {
                    onAddressAdapterClickListener.OnDeleteClick(String.valueOf(addressEntity.getId()), position);
                }
            }
        });
        return convertView;
    }

    public interface OnAddressAdapterClickListener {
        void OnDeleteClick(String id, int position);
    }

    OnAddressAdapterClickListener onAddressAdapterClickListener;

    public void setOnAddressAdapterClickListener(OnAddressAdapterClickListener onAddressAdapterClickListener) {
        this.onAddressAdapterClickListener = onAddressAdapterClickListener;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_name)
        TextView tvName;
        @InjectView(R.id.tv_telphone)
        TextView tvTelphone;
        @InjectView(R.id.tv_address)
        TextView tvAddress;
        @InjectView(R.id.cb)
        CheckBox cb;
        @InjectView(R.id.tv_set)
        TextView tvSet;
        @InjectView(R.id.tv_delete)
        TextView tvDelete;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
