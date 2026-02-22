package az.developia.demo.Controller;

import az.developia.demo.Entity.AboneEntity;
import az.developia.demo.Request.AboneRequest;
import az.developia.demo.Response.AboneResponse;
import az.developia.demo.Service.AboneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/abone")
@CrossOrigin(origins = "*")
public class AboneController {

    private final AboneService aboneService;

    @PostMapping("/buyPlan")
    public AboneResponse AboneOl(@RequestBody AboneRequest request){
        return aboneService.add(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        aboneService.delete(id);
    }

    @PutMapping("/redaktePlan/{id}")
    public void redakte(@PathVariable Long id,@RequestParam String name){
        aboneService.redakte(id,name);
    }

    @GetMapping("/{id}")
    public AboneEntity findById(@PathVariable Long id){
        return aboneService.findById(id);
    }

    @GetMapping
    public List<AboneResponse> findAll(){
        return aboneService.findAll();
    }
}
