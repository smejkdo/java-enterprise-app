package cz.cvut.fit.tjv.smejkdo1.application;

import cz.cvut.fit.tjv.smejkdo1.data.dao.RosterDao;
import cz.cvut.fit.tjv.smejkdo1.data.model.Player;
import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;
import cz.cvut.fit.tjv.smejkdo1.data.model.Tournament;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.RosterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class RosterService {
    @Autowired
    private RosterDao rosterDao;

    @Transactional
    public void add(Roster roster) {
        rosterDao.persist(roster);
    }
    @Transactional
    public void add(RosterDto dto) {
        rosterDao.persist(dto.toEntity());
    }

    @Transactional
    public void addAll(Collection<Roster> rosters) {
        for (Roster roster : rosters) {
            rosterDao.persist(roster);
        }
    }
    @Transactional
    public void addPlayer(Long id, Player player){
        rosterDao.addPlayer(id, player);
    }

    @Transactional
    public void addPlayer(Roster roster, Player player){
        rosterDao.addPlayer(roster.getId(), player);
    }

    /**
     * Add player by its id, that is already in db.
     * @param id_roster
     * @param id_player
     */
    @Transactional
    public void addPlayer(long id_roster, long id_player){
        rosterDao.addPlayer(id_roster, id_player);
    }

    @Transactional
    public void addTournament(long id, Tournament tournament){
        rosterDao.addTournament(id, tournament);
    }

    @Transactional
    public void addTournament(long id_roster, long id_tournament){
        rosterDao.addTournament(id_roster, id_tournament);
    }

    @Transactional(readOnly = true)
    public List<Roster> listAll() {
        return rosterDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Roster> listByName(String name) {
        return rosterDao.findByName(name);
    }

    @Transactional(readOnly = true)
    public Roster findById(long id) {
        return rosterDao.findById(id);
    }

    @Transactional
    public void updateOrCreate(long id, RosterDto dto) {
        if(rosterDao.findById(id) == null)
            rosterDao.persist(dto.toEntity());
        else
            rosterDao.update(id, dto);
    }
    @Transactional
    public void delete(long id) {
        rosterDao.delete(id);
    }
    @Transactional
    public void removeTournament(long rosterId, long tournamentId) {
        rosterDao.removeTournament(rosterId, tournamentId);

    }
    @Transactional
    public void removePlayer(long rosterId, long playerId) {
        rosterDao.removePlayer(rosterId, playerId);

    }
    @Transactional(readOnly = true)
    public List<Player> getPlayers(long id) {
        return rosterDao.listAllPlayers(id);
    }
    @Transactional(readOnly = true)
    public Set<Tournament> listTournaments(long id) {
        return rosterDao.listTournaments(id);
    }
}
