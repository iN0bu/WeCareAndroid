package com.pulusata.wecarefouru;


import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;


public class Configure extends Activity {

    private String PREF_CONFIG = "configuration";
    int tresholdLevel;
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
        SharedPreferences config = getSharedPreferences(PREF_CONFIG, 0);
        SharedPreferences.Editor editor = config.edit();

    //    editor.putString("msg",
    //            ((EditText) findViewById(R.id.customMessage)).getText()
    //                    .toString());

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

    public void sendDataToWatch() {
        // Build up a Pebble dictionary
        PebbleDictionary data = new PebbleDictionary();

        data.addUint8(0, (byte) tresholdLevel);

        // Send the assembled dictionary to the weather watch-app;
        PebbleKit.sendDataToPebble(getApplicationContext(),
                UUID.fromString("5ed10362-a625-41e6-b35c-e6b10feb71e6"),
                data);
    }

    protected void loadConfig() {
        SharedPreferences config = getSharedPreferences(PREF_CONFIG, 0);
        tresholdLevel = Integer.parseInt(config.getString("tresholdValue",
                "50"));
        prenom = config.getString("prenom", "Georgette");
        nom = config.getString("nom", "Dupuis");
        telephone = config.getString("telephone", "06 59 68 90 10");
        contrat = config.getString("contrat", "0849228378");
        infosmedicales = config.getString("infosmedicales", "Allergie à la pénicilline");
    }

    public void modifier(View view) {
        Intent modifProfil = new Intent(this, ModiferProfil.class);
        startActivity(modifProfil);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_temp);
        loadConfig();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ((TextView) findViewById(R.id.current_nom_prenom_telephone_contrat))
                .setText(nom + " " + prenom + " " + telephone + " n° de contrat " + contrat );

        ((TextView) findViewById(R.id.current_infosmedicales))
                .setText("Informations médicales" + infosmedicales);

    }
}
