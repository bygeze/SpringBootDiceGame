package com.example.game.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.game.dto.GameDto;
import com.example.game.dto.MessageResponseDto;
import com.example.game.dto.PlayerSuccessRateDto;
import com.example.game.dto.ResponseDto;
import com.example.game.models.Game;
import com.example.game.models.User;
import com.example.game.repositories.IGameRepository;
import com.example.game.repositories.IUserRepository;

@Service
public class GameService implements IGameService {
	private final IUserRepository userRepo;
	private final IGameRepository gameRepo;
	
	@Autowired
	public GameService(IUserRepository userRepo, IGameRepository gameRepo) {
		this.userRepo = userRepo;
		this.gameRepo = gameRepo;
	}
	
	@Override
	public ResponseDto registerUser(String username) {
		if(username == null) {
			username = "ANÒNIM";
		} else if(!userRepo.findByUsername(username).isEmpty()){
			return new MessageResponseDto("Username already exists.");
		}
		
		User temp = new User(username, new Date());
		temp = userRepo.save(temp);
		return new MessageResponseDto("User \"" + temp.getUsername() + "\" created with Id: " + temp.getId());
	}
	
	@Override
	public ResponseDto userPlayGame(Long id) {
		Optional<User> user = userRepo.findById(id);
		
		if(user.isEmpty()) {
			return new MessageResponseDto("User doesnt exists.");
		} else {
			Game game = new Game(user.get());
			game = gameRepo.save(game);
			
			return new GameDto(game.getId(), game.getDado1(), game.getDado2(), game.getResult());
		}
	}
	
	@Override
	public List<ResponseDto> findAllGames(Long id) {
		List<ResponseDto> res = new ArrayList<>();
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			res.add(new MessageResponseDto("User doesnt exists."));
			return res;
		} else {
			List<Game> games = gameRepo.findAllByUser(id);
			
			for(Game game : games) {
				res.add(new GameDto(game.getId(), game.getDado1(), game.getDado2(), game.getResult()));
			}
			
			return res;
		}
	}
	
	@Override
	public ResponseDto deleteAllGames(Long id) {
		Optional<User> user = userRepo.findById(id);
		
		if(user.isEmpty()) {
			return new MessageResponseDto("User doesnt exists");
		} else {
			return new MessageResponseDto(Integer.toString(gameRepo.deleteAllByUser(id)));
		}
		// TODO Auto-generated method stub
	}
	
	@Override
	public List<ResponseDto> findAllPlayersWithSuccessRate() {
		List<ResponseDto> res = new ArrayList<>();
		Iterable<User> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<User>) userList).size() < 1) {
				res.add(new MessageResponseDto("No hay usuarios registrados."));
				return res;
			} else {
				List<ResponseDto> toReturn = new ArrayList<>();
				
				for(User user : (Collection<User>) userList) {
					List<Game> games = gameRepo.findAllByUser(user.getId());
					
					toReturn.add(new PlayerSuccessRateDto(user.getUsername(), getSuccessRate(games)[2]));
				}
				
				return toReturn;
			}
		} else {
			res.add(new MessageResponseDto("Server error"));
			return res;
		}
	}
	
	@Override
	public ResponseDto findGlobalSuccessRate() {
		int totalSuccess = 0;
		int totalGames = 0;
		
		Iterable<User> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<User>) userList).size() < 1) {
				return new MessageResponseDto("No hay usuarios registrados.");
			} else {
				for(User user : (Collection<User>) userList) {
					List<Game> games = gameRepo.findAllByUser(user.getId());
					int[] sr = getSuccessRate(games);
					totalSuccess += sr[0];
					totalGames += sr[1];
					
				}
				
				float globalRate = (float) totalSuccess / totalGames;
				
				return new MessageResponseDto(Integer.toString((int) (globalRate * 100)));
			}
		} else {
			return new MessageResponseDto("Server error");
		}
	}
	
	@Override
	public ResponseDto findLoser() {
		User loserUser = null;
		int srLoser = 0;
		Iterable<User> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<User>) userList).size() < 1) {
				return new MessageResponseDto("No hay usuarios registrados.");
			} else {
				boolean firstLoopFlag = true;
				for(User user : (Collection<User>) userList) {
					List<Game> games = gameRepo.findAllByUser(user.getId());
					int sr = getSuccessRate(games)[2];
					
					if(firstLoopFlag) {
						loserUser = user;
						srLoser = sr;
						firstLoopFlag = false;
					} else {
						if(sr <= srLoser) {
							loserUser = user;
							srLoser = sr;
						}
					}
				}
				return new PlayerSuccessRateDto(loserUser.getUsername(), srLoser);
			}
		} else {
			return new MessageResponseDto("Server error");
		}
	}
	
	@Override
	public ResponseDto findWinner() {
		User winnerUser = null;
		int srWinner = 0;
		Iterable<User> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<User>) userList).size() < 1) {
				return new MessageResponseDto("No hay usuarios registrados.");
			} else {
				boolean firstLoopFlag = true;
				for(User user : (Collection<User>) userList) {
					List<Game> games = gameRepo.findAllByUser(user.getId());
					int sr = getSuccessRate(games)[2];
					
					if(firstLoopFlag) {
						winnerUser = user;
						srWinner = sr;
						firstLoopFlag = false;
					} else {
						if(sr >= srWinner) {
							winnerUser = user;
							srWinner = sr;
						}
					}
				}
				return new PlayerSuccessRateDto(winnerUser.getUsername(), srWinner);
			}
		} else {
			return new MessageResponseDto("Server error");
		}
	}
	
	/* int[3] = {successCount, totalGames, successRate }*/
	private int[] getSuccessRate(List<Game> games) {
		int total = games.size();
		
		if(total == 0) {
			return new int[] {0,0,0};
		}
		
		int successCount = 0;
		
		for(Game game : games) {
			if(game.getResult()) {
				successCount++;
			}
		}

		float successRate = (float) successCount / total;
		
		return new int[] {successCount, total, (int) (successRate * 100)};
	}
	
	
	
}
