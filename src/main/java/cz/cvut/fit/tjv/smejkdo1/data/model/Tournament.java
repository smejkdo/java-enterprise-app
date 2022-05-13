package cz.cvut.fit.tjv.smejkdo1.data.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue
    private long id;

    private String organizer;
    private String city;
    private int year;

    @ManyToMany
    @JoinTable(name = "invitation",
            joinColumns ={
            @JoinColumn(name = "tournament_id")
            },
            inverseJoinColumns ={
            @JoinColumn(name = "roster_id")
    })
    private Set<Roster> rosters = new HashSet<>();

    public void addRoster(Roster roster){
        rosters.add(roster);
        if (!roster.getTournaments().contains(this))
            roster.addTournament(this);
    }
    public void removeRoster(Roster roster) {
        rosters.remove(roster);
        if (roster.getTournaments().contains(this))
            roster.removeTournament(this);
    }

    public Tournament(String organizer, String city, int year) {
        this.organizer = organizer;
        this.city = city;
        this.year = year;
    }

    public Tournament() {}

    @Override
    public String toString() {
        return "\n\tTournament{" +
                "id=" + id +
                ", organizer='" + organizer + '\'' +
                ", city='" + city + '\'' +
                ", year=" + year +
                ", rosters=\n" + rosters +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return id == that.id &&
                year == that.year &&
                organizer.equals(that.organizer) &&
                city.equals(that.city);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Roster> getRosters() {
        return rosters;
    }

    public void setRosters(Set<Roster> rosters) {
        this.rosters = rosters;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, organizer, city, year);
    }

}
