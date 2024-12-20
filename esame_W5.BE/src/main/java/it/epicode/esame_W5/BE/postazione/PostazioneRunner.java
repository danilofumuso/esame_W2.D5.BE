package it.epicode.esame_W5.BE.postazione;

import it.epicode.esame_W5.BE.edificio.Edificio;
import it.epicode.esame_W5.BE.edificio.EdificioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(3)
@Transactional
public class PostazioneRunner implements ApplicationRunner {

    private final EdificioRepository edificioRepository;
    private final PostazioneRepository postazioneRepository;

    @Autowired
    @Qualifier("postazioneTipoPrivato")
    private ObjectProvider<Postazione> postazioneTipoPrivatoProvider;

    @Autowired
    @Qualifier("postazioneTipoOpenSpace")
    private ObjectProvider<Postazione> postazioneTipoOpenSpaceProvider;

    @Autowired
    @Qualifier("postazioneTipoSalaRiunioni")
    private ObjectProvider<Postazione> postazioneTipoSalaRiunioniProvider;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (postazioneRepository.count() == 0) {
            for (int i = 0; i < 3; i++) {
                Postazione postazione = postazioneTipoPrivatoProvider.getObject();
                postazioneRepository.save(postazione);
            }

            for (int i = 0; i < 3; i++) {
                Postazione postazione = postazioneTipoOpenSpaceProvider.getObject();
                postazioneRepository.save(postazione);
            }

            for (int i = 0; i < 3; i++) {
                Postazione postazione = postazioneTipoSalaRiunioniProvider.getObject();
                postazioneRepository.save(postazione);
            }

            Edificio edificio = edificioRepository.findById(1L).orElse(null);
            Edificio edificio2 = edificioRepository.findById(2L).orElse(null);
            Edificio edificio3 = edificioRepository.findById(3L).orElse(null);

            Postazione postazione = postazioneRepository.findById(1L).orElse(null);
            Postazione postazione2 = postazioneRepository.findById(2L).orElse(null);
            Postazione postazione3 = postazioneRepository.findById(3L).orElse(null);
            Postazione postazione4 = postazioneRepository.findById(4L).orElse(null);
            Postazione postazione5 = postazioneRepository.findById(5L).orElse(null);
            Postazione postazione6 = postazioneRepository.findById(6L).orElse(null);
            Postazione postazione7 = postazioneRepository.findById(7L).orElse(null);
            Postazione postazione8 = postazioneRepository.findById(8L).orElse(null);
            Postazione postazione9 = postazioneRepository.findById(9L).orElse(null);

            postazione.setEdificio(edificio);
            postazioneRepository.save(postazione);
            postazione2.setEdificio(edificio2);
            postazioneRepository.save(postazione2);
            postazione3.setEdificio(edificio3);
            postazioneRepository.save(postazione3);
            postazione4.setEdificio(edificio);
            postazioneRepository.save(postazione4);
            postazione5.setEdificio(edificio2);
            postazioneRepository.save(postazione5);
            postazione6.setEdificio(edificio3);
            postazioneRepository.save(postazione6);
            postazione7.setEdificio(edificio);
            postazioneRepository.save(postazione7);
            postazione8.setEdificio(edificio2);
            postazioneRepository.save(postazione8);
            postazione9.setEdificio(edificio3);
            postazioneRepository.save(postazione9);
        }
    }
}
