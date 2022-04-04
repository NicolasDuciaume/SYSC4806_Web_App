package SYSC6.Project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Main_Controller {

    private Long id = 0L;


    @GetMapping("/")
    public String login(){
        return "login_form";
    }

    /**
     * If login successful it returns the id of the user and give this to the controller
     * @param UserId
     * @return user_portal page
     */
    @PostMapping("/login_form")
    public String login_process(@RequestParam(value="id",required=true) String UserId){
        id = Integer.parseInt(UserId) * 1L;
        User check_user = getUser(id);
        if(check_user.getUsername().equals("admin")){
            if(check_user.getPassword().equals("admin")){
                return "redirect:/admin_portal";
            }
        }
        return "redirect:/user_portal";
    }

    /**
     * Bring the user to the registration form
     * @return Registration page
     */
    @PostMapping("/Register")
    public String Register(){
        return "redirect:/Registration";
    }


    @GetMapping("/Registration")
    public String Reg(){
        return "Registration";
    }

    /*
    @PostMapping("/Create")
    public String create(@RequestParam(value="user",required=true) String user, @RequestParam(value="pass",required=true) String pass){
        id = createUser(user,pass);
        User check_user = getUser(id);
        if(check_user.getUsername().equals("admin")){
            if(check_user.getPassword().equals("admin")){
                return "redirect:/admin_portal";
            }
        }
        return "redirect:/user_portal";
    }
    */

    @PostMapping("/TempCreate")
    public String TempCreate(@RequestParam(value="admin", required = true) String admin, @RequestParam(value="id", required = true) String TempId){
        id = Integer.parseInt(TempId) * 1L;
        if(admin.equals("admin")){
            return "redirect:/admin_portal";
        }
        else{
            return "redirect:/user_portal";
        }
    }

    /**
     * Logs the user out and returns the user to the login screen
     * @return
     */
    @PostMapping("/LogOut")
    public String logout(){
        id = 0L;
        return "redirect:/";
    }

    /**
     * Brings the logged in user to the user portal page
     * @param name_place username
     * @param model
     * @return returns the html for the user portal
     */
    @GetMapping("/user_portal")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
        User user = getUser(id);
        name_place = user.getUsername();
        model.addAttribute("name", name_place);

        model.addAttribute("role", user.getRole().toString());
        return "user_portal";
    }

    @GetMapping("/admin_portal")
    public String greeting_admin(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
        User user = getUser(id);
        name_place = user.getUsername();
        model.addAttribute("name", "Admin");
        user.setRole(RoleType.ADMIN);
        model.addAttribute("role", user.getRole().toString());
        return "admin_portal";
    }

    @GetMapping("/view_users")
    public String getUsers(Model model){
        model.addAttribute("users",checkUser());
        return "redirect:/view_users";
    }


    public Long createUser(String Username, String Password){
        JSONParser jsonParser = new JSONParser();
        Long x = 0L;
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user/add");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{" + '"' + "username" + '"' + ":" + '"' + Username + '"' + "," + '"' + "password" + '"' + ":" + '"' + Password + '"'+"}";
            System.out.println(jsonInputString);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
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
            URL url = new URL ("http://localhost:8080/rest/api/user/"+id.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
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
            URL url = new URL ("http://localhost:8080/rest/api/user");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
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


    public User DelUser(Long id){
        JSONParser jsonParser = new JSONParser();
        User user = new User();
        System.out.println(id);
        try {
            URL url = new URL ("http://localhost:8080/rest/api/user/Del/"+id.toString());
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
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
}
