package cz.cvut.fit.tjv.smejkdo1.rest.dto;

import cz.cvut.fit.tjv.smejkdo1.data.model.Tournament;

import java.util.Objects;

public class TournamentDto {

    private long tid;
    private String organizer;
    private String city;
    private int year;
    public static TournamentDto toDto(Tournament tournament){
        if (tournament == null)
            return null;
        TournamentDto tournamentDto = new TournamentDto();
        tournamentDto.setTid(tournament.getId());
        tournamentDto.setOrganizer(tournament.getOrganizer());
        tournamentDto.setCity(tournament.getCity());
        tournamentDto.setYear(tournament.getYear());

        return tournamentDto;
    }

    /**
     * asi nebude ani treba
     * @return
     */
    public Tournament toEntity(){
        return new Tournament(organizer, city, year);
        //tournament.setRosters();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TournamentDto)) return false;
        TournamentDto that = (TournamentDto) o;
        return tid == that.tid &&
                year == that.year &&
                organizer.equals(that.organizer) &&
                city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tid, organizer, city, year);
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
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
}
