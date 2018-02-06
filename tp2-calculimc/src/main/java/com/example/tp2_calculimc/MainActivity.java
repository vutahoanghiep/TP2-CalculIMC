package com.example.tp2_calculimc;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnCalculer = null;
    Button btnReset = null;
    EditText inputTaille = null;
    EditText inputPoids = null;
    CheckBox optionCommentaire = null;
    RadioGroup cbGroup = null;
    TextView result = null;
    float imc;
//    String texteInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        déclarer des variables
        btnCalculer = findViewById(R.id.btnCalculer);
        btnReset = findViewById(R.id.btnReset);
        inputTaille = findViewById(R.id.inputTaille);
        inputPoids = findViewById(R.id.inputPoids);
        optionCommentaire = findViewById(R.id.optionCommentaire);
        cbGroup = findViewById(R.id.cbGroup);
        result = findViewById(R.id.result);

//        déclarer des actions à faire quand on clique sur un bouton
        btnCalculer.setOnClickListener(calculerListener);
        btnReset.setOnClickListener(resetListener);
        optionCommentaire.setOnClickListener(commentaireListener);

//        solution 1: gérer les EditText avec textWatcher
//        inputTaille.addTextChangedListener(textWatcher);
//        inputPoids.addTextChangedListener(textWatcher);

//        solution 2: gérer les EditText avec Listener
        inputTaille.setOnKeyListener(modifListener);
        inputPoids.setOnKeyListener(modifListener);
    }

    //    définir les actions à faire quand on clique sur un bouton
    private View.OnClickListener calculerListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //  on récupère la taille
            String t = inputTaille.getText().toString();
            // On récupère le poids
            String p = inputPoids.getText().toString();
            float tValue = Float.valueOf(t);

            // Puis on vérifie que la taille est cohérente
            if (tValue <= 0)
                Toast.makeText(MainActivity.this, getString(R.string.taillePos), Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if (pValue <= 0)
                    Toast.makeText(MainActivity.this, getString(R.string.poidsPos), Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (cbGroup.getCheckedRadioButtonId() == R.id.optionCentimetre) {
                        tValue = tValue / 100;
                    }

                    imc = pValue / (tValue * tValue);
                    String resultat;
                    if (cbGroup.getCheckedRadioButtonId() == -1)
                    {
                        resultat = getString(R.string.unite_non_choisie);
                    } else if (optionCommentaire.isChecked()) {
                        resultat = getString(R.string.textRes) + " " + imc + " ==> " + interpreteIMC(imc);
                    } else {
                        resultat = getString(R.string.textRes) + " " + imc;
                    }
                    result.setText(resultat);
                }
            }
        }
    };

    private View.OnClickListener resetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            inputPoids.getText().clear();
            inputTaille.getText().clear();
            result.setText(getString(R.string.result2));
        }
    };

    private View.OnClickListener commentaireListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (((CheckBox) v).isChecked()) {
                result.setText(getString(R.string.result2));
            }
        }
    };

//    solution 1: gérer EditText avec textWatcher
//    private TextWatcher textWatcher = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            result.setText(texteInit);
//        }
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//        }
//    };

    //    solution 2: gérer EditText avec Listener
    private View.OnKeyListener modifListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // choisir automatique l'unité Mètre si la présence d'un point décimal est détectée
            String t = inputTaille.getText().toString();
            if (t.contains(".")) {
                cbGroup.check(R.id.optionMetre);
            } else {
                cbGroup.clearCheck();
            }
            // On remet le texte à sa valeur par défaut
            result.setText(getString(R.string.result2));
            return false;
        }
    };

    //ajout des commentaires si l'option "Commentaire" est choisi en récupérant les valeurs de texte dans strings.xml
    private String interpreteIMC(float imc) {
        String commentaire;
        if (imc < 16.5) {
            commentaire = getString(R.string.famine);
        } else if (16.5 <= imc && imc < 18.5) {
            commentaire = getString(R.string.maigreur);
        } else if (18.5 <= imc && imc < 25) {
            commentaire = getString(R.string.corpulence_normale);
        } else if (25 <= imc && imc < 30) {
            commentaire = getString(R.string.surpoids);
        } else if (30 <= imc && imc < 35) {
            commentaire = getString(R.string.obesite_modere);
        } else if (35 <= imc && imc < 40) {
            commentaire = getString(R.string.obesite_severe);
        } else {
            commentaire = getString(R.string.obesite_massive);
        }
        return commentaire;
    }
}

