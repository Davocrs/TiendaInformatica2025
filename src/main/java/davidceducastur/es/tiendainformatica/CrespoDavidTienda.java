/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author alu03d
 */
public class CrespoDavidTienda {
    
    private  HashMap<String, Cliente> clientes = new HashMap<>();
    private  HashMap<String, Articulo> articulos = new HashMap<>();
    private  ArrayList<Pedido> pedidos = new ArrayList<>();

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(HashMap<String, Cliente> clientes) {
        this.clientes = clientes;
    }

    public HashMap<String, Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(HashMap<String, Articulo> articulos) {
        this.articulos = articulos;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public static void main(String[] args) {
        CrespoDavidTienda t = new CrespoDavidTienda();
        t.cargaDatos();
        t.menuPrincipal();       
    }
      
    
    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. BACKUP CLIENTES");
            System.out.println("2. COLECCION CLIENTES-PEDIDO");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    backupClientes();
                    break;
                case 2:
                    ColeccionClientesPedidos();
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
    
    public void backupClientes() {
        Scanner sc = new Scanner(System.in);
        boolean tienePedidos; 
        for (Cliente c:clientes.values()){
            tienePedidos=false;       
            for (Pedido p: pedidos ){
                if(p.getClientePedido().equals(c)){
                    tienePedidos=true;
                    break;
                }
            }
            if (tienePedidos){
                String archivo="PedidosCliente_" + c.getNombre()+".csv";
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo)))
                {
                   for (Pedido p: pedidos ){
                        if(p.getClientePedido().equals(c)) {
                            oos.writeObject(p);
                        }
                   }
                } catch (IOException e) {
                   System.out.println(e.toString());
                } 
                
            }
        }
        System.out.println("ARCHIVOS CREADOS CORRECTAMENTE\n");
        
        String dniT; 

        do{
            System.out.println("DNI CLIENTE:");
            dniT=sc.next().toUpperCase();    
        }while (!clientes.containsKey(dniT)||!MetodosAux.validarDNI(dniT));
        
        tienePedidos=false;       
        for (Pedido p: pedidos ){
            if(p.getClientePedido().equals(clientes.get(dniT))) {
                tienePedidos=true;
                break;
            }
        }
        
        if (tienePedidos){
            String archivo="PedidosCliente_" + clientes.get(dniT).getNombre()+".csv";
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo)))
            {
                Pedido p=null;
                while ( (p=(Pedido)ois.readObject()) != null){
                     System.out.println("\nPEDIDO: " + p.getIdPedido() + " DE: " + p.getClientePedido().getNombre());
                     for (LineaPedido l:p.getCestaCompra()){
                         System.out.println(articulos.get(l.getIdArticulo()).getDescripcion()
                                 + "\t Unidades: " +l.getUnidades());
                     }
                } 
            } catch (EOFException e) {

            } catch (IOException e) {
                System.out.println("No existen pedidos para ese DNI");
            } catch (ClassNotFoundException ex) {
            }
        }      
    }
    
    public void ColeccionClientesPedidos() {
    Map<String, Integer> coleccion = new HashMap<>();

    for (Cliente c : clientes.values()) {
        int contador = 0;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().equals(c)) {
                contador++;
            }
        }
        coleccion.put(c.getDni(), contador);
    }
        System.out.println(coleccion);
    }
  
    public void cargaDatos(){
       clientes.put("90015161S",new Cliente("90015161S","ANA ","658111111","ana@gmail.com"));
       clientes.put("96819473F",new Cliente("96819473F","BEA","649222222","antonio@gmail.com"));
       clientes.put("95767515T",new Cliente("95767515T","CARLOS","652333333","aurora@gmail.com"));
       clientes.put("97801164N",new Cliente("97801164N","DARIO","649222222","emilio@gmail.com"));
       clientes.put("88801164R",new Cliente("88801164R","EVA","652333333","eva@gmail.com"));
       clientes.put("77701164R",new Cliente("77701164R","VICTOR","652444444","victor@gmail.com"));
         
       
       articulos.put("1-11",new Articulo("1-11","RATON LOGITECH ST ",14,15));
       articulos.put("1-22",new Articulo("1-22","TECLADO STANDARD  ",9,18));
       articulos.put("2-11",new Articulo("2-11","HDD SEAGATE 1 TB  ",16,80));
       articulos.put("2-22",new Articulo("2-22","SSD KINGSTOM 256GB",0,70));
       articulos.put("2-33",new Articulo("2-33","SSD KINGSTOM 512GB",4,200));
       articulos.put("3-22",new Articulo("3-22","EPSON PRINT XP300 ",4,80));
       articulos.put("4-11",new Articulo("4-11","ASUS  MONITOR  22 ",10,110));
       articulos.put("4-22",new Articulo("4-22","HP MONITOR LED 28 ",5,180));
      
       LocalDate hoy = LocalDate.now();
       pedidos.add(new Pedido("90015161S-003/2025",clientes.get("90015161S"),hoy.minusDays(23), new ArrayList<>
        (List.of(new LineaPedido("2-33",5),new LineaPedido("4-11",5)))));                                                                                                                                                               
       pedidos.add(new Pedido("90015161S-002/2025",clientes.get("90015161S"),hoy.minusDays(32), new ArrayList<>
        (List.of(new LineaPedido("2-11",5)))));
       pedidos.add(new Pedido("90015161S-001/2025",clientes.get("90015161S"),hoy.minusDays(33), new ArrayList<>
        (List.of(new LineaPedido("1-11",5)))));
       pedidos.add(new Pedido("96819473F-001/2025",clientes.get("96819473F"),hoy.minusDays(21), new ArrayList<>
        (List.of(new LineaPedido("4-22",1),new LineaPedido("2-22",3)))));
       pedidos.add(new Pedido("95767515T-001/2025",clientes.get("95767515T"),hoy.minusDays(15), new ArrayList<>
        (List.of(new LineaPedido("1-11",3),new LineaPedido("2-11",3)))));
       pedidos.add(new Pedido("97801164N-001/2025",clientes.get("97801164N"),hoy.minusDays(10), new ArrayList<>
        (List.of(new LineaPedido("2-11",1),new LineaPedido("2-33",3),new LineaPedido("1-11",2)))));
       pedidos.add(new Pedido("88801164R-001/2025",clientes.get("88801164R"),hoy.minusDays(5), new ArrayList<>
        (List.of(new LineaPedido("2-11",2),new LineaPedido("2-33",2),new LineaPedido("1-11",1)))));
    } 
}
