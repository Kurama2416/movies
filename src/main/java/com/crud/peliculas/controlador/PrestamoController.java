package com.crud.peliculas.controlador;

import com.crud.peliculas.modelo.Prestamo;
import com.crud.peliculas.servicio.PeliculaService;
import com.crud.peliculas.servicio.PrestamoService;
import com.crud.peliculas.servicio.SocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private SocioService socioService;

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public String listarPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoService.listarPrestamos());
        return "prestamos/lista";
    }

    @GetMapping("/activos")
    public String listarPrestamosActivos(Model model) {
        model.addAttribute("prestamosActivos", prestamoService.listarPrestamosActivos());
        model.addAttribute("totalActivos", prestamoService.contarPrestamosActivos());
        return "prestamos/activos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        model.addAttribute("socios", socioService.listarSocios());
        model.addAttribute("peliculasDisponibles", peliculaService.listarPeliculasDisponibles());
        return "prestamos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPrestamo(@ModelAttribute("prestamo") Prestamo prestamo,
                                  RedirectAttributes redirectAttributes) {
        try {
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamoService.guardarPrestamo(prestamo);
            redirectAttributes.addFlashAttribute("mensaje", "Préstamo registrado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el préstamo: " + e.getMessage());
        }
        return "redirect:/prestamos";
    }

    @GetMapping("/devolver/{id}")
    public String devolverPrestamo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.marcarComoDevuelto(id);
            redirectAttributes.addFlashAttribute("mensaje", "Película marcada como devuelta.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar la devolución: " + e.getMessage());
        }
        return "redirect:/prestamos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPrestamo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            prestamoService.eliminarPrestamo(id);
            redirectAttributes.addFlashAttribute("mensaje", "Préstamo eliminado con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el préstamo: " + e.getMessage());
        }
        return "redirect:/prestamos";
    }
}