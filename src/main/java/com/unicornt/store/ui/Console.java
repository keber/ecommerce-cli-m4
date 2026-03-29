package com.unicornt.store.ui;

import java.util.Scanner;

import com.unicornt.store.service.StoreService;

public class Console {

    private final ConsoleIO io;
    private final AdminMenu adminMenu;
    private final UserMenu  userMenu;

    public Console(StoreService service) {
        this.io        = new ConsoleIO(new Scanner(System.in, "UTF-8"));
        this.adminMenu = new AdminMenu(io, service);
        this.userMenu  = new UserMenu(io, service);
    }

    // ::: Entry point :::

    public void run() {
        printBanner();
        boolean running = true;
        while (running) {
            showMainMenu();
            int option = io.readInt("Selecciona una opcion: ");
            switch (option) {
                case 1  -> adminMenu.run();
                case 2  -> userMenu.run();
                case 0  -> { io.println("\nHasta pronto!"); running = false; }
                default -> io.warn("Opcion invalida. Intenta nuevamente.");
            }
        }
        io.close();
    }

    // ::: Main menu :::

    private void showMainMenu() {
        io.println("\n" + ConsoleIO.DLINE);
        io.println("  UNICORN'T STORE - Menu Principal");
        io.println(ConsoleIO.DLINE);
        io.println("  1) Administrador");
        io.println("  2) Usuario");
        io.println("  0) Salir");
        io.println(ConsoleIO.DLINE);
    }

    private void printBanner() {
        io.println("\n" + ConsoleIO.DLINE);
        io.println("   UNICORN'T STORE - Aplicacion de Consola");
        io.println("   Tienda de poleras geek/memes");
        io.println(ConsoleIO.DLINE);
    }
}
