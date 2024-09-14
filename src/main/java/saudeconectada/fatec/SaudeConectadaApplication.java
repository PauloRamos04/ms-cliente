package saudeconectada.fatec;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import saudeconectada.fatec.infra.config.DotenvLoader;

@SpringBootApplication
public class SaudeConectadaApplication {
    public static void main(String[] args) {

        DotenvLoader.load();
        SpringApplication.run(SaudeConectadaApplication.class, args);
    }

}
