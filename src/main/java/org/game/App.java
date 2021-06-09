package org.game;

import org.game.window.Window;

/*
    TODO:
     - Finalizar colisiones con los edges o mejorar colisiones
     - Agregar IA (DONE)
     - Arreglar NPE cuando se apreta escape (DONE)
     - Arreglar menu principal (hover del mouse) (DONE)
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
