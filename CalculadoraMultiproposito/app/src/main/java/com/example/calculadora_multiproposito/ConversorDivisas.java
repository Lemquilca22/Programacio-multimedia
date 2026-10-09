package com.example.calculadora_multiproposito;

public class ConversorDivisas {

    // Tasas fijas tomando como base 1 Euro (1 EUR = X Moneda)
    private static final double EUR = 1.0;
    private static final double USD = 1.08;
    private static final double PEN = 4.05;
    private static final double CHF = 0.98;
    private static final double GBP = 0.85;
    private static final double JPY = 163.50;

    public double convertir(double cantidad, String monedaOrigen, String monedaDestino) {
        double tasaOrigen = obtenerTasa(monedaOrigen);
        double tasaDestino = obtenerTasa(monedaDestino);

        // Convertir la cantidad primero a Euros y luego a la moneda destino
        double cantidadEnEuros = cantidad / tasaOrigen;
        return cantidadEnEuros * tasaDestino;
    }


     // Retorna la tasa correspondiente al texto seleccionado en el Spinner.

    private double obtenerTasa(String moneda) {
        switch (moneda) {
            case "Dólares (USD)":
                return USD;
            case "Soles (PEN)":
                return PEN;
            case "Francos Suizos (CHF)":
                return CHF;
            case "Libras Esterlinas (GBP)":
                return GBP;
            case "Yenes (JPY)":
                return JPY;
            default:
                return EUR; // Euros (EUR) por defecto
        }
    }
}