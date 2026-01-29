package org.example.controller;

import org.example.model.Peliculas;
import org.example.model.Actores;
import org.example.service.PeliculaService;
import org.example.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestActores.MAPPING)
public class RestActores {

    public static final String MAPPING = "/postgres/actor";

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private ActorService actorService;


    @GetMapping
    public List<Actores> getAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actores> getById(@PathVariable Long id) {
        return actorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Actores> create(@RequestBody Actores actores) { //xogamos con recoller o peliculas e metelo en cada actores
/*{
  "nome": "string",
  "apelidos": "string",
  "posicion": "string",
  "dataNacemento": "2026-01-15",
  "nacionalidade": "string",
  "peliculas": {
    "id": "1"
  }
}*/
        if (actores.getPeliculas() != null && actores.getPeliculas().getIdPelicula() != null) {
            Peliculas eq = peliculaService.findById(actores.getPeliculas().getIdPelicula())
                    .orElse(null);
            if (eq == null) {
                return ResponseEntity.badRequest().build();
            }
            actores.setPeliculas(eq);
        }
        Actores gardado = actorService.save(actores);
        return ResponseEntity.ok(gardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actores> update(@PathVariable Long id,
                                          @RequestBody Actores datos) {
        return actorService.findById(id)
                .map(x -> {
                    x.setNome(datos.getNome());
                    x.setApelido(datos.getApelido());
                    x.setNacionalidade(datos.getNacionalidade());

                    if (datos.getPeliculas() != null && datos.getPeliculas().getIdPelicula() != null) {
                        Peliculas eq = peliculaService.findById(datos.getPeliculas().getIdPelicula())
                                .orElse(null);
                        x.setPeliculas(eq);
                    }

                    return ResponseEntity.ok(actorService.save(x));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!actorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}