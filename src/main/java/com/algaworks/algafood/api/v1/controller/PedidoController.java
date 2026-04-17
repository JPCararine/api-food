package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoResumoAdminDTO;
import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;


    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS')")
    ResponseEntity<Page<PedidoResumoAdminDTO>>  pesquisar(PedidoFilter  pedidoFilter, Pageable pageable) {
        return ResponseEntity.ok(pedidoService.consultaFiltroAdmin(pedidoFilter, pageable));
    }
    @PostMapping
    @CheckSecurity.Pedidos.PodeCriar
    public ResponseEntity<PedidoResumoDTO> criar(@RequestBody @Valid PedidoInputDTO pedidoInputDTO) {
        return new ResponseEntity<>(pedidoService.criar(pedidoInputDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{codigoPedido}")
    @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_PEDIDOS')")
    public ResponseEntity<PedidoResumoDTO> buscar(@PathVariable String codigoPedido) {
        return ResponseEntity.ok(pedidoService.findByCodigo(codigoPedido));
    }







}
