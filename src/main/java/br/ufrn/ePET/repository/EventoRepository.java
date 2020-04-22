package br.ufrn.ePET.repository;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.dialect.pagination.SQL2008StandardLimitHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufrn.ePET.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long>{
	
	@Query(value = "SELECT * FROM evento i WHERE i.participante_anexos = ?1", nativeQuery = true)
	List<Evento> findByParticipantesAnexos(Long id);
	
	@Query(value = "SELECT * FROM evento i WHERE i.ativo = false", nativeQuery = true)
	List<Evento> findByInativos();
	
	@Query(value = "SELECT * FROM evento i WHERE i.ativo = true", nativeQuery = true)
	List<Evento> findByAtivos();

	@Query(value = "SELECT * FROM evento i WHERE i.titulo LIKE %?1%", nativeQuery = true)
	Page<Evento> findByTitulo(String titulo, Pageable pageable);
}
