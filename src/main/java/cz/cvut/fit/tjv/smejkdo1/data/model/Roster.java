package cz.cvut.fit.tjv.smejkdo1.data.model;

import javax.persistence.*;
import java.util.*;

@Entity(name = "roster")
public class Roster {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Player> players = new ArrayList<>();

    @ManyToMany(mappedBy = "rosters")
    private Set<Tournament> tournaments = new HashSet<>();

    public void addPlayer(Player player){
            players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player){
        players.remove(player);
        player.setTeam(null);
    }

    public void removeTournament(Tournament tournament){
        tournaments.remove(tournament);
        if (tournament.getRosters().contains(this))
            tournament.removeRoster(this);
    }

    public void addTournament(Tournament tournament){
        //if (!tournaments.contains(tournament))
            tournaments.add(tournament);
        if (!tournament.getRosters().contains(this))
            tournament.addRoster(this);
    }

    public Roster(String name) {
        this.name = name;
    }
    public Roster(){
    }

    @Override
    public String toString() {
        return "\n\tRoster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", players=\n" + players +
                "}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roster roster = (Roster) o;
        return id == roster.id &&
                name.equals(roster.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
