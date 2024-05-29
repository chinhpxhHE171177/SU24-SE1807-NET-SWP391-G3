/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Subject {
    private int SubjectID;
    private String Subject_Name;
    private String Description;
    private String Image;
    private int Status;
    private int PackageId;
    private int CategoryId;
    private int CreateById; 
    private String CreateAt;
}
