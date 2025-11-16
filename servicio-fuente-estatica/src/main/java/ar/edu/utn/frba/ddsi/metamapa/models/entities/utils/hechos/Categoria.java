package ar.edu.utn.frba.ddsi.metamapa.models.entities.utils.hechos;


import ar.edu.utn.frba.ddsi.metamapa.models.entities.Hecho;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, unique = true)
    public String nombre;

    public Categoria(String dato) {
        this.nombre = dato;
    }

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL) // Si se elimina una categoria, se eliminan todos los hechos asociados a esa categoria
    private List<Hecho> hechos;

}
