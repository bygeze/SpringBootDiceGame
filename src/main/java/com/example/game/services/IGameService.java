package com.example.game.services;

import java.util.List;

import com.example.game.dto.ResponseDto;
import com.example.game.models.User;

public interface IGameService {
	public ResponseDto registerUser(String username);
	public ResponseDto userPlayGame(Long id);
	public List<ResponseDto> findAllGames(Long id);
	public ResponseDto deleteAllGames(Long id);
	public List<ResponseDto> findAllPlayersWithSuccessRate();
	public ResponseDto findGlobalSuccessRate();
	public ResponseDto findLoser();
	public ResponseDto findWinner();
}
