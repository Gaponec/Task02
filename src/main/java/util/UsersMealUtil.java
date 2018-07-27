package util;

import model.UserMeal;
import model.UserMealWithExceed;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UsersMealUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),

                new UserMeal(LocalDateTime.of(2018, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2018, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        for (UserMealWithExceed user : list) {
            System.out.println(user.toString());
        }
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> users = new ArrayList<>();

        HashMap<LocalDate, Integer> myMap = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            if (myMap.containsKey(userMeal.getDateTime().toLocalDate())) {
                Integer temp = myMap.get(userMeal.getDateTime().toLocalDate());
                temp += userMeal.getCalories();

                myMap.put(userMeal.getDateTime().toLocalDate(), temp);
            } else {
                myMap.put(userMeal.getDateTime().toLocalDate(), userMeal.getCalories());
            }
        }

        for (UserMeal userMeal : mealList) {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                boolean exceed = myMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
                users.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), exceed));
            }
        }

        return users;
    }

    private static int compareLDTToLT(LocalTime localTime, LocalDateTime localDateTime) {
        LocalTime temp = localDateTime.toLocalTime();

        return localTime.compareTo(temp);
    }
}
