package cz.cvut.fit.tjv.smejkdo1.rest.dto;


import cz.cvut.fit.tjv.smejkdo1.data.model.Roster;

import java.util.Objects;

public class RosterDto {
    private long rid;
    private String name;

    public static RosterDto toDto(Roster roster){
        if (roster == null)
            return null;
        RosterDto dto = new RosterDto();
        dto.setRid(roster.getId());
        dto.setName(roster.getName());
        return dto;
    }


    /**
     * Nekopiruje ID!!
     * @return
     */
    public Roster toEntity(){
        return new Roster(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RosterDto)) return false;
        RosterDto rosterDto = (RosterDto) o;
        return rid == rosterDto.rid &&
                name.equals(rosterDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rid, name);
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
