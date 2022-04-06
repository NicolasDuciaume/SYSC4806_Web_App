package SYSC6.Project.user.userManagement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Lightweight user object for use in front end elements, no password field
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPOJO {

    private Long id;
    private String username;
    private String role;
    private int clicks;

}
