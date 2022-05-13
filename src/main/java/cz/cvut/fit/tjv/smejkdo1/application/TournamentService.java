package cz.cvut.fit.tjv.smejkdo1.application;

import cz.cvut.fit.tjv.smejkdo1.data.dao.TournamentDao;
import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;
import cz.cvut.fit.tjv.smejkdo1.data.model.Tournament;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.TournamentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class TournamentService {
    @Autowired
    private TournamentDao tournamentDao;

    @Transactional
    public void add(Tournament tournament) {
        tournamentDao.persist(tournament);
    }

    @Transactional
    public void add(TournamentDto dto) {
        tournamentDao.persist(dto.toEntity());
    }

    @Transactional
    public void addAll(Collection<Tournament> tournaments) {
        for (Tournament tournament : tournaments) {
            tournamentDao.persist(tournament);
        }
    }

    @Transactional(readOnly = true)
    public List<Tournament> listAll() {
        return tournamentDao.findAll();
    }


    @Transactional
    public void addRoster(long id, Roster roster){
        tournamentDao.addRoster(id, roster);
    }

    @Transactional
    public void addRoster(long id_tournament, long id_roster){
        tournamentDao.addRoster(id_tournament, id_roster);
    }

    @Transactional
    public void removeRoster(long id_tournament, long id_roster){
        tournamentDao.removeRoster(id_tournament, id_roster);
    }

    @Transactional(readOnly = true)
    public Tournament findById(long tournamentId) {
        return tournamentDao.findById(tournamentId);
    }

    @Transactional
    public void updateOrCreate(long id, TournamentDto dto) {
        if(tournamentDao.findById(id) == null)
            tournamentDao.persist(dto.toEntity());
        else
            tournamentDao.update(id, dto);
    }
    @Transactional
    public void delete(long id) {
        tournamentDao.delete(id);
    }

    public Set<Roster> listRosters(long id) {
        return tournamentDao.listRosters(id);
    }
}
