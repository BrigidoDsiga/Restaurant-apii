import com.example.restaurant.dto.DishDTO;
import com.example.restaurant.exception.ResourceNotFoundException;
import com.example.restaurant.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gerenciar pratos (dishes).
 */
@RestController
@RequestMapping("/api/dishes")
public class DishController {

    private final DishService dishService;

    /**
     * Construtor para injeção de dependência do serviço de pratos.
     *
     * @param dishService serviço responsável pelas operações de pratos
     */
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * Retorna a lista de todos os pratos.
     *
     * @return lista de pratos
     */
    @GetMapping
    public ResponseEntity<List<DishDTO>> getAllDishes() {
        List<DishDTO> dishes = dishService.getAllDishes();
        return ResponseEntity.ok(dishes);
    }

    /**
     * Retorna um prato pelo seu ID.
     *
     * @param id ID do prato
     * @return prato encontrado ou 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<DishDTO> getDishById(@PathVariable Long id) {
        Optional<DishDTO> dishOpt = dishService.getDishById(id);
        return dishOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + id));
    }

    /**
     * Cria um novo prato.
     *
     * @param dishDTO dados do prato a serem criados
     * @return prato criado com URI no header Location
     */
    @PostMapping
    public ResponseEntity<DishDTO> createDish(@Valid @RequestBody DishDTO dishDTO) {
        DishDTO createdDish = dishService.createDish(dishDTO);
        URI location = URI.create(String.format("/api/dishes/%d", createdDish.getId()));
        return ResponseEntity.created(location).body(createdDish);
    }

    /**
     * Atualiza um prato existente.
     *
     * @param id      ID do prato a atualizar
     * @param dishDTO dados atualizados do prato
     * @return prato atualizado ou 404 se não existir
     */
    @PutMapping("/{id}")
    public ResponseEntity<DishDTO> updateDish(@PathVariable Long id, @Valid @RequestBody DishDTO dishDTO) {
        Optional<DishDTO> updatedDishOpt = dishService.updateDish(id, dishDTO);
        return updatedDishOpt
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Prato não encontrado com id: " + id));
    }

    /**
     * Remove um prato pelo seu ID.
     *
     * @param id ID do prato
     * @return resposta 204 se deletado, 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        boolean deleted = dishService.deleteDish(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Prato não encontrado com id: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}
