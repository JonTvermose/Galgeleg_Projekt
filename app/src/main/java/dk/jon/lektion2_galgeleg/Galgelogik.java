package dk.jon.lektion2_galgeleg;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Galgelogik {
  private ArrayList<String> muligeOrd = new ArrayList<String>();
  private ArrayList<String> nemmeOrd = new ArrayList<String>();
  private ArrayList<String> middelOrd = new ArrayList<String>();
  private ArrayList<String> svaereOrd = new ArrayList<String>();
  private String ordet;
  private ArrayList<String> brugteBogstaver = new ArrayList<String>();
  private String synligtOrd;
  private int antalForkerteBogstaver;
  private boolean sidsteBogstavVarKorrekt;
  private boolean spilletErVundet;
  private boolean spilletErTabt;

  public static int svaerhedsgrad = 0;


  public ArrayList<String> getBrugteBogstaver() {
    return brugteBogstaver;
  }

  public String getSynligtOrd() {
    return synligtOrd;
  }

  public String getOrdet() {
    return ordet;
  }

  public int getAntalForkerteBogstaver() {
    return antalForkerteBogstaver;
  }

  public boolean erSidsteBogstavKorrekt() {
    return sidsteBogstavVarKorrekt;
  }

  public boolean erSpilletVundet() {
    return spilletErVundet;
  }

  public boolean erSpilletTabt() {
    return spilletErTabt;
  }

  public boolean erSpilletSlut() {
    return spilletErTabt || spilletErVundet;
  }


  public Galgelogik() {
    muligeOrd.add("bil");
    muligeOrd.add("computer");
    muligeOrd.add("programmering");
    muligeOrd.add("motorvej");
    muligeOrd.add("busrute");
    muligeOrd.add("gangsti");
    muligeOrd.add("skovsnegl");
    muligeOrd.add("solsort");
    nulstil();
  }

  public void nulstil() {
    brugteBogstaver.clear();
    antalForkerteBogstaver = 0;
    spilletErVundet = false;
    spilletErTabt = false;
    switch (svaerhedsgrad){
      case 1: ordet = nemmeOrd.get(new Random().nextInt(muligeOrd.size())); break;
      case 2: ordet = middelOrd.get(new Random().nextInt(muligeOrd.size())); break;
      case 3: ordet = svaereOrd.get(new Random().nextInt(muligeOrd.size())); break;
      default: ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
    }
    opdaterSynligtOrd();
  }


  private void opdaterSynligtOrd() {
    synligtOrd = "";
    spilletErVundet = true;
    for (int n = 0; n < ordet.length(); n++) {
      String bogstav = ordet.substring(n, n + 1);
      if (brugteBogstaver.contains(bogstav)) {
        synligtOrd = synligtOrd + bogstav;
      } else {
        synligtOrd = synligtOrd + "*";
        spilletErVundet = false;
      }
    }
  }

  public void gætBogstav(String bogstav) throws Exception {
    if (bogstav.length() != 1)
      throw new Exception("Indtast ét bogstav");
    System.out.println("Der gættes på bogstavet: " + bogstav);
    if (brugteBogstaver.contains(bogstav))
      throw new Exception("Du har gættet på det bogstav før");
    if (spilletErVundet || spilletErTabt)
      throw new Exception("Spillet er slut, du kan ikke gætte mere.");

    brugteBogstaver.add(bogstav);

    if (ordet.contains(bogstav)) {
      sidsteBogstavVarKorrekt = true;
      System.out.println("Bogstavet var korrekt: " + bogstav);
    } else {
      // Vi gættede på et bogstav der ikke var i ordet.
      sidsteBogstavVarKorrekt = false;
      System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
      antalForkerteBogstaver = antalForkerteBogstaver + 1;
      if (antalForkerteBogstaver > 6) {
        spilletErTabt = true;
      }
    }
    opdaterSynligtOrd();
  }

  public void logStatus() {
    System.out.println("---------- ");
    System.out.println("- ordet (skult) = " + ordet);
    System.out.println("- synligtOrd = " + synligtOrd);
    System.out.println("- forkerteBogstaver = " + antalForkerteBogstaver);
    System.out.println("- brugeBogstaver = " + brugteBogstaver);
    if (spilletErTabt) System.out.println("- SPILLET ER TABT");
    if (spilletErVundet) System.out.println("- SPILLET ER VUNDET");
    System.out.println("---------- ");
  }


  public static String hentUrl(String url) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
    StringBuilder sb = new StringBuilder();
    String linje = br.readLine();
    while (linje != null) {
      sb.append(linje + "\n");
      linje = br.readLine();
    }
    return sb.toString();
  }

  public void hentOrdFraDr() throws Exception {
    String data = hentUrl("https://da.wikipedia.org/wiki/Danmark");
    System.out.println("data = " + data);

    data = data.replaceAll("<.+?>", " ").toLowerCase().replaceAll("[^a-zæøå]", " ");
    System.out.println("data = " + data);
    muligeOrd.clear();
    muligeOrd.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

    while (!muligeOrd.isEmpty()){
      String ord = muligeOrd.remove(0);
      int lix = beregnOrd(ord);
      if(lix > 0 && lix < 200){
        nemmeOrd.add(ord);
      } else if (lix >= 200 && lix <450){
        middelOrd.add(ord);
      } else {
        svaereOrd.add(ord);
      }
    }
    Log.i("Log", "Antal nemme ord: " + nemmeOrd.size());
    Log.i("Log", "Antal middel ord: " + middelOrd.size());
    Log.i("Log", "Antal svære ord: " + svaereOrd.size());

    System.out.println("muligeOrd = " + muligeOrd);
    nulstil();
  }

  public void saetsvaerhedsgrad(int i){
    svaerhedsgrad = i;
    nulstil();
  }

  private int beregnOrd(String ord) {
    int vokaler = 0;
    String unikke ="";
    for(char s : ord.toCharArray()) {
      String p = String.valueOf(s);

      if (p.contains("aeøæåoui")){
        vokaler++;
      }
      if (!unikke.contains(p)){
        unikke += p;
      }
    }
    int retur = ord.length()*unikke.length()*(7-unikke.length()*vokaler);
    Log.i("Log", "Værdi: " + retur + " : Ord: " +ord );
    return retur;
  }
}

