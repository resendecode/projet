package com.gabi.serverside;
import java.net.MalformedURLException;
import java.io.IOException;
/**
 * @author gabriel
 */
public class Serverside {

    public static void main(String[] args)throws MalformedURLException, IOException {

        System.setProperty("http.proxyHost", "cache.franckbarbier.com");
	    System.setProperty("http.proxyPort", "1963");

        System.out.println("Hello World!");
        produit jeik = new produit("5411188103387");
        jeik.print();
        
    }
}
