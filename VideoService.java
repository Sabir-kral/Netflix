package az.developia.demo.Service;

import az.developia.demo.Entity.AboneEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Entity.VideoEntity;
import az.developia.demo.Mapper.VideoMapper;
import az.developia.demo.Repo.UserRepo;
import az.developia.demo.Repo.VideoRepo;
import az.developia.demo.Request.VideoRequest;
import az.developia.demo.Response.VideoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepo videoRepository;
    private final UserRepo userRepo;
    private final LogService logService;

    public VideoResponse addVideo(VideoRequest request) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setName(request.getName());
        videoEntity.setAgeLimit(request.getAgeLimit());
        videoEntity.setDescription(request.getDescription());
        videoEntity.setActive(request.getActive());
        videoEntity.setUploadedBy(username);

        videoRepository.save(videoEntity);
        logService.add("Video has added with name: "+videoEntity.getName(),"VIDEO_ADDED");


        return VideoMapper.toDTO(videoEntity);
    }

    public VideoResponse updateVideo(VideoRequest request, Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        videoEntity.setName(request.getName());
        videoEntity.setDescription(request.getDescription());
        videoEntity.setAgeLimit(request.getAgeLimit());
        videoEntity.setUploadedBy(username);
        videoEntity.setActive(request.getActive());

            videoRepository.save(videoEntity);

        logService.add("Video has updated with name: "+videoEntity.getName(),"VIDEO_UPDATED");
        return VideoMapper.toDTO(videoEntity);
    }

    public VideoEntity getVideo(Long id) {
         VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
         return videoEntity;
    }
    public List<VideoEntity> getMyVideos(){

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return videoRepository.findByUploadedBy(username);
    }


    public void deleteVideo(Long id) {
        VideoEntity videoEntity = videoRepository.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        videoRepository.delete(videoEntity);
        logService.add("Video has deleted with name: "+videoEntity.getName(),"VIDEO_DELETED");
    }

    public List<VideoEntity> getAll(){
       return videoRepository.getAllActive();
    }

    public List<VideoEntity> findByName(String name){
        return videoRepository.findByName(name);
    }
    public List<VideoEntity> findByAgeLimit(Long ageLimit){
        return videoRepository.findByAgeLimit(ageLimit);
    }

    public List<VideoResponse> findAll(){
        return VideoMapper.toDTOList(videoRepository.findAll());
    }



}
