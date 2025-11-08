package com.crud.peliculas.controlador;

import com.crud.peliculas.modelo.Socio;
import com.crud.peliculas.servicio.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/socios")
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping
    public String listarSocios(Model model) {
        model.addAttribute("socios", socioService.listarSocios());
        return "socios/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("socio", new Socio());
        return "socios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarSocio(@ModelAttribute("socio") Socio socio,
                               RedirectAttributes redirectAttributes) {
        try {
            socioService.guardarSocio(socio);
            redirectAttributes.addFlashAttribute("mensaje", "Socio guardado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el socio: " + e.getMessage());
        }
        return "redirect:/socios";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Socio socio = socioService.obtenerSocioPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("ID de socio inválido: " + id));
            model.addAttribute("socio", socio);
            return "socios/formulario";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Socio no encontrado.");
            return "redirect:/socios";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            socioService.eliminarSocio(id);
            redirectAttributes.addFlashAttribute("mensaje", "Socio eliminado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el socio: " + e.getMessage());
        }
        return "redirect:/socios";
    }
}