package turgutsonmez.com.ajandam;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import turgutsonmez.com.ajandam.adapter.MyRecyclerAdapter;
import turgutsonmez.com.ajandam.base.BaseActivity;
import turgutsonmez.com.ajandam.model.Detay;

public class MainActivity extends BaseActivity {

  private SwipeRefreshLayout swipeRefresh;
  private RecyclerView recyclerView;
  private ArrayList<Detay> detaylar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = (RecyclerView) findViewById(R.id.ana_recyclerView);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    layoutManager.scrollToPosition(0);
    recyclerView.setLayoutManager(layoutManager);
    swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.ana_swiperrefresh);
    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        listeyiDoldur();
      }
    });

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabButton);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //Snackbar.make(view, "Aynı Toast Gibi çalışmakta", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        startActivity(new Intent(MainActivity.this, YeniActivity.class));
      }
    });
    listeyiDoldur();
  }

  @Override
  protected void onStart() {
    super.onStart();
    listeyiDoldur();
  }

  private void listeyiDoldur() {
    showProgressDialog("Lütfen bekleyin", "Veritabanına bağlanıyor");
    database = FirebaseDatabase.getInstance();
    myRef = database.getReference().child("notlar");
    final Query query = myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    query.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        detaylar = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          detaylar.add(snapshot.getValue(Detay.class));
        }
        Collections.reverse(detaylar);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(MainActivity.this, detaylar);
        registerForContextMenu(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        hideProgressDialog();
        query.removeEventListener(this);
        swipeRefresh.setRefreshing(false);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

}
