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
import com.example.game.models.MongoGame;
import com.example.game.models.MongoUser;
import com.example.game.models.User;
import com.example.game.repositories.mongo.IMongoGameRepository;
import com.example.game.repositories.mongo.IMongoUserRepository;
import com.example.game.repositories.mysql.IGameRepository;
import com.example.game.repositories.mysql.IUserRepository;

@Service
public class GameService implements IGameService {
	//private final IUserRepository userRepo;
	//private final IGameRepository gameRepo;
	@Autowired
	private final IMongoUserRepository userRepo;
	@Autowired
	private final IMongoGameRepository gameRepo;
	
	
	@Autowired
	public GameService(IMongoUserRepository userRepo, IMongoGameRepository gameRepo) {
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
		
		MongoUser temp = new MongoUser(username, new Date());
		temp = userRepo.save(temp);
		return new MessageResponseDto("User \"" + temp.getUsername() + "\" created with Id: " + temp.getId());
	}
	
	
	@Override
	public ResponseDto userPlayGame(String id) {
		Optional<MongoUser> user = userRepo.findByUsername(id);
		
		if(user.isEmpty()) {
			return new MessageResponseDto("User doesnt exists.");
		} else {
			MongoGame game = new MongoGame(user.get().getUsername());
			game = gameRepo.save(game);
			
			return new GameDto(game.getId(), game.getDado1(), game.getDado2(), game.getResult());
		}
	}
	

	@Override
	public List<ResponseDto> findAllGames(String id) {
		List<ResponseDto> res = new ArrayList<>();
		Optional<MongoUser> user = userRepo.findByUsername(id);
		if(user.isEmpty()) {
			res.add(new MessageResponseDto("User doesnt exists."));
			return res;
		} else {
			List<MongoGame> games = gameRepo.findAllByUserId(id);
			
			for(MongoGame game : games) {
				res.add(new GameDto(game.getId(), game.getDado1(), game.getDado2(), game.getResult()));
			}
			
			return res;
		}
	}
	
	@Override
	public ResponseDto deleteAllGames(String username) {
		Optional<MongoUser> user = userRepo.findByUsername(username);
		
		
		List<MongoGame> deleteItems = gameRepo.findAllByUserId(username);
		
		if(user.isEmpty()) {
			return new MessageResponseDto("User doesnt exists");
		} else if(deleteItems.size() < 1) {
			return new MessageResponseDto("User has no games registered");
		} else {
			gameRepo.deleteAll(deleteItems);
			return new MessageResponseDto("User has a clean record now");
		}
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public List<ResponseDto> findAllPlayersWithSuccessRate() {
		List<ResponseDto> res = new ArrayList<>();
		Iterable<MongoUser> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<MongoUser>) userList).size() < 1) {
				res.add(new MessageResponseDto("No hay usuarios registrados."));
				return res;
			} else {
				List<ResponseDto> toReturn = new ArrayList<>();
				
				System.out.println("Hasta aqui si");
				
				for(MongoUser user : (Collection<MongoUser>) userList) {
					List<MongoGame> games = gameRepo.findAllByUserId(user.getUsername());
					
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
		
		Iterable<MongoUser> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<MongoUser>) userList).size() < 1) {
				return new MessageResponseDto("No hay usuarios registrados.");
			} else {
				for(MongoUser user : (Collection<MongoUser>) userList) {
					List<MongoGame> games = gameRepo.findAllByUserId(user.getUsername());
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
		MongoUser loserUser = null;
		int srLoser = 0;
		Iterable<MongoUser> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<MongoUser>) userList).size() < 1) {
				return new MessageResponseDto("No hay usuarios registrados.");
			} else {
				boolean firstLoopFlag = true;
				for(MongoUser user : (Collection<MongoUser>) userList) {
					List<MongoGame> games = gameRepo.findAllByUserId(user.getUsername());
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
		MongoUser winnerUser = null;
		int srWinner = 0;
		Iterable<MongoUser> userList = userRepo.findAll();
		
		if(userList instanceof Collection) {
			if(((Collection<MongoUser>) userList).size() < 1) {
				return new MessageResponseDto("No hay usuarios registrados.");
			} else {
				boolean firstLoopFlag = true;
				for(MongoUser user : (Collection<MongoUser>) userList) {
					List<MongoGame> games = gameRepo.findAllByUserId(user.getUsername());
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
	
	
	private int[] getSuccessRate(List<MongoGame> games) {
		int total = games.size();
		
		if(total == 0) {
			return new int[] {0,0,0};
		}
		
		int successCount = 0;
		
		for(MongoGame game : games) {
			if(game.getResult()) {
				successCount++;
			}
		}

		float successRate = (float) successCount / total;
		
		return new int[] {successCount, total, (int) (successRate * 100)};
	}

	
	
}
