package com.example.concesionario_adrian;

public class Vehiculo {

    private String marca;
    private String modelo;
    private int matricula;
    private boolean isCoche;
    private boolean isMoto;
    private boolean isCamion;

    public Vehiculo() {
    }

    public Vehiculo(String marca, String modelo, int matricula, boolean isCoche, boolean isMoto, boolean isCamion) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.isCoche = isCoche;
        this.isMoto = isMoto;
        this.isCamion = isCamion;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public boolean isCoche() {
        return isCoche;
    }
    public void setCoche(boolean coche) {
        isCoche = coche;
    }

    public boolean isMoto() {
        return isMoto;
    }
    public void setMoto(boolean moto) {
        isMoto = moto;
    }

    public boolean isCamion() {
        return isCamion;
    }
    public void setCamion(boolean camion) {
        isCamion = camion;
    }

}
