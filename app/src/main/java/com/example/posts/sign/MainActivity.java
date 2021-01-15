package com.example.posts.sign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.TypeConverters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posts.R;
import com.example.posts.model.DataRealTime;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSignIn ;
    EditText name , email , password ;
    TextView goToSignUp ;
    CircleImageView imagView  ;
    String tName , tEmail , tPass  ;
    final int REQUEST_CODE_PICK_IMG = 100 ;
    Uri sImg ;
    FirebaseAuth auth ;
    CheckBox checkBox ;
    DatabaseReference refDatabase ;
    StorageReference refStorage ;
    ProgressBar pro ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntiView();
        registerForContextMenu(goToSignUp);
        imagView.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }
    protected  void IntiView()
    {
        btnSignIn = findViewById(R.id.sign_in_btn);
        name = findViewById(R.id.nick_sign_in);
        email = findViewById(R.id.email_sign_in);
        password = findViewById(R.id.pass_sign_in);
        imagView = findViewById(R.id.img_sign_in);
        goToSignUp = findViewById(R.id.go_to_sign_up);
        auth = FirebaseAuth.getInstance();
        refDatabase = FirebaseDatabase.getInstance().getReference();
        refStorage = FirebaseStorage.getInstance().getReference();
        pro = findViewById(R.id.pro);
        checkBox = findViewById(R.id.check_box);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        new MenuInflater(this).inflate(R.menu.go_to_sign_up , menu);
    }
    protected boolean GetText()
    {
        if (name.getText().toString().equals("")|| email.getText().toString().equals("") || password.getText().toString().equals("") || sImg == null )
        {
            Toast.makeText(this, "Please Check Data You Entered And Pick Image", Toast.LENGTH_LONG).show();
            return false ;
        }
        else
        {
            tName = name.getText().toString().trim();
            tEmail = email.getText().toString().trim();
            tPass = password.getText().toString().trim();
            return true ;
        }
    }
    protected void PickImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , REQUEST_CODE_PICK_IMG);
    }
    protected void UploadData ()
    {

        if (GetText())
        {
            pro.setVisibility(View.VISIBLE);
            final StorageReference realRefStorage = refStorage.child("image"+System.currentTimeMillis()+getEx());


            realRefStorage.putFile(sImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    realRefStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url ;
                            url = uri.toString() ;
                            auth.createUserWithEmailAndPassword(tName , tPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    refDatabase.child("users").
                                            child(auth.getCurrentUser().getUid()).
                                            setValue(new DataRealTime(tEmail , tPass , tName , url));
                                    pro.setVisibility(View.GONE);
                                    CheckCheckBox();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Some Thing Went Wrong Try Again",
                                            Toast.LENGTH_SHORT).show();
                                    pro.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Some Thing Went Wrong Try Again",
                                    Toast.LENGTH_SHORT).show();
                            pro.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Some Thing Went Wrong Try Again", Toast.LENGTH_SHORT).show();
                    pro.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case  REQUEST_CODE_PICK_IMG  :
            {
                if (resultCode == RESULT_OK)
                {
                    sImg = data.getData();
                    imagView.setImageURI(sImg);
                }
            }
        }
    }

    protected void CheckCheckBox()
    {

        if (checkBox.isChecked())
        {
            SharedPreferences shared = getSharedPreferences("Remember" , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putString("email" , tEmail);
            editor.putString("password" , tPass);
            editor.putString("img" , sImg.toString());
            editor.putString("name" , tName);
        }
    }

    protected String getEx()
    {
        if (sImg!= null) {
            ContentResolver resolve = getContentResolver();
            MimeTypeMap typeMap = MimeTypeMap.getSingleton();
            return typeMap.getExtensionFromMimeType(resolve.getType(sImg));
        }
        return null ;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_sign_in :
            {
                PickImage();
                break;
            }
            case R.id.sign_in_btn :
            {
                UploadData();
                break;
            }
        }
    }
}
