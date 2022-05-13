package cz.cvut.fit.tjv.smejkdo1.rest.dto;

import cz.cvut.fit.tjv.smejkdo1.data.model.Player;

import java.util.Objects;

public class PlayerDto {
    private Long pid;
    private String nickname;
    private String firstName;
    private String lastName;
    private Long team;

    public static PlayerDto toDto(Player player){
        if (player == null)
            return null;
        PlayerDto playerDto = new PlayerDto();
        playerDto.setPid(player.getId());
        playerDto.setNickname(player.getNickname());
        playerDto.setFirstName(player.getFirstName());
        playerDto.setLastName(player.getLastName());
        if (player.getTeam() != null)
            playerDto.setTeam(player.getTeam().getId());
        else
            playerDto.setTeam(null);
        return playerDto;
    }

    public Player toPlayer(){
        return new Player(nickname, firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerDto)) return false;
        PlayerDto playerDto = (PlayerDto) o;
        return pid.equals(playerDto.pid) &&
                nickname.equals(playerDto.nickname) &&
                firstName.equals(playerDto.firstName) &&
                lastName.equals(playerDto.lastName) &&
                Objects.equals(team, playerDto.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, nickname, firstName, lastName, team);
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }


}
