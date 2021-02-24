package edu.ncsu.csc.CoffeeMaker.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

    /** id for inventory entry */
    @Id
    @GeneratedValue
    private Long                   id;

    /** List of ingredients **/
    @OneToMany ( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private final List<Ingredient> ingredients;

    /**
     * Empty constructor for Hibernate
     */
    public Inventory () {
        this.ingredients = new LinkedList<Ingredient>();
        // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
    }

    public List<Ingredient> getIngredients () {
        return ingredients;
    }

    /**
     * Returns the ID of the entry in the DB
     *
     * @return long
     */
    public Long getId () {
        return id;
    }

    /**
     * Set the ID of the Inventory (Used by Hibernate)
     *
     * @param id
     *            the ID
     */
    public void setId ( final Long id ) {
        this.id = id;
    }

    /**
     * Add the number of chocolate units in the inventory to the current amount
     * of chocolate units.
     *
     * @param ingredient
     *            name of ingredient
     *
     * @throws IllegalArgumentException
     *             if the parameter isn't a positive integer
     */
    public int checkIngredient ( final String ingredient ) throws IllegalArgumentException {
        int amtIng = 0;
        try {
            amtIng = Integer.parseInt( ingredient );
        }
        catch ( final NumberFormatException e ) {
            throw new IllegalArgumentException( "Units of ingredients must be a positive integer" );
        }
        if ( amtIng < 0 ) {
            throw new IllegalArgumentException( "Units of ingredients must be a positive integer" );
        }

        return amtIng;
    }

    /**
     * Returns true if there are enough ingredients to make the beverage.
     *
     * @param r
     *            recipe to check if there are enough ingredients
     * @return true if enough ingredients to make the beverage
     */
    public boolean enoughIngredients ( final Recipe r ) {
        final List<Ingredient> ing = r.getIngredient();
        for ( int i = 0; i < ing.size(); i++ ) {
            for ( int j = 0; j < ingredients.size(); j++ ) {
                if ( ing.get( i ).getIngredient().equals( ingredients.get( j ).getIngredient() ) ) {
                    if ( ingredients.get( j ).getAmount() - ing.get( i ).getAmount() < 0 ) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Removes the ingredients used to make the specified recipe. Assumes that
     * the user has checked that there are enough ingredients to make
     *
     * @param r
     *            recipe to make
     * @return true if recipe is made.
     */
    public boolean useIngredients ( final Recipe r ) {
        if ( enoughIngredients( r ) ) {
            final List<Ingredient> ing = r.getIngredient();
            for ( int i = 0; i < ing.size(); i++ ) {
                for ( int j = 0; j < ingredients.size(); j++ ) {
                    if ( ing.get( i ).getIngredient().equals( ingredients.get( j ).getIngredient() ) ) {
                        ingredients.get( j ).setAmount( ingredients.get( j ).getAmount() - ing.get( i ).getAmount() );
                    }
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Adds or updates ingredient in inventory, given an ingredient parameter,
     * if that ingredient exists in the inventory, updates it by the amount of
     * the parameter, if it does not, it adds that ingredient to the system
     *
     * @param ing
     *            ingredient to update
     *
     * @return true if ingredient is good
     */
    public boolean addIngredient ( final Ingredient ing ) {
        for ( int i = 0; i < ingredients.size(); i++ ) {
            if ( ing.getIngredient().equals( ingredients.get( i ).getIngredient() ) ) {
                ingredients.get( i ).setAmount( ing.getAmount() + ingredients.get( i ).getAmount() );
                return true;
            }
        }
        ingredients.add( ing );
        return true;
    }

    /**
     * gets an ingredient from the Inventory by its name
     *
     * @param name
     *            name of the ingredient to get
     * @return the Ingredient object, or null if there is no Ingredient with
     *         that name
     */
    public Ingredient getIngredientByName ( final String name ) {
        for ( int i = 0; i < ingredients.size(); i++ ) {
            if ( ingredients.get( i ).getIngredient().equals( name ) ) {
                return ingredients.get( i );
            }
        }
        return null;
    }

    @Override
    public String toString () {
        return "Inventory [ingredients=" + ingredients + "]";
    }

}
