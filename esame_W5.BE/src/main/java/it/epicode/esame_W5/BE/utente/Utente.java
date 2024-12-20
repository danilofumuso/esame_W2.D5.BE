package it.epicode.esame_W5.BE.utente;

import it.epicode.esame_W5.BE.prenotazione.Prenotazione;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "utente")
    @ToString.Exclude
    List<Prenotazione> prenotazioni = new ArrayList<>();

}
