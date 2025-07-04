/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package davidceducastur.es.tiendainformatica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alu03d
 */
public class PracticaTienda {

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
        PracticaTienda t = new PracticaTienda();
        t.cargaDatos();
        t.menuPrincipal();
       
    }
    
    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Listado articulos por seccion");
            System.out.println("2. Total Pedido");
            System.out.println("3. Listado cliente por gasto");
            System.out.println("4. Listado articulo por stock");
            System.out.println("9. Salir");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    listadoArticulosPorSeccion();
                    break;
                case 2:
                    pedidosDeUnCliente();
                    break;
                case 3:
                    listadoClientesDeMayoraMenor();
                    break;
                case 4:
                    articulosConStock();
                    break;
            }
        } while (opcion != 9);
        sc.close();
    }
    
    public void listadoArticulosPorSeccion() {
        Scanner sc = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("\nELIJA SECCION PARA VER ARTICULOS (ENTER PARA SALIR):");
            System.out.println("1 - PERIFERICOS");
            System.out.println("2 - ALMACENAMIENTO");
            System.out.println("3 - IMPRESORAS");
            System.out.println("4 - MONITORES");
            System.out.println("5 - TODAS");
            System.out.print("Opcion: ");
            opcion = sc.nextLine();

            if (opcion.equals("1") || opcion.equals("2") || opcion.equals("3") || opcion.equals("4")) {
                System.out.println("\nARTICULOS DE LA SECCION: " + opcion);
                for (Articulo a : articulos.values()) {
                    if (a.getIdArticulo().startsWith(opcion)) {
                        System.out.println(a.getIdArticulo() + " - " + a.getDescripcion() + " - " + a.getPvp());
                    }
                }
            } else if (opcion.equals("5")) {
                System.out.println("\nARTICULOS DE TODAS LAS SECCIONES:");
                for (Articulo a : articulos.values()) {
                    System.out.println(a.getIdArticulo() + " - " + a.getDescripcion() + " - " + a.getPvp());
                }
            }

        } while (!opcion.equals(""));
    }

    public void pedidosDeUnCliente() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el ID del pedido: ");
        String id = sc.nextLine();

        for (Pedido p : pedidos) {
            if (p.getIdPedido().equalsIgnoreCase(id)) {
                double total = 0;

                System.out.println("Articulos del pedido " + id + ":");
                for (LineaPedido l : p.getCestaCompra()) {
                    Articulo a = articulos.get(l.getIdArticulo());
                    double subtotal = a.getPvp() * l.getUnidades();
                    total += subtotal;

                    System.out.println(a.getDescripcion() + " - " + a.getPvp() + " * " + l.getUnidades() + " = " + subtotal);
                }

                System.out.println("Total del pedido: " + total);
                return;
            }
        }
        System.out.println("No se encontro ningún pedido con ese ID.");
    }

    public void listadoClientesDeMayoraMenor(){
        clientes.values().stream().sorted(Comparator.comparing(Cliente::getNombre))
        .forEach(c -> System.out.println(c.getNombre() + " - " + c.getDni() + " - " + c.getEmail() +
            " - Total gastado: " + totalCliente(c)));
    }
    
    public void articulosConStock() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduce el stock maximo: ");
        int cantidad = sc.nextInt();
        
        for (Articulo a : articulos.values()) {
            if (a.getExistencias() < cantidad) {
                System.out.println(a.getDescripcion() + " - Stock: " + a.getExistencias() + " - " + a.getPvp());
            }
        }
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
        /* VERSIÓN CLÁSICA
        double total=0;
        for(Pedido p:pedidos){
            if (p.getClientePedido().equals(c)){
                total+=totalPedido(p);
            }
        }
        return total;*/
        
        /*VERSIÓN PROGRAMACIÓN FUNCIONAL */
        return pedidos.stream().filter(p-> p.getClientePedido().equals(c))
                .mapToDouble(p -> totalPedido(p)).sum();
    }
    
    public void stock(String id, int unidadesPed) throws StockAgotado, StockInsuficiente {
        int n = articulos.get(id).getExistencias();
        if (n == 0) {
            throw new StockAgotado("Stock AGOTADO para el articulo: " + articulos.get(id).getDescripcion());
        } else if (n < unidadesPed) {
            throw new StockInsuficiente("No hay Stock suficiente. Me pide  " + unidadesPed + " de "
                    + articulos.get(id).getDescripcion()
                    + " y sólo se dispone de: " + n);
        }
    }

    public String generaIdPedido(String idCliente) {
        int contador = 0;
        String nuevoId;
        for (Pedido p : pedidos) {
            if (p.getClientePedido().getDni().equalsIgnoreCase(idCliente)) {
                contador++;
            }
        }
        contador++;
        nuevoId = idCliente + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
        return nuevoId;
    }
    
    public void cargaDatos() {
        
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
    
}
