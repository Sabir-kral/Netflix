package az.developia.demo.Controller;

import az.developia.demo.Request.LogRequest;
import az.developia.demo.Response.LogResponse;
import az.developia.demo.Service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogController {

    private final LogService logService;



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        logService.delete(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<LogResponse> findAll(){
        return logService.findAll();
    }
}
