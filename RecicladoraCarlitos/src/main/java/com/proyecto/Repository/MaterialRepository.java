package com.proyecto.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.Model.Material;

@Repository
public interface MaterialRepository extends JpaRepository< Material,Integer> {

	
	
}
