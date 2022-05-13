package cz.cvut.fit.tjv.smejkdo1.data.model;


import javax.persistence.*;
import java.util.Objects;

@Entity(name = "player")
public class Player {
    @Id
    @GeneratedValue
    private long id;

    private String nickname;
    private String firstName;
    private String lastName;

    @ManyToOne(optional=true)
    @JoinColumn(name = "roster_id", nullable = true)
    private Roster team;

    public Player(String nickname, String firstName, String lastName) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Player() {
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", team=" + (team == null?"null":team.getName()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return id == player.id &&
                nickname.equals(player.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Roster getTeam() {
        return team;
    }

    public void setTeam(Roster team) {
        this.team = team;
    }
}
