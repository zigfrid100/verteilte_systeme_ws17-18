package de.hda.fbi.ds.ks;

/**
 * Created by zigfrid on 08.12.17.
 */
public class Invoice {

    private double productPrice;
    private Product product;

    Invoice(){
        product = new Product("none",0);
        productPrice = 0;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public Product getProduct() {
        return product;
    }

}
