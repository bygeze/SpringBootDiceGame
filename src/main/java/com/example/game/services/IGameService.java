package com.example.game.services;

import java.util.List;

import com.example.game.dto.ResponseDto;

public interface IGameService {
	public ResponseDto registerUser(String username);
	
	public ResponseDto userPlayGame(String username);
	
	public List<ResponseDto> findAllGames(String username);
	
	public ResponseDto deleteAllGames(String id);
	
	
	public List<ResponseDto> findAllPlayersWithSuccessRate();
	
	public ResponseDto findGlobalSuccessRate();
	public ResponseDto findLoser();
	public ResponseDto findWinner();
}
