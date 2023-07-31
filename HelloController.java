package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {

    @FXML
    protected TextArea textArea;
    @FXML
    protected ListView listView;
    @FXML
    protected Label foundListLbl;
    @FXML
    protected Label sifatLbl;
    @FXML
    protected Label uyariLbl ;


    @FXML
    protected Label skorLbl;

    protected ArrayList<String> foundList = new ArrayList<>();

    @FXML
    public  Boolean legalKelimeMi () {

        boolean x = true;

        int j = girisMetni.getText().length();
        String girilenKelime = girisMetni.getText().toLowerCase();
        String[] harfDizi = new String[j];


        StringBuilder kelime = new StringBuilder(girilenKelime);
        char[] letters = new char[j];
        kelime.getChars(0, kelime.length(), letters, 0);

        for (int i = 0; i < girilenKelime.length() && x; i++) {
            char harf = letters[i];
            harfDizi[i] = String.valueOf(harf);

            String  btnHarfRegEx = "[^]";
            StringBuilder string = new StringBuilder(btnHarfRegEx);
            string.insert(2,harf1.getText().toLowerCase()+harf2.getText().toLowerCase()+harf3.getText().toLowerCase()+harf4.getText().toLowerCase()+harf5.getText().toLowerCase()+harf6.getText().toLowerCase()+harfM.getText().toLowerCase());

            if (harfDizi[i].matches(String.valueOf(string))) {
                x = false;
            }
            else {
                x = true;
            }
        }
        return x;
    }

    ArrayList<String> usedWords = new ArrayList<>();
    @FXML
    protected void yenile() throws FileNotFoundException {
        File file = new File("dictionary.txt");
        Scanner scanner = new Scanner(file);

        boolean y = true;
        
        while(y){

            ArrayList<String> harfler = new ArrayList<>();
            String kelime = scanner.nextLine();
            if(usedWords.contains(kelime)){
                continue;
            }
            char[] harfDizi = new char[kelime.length()];
            kelime.getChars(0, kelime.length(), harfDizi,0);
            int count = 0 ;

            for(int i = 0 ; i < kelime.length() ; i++){
                harfler.add(String.valueOf(harfDizi[i]));
            }

            for(int i = 0 ; i < kelime.length() && harfler.size() > 0 ; i++){
                String harf = harfler.get(i);

                if(harfler.get(i) == null){
                    continue;
                }

                for(int j = i+1 ; j < harfler.size() ; j++){
                    if(harf.equals(harfler.get(j))){
                        harfler.set(j,null);
                    }
                }
                count++;
            }

            if(count == 7){
                for(int i = 0 ; harfler.contains(null) ; i++){
                    //if(harfler.get(i) == null){
                    harfler.remove(harfler.indexOf(null));
                    //harfler.removeAll(null);
                    //}
                }

                usedWords.add(kelime);

                    for(int i = 0 ; i < harfler.size() ; i++){
                        if(harfler.get(i) == "i"){
                            harfler.set(i,"İ");
                            continue;
                        }
                        harfler.set(i, harfler.get(i).toUpperCase());
                    }

                harf1.setText(harfler.get(0));
                harf2.setText(harfler.get(1));
                harf3.setText(harfler.get(2));
                harf4.setText(harfler.get(3));
                harf5.setText(harfler.get(4));
                harf6.setText(harfler.get(5));
                harfM.setText(harfler.get(6));


                y = false;
                uyariLbl.setText("");
                scanner.close();
            }
        }//while
        skor = 0;
        skorLbl.setText("Puan : " + skor);
        foundList.clear();
        foundListLbl.setText( foundList.size() + " kelime buldunuz !");
        textArea.clear();
        sifatLbl.setText("");

         }

    int skor = 0;
    @FXML
    protected void fileAl() throws FileNotFoundException {
        File file = new File("dictionary.txt");
        Scanner scanner = new Scanner(file);
        boolean ariyor = true ;
        String girilenK = girisMetni.getText().toLowerCase();

        while(ariyor){
            String kelimeInDictionary = scanner.nextLine();
            if(!scanner.hasNextLine()){
                uyariLbl.setText("Böyle bir kelime sözlükte yok!");

                ariyor = false;
            }
            if(girilenK.equals(kelimeInDictionary) ){
                if(girilenK.length()>3){
                    if(girilenK.contains(harfM.getText().toLowerCase())) {

                        if(legalKelimeMi()){

                            if (foundList.contains(girilenK) == false) {
                                textArea.appendText(kelimeInDictionary + "\n");
                                uyariLbl.setText("Eklendi !");
                                foundList.add(girilenK);
                                foundListLbl.setText( foundList.size() + " kelime buldunuz !");
                                girisMetni.setText("");
                                int count = 0;

                                String[] buttons = {harf1.getText(),harf2.getText(),harf3.getText(),harf4.getText(),harf5.getText(),harf6.getText(),harfM.getText()};
                                for(int i = 0 ; i < 7 ; i++){
                                    if (girilenK.toUpperCase().contains(buttons[i])){
                                        count++;
                                    }
                                }

                                if(count == 7){
                                    skor += girilenK.length() - 3 + 7;
                                    skorLbl.setText(" Puan : " + skor);
                                }
                                else {
                                    skor += girilenK.length() - 3 ;
                                    skorLbl.setText(" Puan : " + skor);
                                }
                                if (1 <= skor){
                                    sifatLbl.setText("FENA DEĞİL!");
                                    sifatLbl.setTextFill(Color.DARKGOLDENROD);
                                    if (8 <= skor){
                                        sifatLbl.setText("NORMAL!");
                                        sifatLbl.setTextFill(Color.LIMEGREEN);
                                        if (15 <= skor){
                                            sifatLbl.setText("İYİ!");
                                            sifatLbl.setTextFill(Color.DEEPSKYBLUE);
                                            if (22 <= skor){
                                                sifatLbl.setText("ÇOK İYİ!");
                                                sifatLbl.setTextFill(Color.DARKBLUE);
                                                if (30 <= skor){
                                                    sifatLbl.setText("MÜKEMMEL!");
                                                    sifatLbl.setTextFill(Color.DARKRED);
                                                    if (45 <= skor){
                                                        sifatLbl.setText("DURDULAMIYOR!");
                                                        sifatLbl.setTextFill(Color.MEDIUMPURPLE);
                                                        if (60 <= skor){
                                                            sifatLbl.setText("KELİME KURDU!");
                                                            sifatLbl.setTextFill(Color.PURPLE);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                uyariLbl.setText("Bu kelimeyi daha önce buldunuz!");
                            }
                        }
                        else{
                            uyariLbl.setText("İllegal harf içeriyor!! ");
                        }
                    }
                    else {
                    uyariLbl.setText("Merkez harf kullanınız!");
                    }
                }
                else {
                    uyariLbl.setText("Kelime çok kısa!");
                }
            ariyor = false ;
            }
        }
        scanner.close();
    }

    @FXML
    protected TextField girisMetni;
    @FXML
    protected TextField cevreHarflerText;
    @FXML
    protected TextField merkezHarfText;

    @FXML
    protected void cevreHarflerVerify(){

        StringBuilder cevreHarfler = new StringBuilder(cevreHarflerText.getText());
        int cevreLimit = 6;

        if(cevreHarfler.length() >= cevreLimit) {
            cevreHarflerText.setText(cevreHarfler.substring(0, cevreLimit-1));
        }
    }
    @FXML
    protected void merkezHarfVerify(){
        StringBuilder merkezHarf = new StringBuilder(merkezHarfText.getText());
        int merkezLimit = 1;

        if(merkezHarf.length() >= merkezLimit) {
            merkezHarfText.setText(merkezHarf.substring(0, merkezLimit-1));
        }
    }
    @FXML
    protected void merkezHarfEkle(){
        StringBuilder merkezHarf = new StringBuilder(merkezHarfText.getText());
        String merkezStr = String.valueOf(merkezHarf);
        ArrayList<String> mArrayLetter = new ArrayList<>();
        mArrayLetter.add(merkezStr.toUpperCase());

        Pattern pattern1 = Pattern.compile("[^a-zA-Z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(merkezStr);
        boolean matchFound1 = matcher1.find();

        if( matchFound1 || mArrayLetter.get(0) == null || mArrayLetter.contains("") ){
            merkezHarfText.setText("");
            merkezHarfText.setPromptText("Uygunsuz karakter!!");
        }else {
            harfM.setText(mArrayLetter.get(0));
            merkezHarfText.setText("");

        }
    }
    @FXML
    protected void cevreHarfEkle(){
        StringBuilder cevreHarfler = new StringBuilder(cevreHarflerText.getText());

        String cevreStr = String.valueOf(cevreHarfler);
        cevreStr.trim();

        char[] letters = new char[cevreStr.length()];

        ArrayList<String> arrayLetters = new ArrayList<>();

        cevreHarfler.getChars(0,cevreHarfler.length(),letters,0);

        for(int i = 0; i < letters.length ; i++ ){
            arrayLetters.add(String.valueOf(letters[i]).toUpperCase());
        }
        String btnM = harfM.getText().toLowerCase()+harfM.getText().toUpperCase();
        StringBuilder regEx = new StringBuilder("[]+");
        regEx.insert(1,btnM);

        Pattern pattern3 = Pattern.compile(String.valueOf(regEx), Pattern.CASE_INSENSITIVE);
        Matcher matcher3 = pattern3.matcher(cevreStr);
        boolean matchFound3 = matcher3.find();


        Pattern pattern2 = Pattern.compile("[^a-zA-Z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(cevreStr);
        boolean matchFound2 = matcher2.find();

        if( matchFound2 || arrayLetters.contains(harfM.getText()) || matchFound3 || arrayLetters.size() < 6){
            cevreHarflerText.setText("");
            cevreHarflerText.setPromptText("Uygunsuz karakter!!");
        }
        else{
            int count = 0;
            for(int i = 0 ; i < arrayLetters.size() && count == 0 ; i++){
                String harf = arrayLetters.get(i);
                for(int j = i+1 ; j < arrayLetters.size() ; j++){
                    if(harf.equals(arrayLetters.get(j))){
                        count++;
                    }
                }
            }
            if(count != 0){
                cevreHarflerText.setText("");
                cevreHarflerText.setPromptText("Aynı harf birden fazla kullanılamaz!!");
            }else{
                for(int i = 0 ; i < arrayLetters.size() ; i++){
                    if(arrayLetters.get(i) == "i"){
                        arrayLetters.set(i,"İ");
                        continue;
                    }
                    arrayLetters.set(i, arrayLetters.get(i).toUpperCase());
                }
                harf1.setText(arrayLetters.get(0));
                harf2.setText(arrayLetters.get(1));
                harf3.setText(arrayLetters.get(2));
                harf4.setText(arrayLetters.get(3));
                harf5.setText(arrayLetters.get(4));
                harf6.setText(arrayLetters.get(5));
            }
        }
    }

    @FXML
    protected void harfKaristir(){
        ArrayList<String> grup = new ArrayList<String>();
        grup.add(harf1.getText());
        grup.add(harf2.getText());
        grup.add(harf3.getText());
        grup.add(harf4.getText());
        grup.add(harf5.getText());
        grup.add(harf6.getText());

        Collections.shuffle(grup);
        harf1.setText(grup.get(0));
        harf2.setText(grup.get(1));
        harf3.setText(grup.get(2));
        harf4.setText(grup.get(3));
        harf5.setText(grup.get(4));
        harf6.setText(grup.get(5));
    }
    @FXML
    public void sil(){

        if(girisMetni.getText().length() > 0) {
            String girilenKelime = girisMetni.getText();
            girisMetni.setText(girilenKelime.substring(0, girilenKelime.length() - 1));
        }
        else{
            uyariLbl.setText("Yeni bir kelime giriniz!");
        }
    }
    @FXML
    protected Button harf1;
    @FXML
    protected Button harf2;
    @FXML
    protected Button harf3;
    @FXML
    protected Button harf4;
    @FXML
    protected Button harf5;
    @FXML
    protected Button harf6;
    @FXML
    protected Button harfM;
    @FXML
    public void yazdir1(){
        girisMetni.setText(girisMetni.getText() + harf1.getText());
        uyariLbl.setText("");
    }
    @FXML
    public void yazdir2(){
        girisMetni.setText(girisMetni.getText() + harf2.getText());
        uyariLbl.setText("");
    }
    @FXML
    public void yazdir3(){
        girisMetni.setText(girisMetni.getText() + harf3.getText());
        uyariLbl.setText("");
    }
    public void yazdir4(){
        girisMetni.setText(girisMetni.getText() + harf4.getText());
        uyariLbl.setText("");
    }
    @FXML
    public void yazdir5(){
        girisMetni.setText(girisMetni.getText() + harf5.getText());
        uyariLbl.setText("");
    }
    @FXML
    public void yazdir6(){
        girisMetni.setText(girisMetni.getText() + harf6.getText());
        uyariLbl.setText("");
    }
    @FXML
    public void yazdirM(){
        girisMetni.setText(girisMetni.getText() + harfM.getText());
        uyariLbl.setText("");
    }

}