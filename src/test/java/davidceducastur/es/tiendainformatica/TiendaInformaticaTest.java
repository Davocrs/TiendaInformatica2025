/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alu03d
 */
public class TiendaInformaticaTest {
    
    public TiendaInformaticaTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    TiendaInformatica t = new TiendaInformatica();
    
    @Test
    public void testTotalPedido() {
        
        LocalDate hoy = LocalDate.now();
        System.out.println("totalPedido");
        
        Pedido p1 = new Pedido("80580845T-001/2024", t.getClientes().get("80580845T"), hoy.minusDays(1), new ArrayList<>
        (List.of(new LineaPedido("1-11", 3), new LineaPedido("4-22", 3))));
        Pedido p2 = new Pedido("80580845T-002/2024", t.getClientes().get("80580845T"), hoy.minusDays(2), new ArrayList<>
        (List.of(new LineaPedido("4-11", 3), new LineaPedido("4-22", 2), new LineaPedido("4-33", 4))));
        Pedido p3 = new Pedido("36347775R-001/2024", t.getClientes().get("36347775R"), hoy.minusDays(3), new ArrayList<>
        (List.of(new LineaPedido("4-22", 1), new LineaPedido("2-22", 3))));
        Pedido p4 = new Pedido("36347775R-002/2024", t.getClientes().get("36347775R"), hoy.minusDays(5), new ArrayList<>
        (List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3))));
        Pedido p5 = new Pedido("63921307Y-001/2024", t.getClientes().get("63921307Y"), hoy.minusDays(4), new ArrayList<>
        (List.of(new LineaPedido("2-11", 5), new LineaPedido("2-33", 3), new LineaPedido("4-33", 2))));
        
        assertEquals(585, t.totalPedido(p1));
        assertEquals(2980, t.totalPedido(p2));
        assertEquals(390, t.totalPedido(p3));
        assertEquals(1980, t.totalPedido(p4));
        assertEquals(2160, t.totalPedido(p5));
 
    }
    
}
