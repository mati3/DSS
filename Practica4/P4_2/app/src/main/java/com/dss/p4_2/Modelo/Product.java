package com.dss.p4_2.Modelo;

public class Product {

    public static final String TABLE_NAME = "products";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RESUMEN = "resumen";
    public static final String COLUMN_DESCRIPCION = "descripcion";

    public int id;
    public String resumen;
    public String descripcion;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_RESUMEN + " TEXT,"
                    + COLUMN_DESCRIPCION + " TEXT"
                    + ")";

    public Product() {
    }

    public Product(int id, String resumen, String descripcion) {
        this.id = id;
        this.resumen = resumen;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String res) {
        this.resumen = res;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String des) {
        this.descripcion = des;
    }
}
