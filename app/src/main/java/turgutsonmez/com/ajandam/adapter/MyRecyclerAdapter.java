package turgutsonmez.com.ajandam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import turgutsonmez.com.ajandam.R;
import turgutsonmez.com.ajandam.model.Detay;

/**
 * Created by TurgutSonmez on 28.09.2017.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public static final int STANDART_MENU_ITEM = 0;
  public static final int AD_ITEM = 1;

  private FirebaseDatabase database;
  private DatabaseReference myRef;
  private ArrayList<Detay> detaylar;
  private Context context;
  private myOnClickListener onClickListener;

  public MyRecyclerAdapter(Context context, ArrayList<Detay> detaylar) {
    this.detaylar = detaylar;
    this.context = context;
    //this.onClickListener = onClickListener;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
    final RecyclerView.ViewHolder holder = new MyViewHolder(view);


    return holder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final MyViewHolder viewHolder = (MyViewHolder) holder;
    Detay gelenDetay = detaylar.get(position);

    viewHolder.txtBaslik.setText(gelenDetay.getBaslik());
    viewHolder.txtSaat.setText(gelenDetay.getTarihSaat());
    viewHolder.txtUzunTarih.setText(gelenDetay.getTarih());
  }

  @Override
  public int getItemCount() {
    return detaylar.size();
  }

  public static class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView imgViewRenk;
    public TextView txtGun, txtUzunTarih, txtBaslik, txtSaat;

    public MyViewHolder(View itemView) {
      super(itemView);
      imgViewRenk = itemView.findViewById(R.id.card_imgViewRenk);
      txtGun = itemView.findViewById(R.id.card_txtGun);
      txtUzunTarih = itemView.findViewById(R.id.card_txtUzunTarih);
      txtBaslik = itemView.findViewById(R.id.card_txtBaslik);
      txtSaat = itemView.findViewById(R.id.card_txtSaat);
    }
  }
}
