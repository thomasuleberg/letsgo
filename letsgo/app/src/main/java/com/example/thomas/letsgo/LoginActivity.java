package com.example.thomas.letsgo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{

    EditText txtEmail, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sette current view
        setContentView(R.layout.login);

        txtEmail = this.findViewById(R.id.input_email);
        txtPassword = this.findViewById(R.id.input_password);
        btnLogin = this.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logge inn brukeren:
                String email = txtEmail.getText().toString();
                String psw = txtPassword.getText().toString();

                if(!email.isEmpty() && !psw.isEmpty()){
                    //Logg inn bruker
                    final ProgressDialog loginDialog = new ProgressDialog(view.getContext());
                    loginDialog.setTitle("Logger deg inn...");
                    loginDialog.setMessage("Vennligst vent");
                    loginDialog.create();
                    loginDialog.show();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, psw)
                            .addOnCompleteListener(task -> {
                                loginDialog.dismiss();
                                if(task.isSuccessful()){
                                    //Show main activity
                                    Intent startMainActivity = new Intent(view.getContext(), MainActivity.class);
                                    startActivity(startMainActivity);
                                    finish();
                                } else {
                                    //Show error dialog
                                    Dialog loginFailedDialog = new AlertDialog.Builder(view.getContext())
                                            .setTitle("Innlogging feilet")
                                            .setMessage("Feil epost eller passord. Prøv igjen.")
                                            .setNeutralButton("OK", (dialogInterface, i) -> {
                                                // When user clickes the ok button: Do nothing.
                                            })
                                            .create();
                                    loginFailedDialog.show();
                                }
                            });

                }

            }
        });


    }
}