package com.crud.peliculas.controlador;

import com.crud.peliculas.servicio.PeliculaService;
import com.crud.peliculas.servicio.PrestamoService;
import com.crud.peliculas.servicio.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @Autowired
    private SocioService socioService;

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalSocios", socioService.listarSocios().size());
        model.addAttribute("totalPeliculas", peliculaService.listarPeliculas().size());
        model.addAttribute("prestamosActivos", prestamoService.contarPrestamosActivos());
        model.addAttribute("peliculasDisponibles", peliculaService.listarPeliculasDisponibles().size());
        return "index";
    }
}