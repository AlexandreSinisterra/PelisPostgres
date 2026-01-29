package org.example.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "actores")
public class Actores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idactor")
    private Long idActor;

    @Column(name = "nome")
    private String nome;

    @Column(name = "apelidos")
    private String apelido;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @ManyToOne
    @JoinColumn(name = "idpelicula")
    @JsonBackReference
    private Peliculas peliculas;

    public Peliculas getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Peliculas peliculas) {
        this.peliculas = peliculas;
    }

    public Long getIdActor() {
        return idActor;
    }

    public void setIdActor(Long idActor) {
        this.idActor = idActor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
