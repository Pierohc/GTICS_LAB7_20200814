package com.example.lab7_20200814.Controller;


import com.example.lab7_20200814.Entity.Players;
import com.example.lab7_20200814.Repository.PlayersRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MainController {



    final PlayersRepository playersRepository;
    public MainController(PlayersRepository playersRepository){
        this.playersRepository = playersRepository;
    }

    @GetMapping(value="listar")
    public List<Players> listar(){
        return playersRepository.findAll();
    }




    @PostMapping(value="crear_player")
    public ResponseEntity<HashMap<String, Object>> crear_player(
            @RequestBody Players player,
            @RequestParam(value="fetchId", required = false) boolean fetchId){

        System.out.println(player.getName());
        System.out.println(player.getMmr());
        System.out.println(player.getRegion());

        /* ID del nuevo Player */
        List<Players> listaOrdenada = playersRepository.listarDeMayorMenor();
        Integer idNuevoPlayer = listaOrdenada.get(0).getPlayerId() + 1;
        player.setPlayerId(idNuevoPlayer);


        /* Nueva Posición */



        Integer i = new Integer(0);

        List<Players> listarPlayersPorMmr = playersRepository.listarPlayersPorMmr();
        for (Players p : listarPlayersPorMmr) {



            if(p.getMmr() >= player.getMmr()){

                Integer nuevaPosicion = p.getPosition();
                player.setPosition(nuevaPosicion);

                System.out.println("Nueva posicion: " + nuevaPosicion  );
                //playersRepository.save(player);
                System.out.println("indice en que para: " + i);



                while(i <= listarPlayersPorMmr.size()-1){
                    System.out.println("posicion del resto: " + i);
                    i = i + 1;
                }



                break;
            }

            i = i + 1;

        }






        HashMap<String, Object> responseJson = new HashMap<>();





        if(fetchId) {
            responseJson.put("id", player.getPlayerId());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String,String>> gestionExcepcion(HttpServletRequest request){

        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST")){
            responseMap.put("estado","error");
            responseMap.put("msg","Debe enviar un producto");
        }
        return ResponseEntity.badRequest().body(responseMap);

    }




}
