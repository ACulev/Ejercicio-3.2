package com.mycompany.fabula;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class PrimaryController {

    @FXML ProgressBar barraLiebre;

    @FXML ProgressBar barraTortuga;

    @FXML Label lblLiebre;

    @FXML Label lblTortuga;

    @FXML Label lblResultado;

    @FXML Button btnIniciar;

    private volatile boolean carreraIniciada = false;

    @FXML
    private void iniciarCarrera() {
        if (carreraIniciada) {
            return;
        }

        carreraIniciada = true;
        btnIniciar.setDisable(true);

        barraLiebre.setProgress(0);
        barraTortuga.setProgress(0);
        lblLiebre.setText("0%");
        lblTortuga.setText("0%");
        lblResultado.setText("¡La carrera ha comenzado!");

        Thread hiloLiebre = new Thread(() -> correrLiebre());
        Thread hiloTortuga = new Thread(() -> correrTortuga());

        hiloLiebre.setDaemon(true);
        hiloTortuga.setDaemon(true);

        hiloLiebre.start();
        hiloTortuga.start();
    }

    private void correrLiebre() {
        double avance = 0;

        while (avance < 1.0) {
            try {
                Thread.sleep(100);
                avance += 0.025;

                if (avance > 1.0) {
                    avance = 1.0;
                }

                final double progreso = avance;

                Platform.runLater(() -> {
                    barraLiebre.setProgress(progreso);
                    lblLiebre.setText(
                        (int) (progreso * 100) + "%"
                    );
                });

                if (avance >= 0.30 && avance < 0.33) {
                    Platform.runLater(() ->
                        lblResultado.setText(
                            "¡La liebre se ha dormido! La tortuga sigue avanzando."
                        )
                    );

                    Thread.sleep(5000);

                    Platform.runLater(() ->
                        lblResultado.setText(
                            "¡La liebre despertó! Intenta alcanzar a la tortuga."
                        )
                    );
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        Platform.runLater(() -> {
            lblResultado.setText("¡La liebre llegó a la meta!");
            finalizarCarrera();
        });
    }

    private void correrTortuga() {
        double avance = 0;

        while (avance < 1.0) {
            try {
                Thread.sleep(150);
                avance += 0.012;

                if (avance > 1.0) {
                    avance = 1.0;
                }

                final double progreso = avance;

                Platform.runLater(() -> {
                    barraTortuga.setProgress(progreso);
                    lblTortuga.setText(
                        (int) (progreso * 100) + "%"
                    );
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        Platform.runLater(() -> {
            lblResultado.setText("¡La tortuga llegó a la meta!");
            finalizarCarrera();
        });
    }

    private void finalizarCarrera() {
        if (!carreraIniciada) {
            return;
        }

        carreraIniciada = false;
        btnIniciar.setDisable(false);
    }
}
