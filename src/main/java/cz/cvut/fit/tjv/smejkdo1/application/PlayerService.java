package cz.cvut.fit.tjv.smejkdo1.application;

import cz.cvut.fit.tjv.smejkdo1.data.dao.PlayerDao;
import cz.cvut.fit.tjv.smejkdo1.data.model.Player;
import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;
import cz.cvut.fit.tjv.smejkdo1.rest.dto.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerDao playerDao;

    @Transactional
    public void add(Player player) {
        playerDao.persist(player);
    }

    @Transactional
    public void add(PlayerDto dto) {
        playerDao.persist(dto);
    }

    @Transactional
    public void updateOrCreate(long id, PlayerDto dto) {
        if(playerDao.findById(id) == null)
            playerDao.persist(dto.toPlayer());
        else
            playerDao.update(id, dto);
    }

    @Transactional
    public void addAll(Collection<Player> players) {
        for (Player player : players) {
            playerDao.persist(player);
        }
    }

    @Transactional(readOnly = true)
    public Collection<Player> listAll() {
        return playerDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Player> listByNickname(String name) {
        return playerDao.findByNickname(name);
    }
    @Transactional(readOnly = true)
    public List<Player> listByDto(PlayerDto playerDto) {
        return playerDao.findByDto(playerDto);
    }

    @Transactional(readOnly = true)
    public Player listById(long id) {
        return playerDao.findById(id);
    }

    @Transactional
    public void setRoster(long id, Roster roster){
        playerDao.setRoster(id, roster);
    }
    @Transactional
    public void setRoster(long id_player, long id_roster){
        playerDao.setRoster(id_player, id_roster);
    }

    @Transactional
    public void delete(long id){ playerDao.delete(id);}
}
