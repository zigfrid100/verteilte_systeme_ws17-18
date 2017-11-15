package de.hda.fbi.ds.ks;

import java.io.Serializable;


/**
 * Created by zigfrid on 13.11.17.
 */

public class Product implements Serializable{

    static String[] prodacts = {"Tomaten","Gurken","Zwibeln","Wurst","Käse","Zuker","Wasser","Karotten","Milch", "Soja",
            "Kartoffeln","Kohl","Rettich","Hänchen","Rind","Salz","Paprika","Fisch","Butter","Schmand"};


    public String nameOfProduct;
    public int valueOfProduct;
    private static final long serialVersionUID = 1;

    Product(String name , int value){
        nameOfProduct = name;
        valueOfProduct = value;
    }


    Product(){
        //random Interger for Array of Products
        int min = 0;
        int max = 19;
        int random = (int )(Math.random() * max + min);

        nameOfProduct = prodacts[random];
        valueOfProduct = random + 10;
    }

    public void reduce() {
        valueOfProduct = valueOfProduct - 1;
    }


    public String toString(){
        return nameOfProduct + " " + valueOfProduct;
    }

    int getValueOfProduct(){
        return valueOfProduct;
    }

    String getNameOfProduct(){
        return nameOfProduct;
    }

    void setNameOfProduct(String name){
        nameOfProduct = name;
    }

    void setValueOfProduct(int value){
        valueOfProduct = value;
    }

    public String toPrint(){
        return  nameOfProduct + " " + valueOfProduct;
    }

}
