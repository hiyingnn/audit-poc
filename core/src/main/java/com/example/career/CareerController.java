package com.example.career;


import com.example.career.dto.CareerHistoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/v1/career")
@RequiredArgsConstructor
public class CareerController {

    private final CareerHistoryService careerHistoryService;

    @GetMapping
    public List<CareerHistoryDTO> getAllRecords() {
        return careerHistoryService.getAllRecords();
    }

    @GetMapping("/{id}")
    public Optional<CareerHistoryDTO> getOneRecord(@PathVariable String id) {
        return careerHistoryService.getRecordById(id);
    }

    @PostMapping
    public CareerHistoryDTO addRecord(@RequestBody @Valid CareerHistoryDTO careerHistoryDTO) {
        return careerHistoryService.addRecord(careerHistoryDTO);
    }

    @PutMapping("/{id}")
    public CareerHistoryDTO updateRecord(@PathVariable String id, @RequestBody @Valid CareerHistoryDTO careerHistoryDTO) {
        return careerHistoryService.updateRecord(id, careerHistoryDTO);
    }

}
