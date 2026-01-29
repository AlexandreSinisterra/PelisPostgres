package org.example.controller;

import org.example.model.Peliculas;
import org.example.service.PeliculaService;
import org.example.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestPeliculas.MAPPING)
public class RestPeliculas {

    public static final String MAPPING = "/postgres/peliculas";

    @Autowired
    private PeliculaService peliculaService;
    @Autowired
    private ActorService actorService;

    @GetMapping
    public List<Peliculas> getAll() {
        return peliculaService.obterTodosPeliculass();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peliculas> getById(@PathVariable Long id) {
        return peliculaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Peliculas> create(@RequestBody Peliculas peliculas) { //acepta crear xogadores no peliculas porque se crea 1º peliculas
        Peliculas gardado = peliculaService.save(peliculas);
        return ResponseEntity.ok(gardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peliculas> update(@PathVariable Long id,
                                         @RequestBody Peliculas datos) {
      /*  return peliculasService.findById(id)
                .map(e -> {
                    e.setNome(datos.getNome());
                    e.setCidade(datos.getCidade());
                    return ResponseEntity.ok(peliculasService.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
        */

        var peliculasOptional= peliculaService.findById(id);
        if(peliculasOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Peliculas peliculasToUpdate = peliculasOptional.get();
        peliculasToUpdate.setAno(datos.getAno());
        peliculasToUpdate.setTitulo(datos.getTitulo());
        peliculasToUpdate.setXenero(datos.getXenero());
        peliculasToUpdate = peliculaService.save(peliculasToUpdate);

        return ResponseEntity.ok(peliculasToUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!peliculaService.existe(id)) {
            return ResponseEntity.notFound().build();
        }
        peliculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
