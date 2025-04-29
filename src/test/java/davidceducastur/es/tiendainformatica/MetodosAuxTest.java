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
        assertAll(
          () -> assertTrue(MetodosAux.esInt("-5"),"El -5 es Int"),
          () -> assertTrue(MetodosAux.esInt("5"),"El 5 es Int"),
          () -> assertTrue(MetodosAux.esInt("0"),"El 0 es Int"),
          () -> assertFalse(MetodosAux.esInt("5.5"),"El 5.5 no es Int"),
          () -> assertFalse(MetodosAux.esInt("xty"),"xty no es Int"),
          () -> assertFalse(MetodosAux.esInt(" "),"whitespace no es Int"),
          () -> assertFalse(MetodosAux.esInt(""),"null no es Int"),
          () -> assertFalse(MetodosAux.esInt("678492358723945"),"678492358723945 no es Int")
        );
        
      /*assertTrue(MetodosAux.esInt("-5"),"El -5 es Int");
        assertTrue(MetodosAux.esInt("5"),"El 5 es Int");
        assertTrue(MetodosAux.esInt("0"),"El 0 es Int");
        assertFalse(MetodosAux.esInt("5.5"),"El 5.5 no es Int");
        assertFalse(MetodosAux.esInt("xty"),"xty no es Int");
        assertFalse(MetodosAux.esInt(" "),"whitespace no es Int");
        assertFalse(MetodosAux.esInt(""),"null no es Int");
        assertFalse(MetodosAux.esInt("678492358723945"),"678492358723945 no es Int");*/
    }

    @Test
    public void testEsDouble() {
        System.out.println("Test para el metodo esDouble");
        assertTrue(MetodosAux.esDouble("-5"),"El -5 es Double");
        assertTrue(MetodosAux.esDouble("5"),"El 5 es Double");
        assertTrue(MetodosAux.esDouble("5.5"),"El 5.5 no es Double");
        assertTrue(MetodosAux.esDouble("678492358723945"),"678492358723945 es Double");
        assertFalse(MetodosAux.esDouble("xty"),"xty no es Double");
        assertFalse(MetodosAux.esDouble(" "),"whitespace no es Double");
        assertFalse(MetodosAux.esDouble(""),"null no es Double");
    }

    @Test
    public void testValidarDNI() {
        System.out.println("Test para el metodo ValidarDni");
        assertTrue(MetodosAux.validarDNI("80580845T"),"El dni existe");
        assertTrue(MetodosAux.validarDNI("36347775R"),"El dni existe");
        assertFalse(MetodosAux.validarDNI("23562354P"),"El dni no existe");
        assertFalse(MetodosAux.validarDNI("58973453A"),"El dni no existe");
    }
    
}
