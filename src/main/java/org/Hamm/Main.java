package org.Hamm;

public class Main {
    public static void main(String[] args) {
        // Lanza la primera instancia de la aplicación
        App.launch(App.class, args);

        // Abre una segunda instancia de la aplicación en otro hilo o método
        abrirSegundaInstancia();
    }

    public static void abrirSegundaInstancia() {
        new Thread(() -> {
            App.launch(App.class);
        }).start();
    }
}

