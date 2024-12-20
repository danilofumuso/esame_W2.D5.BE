package it.epicode.esame_W5.BE.utente;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class UtenteConfiguration {

    @Autowired
    private Faker faker;

    @Bean
    @Scope("prototype")
    public Utente newUtente(){
        Utente utente = new Utente();
        utente.setNome(faker.name().firstName());
        utente.setCognome(faker.name().lastName());
        utente.setEmail(faker.internet().emailAddress());
        utente.setUsername(faker.name().username());
        return utente;
    }

}
