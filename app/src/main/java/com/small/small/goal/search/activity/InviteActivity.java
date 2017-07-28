package com.small.small.goal.search.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.search.adapter.ContactsAdapter;
import com.small.small.goal.utils.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity implements View.OnClickListener {


    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(R.id.head_layout)
    RelativeLayout headLayout;
    @InjectView(R.id.contacts_list)
    ListView contactsList;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;

    private ContactsAdapter contactsAdapter;

    private List<Map<String, String>> contacts_list;
    private Map<String, String> map;


    //获取库Data表字段
    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.Data.DISPLAY_NAME, ContactsContract.Data.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY};
    //联系人显示名称
    private static final int CONTACT_DISPLAY_NAME = 0;
    //头像ID
    private static final int CONTACT_PHOTO_ID = 1;
    //联系人的ID
    private static final int CONTACT_CONTACT_ID = 2;
    //联系人全拼
    private static final int CONTACT_LETTER_NAME = 3;


    private Parcelable state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        contacts_list = new ArrayList<Map<String, String>>();

        contactsAdapter = new ContactsAdapter(this, contacts_list);
        contactsList.setAdapter(contactsAdapter);
        init();
    }

    private void init() {

        getPhoneContacts();
    }

//    /**
//     * 根据输入框中的值来过滤数据并更新ListView
//     *
//     * @param filterStr
//     */
//    private void filterData(String filterStr) {
//        List<Map<String, String>> filterDateList = new ArrayList<Map<String, String>>();
//        if (TextUtils.isEmpty(filterStr)) {
//            filterDateList = contacts_list;
//        } else {
//            filterDateList.clear();
//            for (int i = 0; i < contacts_list.size(); i++) {
//
//                String name = contacts_list.get(i).get("ContactName");
//                String letter = contacts_list.get(i).get("LetterName");
//                String headId = contacts_list.get(i).get("HeadId");
//                String ContactId = contacts_list.get(i).get("ContactId");
//                String str = filterStr.toString();
//                char[] ch = str.toCharArray();
//// 将大写字母转化为小写字母，实现大小写检索查询
//                for (int j = 0; j < ch.length; j++) {
//                    if ((int) ch[j] >= 65 && (int) ch[j] <= 90) {
//                        ch[j] += 32;
//                    }
//                }
//                str = new String(ch);
//// 获取搜索条件的首字母，实现首字母检索查询
//                CharacterParser characterParser = CharacterParser.getInstance();
//                name.compareToIgnoreCase(characterParser.getSelling(name));
//                if (name.indexOf(str) != -1 || letter.startsWith(str)
//                        || characterParser.getSelling(name).startsWith(str)
//// 匹配手机号查询，实现手机号和姓名的双向查询
//                        ) {
//                    filterDateList.add(contacts_list.get(i));
//
//                }
//            }
//        }
//
//        contactsAdapter = new ContactsAdapter(this, filterDateList);
//        list_contacts.setAdapter(contactsAdapter);
//    }


    @OnClick({R.id.left_image, R.id.right_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_image:
                break;
        }
    }

    private void getPhoneContacts() {
        ContentResolver resolver = getContentResolver();

        // 获取手机联系人
        Cursor phoneCursor = resolver.query(ContactsContract.Data.CONTENT_URI, PHONES_PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);

        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                map = new HashMap<String, String>();

                //得到联系人名称
                String ContactName = phoneCursor.getString(CONTACT_DISPLAY_NAME);
                //得到联系人拼音
                String LetterName = phoneCursor.getString(CONTACT_LETTER_NAME);
                //得到联系人ID
                Long ContactId = phoneCursor.getLong(CONTACT_CONTACT_ID);
                //得到联系人头像ID
                Long HeadId = phoneCursor.getLong(CONTACT_PHOTO_ID);

                map.put("ContactName", ContactName);
                map.put("ContactId", String.valueOf(ContactId));
                map.put("HeadId", String.valueOf(HeadId));
                map.put("LetterName", LetterName);

                contacts_list.add(map);

            }
            if (contacts_list.size()<1){
                nullLayout.setVisibility(View.VISIBLE);
                tvEmpty.setText("暂 时 没 有 通 讯 录 联 系 人");
            }

            phoneCursor.close();
            contactsAdapter.notifyDataSetChanged();
        }

    }

//    @Override
//    public void onResume() {
//        state = contactsList.onSaveInstanceState();
//
//        contacts_list.clear();
//        getPhoneContacts();
//        contactsList.onRestoreInstanceState(state);
//        super.onResume();
//    }


}
