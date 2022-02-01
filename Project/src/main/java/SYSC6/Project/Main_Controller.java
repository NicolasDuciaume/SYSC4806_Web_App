package SYSC6.Project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Main_Controller {

    private String name;
    private String student_number;

    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("Login",new Login());
        return "login_form";
    }

    @PostMapping("/login_form")
    public String login_process(@ModelAttribute Login login, Model model, RedirectAttributes attributes){
        model.addAttribute("login", login);
        name = login.getName();
        student_number = login.getStudent_num();

        model.addAttribute("name", name);
        return "compare_talent";
    }


    @GetMapping("/compare_talent")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
        name_place = name;
        model.addAttribute("name", name_place);
        return "compare_talent";
    }
}
