/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

/**
 *
 * @author alu03d
 */
import java.util.Comparator;

public class ComparaClientesPorOrden implements Comparator<Cliente> {
    @Override
    public int compare(Cliente c1, Cliente c2) {
        return c1.getNombre().compareToIgnoreCase(c2.getNombre());
    }
}

