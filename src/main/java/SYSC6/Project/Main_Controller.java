package SYSC6.Project;

import com.fasterxml.jackson.core.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Controller
public class Main_Controller {

    private Long id = 0L;

    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("Login",new Login());
        return "login_form";
    }

    @PostMapping("/login_form")
    public String login_process(@ModelAttribute Login login, Model model, RedirectAttributes attributes){
        model.addAttribute("login", login);
        ArrayList<User> users = checkUser();
        for(User user : users){
            if(user.getUsername().equals(login.getUsername())){
                if(user.getPassword().equals(login.getPassword())){
                    id = user.getId();
                    return "redirect:/compare_talent";
                }
            }
        }
        model.addAttribute("message", "Wrong login information");
        return "login_form";
    }

    @PostMapping("/Register")
    public String Register(){
        return "redirect:/Registration";
    }

    @GetMapping("/Registration")
    public String Reg(Model model){
        model.addAttribute("Login",new Login());
        return "Registration";
    }

    @PostMapping("/Create")
    public String create(@ModelAttribute Login login, Model model, RedirectAttributes attributes){
        model.addAttribute("login", login);
        id = createUser(login.getUsername(), login.getPassword());
        return "redirect:/compare_talent";
    }

    @PostMapping("/LogOut")
    public String logout(){
        id = 0L;
        return "redirect:/";
    }

    @GetMapping("/compare_talent")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
        User user = getUser(id);
        name_place = user.getUsername();
        model.addAttribute("name", name_place);
        return "compare_talent";
    }

    public Long createUser(String Username, String Password){
        JSONParser jsonParser = new JSONParser();
        Long x = 0L;
        try {
            URL url = new URL ("https://projectsysc4806.herokuapp.com/rest/api/user/add");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{" + '"' + "username" + '"' + ":" + '"' + Username + '"' + "," + '"' + "password" + '"' + ":" + '"' + Password + '"'+"}";
            System.out.println(jsonInputString);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                x = (Long) temp.get("id");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return x;
    }

    public User getUser(Long id){
        JSONParser jsonParser = new JSONParser();
        User user = new User();
        System.out.println(id);
        try {
            URL url = new URL ("https://projectsysc4806.herokuapp.com/rest/api/user/"+id.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JSONObject temp = (JSONObject) jsonParser.parse(response.toString());
                //System.out.println(temp.get("username").toString());
                user = new User(temp.get("username").toString(), temp.get("password").toString());

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return user;
    }

    public ArrayList<User> checkUser(){
        JSONParser jsonParser = new JSONParser();
        ArrayList<User> users = new ArrayList<>();
        try {
            URL url = new URL ("https://projectsysc4806.herokuapp.com/rest/api/user");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JSONArray temp = (JSONArray) jsonParser.parse(response.toString());
                for(Object o : temp){
                    JSONObject user = (JSONObject) o;
                    User userTemp = new User(user.get("username").toString(), user.get("password").toString());
                    userTemp.setId((Long) user.get("id"));
                    users.add(userTemp);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e){
            System.out.println("Error");
        }
        return users;
    }
}
