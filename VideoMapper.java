package az.developia.demo.Mapper;

import az.developia.demo.Entity.VideoEntity;
import az.developia.demo.Response.VideoResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VideoMapper {
    public static VideoResponse toDTO(VideoEntity entity) {
        if (entity == null) return null; // Prevent NullPointerException

        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setId(entity.getId());
        videoResponse.setName(entity.getName());
        videoResponse.setDescription(entity.getDescription());
        videoResponse.setUploadedBy(entity.getUploadedBy());
        videoResponse.setAgeLimit(entity.getAgeLimit());
        videoResponse.setActive(entity.getActive());

        return videoResponse;
    }

    public static List<VideoResponse> toDTOList(List<VideoEntity> entities) {
        if (entities == null) return new ArrayList<>(); // Return empty list instead of null
        return entities.stream()
                .filter(e -> e != null) // Remove null entries
                .map(VideoMapper::toDTO)
                .collect(Collectors.toList());
    }
}