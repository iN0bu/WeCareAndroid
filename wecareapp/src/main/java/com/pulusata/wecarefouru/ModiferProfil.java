package com.pulusata.wecarefouru;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class ModiferProfil extends ListActivity {

    private class MyArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final ArrayList<String> phoneNumbers;
        private final ArrayList<String> names;

        public MyArrayAdapter(Context context,
                              ArrayList<String> phoneNumbers, ArrayList<String> names) {
            super(context, R.layout.contact_list_layout, phoneNumbers);
            this.context = context;
            this.phoneNumbers = phoneNumbers;
            this.names = names;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.contact_list_layout,
                    parent, false);
            TextView numberView = (TextView) rowView
                    .findViewById(R.id.phoneNumber);
            TextView nameView = (TextView) rowView.findViewById(R.id.name);
            numberView.setText(phoneNumbers.get(position));
            nameView.setText(names.get(position));

            Button deleteButton = (Button) rowView
                    .findViewById(R.id.delete_button);
            final int currentPosition = position;
            deleteButton.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("position", "" + currentPosition);
                    delAtPos(currentPosition);

                }
            });
            return rowView;

        }

    }

    private final String PREF_PHONE_NUMBERS = "phone_numbers";
    private final String PREF_NAMES = "names";
    protected Handler mHandler;
    ArrayList<String> contactList = new ArrayList<String>();
    ArrayList<String> nameList = new ArrayList<String>();

    ArrayAdapter<String> adapter;


    private String PREF_MODIF = "modification";
    private String prenom;
    private String nom;
    private String telephone;
    private String contrat;
    private String infosmedicales;



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void save(View view) {
        SharedPreferences config = getSharedPreferences(PREF_MODIF, 0);
        SharedPreferences.Editor editor = config.edit();

        editor.putString("nom",
                ((EditText) findViewById(R.id.nom)).getText()
                        .toString());

        editor.putString("prenom",
                ((EditText) findViewById(R.id.prenom)).getText()
                        .toString());

        editor.putString("telephone",
                ((EditText) findViewById(R.id.telephone)).getText()
                        .toString());

        editor.putString("contrat",
                ((EditText) findViewById(R.id.contrat)).getText()
                        .toString());

        editor.putString("infosmedicales",
                ((EditText) findViewById(R.id.infosmedicales)).getText()
                        .toString());

        editor.commit();

        ((EditText) findViewById(R.id.nom))
                .setText(((EditText) findViewById(R.id.nom))
                        .getText().toString());
        ((EditText) findViewById(R.id.prenom))
                .setText(((EditText) findViewById(R.id.prenom))
                        .getText().toString());
        ((EditText) findViewById(R.id.telephone))
                .setText(((EditText) findViewById(R.id.telephone))
                        .getText().toString());
        ((EditText) findViewById(R.id.contrat))
                .setText(((EditText) findViewById(R.id.contrat))
                        .getText().toString());
        ((EditText) findViewById(R.id.infosmedicales))
                .setText(((EditText) findViewById(R.id.infosmedicales))
                        .getText().toString());
    }


    protected void loadConfig() {
        SharedPreferences config = getSharedPreferences(PREF_MODIF, 0);
        prenom = config.getString("prenom", "Gorgette");
        nom = config.getString("nom", "Dupuis");
        telephone = config.getString("telephone", "06 59 68 90 10");
        contrat = config.getString("contrat", "0849228378");
        infosmedicales = config.getString("infosmedicales", "hémophile");

    }

    public void pickContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profil_temp);
        loadConfig();
        loadNumbers();
        loadNames();

        // adapter = new ArrayAdapter<String>(this,
        // android.R.layout.simple_list_item_1, nameList);
        adapter = new MyArrayAdapter(this, contactList, nameList);
        setListAdapter(adapter);
    }


        protected void loadNames() {
            SharedPreferences names = getSharedPreferences(PREF_NAMES, 0);
            String currName;
            for (int i = 0; !((currName = names.getString(Integer.toString(i),
                    "")).equals("")); i++) {
                nameList.add(currName);
            }
        }

        protected void loadNumbers() {
            SharedPreferences phoneNumbers = getSharedPreferences(
                    PREF_PHONE_NUMBERS, 0);
            String currPhoneNumber;
            for (int i = 0; !((currPhoneNumber = phoneNumbers.getString(
                    Integer.toString(i), "")).equals("")); i++) {
                contactList.add(currPhoneNumber);
            }
        }

    @Override
    protected void onResume() {
        super.onResume();

        ((EditText) findViewById(R.id.nom))
                .setText(nom);
        ((EditText) findViewById(R.id.prenom))
                .setText(prenom);
        ((EditText) findViewById(R.id.telephone))
                .setText(telephone);
        ((EditText) findViewById(R.id.contrat))
                .setText(contrat);
        ((EditText) findViewById(R.id.infosmedicales))
                .setText(infosmedicales);
    }

    protected void saveNames() {
        SharedPreferences names = getSharedPreferences(PREF_NAMES, 0);
        SharedPreferences.Editor editor = names.edit();
        for (int i = 0; i < nameList.size(); i++) {
            String currName = nameList.get(i);
            editor.putString(Integer.toString(i), currName);
        }
        editor.commit();
    }

    protected void saveNumbers() {
        SharedPreferences phoneNumbers = getSharedPreferences(
                PREF_PHONE_NUMBERS, 0);
        SharedPreferences.Editor editor = phoneNumbers.edit();
        for (int i = 0; i < contactList.size(); i++) {
            String currPhoneNumber = contactList.get(i);
            editor.putString(Integer.toString(i), currPhoneNumber);
        }
        editor.commit();
    }

    private void delAtPos(int position) {
        contactList.remove(position);
        nameList.remove(position);
        adapter.notifyDataSetChanged();
        SharedPreferences phoneNumbers = getSharedPreferences(
                PREF_PHONE_NUMBERS, 0);
        phoneNumbers.edit().clear().commit();
        SharedPreferences names = getSharedPreferences(PREF_NAMES, 0);
        names.edit().clear().commit();
        saveNames();
        saveNumbers();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Log.d("destroy", "destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNumbers();
        saveNames();
    }

}
