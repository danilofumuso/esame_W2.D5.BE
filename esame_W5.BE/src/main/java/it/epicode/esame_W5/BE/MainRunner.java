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
                System.out.println();
                System.out.println("Salve " + utente.orElse(null).getNome() + " " + utente.orElse(null).getCognome());

                List<Prenotazione> prenotazioniEffettuate = prenotazioneRepository.findAllPrenotazioniByUtente(userId);

                System.out.println("Ecco le tue precedenti prenotazioni: ");
                System.out.println();

                for (Prenotazione prenotazione : prenotazioniEffettuate) {
                    System.out.println("Città: " + prenotazione.getPostazione().getEdificio().getCitta() + "\nEdificio: " + prenotazione.getPostazione().getEdificio().getNome() + " Ambiente: " + prenotazione.getPostazione().getTipoPostazione() + "\nData Prenotazione: " + prenotazione.getDataPrenotazione() + "\nLa prenotazione scadrà in data: " + prenotazione.getDataScadenza());
                    System.out.println();
                }

                System.out.println("Vuoi prenotare altre postazioni? si/no");
                String scelta = scanner.nextLine().toLowerCase();

                switch (scelta) {
                    case "si":
                        System.out.println("Inserisci la data in cui vuoi prenotare la postazione YYYY-MM-DD");
                        LocalDate dataPrenotazione = LocalDate.parse(scanner.nextLine());
                        System.out.println("Inserisci la città della postazione");
                        String citta = scanner.nextLine();
                        List<Postazione> postazioniDisponibili = postazioneRepository.findPostazioniDisponibiliByDataAndCitta(dataPrenotazione, citta);
                        System.out.println("Ecco le postazioni disponibili nella tua città per il " + dataPrenotazione + ": ");
                        System.out.println();

                        for (Postazione postazione : postazioniDisponibili) {
                            System.out.println("Id: " + postazione.getId() + "\nAmbiente: " + postazione.getTipoPostazione() + "\nDescrizione postazione: " + postazione.getDescrizione() + "\nNumero posti totali: " + postazione.getNumeroPostiMax());
                            System.out.println();
                        }

                        System.out.println("Seleziona la postazione scegliendo l'id");
                        Long postazioneId = scanner.nextLong();
                        scanner.nextLine();

                        List<Prenotazione> prenotazioniUtentePerData = prenotazioneRepository.findPrenotazioniByUtenteAndData(userId, dataPrenotazione);

                        if (!prenotazioniUtentePerData.isEmpty()) {
                            throw new PrenotazioneGiaEffettuataException("Hai già effettuato una prenotazione per oggi!");
                        }

                        System.out.println();
                        Prenotazione prenotazione = new Prenotazione();
                        prenotazione.setUtente(utente.orElse(null));
                        prenotazione.setDataPrenotazione(dataPrenotazione);
                        prenotazione.setDataScadenza(dataPrenotazione.plusDays(1));
                        prenotazione.setPostazione(postazioneRepository.findById(postazioneId).orElse(null));
                        prenotazioneRepository.save(prenotazione);
                        System.out.println("Prenotazione andata a buon fine!");
                        System.out.println();

                        break;

                    case "no":
                        System.out.println("Grazie per aver usato il nostro servizio di prenotazione!");
                        System.out.println();
                        return;

                    default:
                        throw new InputMismatchException("Per favore digitare soltanto si o no!");

                }


            } catch (UtenteNotFoundException | PrenotazioneGiaEffettuataException | InputMismatchException e) {
                LOGGER.error(e.getMessage());
            }
            System.out.print("Vuoi prenotare un'altra postazione? si/no: ");
            String risposta = scanner.nextLine().toLowerCase();
            if (risposta.equals("no")) {
                System.out.println("Grazie per aver usato il nostro servizio di prenotazione!");
                esecuzione = false;
            } else if (!risposta.equals("si")) {
                System.out.println("Per favore digitare soltanto si o no!");
                break;
            }
        }
    }
}