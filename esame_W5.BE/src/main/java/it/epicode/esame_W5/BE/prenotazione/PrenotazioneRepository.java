package it.epicode.esame_W5.BE.prenotazione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {

    @Query("SELECT pr FROM Prenotazione pr WHERE pr.utente.id = :utenteId")
    List<Prenotazione> findAllPrenotazioniByUtente(@Param("utenteId") Long utenteId);

    @Query("SELECT pr FROM Prenotazione pr WHERE pr.utente.id = :utenteId AND pr.dataPrenotazione = :dataPrenotazione")
    List<Prenotazione> findPrenotazioniByUtenteAndData(@Param("utenteId") Long utenteId, @Param("dataPrenotazione") LocalDate dataPrenotazione);


}
