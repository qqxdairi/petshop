package br.com.projetocurso.petshop.repository;

import br.com.projetocurso.petshop.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepositoy extends JpaRepository<Animal, Long> {
}
