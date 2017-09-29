package turgutsonmez.com.ajandam.model;

import java.util.ArrayList;

/**
 * Created by TurgutSonmez on 26.09.2017.
 */

public class Detay {

  private String id,gonderenID,baslik,not,tarih,kategori,tarihSaat;
  private ArrayList<String> cokluDetay;

  public String getGonderenID() {
    return gonderenID;
  }

  public void setGonderenID(String gonderenID) {
    this.gonderenID = gonderenID;
  }

  public ArrayList<String> getCokluDetay() {
    return cokluDetay;
  }

  public void setCokluDetay(ArrayList<String> cokluDetay) {
    this.cokluDetay = cokluDetay;
  }

  public String getTarihSaat() {
    return tarihSaat;
  }

  public void setTarihSaat(String tarihSaat) {
    this.tarihSaat = tarihSaat;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getBaslik() {
    return baslik;
  }

  public void setBaslik(String baslik) {
    this.baslik = baslik;
  }

  public String getNot() {
    return not;
  }

  public void setNot(String not) {
    this.not = not;
  }

  public String getTarih() {
    return tarih;
  }

  public void setTarih(String tarih) {
    this.tarih = tarih;
  }

  public String getKategori() {
    return kategori;
  }

  public void setKategori(String kategori) {
    this.kategori = kategori;
  }
}
