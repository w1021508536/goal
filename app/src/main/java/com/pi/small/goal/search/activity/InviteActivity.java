package com.pi.small.goal.search.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.search.adapter.ContactsAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CharacterParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InviteActivity extends BaseActivity implements View.OnClickListener {


    private ImageView image_right;
    private ListView list_contacts;

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
        super.onCreate(savedInstanceState);

        contacts_list = new ArrayList<Map<String, String>>();


        image_right = (ImageView) findViewById(R.id.right_image);
        list_contacts = (ListView) findViewById(R.id.contacts_list);


        contactsAdapter = new ContactsAdapter(this, contacts_list);
        list_contacts.setAdapter(contactsAdapter);
        init();
    }

    private void init() {

        image_right.setOnClickListener(this);
        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        getPhoneContacts();
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Map<String, String>> filterDateList = new ArrayList<Map<String, String>>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = contacts_list;
        } else {
            filterDateList.clear();
            for (int i = 0; i < contacts_list.size(); i++) {

                String name = contacts_list.get(i).get("ContactName");
                String letter = contacts_list.get(i).get("LetterName");
                String headId = contacts_list.get(i).get("HeadId");
                String ContactId = contacts_list.get(i).get("ContactId");
                String str = filterStr.toString();
                char[] ch = str.toCharArray();
// 将大写字母转化为小写字母，实现大小写检索查询
                for (int j = 0; j < ch.length; j++) {
                    if ((int) ch[j] >= 65 && (int) ch[j] <= 90) {
                        ch[j] += 32;
                    }
                }
                str = new String(ch);
// 获取搜索条件的首字母，实现首字母检索查询
                CharacterParser characterParser = CharacterParser.getInstance();
                name.compareToIgnoreCase(characterParser.getSelling(name));
                if (name.indexOf(str) != -1 || letter.startsWith(str)
                        || characterParser.getSelling(name).startsWith(str)
// 匹配手机号查询，实现手机号和姓名的双向查询
                        ) {
                    filterDateList.add(contacts_list.get(i));

                }
            }
        }

        contactsAdapter = new ContactsAdapter(this, filterDateList);
        list_contacts.setAdapter(contactsAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

//                Cursor groupContactCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + ContactId,
//                        null,
//                        null);
//                while (groupContactCursor.moveToNext()) {
//                    groupContactCursor.getString(0);
//                    System.out.println("=============" + groupContactCursor.getString(0));
//                    map.put("Number", groupContactCursor.getString(0));
//                }
//                groupContactCursor.close();


                contacts_list.add(map);

            }

            phoneCursor.close();
            contactsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        state = list_contacts.onSaveInstanceState();

        contacts_list.clear();
        getPhoneContacts();
        list_contacts.onRestoreInstanceState(state);
        super.onResume();
    }
}
