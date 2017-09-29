package turgutsonmez.com.ajandam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import turgutsonmez.com.ajandam.base.BaseActivity;
import turgutsonmez.com.ajandam.model.Detay;
import turgutsonmez.com.ajandam.model.Kisi;

public class YeniActivity extends BaseActivity {

  EditText txtBaslik, txtNot;
  TextView txtGun, txtSaat;
  Button btnKaydet;
  ImageButton btnTakvim, btnTakvimSaat;
  Kisi kisi;
  Date seciliTarih;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_yeni);

    txtBaslik = (EditText) findViewById(R.id.txtBaslik);
    txtNot = (EditText) findViewById(R.id.txtNot);
    btnKaydet = (Button) findViewById(R.id.btnEkle);
    txtSaat = (TextView) findViewById(R.id.txtSaat);
    btnTakvimSaat = (ImageButton) findViewById(R.id.btnTakvimSaat);
    txtGun = (TextView) findViewById(R.id.txtGun);
    btnTakvim = (ImageButton) findViewById(R.id.btnTakvimGun);

    btnTakvim.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker;
        datePicker = new DatePickerDialog(YeniActivity.this, new DatePickerDialog.OnDateSetListener() {
          @Override
          public void onDateSet(DatePicker view, int year, int monthOfYear,
                                int dayOfMonth) {
            seciliTarih = new Date(year - 1900, monthOfYear, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");

            txtGun.setText(simpleDateFormat.format(seciliTarih));
          }
        }, year, month, day);
        datePicker.setTitle("Tarihinizi Seçiniz");
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", datePicker);
        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);

        datePicker.show();
      }
    });

    btnTakvimSaat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(YeniActivity.this, new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
            txtSaat.setText(selectedHour + ":" + selectedMinute);
          }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Saati Seçiniz");
        mTimePicker.show();
      }
    });

    /*showProgressDialog("Lütfen bekleyin");

    mAuth = FirebaseAuth.getInstance();
    user = mAuth.getCurrentUser();
    if (user == null) finish();
    database = FirebaseDatabase.getInstance();
    myRef = database.getReference().child("uyeler");
    final Query query = myRef.child(user.getUid());
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        kisi = dataSnapshot.getValue(Kisi.class);
        txtBaslik.setText(kisi.getBaslik());
        txtNot.setText(kisi.getNot());
        if (kisi.getTarih() != null) {
          Date dtarihi = new Date(kisi.getTarih());
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
          seciliTarih = dtarihi;
          txtGun.setText(simpleDateFormat.format(dtarihi));
        }
        if (kisi.getTarihSaat() != null) {
          Date dtarihi = new Date(kisi.getTarihSaat());
          SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
          seciliTarih = dtarihi;
          txtSaat.setText(simpleDateFormat.format(dtarihi));
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });*/

    btnKaydet.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Detay kaydedilecekDetay = new Detay();
        kaydedilecekDetay.setBaslik(txtBaslik.getText().toString());
        kaydedilecekDetay.setNot(txtNot.getText().toString());
        kaydedilecekDetay.setTarih(txtGun.getText().toString());
        kaydedilecekDetay.setTarihSaat(txtSaat.getText().toString());

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("notlar");
        myRef.child(user.getUid()).push().setValue(kaydedilecekDetay);


        Toast.makeText(YeniActivity.this, "Ekledi", Toast.LENGTH_SHORT).show();
        finish();
      }
    });

  }
}
