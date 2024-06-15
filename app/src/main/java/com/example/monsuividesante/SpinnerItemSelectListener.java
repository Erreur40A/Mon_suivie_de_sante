package com.example.monsuividesante;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinnerItemSelectListener implements AdapterView.OnItemSelectedListener{

    /*Au début le listener onItemSelected est déclancher je vais régler ca*/
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Récupérer l'élément sélectionné
        String selectionner = (String) parent.getItemAtPosition(position);

        /*
        * A REMPLACER
        * Il faut mettre le choix que l'utilisateur a sélectionné à la place de "Choisissez un activité/durée
        */
        Toast.makeText(parent.getContext(), "Option sélectionnée : " + selectionner, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        /*
         * A REMPLACER
         * Il faut laisser "Choisissez un activité/durée
         */
        Toast.makeText(parent.getContext(), "Aucun Item selectioner", Toast.LENGTH_SHORT).show();
    }
}
