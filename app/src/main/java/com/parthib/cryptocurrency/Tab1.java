package com.parthib.cryptocurrency;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;


public class Tab1 extends Fragment {
    EditText email, password;
    Button mLoginBtn;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_tab1, container, false);
        createDialog();
        auth = FirebaseAuth.getInstance();

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        mLoginBtn=(Button) view.findViewById(R.id.loginbtn);

        mLoginBtn.setOnClickListener(v -> {
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();

            if (TextUtils.isEmpty(txt_email) || (TextUtils.isEmpty(txt_password))){
                Toast.makeText(getContext(), "All Fields are required!",Toast.LENGTH_SHORT).show();
            }
            else {
                dialog.show();
                auth.signInWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(task -> {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getActivity(), CoinActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "Authentication Failed!",Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        });
        return  view;
    }

    public void createDialog(){
        dialog=new ProgressDialog(getContext());
        dialog.setTitle("Authenticating User");
        dialog.setMessage("Loading... Please Wait");

    }


}
