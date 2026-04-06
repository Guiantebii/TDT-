package application;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import visuals.Menu;
import modulos.Memoria;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Menu menu = new Menu();

        menu.telaInicial();

    }

}
