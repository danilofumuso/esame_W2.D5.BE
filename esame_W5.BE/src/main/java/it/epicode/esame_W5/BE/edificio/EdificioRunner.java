package it.epicode.esame_W5.BE.edificio;

import it.epicode.esame_W5.BE.postazione.PostazioneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Order(2)
public class EdificioRunner implements ApplicationRunner {

    private final PostazioneRepository postazioneRepository;

    private final EdificioRepository edificioRepository;
    private final ObjectProvider<Edificio> edificioProvider;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (edificioRepository.count() == 0) {
            for (int i = 0; i < 3; i++) {
                Edificio edificio = edificioProvider.getObject();
                edificioRepository.save(edificio);
            }
        }
    }
}
