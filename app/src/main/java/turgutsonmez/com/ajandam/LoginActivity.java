package turgutsonmez.com.ajandam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import turgutsonmez.com.ajandam.base.BaseActivity;
import turgutsonmez.com.ajandam.model.Kisi;

public class LoginActivity extends BaseActivity {

  private static final int RC_SIGN_IN = 9001;
  private Button btnLogin, btnSifremiUnuttum;
  private EditText txtEmail, txtPassword;
  private TextView txtKayitOl;
  private ImageButton btnGoogleGirisYap, btnFacebookGirisYap;
  private GoogleApiClient mGoogleApiClient;
  private FirebaseAuth.AuthStateListener authStateListener;
  private CallbackManager callbackManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    initComp();
    callbackManager=CallbackManager.Factory.create();
    btnSifremiUnuttum = (Button) findViewById(R.id.login_btnSifremiUnuttum);
    btnLogin = (Button) findViewById(R.id.login_btnGirisYap);
    txtEmail = (EditText) findViewById(R.id.login_txtEmail);
    txtPassword = (EditText) findViewById(R.id.login_txtSifre);
    txtKayitOl = (TextView) findViewById(R.id.login_txtKayıtOl);
    btnFacebookGirisYap = (ImageButton) findViewById(R.id.login_imgBtnFacebook);


    txtKayitOl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
      }
    });

  }

  @Override
  protected void onStart() {
    super.onStart();
    kullaniciKontrol();
  }

  private void initComp() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(getString(R.string.default_web_client_id))
      .requestEmail()
      .build();
    mGoogleApiClient = new GoogleApiClient.Builder(this)
      .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
      .build();
    btnGoogleGirisYap = (ImageButton) findViewById(R.id.login_imgBtnGoogle);
    btnGoogleGirisYap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showProgressDialog("Giriş", "Lütfen Bekleyin");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if (result.isSuccess()) {
        Toast.makeText(this, "Giriş işlemi başarılı", Toast.LENGTH_SHORT).show();
        // Google Sign In was successful, authenticate with Firebase
        GoogleSignInAccount account = result.getSignInAccount();
        firebaseAuthWithGoogle(account);
      } else {
        // Google Sign In failed, update UI appropriately
        // ...
        Toast.makeText(this, "Google hesabına bağlanılamadı", Toast.LENGTH_SHORT).show();
      }
    }
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    mAuth = FirebaseAuth.getInstance();
    mAuth.signInWithCredential(credential)
      .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            kullaniciKontrol();
          } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(LoginActivity.this, "Authentication failed.",
              Toast.LENGTH_SHORT).show();
          }

          // [START_EXCLUDE]
          hideProgressDialog();
          // [END_EXCLUDE]
        }
      });
  }

  private void kullaniciKontrol() {
    mAuth = FirebaseAuth.getInstance();
    user = mAuth.getCurrentUser();
    if (user != null) {
      database = FirebaseDatabase.getInstance();
      myRef = database.getReference().child("uyeler");
      final Query query = myRef.child(user.getUid());
      query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          if (!dataSnapshot.exists()) {
            Kisi yeniKisi = new Kisi();
            yeniKisi.setEmail(user.getEmail());
            yeniKisi.setId(user.getUid());
            yeniKisi.setAd(user.getDisplayName());
            myRef.child(user.getUid()).setValue(yeniKisi);
          }
          query.removeEventListener(this);
          Toast.makeText(LoginActivity.this, "Hoşgeldiniz", Toast.LENGTH_SHORT).show();
          hideProgressDialog();
          startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
      });
    }
  }


}
