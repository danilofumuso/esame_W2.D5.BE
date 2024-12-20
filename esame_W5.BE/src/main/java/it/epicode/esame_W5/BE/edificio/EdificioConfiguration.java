package it.epicode.esame_W5.BE.edificio;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
public class EdificioConfiguration {

    @Autowired
    private Faker faker;

    @Bean
    @Scope("prototype")
    public Edificio newEdificio(){
        Edificio edificio = new Edificio();
        edificio.setCitta(faker.address().cityName());
        edificio.setNome(faker.company().name());
        edificio.setIndirizzo(faker.address().streetAddress());
        return edificio;
    }

}
