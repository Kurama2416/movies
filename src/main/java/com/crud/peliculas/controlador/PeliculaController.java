package com.crud.peliculas.controlador;

import com.crud.peliculas.modelo.Pelicula;
import com.crud.peliculas.servicio.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaService.listarPeliculas());
        return "peliculas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        return "peliculas/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPelicula(@ModelAttribute("pelicula") Pelicula pelicula,
                                  RedirectAttributes redirectAttributes) {
        peliculaService.guardarPelicula(pelicula);
        redirectAttributes.addFlashAttribute("mensaje", "Película guardada con éxito.");
        return "redirect:/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID de película inválido: " + id));
            model.addAttribute("pelicula", pelicula);
            return "peliculas/formulario";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Película no encontrada.");
            return "redirect:/peliculas";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPelicula(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            peliculaService.eliminarPelicula(id);
            redirectAttributes.addFlashAttribute("mensaje", "Película eliminada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la película: " + e.getMessage());
        }
        return "redirect:/peliculas";
    }
}