#Author: ncpage

Feature: Add an ingredient
		As a system user
		I want to be able to add ingredients
		Add if they are not already in the system
		
Scenario Outline: Valid Ingredient added
Given the add ingredient screen is open
When I submit valid values for name: <ingredientName>; amount <ingredientAmount>;
Then the ingredient is added successfully

Examples:
  | ingredientName   | amount |
  | white chocolate  | 1      |
  | cinnamon         | 1      |
  | peppermint       | 1      |
  | pumpkin          | 1      |
  
  Scenario Outline: Invalid Ingredient added
Given the add ingredient screen is open
When I submit invalid values for name: <ingredientName>; amount <ingredientAmount>;
Then the ingredient is not added successfully

Examples:
  | ingredientName   | amount |
  | peppermint       | -1     |
  | DNE              |  1     |
  
    Scenario Outline: Invalid Ingredient added
Given the add ingredient screen is open
When I submit duplicate values for name: <ingredientName>; amount <ingredientAmount>;
Then the ingredient is not added successfully

Examples:
  | ingredientName   | amount |
  | peppermint       |  1     |
  | pumpkin          |  1     |
  | pumpkin          |  1     |
  | milk             |  1     |
  