package az.developia.demo.Mapper;


import az.developia.demo.Entity.AboneEntity;
import az.developia.demo.Response.AboneResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AboneMapper {
    public static AboneResponse toDTO(AboneEntity aboneEntity){
        AboneResponse aboneResponse = new AboneResponse();
        aboneResponse.setId(aboneEntity.getId());
        aboneResponse.setName(aboneEntity.getName());



        return aboneResponse;
    }
    public static List<AboneResponse> toDTOLIst(List<AboneEntity> aboneEntities){
        return aboneEntities.stream().map(AboneMapper::toDTO).collect(Collectors.toList());
    }
}
