package com.dutymator;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * @author Zsolt Takacs <zsolt@takacs.cc>
 */
public class ContactsReader {
    public String getNumber(Context context, String name) throws IntermittentException {
        String number = null;

        Cursor cursor = context.getContentResolver().query(
            ContactsContract.Contacts.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE '"+name+"'", null, null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID));
                int hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhone == 1) {
                    Cursor phones = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null
                    );
                    if (phones.moveToNext()) {
                        number = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
                        break;
                    }
                    phones.close();
                }
            }

            return number;
        } else {
            throw new IntermittentException();
        }
    }
}