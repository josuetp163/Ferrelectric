/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.io.FileNotFoundException;
import javafx.scene.Parent;

/**
 *
 * @author xavic
 */
public interface View {
    Parent build() throws FileNotFoundException;
}
