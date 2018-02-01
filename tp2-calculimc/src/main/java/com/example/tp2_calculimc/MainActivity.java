package com.example.tp2_calculimc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

    private final String texteInit = "Cliquez sur le bouton « Calculer l'IMC » pour obtenir un résultat.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCalculer = findViewById(R.id.btnCalculer);
        btnReset = findViewById(R.id.btnReset);
        inputTaille = findViewById(R.id.inputTaille);
        inputPoids = findViewById(R.id.inputPoids);
        optionCommentaire = findViewById(R.id.optionCommentaire);
        cbGroup = findViewById(R.id.cbGroup);
        result = findViewById(R.id.result);

        btnCalculer.setOnClickListener(calculerListener);
        btnReset.setOnClickListener(resetListener);
        optionCommentaire.setOnClickListener(commentaireListener);
        inputTaille.addTextChangedListener(textWatcher);
        inputPoids.addTextChangedListener(textWatcher);
    }

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
                Toast.makeText(MainActivity.this, "La taille doit être positive", Toast.LENGTH_SHORT).show();
            else {
                float pValue = Float.valueOf(p);
                if (pValue <= 0)
                    Toast.makeText(MainActivity.this, "Le poids doit etre positif", Toast.LENGTH_SHORT).show();
                else {
                    // Si l'utilisateur a indiqué que la taille était en centimètres
                    // On vérifie que la Checkbox sélectionnée est la deuxième à l'aide de son identifiant
                    if (cbGroup.getCheckedRadioButtonId() == R.id.optionCentimetre)
                        tValue = tValue / 100;
                    imc = pValue / (tValue * tValue);
                    String resultat = "Votre IMC est " + imc + " ";
                    if(optionCommentaire.isChecked()) {
                        resultat += interpreteIMC(imc);
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
            result.setText(texteInit);
        }
    };

    private View.OnClickListener commentaireListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                result.setText(texteInit);
            }
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            result.setText(texteInit);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private String interpreteIMC(float imc) {
        String commentaire;
        if (imc < 16.5) {
            commentaire = "==> famine";
        } else if (16.5 <= imc && imc <18.5) {
            commentaire = "==> maigreur";
        } else if (18.5 <= imc && imc <25) {
            commentaire = "==> corpulence normale";
        } else if (25 <= imc && imc <30) {
            commentaire = "==> surpoids";
        } else if (30 <= imc && imc <35) {
            commentaire = "==> obésité modérée";
        } else if (35 <= imc && imc <40) {
            commentaire = "==> obésité sévère";
        } else {
            commentaire = "==> obésité morbide ou massive";
        }
        return commentaire;
    }
}

