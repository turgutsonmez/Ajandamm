package turgutsonmez.com.ajandam.model;

/**
 * Created by TurgutSonmez on 21.09.2017.
 */

public class Kisi {
  private String id, ad, soyad, email,baslik,not,tarih,kategori,tarihSaat;

  public String getTarihSaat() {
    return tarihSaat;
  }

  public void setTarihSaat(String tarihSaat) {
    this.tarihSaat = tarihSaat;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAd() {
    return ad;
  }

  public void setAd(String ad) {
    this.ad = ad;
  }

  public String getSoyad() {
    return soyad;
  }

  public void setSoyad(String soyad) {
    this.soyad = soyad;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
