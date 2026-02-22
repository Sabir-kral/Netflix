package az.developia.demo.Controller;


import az.developia.demo.Entity.VideoEntity;
import az.developia.demo.Request.VideoRequest;
import az.developia.demo.Response.VideoResponse;
import az.developia.demo.Service.VideoService;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VideoController {

    private final VideoService videoService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public VideoResponse addVideo(@RequestBody VideoRequest video) {
        return videoService.addVideo(video);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_ADMIN')")
    public VideoResponse updateVideo(@PathVariable Long id,
                             @RequestBody VideoRequest request) {

        return videoService.updateVideo(request,id);
    }

    @GetMapping("/getAllWithActive")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public ResponseEntity<List<VideoResponse>> getAllWithActive(){
        try {
            List<VideoResponse> responses = videoService.findAll();
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{id}")
    public VideoEntity findById(@PathVariable Long id){
       return videoService.getVideo(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MANAGER')")
    public void delete(@PathVariable Long id){
        videoService.deleteVideo(id);
    }


    @GetMapping("/getAllVideos")
    public List<VideoEntity> getAll(){
        return videoService.getAll();
    }

    @GetMapping("/findByName")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<VideoEntity> findByName(@RequestParam String name){
      return videoService.findByName(name);
    }

    @GetMapping("/ageLimit/{ageLimit}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<VideoEntity> findByAgeLimit(@PathVariable Long ageLimit){
       return videoService.findByAgeLimit(ageLimit);
    }

    @GetMapping("/myVideos")
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public List<VideoEntity> myVideos(){
        return videoService.getMyVideos();
    }

}
