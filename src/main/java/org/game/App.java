package org.game;

import org.game.window.Window;

/*
    TODO:
     - Finalizar colisiones con los edges o mejorar colisiones (DONE)
     - Arreglar issues de colision con el centro de la paleta y con los bordes (DONE)
     - Agregar IA (DONE)
     - Arreglar NPE cuando se apreta escape (DONE)
     - Arreglar menu principal (hover del mouse) (DONE)
     - Agregar estado de Finalizacion (DONE)
     - Mejorar textos en el menu principal (DONE)
     - Arreglar issue de pantalla en blanco al principio
             - Agregar game launcher, Canvas y DoubleBuffering
     - Arreglar typos y comentar codigo (DONE)
     - Arreglar logging con SLF4J por consola (DONE)
     - Hacer que la pantalla aparezca centrada
*/
public class App {
    public static void main( String[] args ) {
       Window w = new Window();
       w.startWindow();
    }
}
