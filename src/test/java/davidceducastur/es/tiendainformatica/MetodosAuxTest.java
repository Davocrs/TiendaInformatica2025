/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

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
public class MetodosAuxTest {
    
    public MetodosAuxTest() {
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

    @Test
    public void testEsInt() {
        System.out.println("Test para el metodo esInt");
        assertTrue(MetodosAux.esInt("-5"),"El -5 es Int");
        assertTrue(MetodosAux.esInt("5"),"El 5 es Int");
        assertFalse(MetodosAux.esInt("5.5"),"El 5.5 no es Int");
        assertFalse(MetodosAux.esInt("xty"),"xty no es Int");
        
    }

    @Test
    public void testEsDouble() {
        System.out.println("Test para el metodo esDoble");
    }

    @Test
    public void testValidarDNI() {
        System.out.println("Test para el metodo ValidarDni");
    }
    
}
