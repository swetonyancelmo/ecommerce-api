package com.swetonyancelmo.ecommerce.controllers;

import com.swetonyancelmo.ecommerce.dtos.LoginRequestDTO;
import com.swetonyancelmo.ecommerce.dtos.LoginResponseDTO;
import com.swetonyancelmo.ecommerce.dtos.RegisterDTO;
import com.swetonyancelmo.ecommerce.models.User;
import com.swetonyancelmo.ecommerce.repository.UserRepository;
import com.swetonyancelmo.ecommerce.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Sessão de Login/Registro")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Realiza o Login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao logar o usuário")
    })
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Realiza o registro de um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o usuário")
    })
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto){
        if(this.userRepository.findByUsername(dto.username()) != null) {
            return ResponseEntity.badRequest().body("Username já está em uso");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User newUser = new User(null, dto.username(), encryptedPassword, dto.role());
        this.userRepository.save(newUser);

        return ResponseEntity.ok().body("Usuário registrado com sucesso.");
    }
}
