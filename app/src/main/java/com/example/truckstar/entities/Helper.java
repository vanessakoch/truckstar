package com.example.truckstar.entities;

public class Helper {
    private String name;
    private String cpf;
    private float cost;

    public Helper(String name, String cpf, float cost) {
        this.name = name;
        this.cpf = cpf;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Helper{" +
                "name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cost=" + cost +
                '}';
    }
}
