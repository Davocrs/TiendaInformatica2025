/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Correcion del examen del 26 de febrero
 * @author alu03d
 */
public class Prueba5Marzo {
    
    Scanner sc=new Scanner(System.in);
    private ArrayList<Pedido> pedidos;
    private HashMap <String, Articulo> articulos;
    private HashMap <String, Cliente> clientes;
   
    public Prueba5Marzo() {
        pedidos = new ArrayList();
        articulos = new HashMap();
        clientes = new HashMap();
    }
    
    public static void main(String[] args) {
        Prueba5Marzo c = new Prueba5Marzo();
        //c.cargaDatos();
        c.clientesTxtLeer();
        c.menu();
        c.clientesTxtBackup();      
    }

    public void cargaDatos(){
        
       clientes.put("80580845T",new Cliente("80580845T","ANA ","658111111","ana@gmail.com"));
       clientes.put("36347775R",new Cliente("36347775R","ANTONIO","649222222","antonio@gmail.com"));
       clientes.put("63921307Y",new Cliente("63921307Y","AURORA","652333333","aurora@gmail.com"));
       clientes.put("53472775R",new Cliente("53472775R","EMILIO","649222222","emilio@gmail.com"));
       clientes.put("43211307Y",new Cliente("43211307Y","EVA","652333333","eva@gmail.com"));
       clientes.put("02337565Y",new Cliente("02337565Y","MATEO","634567890","mateo@gmail.com"));
       clientes.put("35445638M",new Cliente("35445638M","MARIA","633478990","maria@gmail.com"));
       
       articulos.put("1-11",new Articulo("1-11","RATON LOGITECH ST ",14,15));
       articulos.put("1-22",new Articulo("1-22","TECLADO STANDARD  ",9,18));
       articulos.put("2-11",new Articulo("2-11","HDD SEAGATE 1 TB  ",16,80));
       articulos.put("2-22",new Articulo("2-22","SSD KINGSTOM 256GB",9,70));
       articulos.put("2-33",new Articulo("2-33","SSD KINGSTOM 512GB",0,200));
       articulos.put("3-22",new Articulo("3-22","EPSON PRINT XP300 ",5,80));
       articulos.put("4-11",new Articulo("4-11","ASUS  MONITOR  22 ",5,100));
       articulos.put("4-22",new Articulo("4-22","HP MONITOR LED 28 ",5,180));
       articulos.put("4-33",new Articulo("4-33","SAMSUNG ODISSEY G5",12,580));
       
       LocalDate hoy = LocalDate.now();
       pedidos.add(new Pedido("80580845T-001/2024",clientes.get("80580845T"),hoy.minusDays(1), new ArrayList<>
        (List.of(new LineaPedido("1-11",3),new LineaPedido("4-22",3)))));                                                                                                                                                               
       pedidos.add(new Pedido("80580845T-002/2024",clientes.get("80580845T"),hoy.minusDays(2), new ArrayList<>
        (List.of(new LineaPedido("4-11",3),new LineaPedido("4-22",2),new LineaPedido("4-33",4)))));
       pedidos.add(new Pedido("36347775R-001/2024",clientes.get("36347775R"),hoy.minusDays(3), new ArrayList<>
        (List.of(new LineaPedido("4-22",1),new LineaPedido("2-22",3)))));
       pedidos.add(new Pedido("36347775R-002/2024",clientes.get("36347775R"),hoy.minusDays(5), new ArrayList<>
        (List.of(new LineaPedido("4-33",3),new LineaPedido("2-11",3)))));
       pedidos.add(new Pedido("63921307Y-001/2024",clientes.get("63921307Y"),hoy.minusDays(4), new ArrayList<>
        (List.of(new LineaPedido("2-11",5),new LineaPedido("2-33",3),new LineaPedido("4-33",2)))));
       pedidos.add(new Pedido("53472775R-001/2025",clientes.get("53472775R"),hoy, new ArrayList<>
        (List.of(new LineaPedido("1-11",2),new LineaPedido("2-11",2)))));
       pedidos.add(new Pedido("43211307Y-001/2025",clientes.get("43211307Y"),hoy, new ArrayList<>
        (List.of(new LineaPedido("4-33",1)))));

    } 
    
    public void clientesTxtBackup() {
        
    ArrayList<Cliente> clientesSin = new ArrayList<>();
    ArrayList<Cliente> clientesCon = new ArrayList<>();
    ArrayList<Cliente> clientesMas1000 = new ArrayList<>();   

    try (BufferedWriter bfwClientesCon = new BufferedWriter(new FileWriter("clientesCon.csv"));
         BufferedWriter bfwClientesSin = new BufferedWriter(new FileWriter("clientesSin.csv"));
         BufferedWriter bfwClientesMas1000 = new BufferedWriter(new FileWriter("clientesMas1000.csv")))
         
    {
        
        for (Cliente c : clientes.values()) {
        /* ESTILO CLASICO
            boolean tienePedido = false;
            for (Pedido p : pedidos){
                if (p.getClientePedido() == c){
                    tienePedido = true;
                    break;
                }
            }
            if (tienePedido){
                bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
            } else {
                bfwClientesSin.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
            }*/
        
         // ESTILO CON STREAMS Y EL METODO ANYMATCH
        if (totalCliente(c)>=1000){
            bfwClientesMas1000.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
            bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
        }else if (totalCliente(c)>0){
            bfwClientesCon.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n");
            }else{
               bfwClientesSin.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() + "\n"); 
            }      
        }
        
    } catch (IOException e) {
        System.out.println("Error al escribir los archivos: " + e.getMessage());
    }
        System.out.println("Copia de seguridad realizada");
    }

    public void clientesTxtLeer() {

        List<Cliente> clientesSin = new ArrayList<>();
        List<Cliente> clientesCon = new ArrayList<>();
        List<Cliente> clientesMas1000 = new ArrayList<>();

        try (Scanner scClientesSin = new Scanner(new File("clientesSin.csv"))) {
            while (scClientesSin.hasNextLine()) {
                String[] atributos = scClientesSin.nextLine().split("[,]");
                Cliente c = new Cliente(atributos[0], atributos[1], atributos[2], atributos[3]);
                clientesSin.add(c);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        try (Scanner scClientesCon = new Scanner(new File("clientesCon.csv"))) {
            while (scClientesCon.hasNextLine()) {
                String[] atributos = scClientesCon.nextLine().split("[,]");
                Cliente c = new Cliente(atributos[0], atributos[1], atributos[2], atributos[3]);
                clientesCon.add(c);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        try (Scanner scClientesMas1000 = new Scanner(new File("clientesMas1000.csv"))) {
            while (scClientesMas1000.hasNextLine()) {
                String[] atributos = scClientesMas1000.nextLine().split("[,]");
                Cliente c = new Cliente(atributos[0], atributos[1], atributos[2], atributos[3]);
                clientesMas1000.add(c);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        System.out.println("\nCLIENTES CON PEDIDOS:");
        clientesCon.forEach(System.out::println);
        System.out.println("\nCLIENTES SIN PEDIDOS:");
        clientesSin.forEach(System.out::println);
        System.out.println("\nCLIENTES CON PEDIDOS DE MAS DE 1000 EUROS GASTADOS:");
        clientesMas1000.forEach(System.out::println);
    }

    public double totalPedido(Pedido p){
        double total=0;
        for (LineaPedido l:p.getCestaCompra())
        {
            total+=(articulos.get(l.getIdArticulo()).getPvp())*l.getUnidades();
        }
        return total;
    }
    
    public double totalCliente(Cliente c){
        double total=0;
        for (Pedido p : pedidos){
            if (p.getClientePedido().equals(c)){
                total+=totalPedido(p);
            } 
        }
        return total;
    }
    
    public void clientePedido(){
        try(BufferedWriter bfwClientePedido = new BufferedWriter(new FileWriter("clientePedido.csv"))){
            for (Cliente c : clientes.values())
                bfwClientePedido.write(c.getDni() + "," + c.getNombre() + "," + c.getTelefono() + "," + c.getEmail() 
                        + "," + pedidos.stream().filter(p->p.getClientePedido().equals(c)).count() + "\n"); 
        }catch (FileNotFoundException e) {
                 System.out.println(e.toString());
        }catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    
  /*int cuentaPedidos (Cliente c){
        int contador = 0;
        for (Pedido p : pedidos)
            if(p.getClientePedido().equals(c)){
                contador++;
            }
        return contador;
    }*/
      
    public void menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Buckup clientes");
            System.out.println("2. Importar clientes");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    clientesTxtBackup();
                    break;
                case 2:
                    clientesTxtLeer();
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }  
    
}
