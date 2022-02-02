import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime desiredTime;
    LocalTime unDesiredTime;
    List<String> validItemNames  = new ArrayList<String>();
    List<String> invalidItemNames = new ArrayList<String>();

    @BeforeEach
    public void setup(){
//        validItemNames
//        invalidItemNames
        String name = "MyRestuarant";
        String location =  "Berlin 1064, street 26";
        LocalTime openingTime = LocalTime.of(8, 30, 59, 11001);
        LocalTime closingTime = LocalTime.of(19, 30, 59, 11001);

        desiredTime = LocalTime.of(11, 30, 59, 11001);
        unDesiredTime = LocalTime.of(20, 30, 59, 11001);
        restaurant  =  new Restaurant(name, location, openingTime, closingTime);

        restaurant.addToMenu("aa", 234);
        restaurant.addToMenu("bb", 312);
        restaurant.addToMenu("cc", 442);

        validItemNames = restaurant.getMenu().stream().map(Item::getName).collect(Collectors.toList());
        invalidItemNames = restaurant.getMenu().stream().map(Item::getName).collect(Collectors.toList());
        invalidItemNames.add("Invalid");


        System.out.println("at before each test");
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(desiredTime);
        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(unDesiredTime);
        assertFalse(mockRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Item<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void get_total_order_cost_should_should_throw_exception_if_any_item_from_order_not_found(){

        assertThrows(itemNotFoundException.class, () -> {
            restaurant.getTotalOrderCost(invalidItemNames);
        });

    }

    @Test
    public void get_total_order_value_should_should_throw_exception_if_any_item_from_order_not_found(){


        assertThrows(itemNotFoundException.class, () -> {
            restaurant.getTotalOrderValue(invalidItemNames);
        });

    }

    @Test
    public void get_total_order_value_should_should_return_order_value_given_valid_items() throws itemNotFoundException {

        String validOrderValue = restaurant.getTotalOrderValue(validItemNames);

        assertThat(validOrderValue, containsString(restaurant.getOrderValuePreString()));
        assertThat(validOrderValue, containsString(restaurant.getTotalOrderValue(validItemNames)));

    }
    //<<<<<<<<<<<<<<<<<<<<<<<Item>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}