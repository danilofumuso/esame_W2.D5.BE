package it.epicode.esame_W5.BE;

import it.epicode.esame_W5.BE.postazione.Postazione;
import it.epicode.esame_W5.BE.postazione.PostazioneRepository;
import it.epicode.esame_W5.BE.prenotazione.Prenotazione;
import it.epicode.esame_W5.BE.prenotazione.PrenotazioneGiaEffettuataException;
import it.epicode.esame_W5.BE.prenotazione.PrenotazioneRepository;
import it.epicode.esame_W5.BE.utente.Utente;
import it.epicode.esame_W5.BE.utente.UtenteNotFoundException;
import it.epicode.esame_W5.BE.utente.UtenteRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


@Component
@Order(5)
@Transactional
public class MainRunner implements ApplicationRunner {

    public Logger LOGGER = LoggerFactory.getLogger(MainRunner.class);
    public Scanner scanner = new Scanner(System.in);

    @Autowired
    public UtenteRepository utenteRepository;

    @Autowired
    public PostazioneRepository postazioneRepository;

    @Autowired
    public PrenotazioneRepository prenotazioneRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("Benvenuto nel sistema di prenotazione postazioni!");
        boolean esecuzione = true;

        while (esecuzione) {
            try {
                System.out.println("Inserisci il tuo userId");
                Long userId = scanner.nextLong();
                scanner.nextLine();
                Optional<Utente> utente = utenteRepository.findById(userId);
                if (utente.isEmpty()) {
                    throw new UtenteNotFoundException("Utente non presente in dataBase");

                }
                System.out.println("Salve " + utente.orElse(null).getNome() + " " + utente.orElse(null).getCognome());
                System.out.println("Inserisci la data in cui vuoi prenotare la postazione YYYY-MM-DD");
                LocalDate dataPrenotazione = LocalDate.parse(scanner.nextLine());
                System.out.println("Inserisci la città della postazione");
                String citta = scanner.nextLine();
                List<Postazione> postazioniDisponibili = postazioneRepository.findPostazioniDisponibiliByDataAndCitta(dataPrenotazione, citta);
                System.out.println("Ecco le postazioni disponibili nella tua città: ");
                for (Postazione postazione : postazioniDisponibili) {
                    System.out.println("postazioneId: " + postazione.getId() + " Ambiente: " + postazione.getTipoPostazione() + " Descrizione postazione: " + postazione.getDescrizione() + " Numero posti totali: " + postazione.getNumeroPostiMax());
                    System.out.println();
                }
                System.out.println("Seleziona la postazione scegliendo l'id");
                Long postazioneId = scanner.nextLong();
                scanner.nextLine();

                List<Prenotazione> prenotazioniUtentePerData = prenotazioneRepository.findPrenotazioniByUtenteAndData(userId, dataPrenotazione);

                if (!prenotazioniUtentePerData.isEmpty()) {
                    throw new PrenotazioneGiaEffettuataException("Hai già effettuato una prenotazione per oggi!");
                }

                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setUtente(utente.orElse(null));
                prenotazione.setDataPrenotazione(dataPrenotazione);
                prenotazione.setDataScadenza(dataPrenotazione.plusDays(1));
                prenotazione.setPostazione(postazioneRepository.findById(postazioneId).orElse(null));
                prenotazioneRepository.save(prenotazione);
                System.out.println("Prenotazione andata a buon fine!");


            } catch (UtenteNotFoundException| PrenotazioneGiaEffettuataException | InputMismatchException e) {
                LOGGER.error(e.getMessage());
            }
            System.out.print("Devi prenotare un'altra postazione? si/no: ");
            String risposta = scanner.nextLine().toLowerCase();
            if (risposta.equals("no")) {
                System.out.println("Grazie, buona giornata!");
                esecuzione = false;
            } else if (!risposta.equals("si")) {
                System.out.println("Per favore digitare soltanto si o no!");
                break;
            }
        }
    }
}