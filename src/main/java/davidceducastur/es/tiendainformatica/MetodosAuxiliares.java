/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

/**
 *
 * @author alu03d
 */
public class MetodosAuxiliares {
    
    public static boolean esInt (String s) {
        int n;
        try {
            n=Integer.parseInt(s);
            return true;        
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    public static boolean esDouble (String s) {
        double n;
        try {
            n = Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        } 
    }
    
    public static boolean validarDNI(String dni) {
        if (dni == null || dni.length() != 9) {
            return false;
        }

        String numeroParte = dni.substring(0, 8);
        char letraParte = dni.charAt(8);

        if (!numeroParte.matches("\\d{8}")) {
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(numeroParte);
        char letraCorrecta = letras.charAt(numero % 23);

        return Character.toUpperCase(letraParte) == letraCorrecta;
    }
    
}
