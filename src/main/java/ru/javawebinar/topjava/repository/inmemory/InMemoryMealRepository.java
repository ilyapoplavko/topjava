package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return meal.getUserId() == userId ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.getOrDefault(id, null) != null &&
                (repository.get(id).getUserId() == userId && repository.remove(id) != null);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.getOrDefault(id, null) == null ? null :
                (repository.get(id).getUserId() == userId ? repository.get(id) : null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().
                filter(meal -> meal.getUserId() == userId).
                sorted((meal1, meal2) -> (meal2.getDateTime().compareTo(meal1.getDateTime()))).
                collect(Collectors.toList());
    }
}

