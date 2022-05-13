package cz.cvut.fit.tjv.smejkdo1.rest;

import cz.cvut.fit.tjv.smejkdo1.application.TournamentService;
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
@RequestMapping(value = "/api/tournaments", produces = {MediaTypes.HAL_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class TournamentController {
    @Autowired
    TournamentService service = new TournamentService();

    private boolean isComplete(TournamentDto dto) {
        return dto.getOrganizer() != null && dto.getCity() != null && dto.getYear() > 1900;
    }

    @PostMapping
    public HttpEntity<TournamentDto> create(@RequestBody TournamentDto dto)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "create");
        if (!isComplete(dto))
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        service.add(dto);
        return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);

    }


    @RequestMapping(value = "/{tournamentId}", method = RequestMethod.GET)
    public HttpEntity<TournamentDto> getById(@PathVariable long tournamentId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "getById");
        TournamentDto dto = TournamentDto.toDto(service.findById(tournamentId));
        if(dto == null)
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }

    @GetMapping
    public Collection<TournamentDto> getAll()
    {
        return service.listAll().stream()
                .map(TournamentDto::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{tournamentId}", method = RequestMethod.PUT)
    public HttpEntity<TournamentDto> updateOrCreate(
            @PathVariable long tournamentId,
            @RequestBody TournamentDto dto)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "create");
        if (!isComplete(dto))
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        service.updateOrCreate(tournamentId, dto);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value = "/{tournamentId}", method = RequestMethod.DELETE)
    public HttpEntity<TournamentDto> delete(
            @PathVariable long tournamentId)
    {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "delete");
        try {
            service.delete(tournamentId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{tournamentId}/rosters/{rosterId}", method = RequestMethod.PUT)
    public HttpEntity<TournamentDto> addRoster(@PathVariable long tournamentId,
                                           @PathVariable long rosterId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "addRoster");
        try {
            service.addRoster(tournamentId, rosterId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{tournamentId}/rosters", method = RequestMethod.GET)
    public List<RosterDto> getRosters(@PathVariable long tournamentId)
    {
        try{
            return service.listRosters(tournamentId)
                    .stream()
                    .map(RosterDto::toDto)
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @RequestMapping(value = "/{tournamentId}/rosters/{rosterId}", method = RequestMethod.DELETE)
    public HttpEntity<TournamentDto> removeRoster(@PathVariable long tournamentId,
                                               @PathVariable long rosterId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "removeRoster");
        try {
            service.removeRoster(tournamentId, rosterId);
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
    }

}
