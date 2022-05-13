package cz.cvut.fit.tjv.smejkdo1.rest;

import cz.cvut.fit.tjv.smejkdo1.application.RosterService;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.PlayerDto;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.RosterDto;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.TournamentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/rosters", produces = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class RosterController {
    @Autowired
    RosterService service = new RosterService();

    public boolean isComplete(RosterDto dto){
        return dto.getName() != null;
    }
    @PostMapping
    public HttpEntity<RosterDto> create(@RequestBody RosterDto dto)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "create");
        if (!isComplete(dto))
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        service.add(dto);
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);

    }


    @GetMapping("/{rosterId}")
    public HttpEntity<RosterDto> getById(@PathVariable long rosterId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "getById");
        RosterDto dto = RosterDto.toDto(service.findById(rosterId));
        if (dto == null)
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }

    @GetMapping
    public Collection<RosterDto> getAll()
    {
        return service.listAll().stream()
                .map(RosterDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{rosterId}", method = RequestMethod.PUT)
    public HttpEntity<RosterDto> updateOrCreate(
            @PathVariable long rosterId,
            @RequestBody RosterDto dto)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "create");
        if (!isComplete(dto))
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        service.updateOrCreate(rosterId, dto);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/{rosterId}", method = RequestMethod.DELETE)
    public HttpEntity<RosterDto> delete(
            @PathVariable long rosterId)
    {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "delete");
        try {
            service.delete(rosterId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{rosterId}/tournaments/{tournamentId}", method = RequestMethod.PUT)
    public HttpEntity<RosterDto> addTournament(@PathVariable long rosterId,
                                               @PathVariable long tournamentId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "addTournament");
        try {
            service.addTournament(rosterId, tournamentId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{rosterId}/tournaments", method = RequestMethod.GET)
    public List<TournamentDto> getTournaments(@PathVariable long rosterId)
    {
        try{
        return service.listTournaments(rosterId)
                .stream()
                .map(TournamentDto::toDto)
                .collect(Collectors.toList());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @RequestMapping(value = "/{rosterId}/tournaments/{tournamentId}", method = RequestMethod.DELETE)
    public HttpEntity<RosterDto> removeTournament(@PathVariable long rosterId,
                                           @PathVariable long tournamentId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "removeTournament");
        try {
            service.removeTournament(rosterId, tournamentId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{rosterId}/players/{playerId}", method = RequestMethod.PUT)
    public HttpEntity<RosterDto> addPlayer(@PathVariable long rosterId,
                                               @PathVariable long playerId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "addPlayer");
        try {
            service.addPlayer(rosterId, playerId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{rosterId}/players/", method = RequestMethod.GET)
    public List<PlayerDto> getPlayers(@PathVariable long rosterId)
    {
        try{
            return service.getPlayers(rosterId)
                    .stream()
                    .map(PlayerDto::toDto)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @RequestMapping(value = "/{rosterId}/players/{playerId}", method = RequestMethod.DELETE)
    public HttpEntity<RosterDto> removePlayer(@PathVariable long rosterId,
                                                  @PathVariable long playerId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "removePlayer");
        try {
            service.removePlayer(rosterId, playerId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }
}
