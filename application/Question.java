package application;

import java.util.ArrayList;
import javafx.scene.image.Image;

public class Question {
  String description; // description of the question
  boolean isFetched; // if this Question is Fetched
  ArrayList<Choice> choices; // choices of the current Question
  int userAnswer; // this indicates which question user currently pick 
  String topic;
  Image image;
  
  Question(String topic, ArrayList<Choice> choices, String description){// Constructor 
    this.topic = topic;
    this.choices = choices;
    this.description = description;
  }
}
