package cz.cvut.fit.tjv.smejkdo1.data.dao;

import cz.cvut.fit.tjv.smejkdo1.data.model.Player;
import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.PlayerDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PlayerDao {
    @PersistenceContext
    private EntityManager em;

    public void persist(Player player) {
        em.persist(player);
    }

    public void persist(PlayerDto dto) {
        Player player = dto.toPlayer();
        em.persist(player);
        if (dto.getTeam() != null && em.find(Roster.class, dto.getTeam()) != null)
            setRoster(player.getId(), dto.getTeam());
    }

    public void update(long id, PlayerDto dto){
        Player player = em.find(Player.class, id);
        player.setNickname(dto.getNickname());
        player.setFirstName(dto.getFirstName());
        player.setLastName(dto.getLastName());
        em.merge(player);
        if (dto.getTeam() != null && em.find(Roster.class, dto.getTeam()) != null)
            setRoster(id, dto.getTeam());
    }

    public List findAll() {
        return em.createQuery("SELECT p FROM player p").getResultList();
    }

    public List<Player> findByNickname(String name) {
        return em.createQuery("SELECT t FROM player t WHERE t.nickname = :n", Player.class)
                .setParameter("n", name).getResultList();
    }

    public List<Player> findByDto(PlayerDto playerDto) {
        return em.createQuery(
                "SELECT t FROM player t WHERE t.nickname = :n AND t.firstName = :f AND t.lastName = :l",
                Player.class).setParameter("n", playerDto.getNickname())
                .setParameter("f", playerDto.getFirstName())
                .setParameter("l", playerDto.getLastName()).getResultList();
    }

    public Player findById(long id) {
        return em.find(Player.class, id);
    }


    public void setRoster(long id, Roster roster) {
        Player player = em.find(Player.class, id);
        roster.addPlayer(player);
        em.merge(player);
        em.merge(roster);
    }

    public void setRoster(long id_player, long id_roster) {
        Player player = em.find(Player.class, id_player);
        Roster roster = em.find(Roster.class, id_roster);
        if (roster == null){
            if (player.getTeam() != null)
                player.getTeam().removePlayer(player);
            player.setTeam(null);
            return;
        }
        roster.addPlayer(player);
        em.merge(player);
        em.merge(roster);
    }

    public void delete(long id) {
        Player player = em.find(Player.class, id);
        if (player.getTeam() != null){
            Roster roster = em.find(Roster.class, player.getTeam());
            roster.removePlayer(player);
            em.merge(roster);
        }
        em.remove(player);
    }
}
