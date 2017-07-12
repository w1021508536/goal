package com.small.small.goal.search.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.small.small.goal.R;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class ContactsAdapter extends BaseAdapter {

    private List<Map<String, String>> contacts_list;
    private Context context;

    public ContactsAdapter(Context context, List<Map<String, String>> contacts_list) {
        this.context = context;
        this.contacts_list = contacts_list;
    }


    @Override
    public int getCount() {
        return contacts_list.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contacts, null);
            viewHolder.imageView = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.letter_text = (TextView) convertView.findViewById(R.id.letter_text);
            viewHolder.invite_text = (TextView) convertView.findViewById(R.id.invite_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int letter = contacts_list.get(position).get("LetterName").toUpperCase().charAt(0);
        if (position == getPositionForSelection(letter)) {// 相等说明需要显示字母
            viewHolder.letter_text.setVisibility(View.VISIBLE);
            viewHolder.letter_text.setText(contacts_list.get(position).get("LetterName").substring(0, 1).toUpperCase());
        } else {
            viewHolder.letter_text.setVisibility(View.GONE);

        }

        viewHolder.name_text.setText(contacts_list.get(position).get("ContactName"));


        //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
        if (Long.valueOf(contacts_list.get(position).get("HeadId")) > 0) {
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contacts_list.get(position).get("ContactId")));
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), uri);
            viewHolder.imageView.setImageBitmap(BitmapFactory.decodeStream(input));
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.icon_head);
        }

        viewHolder.invite_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = null;
                Cursor groupContactCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contacts_list.get(position).get("ContactId"),
                        null,
                        null);
                while (groupContactCursor.moveToNext()) {
                    groupContactCursor.getString(0);
                    System.out.println("=============" + groupContactCursor.getString(0));
                    number = groupContactCursor.getString(0);
                }
                groupContactCursor.close();

                Uri smsToUri = Uri.parse("smsto:" + number);
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                context.startActivity(messageIntent);
            }
        });

        return convertView;
    }


    public class ViewHolder {
        private CircleImageView imageView;
        private TextView name_text;
        private TextView letter_text;
        private TextView invite_text;


    }

    public int getPositionForSelection(int selection) {
        for (int i = 0; i < contacts_list.size(); i++) {
            String Fpinyin = contacts_list.get(i).get("LetterName");
            char first = Fpinyin.toUpperCase().charAt(0);
            if (first == selection) {
                return i;
            }
        }
        return -1;

    }
}
