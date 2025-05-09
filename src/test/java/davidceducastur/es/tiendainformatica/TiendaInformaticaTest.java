/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

import java.util.ArrayList;
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
    
    TiendaInformatica t = new TiendaInformatica();
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void antesDeCadaTest() {
        t.cargaDatos();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getClientes method, of class TiendaInformatica.
     */
    @Test
    public void testGetClientes() {
        System.out.println("getClientes");
        TiendaInformatica instance = new TiendaInformatica();
        HashMap<String, Cliente> expResult = null;
        HashMap<String, Cliente> result = instance.getClientes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setClientes method, of class TiendaInformatica.
     */
    @Test
    public void testSetClientes() {
        System.out.println("setClientes");
        HashMap<String, Cliente> clientes = null;
        TiendaInformatica instance = new TiendaInformatica();
        instance.setClientes(clientes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArticulos method, of class TiendaInformatica.
     */
    @Test
    public void testGetArticulos() {
        System.out.println("getArticulos");
        TiendaInformatica instance = new TiendaInformatica();
        HashMap<String, Articulo> expResult = null;
        HashMap<String, Articulo> result = instance.getArticulos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setArticulos method, of class TiendaInformatica.
     */
    @Test
    public void testSetArticulos() {
        System.out.println("setArticulos");
        HashMap<String, Articulo> articulos = null;
        TiendaInformatica instance = new TiendaInformatica();
        instance.setArticulos(articulos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPedidos method, of class TiendaInformatica.
     */
    @Test
    public void testGetPedidos() {
        System.out.println("getPedidos");
        TiendaInformatica instance = new TiendaInformatica();
        ArrayList<Pedido> expResult = null;
        ArrayList<Pedido> result = instance.getPedidos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPedidos method, of class TiendaInformatica.
     */
    @Test
    public void testSetPedidos() {
        System.out.println("setPedidos");
        ArrayList<Pedido> pedidos = null;
        TiendaInformatica instance = new TiendaInformatica();
        instance.setPedidos(pedidos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class TiendaInformatica.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        TiendaInformatica.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stock method, of class TiendaInformatica.
     */
    @Test
    public void testStock() throws Exception {
        assertAll(
          () -> assertThrows(StockInsuficiente.class, ()-> {t.stock("1-11",22);},"El stock es insuficiente"),
          () -> assertThrows(StockAgotado.class, ()-> {t.stock("2-33",5);}, "El stock esta agotado")
        );
    }

    /**
     * Test of generaIdPedido method, of class TiendaInformatica.
     */
    @Test
    public void testGeneraIdPedido() {
        System.out.println("generaIdPedido");
        String idCliente = "";
        TiendaInformatica instance = new TiendaInformatica();
        String expResult = "";
        String result = instance.generaIdPedido(idCliente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of nuevoPedido method, of class TiendaInformatica.
     */
    @Test
    public void testNuevoPedido() {
        System.out.println("nuevoPedido");
        TiendaInformatica instance = new TiendaInformatica();
        instance.nuevoPedido();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of totalPedido method, of class TiendaInformatica.
     */
    @Test
    public void testTotalPedido() {
        System.out.println("totalPedido");
        Pedido p = null;
        TiendaInformatica instance = new TiendaInformatica();
        double expResult = 0.0;
        double result = instance.totalPedido(p);
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of backup method, of class TiendaInformatica.
     */
    @Test
    public void testBackup() {
        System.out.println("backup");
        TiendaInformatica instance = new TiendaInformatica();
        instance.backup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of leerArchivos method, of class TiendaInformatica.
     */
    @Test
    public void testLeerArchivos() {
        System.out.println("leerArchivos");
        TiendaInformatica instance = new TiendaInformatica();
        instance.leerArchivos();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of backupPorSeccion method, of class TiendaInformatica.
     */
    @Test
    public void testBackupPorSeccion() {
        System.out.println("backupPorSeccion");
        TiendaInformatica instance = new TiendaInformatica();
        instance.backupPorSeccion();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of leerArchivosSeccion method, of class TiendaInformatica.
     */
    @Test
    public void testLeerArchivosSeccion() {
        System.out.println("leerArchivosSeccion");
        TiendaInformatica instance = new TiendaInformatica();
        instance.leerArchivosSeccion();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clientesTxtBackup method, of class TiendaInformatica.
     */
    @Test
    public void testClientesTxtBackup() {
        System.out.println("clientesTxtBackup");
        TiendaInformatica instance = new TiendaInformatica();
        instance.clientesTxtBackup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clientesTxtLeer method, of class TiendaInformatica.
     */
    @Test
    public void testClientesTxtLeer() {
        System.out.println("clientesTxtLeer");
        TiendaInformatica instance = new TiendaInformatica();
        instance.clientesTxtLeer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of backupPedidosClientes method, of class TiendaInformatica.
     */
    @Test
    public void testBackupPedidosClientes() {
        System.out.println("backupPedidosClientes");
        TiendaInformatica instance = new TiendaInformatica();
        instance.backupPedidosClientes();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of testTotalCliente method, of class TiendaInformatica.
     */
    @Test
    public void testTotalCliente() {
        assertAll(
          () -> assertEquals(585,  t.totalPedido(t.getPedidos().get(0))),
          () -> assertEquals(2980, t.totalPedido(t.getPedidos().get(1))),
          () -> assertEquals(390,  t.totalPedido(t.getPedidos().get(2))),
          () -> assertEquals(1980, t.totalPedido(t.getPedidos().get(3))),
          () -> assertEquals(2160, t.totalPedido(t.getPedidos().get(4)))
        );
    }

    /**
     * Test of cargaDatos method, of class TiendaInformatica.
     */
    @Test
    public void testCargaDatos() {
        assertAll(
          () -> assertEquals(9, t.getArticulos().size()),
          () -> assertEquals(7, t.getClientes().size()),
          () -> assertEquals(7, t.getPedidos().size())
        );
    }
    
}
