package it.epicode.esame_W5.BE.postazione;

import it.epicode.esame_W5.BE.edificio.Edificio;
import it.epicode.esame_W5.BE.prenotazione.Prenotazione;
import it.epicode.esame_W5.BE.utente.Utente;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "postazioni")
public class Postazione {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String descrizione;

    @Column(name = "tipo_postazione", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPostazioneEnum tipoPostazione;

    @Column(name = "numero_posti_max", nullable = false)
    private int numeroPostiMax;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;

    @OneToMany(mappedBy = "postazione")
    @ToString.Exclude
    List<Prenotazione> prenotazioni = new ArrayList<>();

}
