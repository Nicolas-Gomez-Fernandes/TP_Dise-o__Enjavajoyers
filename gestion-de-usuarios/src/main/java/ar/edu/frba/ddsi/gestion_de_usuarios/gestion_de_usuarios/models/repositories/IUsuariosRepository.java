package ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.repositories;

import ar.edu.frba.ddsi.gestion_de_usuarios.gestion_de_usuarios.models.entities.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEmail(String username);
}
