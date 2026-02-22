package az.developia.demo.Request;

import az.developia.demo.Entity.AboneEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VideoRequest {

    private String name;
    private String description;
    private Long ageLimit;

    private Boolean active = true;

    private String uploadedBy;

}
