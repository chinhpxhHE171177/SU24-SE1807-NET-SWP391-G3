/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Datnt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizHistoryVM {
    private int QuizID;
    private String title;
    private String image;
    private String description;
    private int Level;
    private String Category;
    private String Subject;
    private String CreateAt;
    private int createById;
    private int Score;
    private String CompleteAt;
}
