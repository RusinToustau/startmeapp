package com.example.ftoustau.startmeapp.View.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.Model.POJO.User;
import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.MainActivity;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class SignInActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignInActivity";
    public static final Integer SIGN_IN_CODE = 1;
    int ACCOUNT_PICKER_REQ_CODE = 2;

    private GoogleApiClient googleApiClient;
    private SignInButton signInGoogleButton;
    private TwitterLoginButton loginButton;
    private EditText tv_email, mPasswordField;
    private Button buttonSingUp, buttonSingIn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    String email;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        ////MAil
        buttonSingUp = (Button) findViewById(R.id.signup_button);
        buttonSingIn = (Button) findViewById(R.id.signin_button);
        tv_email = (EditText) findViewById(R.id.email_edittext);
        mPasswordField = (EditText) findViewById(R.id.password_edittext);

        buttonSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // name = tv_email.getText().toString();
                email = tv_email.getText().toString();
                pass = mPasswordField.getText().toString();
                createAccount(email, pass);
            }
        });

        buttonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = tv_email.getText().toString();
                pass = mPasswordField.getText().toString();
                signIn(email, pass);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    Toast.makeText(SignInActivity.this, "Bienvenido "+user.getEmail()
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Para entrar debe loguearse"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        };


        ////twitter
        loginButton = (TwitterLoginButton) findViewById(R.id.loginButton);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(SignInActivity.this, result.data.getUserName() + " Bienvenido"
                        , Toast.LENGTH_SHORT).show();
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(SignInActivity.this, "Login fallido", Toast.LENGTH_SHORT).show();
            }
        });
        ////google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInGoogleButton = (SignInButton) findViewById(R.id.SignInGoogleButton);

        signInGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        if (mAuth.getCurrentUser() != null) {
            goToMainActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void handleTwitterSession(TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToMainActivity();
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loginButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResul : " + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            firebaseAuthWithGoogle(acct);
            goMainScreen();
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            goToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            createIntentPiker();
            valid = false;
        }

        String name = tv_email.getText().toString();
        if (TextUtils.isEmpty(name)) {
            tv_email.setError("Required.");
            Toast.makeText(this,"Debes completar el email",Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            Toast.makeText(this,"Debes definir una contraseña",Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){
            User myuser = new User();
            myuser.setEmail(user.getEmail());
            //myuser.setFullname(name);
            myuser.setId(user.getUid());
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            myRef.child("Users").child(user.getUid()).setValue(myuser);
        } else {
            Toast.makeText(this,"user fail",Toast.LENGTH_SHORT).show();
        }
    }

    private void createIntentPiker(){
        Intent intent = AccountPicker.newChooseAccountIntent(null, null
                , new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
        startActivityForResult(intent, ACCOUNT_PICKER_REQ_CODE);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            String message = task.getException().getMessage();
                            Toast.makeText(SignInActivity.this, message,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}



