package it.epicode.esame_W5.BE.prenotazione;

import it.epicode.esame_W5.BE.postazione.Postazione;
import it.epicode.esame_W5.BE.utente.Utente;
import jakarta.persistence.*;
import lombok.Data;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "data_prenotazione")
    private LocalDate dataPrenotazione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "postazione_id")
    private Postazione postazione;

}
