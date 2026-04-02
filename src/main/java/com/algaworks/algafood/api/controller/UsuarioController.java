package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.DTO.Usuario.LoginInputDTO;
import com.algaworks.algafood.api.DTO.Senha.SenhaInput;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioInputDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioDTO;
import com.algaworks.algafood.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.listAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }
    @PostMapping
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
        return new ResponseEntity<>(usuarioService.save(usuarioInputDTO), HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@RequestBody @Valid UsuarioInputDTO usuarioInputDTO, @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.update(usuarioInputDTO, id));
    }
    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senha) {
        usuarioService.alterarSenha(id, senha.getSenhaAtual(), senha.getSenhaNova());
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginInputDTO loginInputDTO) {
        usuarioService.login(loginInputDTO.getEmail(), loginInputDTO.getSenha());
        return ResponseEntity.noContent().build();
    }

}
