package de.hda.fbi.ds.ks;

import java.net.InetAddress;

/**
 * Created by zigfrid on 13.11.17.
 */
public class SensorData {

    private Product product;
    private int portNummber;
    private InetAddress address;
    private int length;

    public SensorData(Product product, int portNummber, InetAddress address, int length){
        this.product = product;
        this.portNummber = portNummber;
        this.address = address;
        this.length = length;
    }

    public int getPortNummber(){
        return portNummber;
    }

    public void setPortNummber(int portNummber){
        this.portNummber = portNummber;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public InetAddress getAddress(){
        return this.address;
    }

    public void setAddress(InetAddress address){
        this.address = address;
    }

    public int getLengt(){
        return length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public String printSensorData(){
        return "Adress: " + address +
                " Portnummer: " + portNummber + " Length: " + length +
                " Product: " + product.getNameOfProduct() + " " + product.getValueOfProduct();
    }

}
