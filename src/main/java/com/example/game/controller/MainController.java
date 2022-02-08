package com.example.game.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.game.dto.AuthUser;
import com.example.game.dto.ResponseDto;
import com.example.game.services.GameService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController

public class MainController {
	private final GameService gameService;
	
	@Autowired
	public MainController(GameService gameService) {
		this.gameService = gameService;
	}
	
	@GetMapping("/test")
	public String test() {
		return "Hola mundo";
	}
	
	@PostMapping("/login")
	public AuthUser login(@RequestParam("user") String username, @RequestParam("pass") String password) {
		
		String token = getJWTToken(username);
		AuthUser aUser = new AuthUser(username, token);
		return aUser;
	}
		
	private String getJWTToken(String username) {
		String secretKey = "itacademy";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("mygameJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
	// create player
	@PostMapping("/players")
	public ResponseEntity<ResponseDto> createPlayer(@RequestBody(required = false) String username) {
		
		return new ResponseEntity<>(gameService.registerUser(username), HttpStatus.OK);
	}
	
	
	// play game
	@PostMapping("/players/{id}/games")
	public ResponseEntity<ResponseDto> playGame(@PathVariable("id") String id) {
		
		return new ResponseEntity<>(gameService.userPlayGame(id), HttpStatus.OK);
		
	}
	
	// get all games from player
	@GetMapping("/players/{id}/games")
	public ResponseEntity<List<ResponseDto>> findAllGames(@PathVariable("id") String id) {
	
		return new ResponseEntity<>(gameService.findAllGames(id), HttpStatus.OK);
		
	}
	
	// delete all games from player
	@DeleteMapping("/players/{id}/games")
	public ResponseEntity<ResponseDto> deleteAllGames(@PathVariable("id") String id) {
		
		return new ResponseEntity<>(gameService.deleteAllGames(id), HttpStatus.OK);
	
	}
	
	// get all players with success rate
	@GetMapping("/players")
	public ResponseEntity<List<ResponseDto>> getPlayersAndSuccessRate() {
		return new ResponseEntity<>(gameService.findAllPlayersWithSuccessRate(), HttpStatus.OK);
	}
	
	// get global success rate
	@GetMapping("players/ranking")
	public ResponseEntity<ResponseDto> globalSuccessRate() {
		
		return new ResponseEntity<>(gameService.findGlobalSuccessRate(), HttpStatus.OK);
	}
	
	@GetMapping("players/ranking/winner") 
	public ResponseEntity<ResponseDto> findWinner() {
		return new ResponseEntity<>(gameService.findWinner(), HttpStatus.OK);
	}
	
	@GetMapping("players/ranking/loser") 
	public ResponseEntity<ResponseDto> findLoser() {
		return new ResponseEntity<>(gameService.findLoser(), HttpStatus.OK);
	}
}
