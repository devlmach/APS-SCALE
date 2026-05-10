package aps.trabalho.SCALE_Software;

import aps.trabalho.SCALE_Software.view.InterfaceFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class ScaleSoftwareApplication {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new InterfaceFrame().setVisible(true));
		SpringApplication.run(ScaleSoftwareApplication.class, args);
	}

}
