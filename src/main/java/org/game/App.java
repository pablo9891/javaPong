package org.game;

import org.game.window.Window;

/*
    TODO:
     - Finalizar colisiones con los edges o mejorar colisiones
     - Agregar IA
     - Arreglar NPE cuando se apreta escape
     - Arreglar menu principal (hover del mouse)
     - Agregar estado de Finalizacion (DONE)
     - Mejorar textos en el menu principal (DONE)
     - Arreglar issue de pantalla en blanco al principio
*/
public class App {
    public static void main( String[] args ) {
       Window w = new Window();
       w.startWindow();
    }
}
