package cz.cvut.fit.tjv.smejkdo1.data.dao;

import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;
import cz.cvut.fit.tjv.smejkdo1.data.model.Tournament;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.TournamentDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class TournamentDao {
    @PersistenceContext
    private EntityManager em;

    public void persist(Tournament tournament) {
        em.persist(tournament);
    }

    public void update(long id, TournamentDto dto){
        Tournament tournament = em.find(Tournament.class, id);
        tournament.setOrganizer(dto.getOrganizer());
        tournament.setCity(dto.getCity());
        tournament.setYear(dto.getYear());
        em.merge(tournament);
    }


    public List findAll() {
        return em.createQuery("SELECT p FROM tournament p").getResultList();
    }

    public void addRoster(long id, Roster roster) {
        Tournament tournament = em.find(Tournament.class, id);
        tournament.addRoster(roster);
        em.merge(tournament);
        em.merge(roster);
    }

    public void addRoster(long id_tournament, long id_roster) {
        Tournament tournament = em.find(Tournament.class, id_tournament);
        Roster roster = em.find(Roster.class, id_roster);
        tournament.addRoster(roster);
        em.merge(tournament);
        em.merge(roster);
    }

    public void removeRoster(long id_tournament, long id_roster) {
        Tournament tournament = em.find(Tournament.class, id_tournament);
        Roster roster = em.find(Roster.class, id_roster);
        tournament.removeRoster(roster);
        em.merge(tournament);
        em.merge(roster);
    }

    public Tournament findById(long tournamentId) {
        return em.find(Tournament.class, tournamentId);
    }

    public void delete(long id) {
        Tournament tournament = em.find(Tournament.class, id);
        Set<Roster> set = new HashSet<>(tournament.getRosters());
        set.forEach(roster -> roster.removeTournament(tournament));
        set.forEach(em::merge);
        em.remove(tournament);
    }

    public Set<Roster> listRosters(long id) {
        return em.find(Tournament.class, id).getRosters();
    }
}
