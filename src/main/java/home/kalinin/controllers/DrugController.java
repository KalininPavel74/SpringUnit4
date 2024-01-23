package home.kalinin.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import home.kalinin.models.Dict;
import home.kalinin.repository.DictRepository;

@Controller
@AllArgsConstructor
@Slf4j
public class DrugController {
    private final DictRepository dictRepository;

    @GetMapping("/dicts")
    public String getDicts(Model model){
        model.addAttribute("products", dictRepository.findAll());
        return "dicts";
    }
    @ModelAttribute(name = "dict")
    public Dict createDict() {
        return new Dict();
    }
    @PostMapping("/dicts")
    public String addDict(@Valid Dict dict, Errors errors, Model model){
        if (errors.hasErrors()) {
            log.error("errors.hasErrors() "+errors);
            model.addAttribute("db_save_error", errors);
        } else {
            try {
                dictRepository.save(dict);
            } catch (DataAccessException ex) {
                log.error("DataAccessException ");
                log.error(ex.getLocalizedMessage());
                model.addAttribute("db_save_error", ex.getMessage());
            }
        }
        model.addAttribute("dicts", dictRepository.findAll());
        return "dicts";
    }
}