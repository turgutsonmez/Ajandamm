package turgutsonmez.com.ajandam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import turgutsonmez.com.ajandam.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

  private EditText txtEmail,txtSifre,txtSifreTekrar;
  private Button btnKayıtOl;
  private TextView txtGirisYap;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    txtEmail= (EditText) findViewById(R.id.register_txtEmail);
    txtSifre= (EditText) findViewById(R.id.register_txtSifre);
    txtSifreTekrar= (EditText) findViewById(R.id.register_txtSifreTekrar);
    btnKayıtOl= (Button) findViewById(R.id.register_btnKayıtOl);
    txtGirisYap= (TextView) findViewById(R.id.register_txtGirisYap);

    txtGirisYap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
      }
    });

    btnKayıtOl.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        kullaniciOlustur(txtEmail.getText().toString(),txtSifre.getText().toString());
      }
    });
  }

  private void kullaniciOlustur(String email, String pass) {
    if (!validateForm(txtEmail, txtSifre,txtSifreTekrar))
      return;
    showProgressDialog("Kayıt İşlemi", "Lütfen bekleyin");
    mAuth = FirebaseAuth.getInstance();

    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (!task.isSuccessful())
          Toast.makeText(RegisterActivity.this, "Kayıt işleminde bir hata oluştu", Toast.LENGTH_SHORT).show();
        hideProgressDialog();
      }
    });
    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
  }
}
