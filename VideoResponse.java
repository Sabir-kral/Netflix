package az.developia.demo.Response;

import az.developia.demo.Entity.AboneEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VideoResponse {
    private Long id;

    private String name;
    private String description;
    private Long ageLimit;

    private Boolean active;

    private String uploadedBy;

}

