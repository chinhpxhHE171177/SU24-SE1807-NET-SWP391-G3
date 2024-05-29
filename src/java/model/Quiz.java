
package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author adnmin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    private int QuizID;
    private String title;
    private String image;
    private String description;
    private int Level;
    private int CategoryID; 
    private int SubjectID;
    private String CreateAt;
    private int createById;
}

