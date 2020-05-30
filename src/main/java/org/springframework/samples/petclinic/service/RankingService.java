package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.repository.RankingRepository;
import org.springframework.samples.petclinic.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RankingService {

	private RankingRepository rankingRepository;
	private ReportRepository reportRepository;
	
	@Autowired
	public RankingService(RankingRepository rankingRepository, ReportRepository reportRepository) {
		this.rankingRepository = rankingRepository;
		this.reportRepository = reportRepository;
	}

	@Transactional(readOnly = true)
	public Ranking findRankingById(int id) throws DataAccessException {
		return rankingRepository.findById(id);
	}

	public Collection<Ranking> findAll() {
		return this.rankingRepository.findAllRanking();
	}

	public void saveRanking(Ranking ranking) throws DataAccessException {
		rankingRepository.save(ranking);                
	}

	public Collection<Ranking> findRankingByTournamentId(int tournamentId) {		
		return this.rankingRepository.findByTournamentId(tournamentId);
	}

	public Ranking makeRanking(Tournament tournament) {	
		Ranking ranking = new Ranking();

		List<Report> reports = this.reportRepository.findAll().stream().collect(Collectors.toList());
		reports.stream().filter(r -> r.getTournament().equals(tournament));

		HashMap<Integer, Integer> mapa = new HashMap<Integer, Integer>();

		/* Creamos el ranking que vamos a devolver, junto con varias variables más
		para ayudarnos; La lista de los report del torneo entrante, el podio de las pets 
		para luego hacer el set, y un mapa parar asignar las puntuaciones de los report 
		a las pet y luego meterlas en el podio
		*/

		addPets(mapa, reports);

		ranking.setTournament(tournament);
		ranking.setPodium(mapa); 

		/* Por último asignamos los atributos al ranking, y lo devolvemos
		PD: No sé si habría que añadirle la id de forma manual */
	
		return ranking;
	}

	public Integer findScoreByPetIdAndRanking(Ranking ranking, int petId) {
		return ranking.getPodium().get(petId);
	}

	private HashMap<Integer, Integer> addPets(HashMap<Integer, Integer> mapa, List<Report> reports){

		for(Report r: reports){
			if(!(mapa.containsKey(r.getPet().getId()))){
				mapa.put(r.getPet().getId(), r.getPoints());
			} else {
				mapa.replace(r.getPet().getId(), mapa.get(r.getPet().getId()) + r.getPoints());
			}
		}

		/* En el for, si el mapa no contiene a la pet, la añadimos con los puntos de 
		ese report. Si la pet ya se encuentra en el mapa, añadimos a la pet los puntos
		de ese report, que tiene la misma pet*/

		mapa.entrySet().stream().sorted(Collections.reverseOrder());

		// Añadimos las pet al podium, entran ordenadas empezando por la que tiene más puntos

		return mapa;
	}

}
