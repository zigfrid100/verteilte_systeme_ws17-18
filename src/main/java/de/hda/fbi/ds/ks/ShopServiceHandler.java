package de.hda.fbi.ds.ks;

import de.hda.fbi.ds.myShopService.ShopService;
import org.apache.thrift.TException;
import java.lang.*;
import java.util.*;
import java.util.Random;

/**
 * Created by zigfrid on 09.12.17.
 */
public class ShopServiceHandler implements ShopService.Iface{

    List<String> history = new ArrayList<String>();



    @Override
    public String hello(String n1) throws TException {
        return  "Answer from "+n1;
    }

    @Override
    public int getPriceByName(String name) throws TException {
        //note a single Random object is reused here
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);
        return randomInt;
    }

    @Override
    public int buyProdact(String name , int value , int price) throws TException {
        System.out.println("Client buy: " + value +" "+ name  +  "  and pay " + price*value + " money.");
        history.add("Client buy: " + value +" "+ name  +  "  and pay " + price*value + " money.");
        return value;
    }

}
