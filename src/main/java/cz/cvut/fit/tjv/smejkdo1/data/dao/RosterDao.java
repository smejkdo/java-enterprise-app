package cz.cvut.fit.tjv.smejkdo1.data.dao;

import cz.cvut.fit.tjv.smejkdo1.data.model.Player;
import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;
import cz.cvut.fit.tjv.smejkdo1.data.model.Tournament;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.RosterDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RosterDao {
    @PersistenceContext
    private EntityManager em;

    public void persist(Roster roster) {
        em.persist(roster);
    }

    public List findAll() {
        return em.createQuery("SELECT p FROM roster p").getResultList();
    }

    public List<Roster> findByName(String name) {
        return em.createQuery("SELECT t FROM roster t WHERE t.name = :n", Roster.class)
                .setParameter("n", name).getResultList();
    }

    public void addPlayer(long id, Player player) {
        Roster roster = em.find(Roster.class, id);
        roster.addPlayer(player);
        em.merge(roster);
        em.merge(player);
    }

    public void addPlayer(long id_roster, long id_player){
        Roster roster = em.find(Roster.class, id_roster);
        Player player = em.find(Player.class, id_player);
        roster.addPlayer(player);
        em.merge(roster);
        em.merge(player);
    }


    public void addTournament(Long id, Tournament tournament) {
        Roster roster = em.find(Roster.class, id);
        roster.addTournament(tournament);
        em.merge(roster);
        em.merge(tournament);
    }

    public void addTournament(Long id_roster, Long id_tournament) {
        Roster roster = em.find(Roster.class, id_roster);
        Tournament tournament = em.find(Tournament.class, id_tournament);
        roster.addTournament(tournament);
        em.merge(roster);
        em.merge(tournament);
    }

    public Roster findById(long id) {
        return em.find(Roster.class, id);
    }

    public void update(long id, RosterDto dto) {
        Roster roster = em.find(Roster.class, id);
        roster.setName(dto.getName());
        em.merge(roster);
    }

    public void delete(long id) {
        Roster roster = em.find(Roster.class, id);
        List<Player> list = new ArrayList<>(roster.getPlayers());
        list.forEach(roster::removePlayer);
        Set<Tournament> set = new HashSet<>(roster.getTournaments());
        set.forEach(roster::removeTournament);
        set.forEach(em::merge);
        list.forEach(em::merge);
        em.remove(roster);
    }

    public void removeTournament(long rosterId, long tournamentId) {
        Roster roster = em.find(Roster.class, rosterId);
        Tournament tournament = em.find(Tournament.class, tournamentId);
        roster.removeTournament(tournament);
        em.merge(tournament);
        em.merge(roster);
    }

    public void removePlayer(long rosterId, long playerId) {
        Roster roster = em.find(Roster.class, rosterId);
        Player player = em.find(Player.class, playerId);
        roster.removePlayer(player);
        em.merge(roster);
        em.merge(player);
    }

    public List<Player> listAllPlayers(long id) {
        return em.find(Roster.class, id).getPlayers();
    }

    public Set<Tournament> listTournaments(long id) {
        return em.find(Roster.class, id).getTournaments();
    }
}
