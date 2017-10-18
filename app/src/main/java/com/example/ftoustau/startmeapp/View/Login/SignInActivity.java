package com.example.ftoustau.startmeapp.View.Login;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ftoustau.startmeapp.R;
import com.example.ftoustau.startmeapp.View.MainActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
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
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignInActivity";
    public static final Integer SIGN_IN_CODE = 1;

    private GoogleApiClient googleApiClient;
    private SignInButton signInGoogleButton;
    private TwitterLoginButton loginButton;

    private EditText editEmail, editPassword;
    private Button buttonSingUp, buttonSingIn;

    private FirebaseAuth mAuth;

    ////para el login con mail
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        ////MAil
        buttonSingUp = (Button) findViewById(R.id.signup_button);
        buttonSingIn = (Button) findViewById(R.id.signin_button);
        editEmail = (EditText) findViewById(R.id.email_edittext);
        editPassword = (EditText) findViewById(R.id.password_edittext);

        buttonSingUp.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String emailInicia = editEmail.getText().toString();
                String passInicia = editPassword.getText().toString();
                if (emailInicia.length() > 0 && passInicia.length() > 0) {
                    altaUser(emailInicia, passInicia);
                }else {
                    Toast.makeText(SignInActivity.this, "Introduzca todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInicia = editEmail.getText().toString();
                String passInicia = editPassword.getText().toString();
                if (emailInicia.length() > 0 && passInicia.length() > 0) {
                    iniciaUser(emailInicia, passInicia);
                }else {
                    Toast.makeText(SignInActivity.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // Name, email address, and profile photo Url
                    Toast.makeText(SignInActivity.this, "Bienvenido "+user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignInActivity.this, "Para entrar debe loguearse", Toast.LENGTH_SHORT).show();
                }
            }
        };


        ////twitter
        loginButton = (TwitterLoginButton) findViewById(R.id.loginButton);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Toast.makeText(SignInActivity.this, result.data.getUserName() + " Bienvenido", Toast.LENGTH_SHORT).show();
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
        finish();
    }

    private void goToStart(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void altaUser(final String email, final String pass) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    goToMainActivity();
                } else {
                    goToStart();
                    Log.e("SESION", task.getException().getMessage()+"No se pudo dar de alta");
                    Toast.makeText(SignInActivity.this, "Problema de autenticación", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void iniciaUser(String email, String pass) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                goToMainActivity();
                //Toast.makeText(SignInActivity.this,FirebaseAuth.getInstance().getCurrentUser(),Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                goToStart();
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}



