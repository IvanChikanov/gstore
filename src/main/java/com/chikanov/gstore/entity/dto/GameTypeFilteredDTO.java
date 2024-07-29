package com.chikanov.gstore.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameTypeFilteredDTO {
    @JsonProperty("coop")
    private List<GameTypeDTO> coop = new ArrayList<>();

    @JsonProperty("single")
    private List<GameTypeDTO> single = new ArrayList<>();
}
