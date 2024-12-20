package it.epicode.esame_W5.BE.postazione;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;

@Configuration
public class PostazioneConfiguration {

    @Autowired
    private Faker faker;

    @Bean(name = "postazioneTipoPrivato")
    @Scope("prototype")
    public Postazione newPostazioneTipoPrivato() {
        Postazione postazione = new Postazione();
        postazione.setTipoPostazione(TipoPostazioneEnum.PRIVATO);
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setNumeroPostiMax(faker.number().numberBetween(5, 20));
        return postazione;
    }

    @Bean(name = "postazioneTipoOpenSpace")
    @Scope("prototype")
    public Postazione newPostazioneTipoOpenSpace() {
        Postazione postazione = new Postazione();
        postazione.setTipoPostazione(TipoPostazioneEnum.OPENSPACE);
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setNumeroPostiMax(faker.number().numberBetween(20, 300));
        return postazione;
    }

    @Bean(name = "postazioneTipoSalaRiunioni")
    @Scope("prototype")
    public Postazione newPostazioneTipoSalaRiunioni() {
        Postazione postazione = new Postazione();
        postazione.setTipoPostazione(TipoPostazioneEnum.SALA_RIUNIONI);
        postazione.setDescrizione(faker.lorem().sentence());
        postazione.setNumeroPostiMax(faker.number().numberBetween(50, 200));
        return postazione;
    }

}
