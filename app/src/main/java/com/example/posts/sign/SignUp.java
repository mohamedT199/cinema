package com.example.posts.sign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.posts.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity {

    String emailsh , passsh , namesh , imgsh , temail , tpass  ;

    TextInputEditText email , pass ;
    AutoCompleteTextView autoCompleteTextView ;
    FloatingActionButton floatingActionButton ;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        IntiView();
        IntiAutoComplet();
        autoCompleteForEdit();

    }
    protected void IntiView()
    {
        email = findViewById(R.id.text_email);
        pass = findViewById(R.id.text_pass) ;
        floatingActionButton = findViewById(R.id.btn_go);
        autoCompleteTextView = findViewById(R.id.auto_complet);
        auth = FirebaseAuth.getInstance();

    }

    protected void IntiAutoComplet()
    {
        List<String> list = new ArrayList<>();
        list.add("The Best");
        list.add("Just Good ");
        list.add("Not Bad");
        list.add("Bad");
        ArrayAdapter adapter = new ArrayAdapter(this , R.layout.item_autocomplete , list);
        autoCompleteTextView.setAdapter(adapter);

    }
    protected void autoCompleteForEdit()
    {
        SharedPreferences share = getSharedPreferences("Remember" , MODE_PRIVATE);
        if (share.getString("email" , null ) != null)
        {
            emailsh = share.getString("email" , null ) ;
            passsh = share.getString("password" , null ) ;
            namesh = share.getString("name" , null ) ;
            imgsh = share.getString("img" , null ) ;
            email.setText(emailsh);
            pass.setText(passsh);
        }
    }
    protected void searchUser()
    {
        if (GetText()) {
            auth.signInWithEmailAndPassword(temail , tpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUp.this, "Hi", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private boolean GetText()
    {
        if(!email.getText().toString().equals("") || !pass.getText().toString().equals("") )
        {
            temail = email.getText().toString().trim() ;
            tpass = pass.getText().toString().trim() ;
            return true ;
        }
        else
        {
            Toast.makeText(this, "Check Data That You Enterd", Toast.LENGTH_SHORT).show();
            return  false ;
        }
    }
}
