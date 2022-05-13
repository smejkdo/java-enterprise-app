package cz.cvut.fit.tjv.smejkdo1.rest;

import cz.cvut.fit.tjv.smejkdo1.application.PlayerService;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/players",
        produces = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class PlayerController {

    @Autowired
    PlayerService service = new PlayerService();

    private boolean isComplete(PlayerDto dto) {
        return dto.getNickname() != null && dto.getFirstName() != null && dto.getLastName() != null;
    }

    @GetMapping
    public Collection<PlayerDto> getAll()
    {
        return service.listAll().stream()
                .map(PlayerDto::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public HttpEntity<PlayerDto> create(@RequestBody PlayerDto dto)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "create");
        if (!isComplete(dto))
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        service.add(dto);
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
    }


    @GetMapping("/{playerId}")
    public HttpEntity<PlayerDto> getById(@PathVariable long playerId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "getById");
        PlayerDto dto = PlayerDto.toDto(service.listById(playerId));
        if (dto != null)
            return new ResponseEntity<>(dto, headers, HttpStatus.OK);
        else
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.PUT)
    public HttpEntity<PlayerDto> updateOrCreate(
            @PathVariable long playerId,
            @RequestBody PlayerDto dto)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "updateOrCreate");
        if (!isComplete(dto))
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        service.updateOrCreate(playerId, dto);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.DELETE)
    public HttpEntity<PlayerDto> delete(
            @PathVariable long playerId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "delete");
        try {
            service.delete(playerId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{playerId}/{rosterId}", method = RequestMethod.PUT)
    public HttpEntity<PlayerDto> setRoster(@PathVariable long playerId,
                                           @PathVariable long rosterId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "updateOrCreate");
        try{
            service.setRoster(playerId, rosterId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e){
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }





}
