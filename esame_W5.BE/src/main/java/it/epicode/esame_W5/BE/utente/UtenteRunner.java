package it.epicode.esame_W5.BE.utente;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Order(1)
public class UtenteRunner implements ApplicationRunner {

    private final UtenteRepository utenteRepository;
    private final ObjectProvider<Utente> utenteProvider;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (utenteRepository.count() == 0) {
            for (int i = 0; i < 10; i++) {
                Utente utente = utenteProvider.getObject();
                utenteRepository.save(utente);
            }
        }
    }
}
