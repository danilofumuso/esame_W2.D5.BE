package it.epicode.esame_W5.BE.postazione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione,Long> {


    @Query("SELECT p FROM Postazione p WHERE " +
            "(p.prenotazioni IS EMPTY OR " +
            "NOT EXISTS (SELECT pr FROM Prenotazione pr WHERE pr.postazione = p AND pr.dataPrenotazione = :dataPrenotazione)) " +
            "AND p.edificio.citta = :citta")
    List<Postazione> findPostazioniDisponibiliByDataAndCitta(@Param("dataPrenotazione") LocalDate dataPrenotazione, @Param("citta") String citta);


}
