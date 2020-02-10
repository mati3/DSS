package com.dss.p4_2.Controlador;

import android.os.AsyncTask;
import com.dss.p4_2.Modelo.Product;
import com.dss.p4_2.UI.MainActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ApiRestProducts extends AsyncTask<String, String, String> {

    private HttpURLConnection urlConnection;

        @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {

            URL url = new URL("http://10.0.2.2:8080/P2_3/rest/todos");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());


            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);
            Element raiz = doc.getDocumentElement();

            // Obtenemos todos los elementos
            NodeList items = raiz.getElementsByTagName("todo");
            for( int i = 0; i < items.getLength(); i++ ) {
                Node nodoproduc = items.item(i);
                Product product = new Product();

                // Recorremos todos los hijos que tenga
                for(int j = 0; j < nodoproduc.getChildNodes().getLength(); j++ ) {
                    Node nodoActual = nodoproduc.getChildNodes().item(j);

                    // Compruebo si es un elemento
                    if( nodoActual.getNodeType() == Node.ELEMENT_NODE ) {
                        if( nodoActual.getNodeName().equalsIgnoreCase("resumen") )
                            product.setResumen(nodoActual.getChildNodes().item(0)
                                    .getNodeValue());
                        else if( nodoActual.getNodeName()
                                .equalsIgnoreCase("descripcion") )
                            product.setDescripcion(nodoActual.getChildNodes().item(0)
                                    .getNodeValue());
                    }
                }
                result.append(product);
                MainActivity.db.insertProduct(product);
            }
        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }

        return result.toString();
    }

}