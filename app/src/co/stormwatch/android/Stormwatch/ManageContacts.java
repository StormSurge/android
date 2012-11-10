package co.stormwatch.android.Stormwatch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: kgalligan
 * Date: 11/10/12
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageContacts extends Activity
{

    private ListView contactList;
    private ArrayAdapter<Contact> adapter;

    private static final int CONTACT_PICKER_RESULT = 1001;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_contacts);

        contactList = (ListView) findViewById(R.id.contactList);
        adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1);
        contactList.setAdapter(adapter);

        findViewById(R.id.addNewContact).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                launchContactPicker(v);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_RESULT)
        {
            if (resultCode == RESULT_OK)
            {
                Uri contentUri = data.getData();
                String contactId = contentUri.getLastPathSegment();
                Cursor cursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone._ID + "=?",       // < - Note, not CONTACT_ID!
                        new String[]{contactId}, null);
                startManagingCursor(cursor);
                Boolean numbersExist = cursor.moveToFirst();
                int phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = "";
                while (numbersExist)
                {
                    phoneNumber = cursor.getString(phoneNumberColumnIndex);
                    phoneNumber = phoneNumber.trim();
                    numbersExist = cursor.moveToNext();
                }
                stopManagingCursor(cursor);
                if (!phoneNumber.equals(""))
                {
                    Contact contact = new Contact();
                    contact.name = "Mike Smith";
                    contact.phone = phoneNumber;

                    adapter.add(contact);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public static class Contact
    {
        public String name;
        public String email;
        public String phone;

        @Override
        public String toString()
        {
            return phone;
        }
    }

    public static void callMe(Context c)
    {
        c.startActivity(new Intent(c, ManageContacts.class));
    }

    public void launchContactPicker(View view)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }
}