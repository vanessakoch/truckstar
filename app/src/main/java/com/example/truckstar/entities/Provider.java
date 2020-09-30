package com.example.truckstar.entities;

public class Provider {
    private String name;
    private String cnpj;
    private String city;
    private String uf;

    public Provider(String name, String cnpj, String city, String uf) {
        this.name = name;
        this.cnpj = cnpj;
        this.city = city;
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCityUf() {
        return "Localização: " + city + "-" + uf;
    }
}
